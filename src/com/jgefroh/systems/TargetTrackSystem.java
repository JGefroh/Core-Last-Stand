package com.jgefroh.systems;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.TargetInfoPack;
import com.jgefroh.infopacks.TargetTrackInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.EVENT_IN_RANGE_OF_TARGET;
import com.jgefroh.tests.Benchmark;


/**
 * Rotates entities towards a specific target.
 * @author Joseph Gefroh
 */
public class TargetTrackSystem extends AbstractSystem {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A reference to the core engine controlling this system.*/
	private Core core;

	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);

	/**Performance benchmark.*/
	private Benchmark bench = new Benchmark(this.getClass().getName(), false);
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this {@code System}.
	 * 
	 * @param core a reference to the Core controlling this system
	 */
	public TargetTrackSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);
	}
	
	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void work(final long now) {
		long startTime = System.nanoTime();
		int numEntities = track(now);
		bench.benchmark(System.nanoTime() - startTime, numEntities);
	}

	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	/**
	 * Selects and tracks targets.
	 */
	private int track(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(TargetTrackInfoPack.class);
		TargetTrackInfoPack pack = core.getInfoPackOfType(TargetTrackInfoPack.class);
		
		int numEntities = 0;
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			TargetInfoPack target = null;
			//Get target if it already has one...
			if (pack.getTarget() == null) {
				target = pickTarget(pack);
			}
			else {				
				target = core.getInfoPackFrom(pack.getTarget().getID(), TargetInfoPack.class);
			}

			if (target != null) {
				//if a target was succesfully chosen...
				pack.setTarget(target.getEntity());
			}

			//If the target is in range...
			if (now - pack.getLastTurned() >= pack.getTurnInterval()
					&& checkTargetInRange(pack, target)) {
				turnTowardsTarget(pack, target);
				pack.setLastTurned(now);
			}
			numEntities++;
		}
		return numEntities;
	}
	
	/**
	 * Selects a target from the available targets.
	 * @param each	the InfoPack of the entity that wants a target
	 * @return		a target if one is found;null otherwise
	 */
	private TargetInfoPack pickTarget(final TargetTrackInfoPack each) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(TargetInfoPack.class);
		TargetInfoPack pack = core.getInfoPackOfType(TargetInfoPack.class);
		
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			//Put target criteria here.
			return pack;
		}
		
		return null;
	}
	
	/**
	 * Turns entities towards their targets.
	 * @param each		the tracker
	 * @param target	the target
	 */
	private void turnTowardsTarget(final TargetTrackInfoPack each, final TargetInfoPack target) {
		if (target != null) {
			double adj = target.getXPos() - each.getXPos();
			double opp = target.getYPos() - each.getYPos();

			//The angle the target is at relative to the source
			double targetAngle = Math.toDegrees(Math.atan2(opp, adj));

			//The angle the source is currently facing
			double sourceAngle = each.getBearing();

			//The smallest difference between the two angles
			double angleDifference;

			//The difference between the two angles on the left side.
			double angleLeft;

			//The difference between the two angles on the right side.
			double angleRight;

			//FLAG: Turn right to reach target angle.
			boolean turnRight = false;

			//Step 1: Normalize angle to target
			targetAngle = targetAngle % 360; //Normalizes angle, [0-360)
			if (targetAngle < 0) {
				//Done because Java's mod operator is weird with negatives.
				targetAngle += 360;
			}

			//Step 2: Get the difference in the two angles from left and right
			if (targetAngle > sourceAngle) {
				angleLeft = targetAngle - sourceAngle; //Diff turning left
				angleRight = 360 + sourceAngle - targetAngle; //Diff turning right
			} else {
				angleLeft = 360 + targetAngle - sourceAngle; //Diff turning left
				angleRight = sourceAngle - targetAngle; //Diff turning right
			}

			//Step 3: Get the shorter of the two paths
			if (angleLeft <= angleRight) {
				//If turning left is faster than turning right
				angleDifference = angleLeft;
				turnRight = false;
			} else {
				angleDifference = angleRight;
				turnRight = true;
			}

			//Step 4: Turn
			if (Math.abs(angleDifference) <= each.getTurnSpeed()) {
				//If the turn is within the turn speed...
				each.setBearing(targetAngle); //face it
			} else {
				if (turnRight == false) {
					//If turning right...
					each.setBearing(sourceAngle + each.getTurnSpeed());
				} else {
					//If turning left
					each.setBearing(sourceAngle - each.getTurnSpeed());
				}
			}
		}
	}
	
	/**
	 * Checks to see if the target is in range.
	 * @param each		the tracker
	 * @param target	the target
	 * @return			true if the target is in range; false otherwise
	 */
	private boolean checkTargetInRange(final TargetTrackInfoPack each,
			final TargetInfoPack target) {
		if (target != null) {
			double adj = target.getXPos() - each.getXPos();
			double opp = target.getYPos() - each.getYPos();
			double dist = Math.sqrt(adj * adj + opp * opp);

			boolean isInRange = (dist <= each.getTargetRange());
			
			Map<IPayload, String> parameters = new HashMap<IPayload, String>();
			parameters.put(EVENT_IN_RANGE_OF_TARGET.SOURCE_ENTITY_ID, each.getEntity().getID());
			parameters.put(EVENT_IN_RANGE_OF_TARGET.IS_IN_RANGE, isInRange + "");
			core.send(DefaultMessage.EVENT_IN_RANGE_OF_TARGET, parameters);
			
			return isInRange;
		}
		return false;
	}

	
	//////////////////////////////////////////////////
	// Debug
	//////////////////////////////////////////////////
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level) {
		this.LOGGER.setLevel(level);
	}
}
