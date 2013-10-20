package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Vector;
import com.jgefroh.infopacks.MovementInfoPack;
import com.jgefroh.tests.Benchmark;


/**
 * System that handles movement and repositioning of entities.
 * @author Joseph Gefroh
 */
public class TransformSystem extends AbstractSystem {
	
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
	
	private Benchmark bench = new Benchmark(this.getClass().getName(), false);

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public TransformSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);
	}

	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void work(final long now) {
		long startTime = System.nanoTime();
		int numEntities = moveAll(now);			
		bench.benchmark(System.nanoTime()-startTime, numEntities);
	}
	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	/**
	 * Moves all entities that are requesting moves based on their velocities.
	 */
	public int moveAll(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(MovementInfoPack.class);
		MovementInfoPack pack = core.getInfoPackOfType(MovementInfoPack.class);
		
		int numEntities = 0;
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			if (now - pack.getLastUpdated() >= pack.getInterval()) {
				move(pack);
				pack.setLastUpdated(now);
			}
			numEntities++;
		}
		return numEntities;
	}

	private void move(final MovementInfoPack each) {
		Vector totalV = each.getTotalMovementVector();
		each.setXPos((each.getXPos() + totalV.getVX()));
		each.setYPos((each.getYPos() + totalV.getVY()));
		//Translate object by total vector amount.
		if (!each.isContinuous()) {
			each.setTotalMovementVector(new Vector());
		}
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
