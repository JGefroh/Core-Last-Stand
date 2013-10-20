package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.MaxRangeInfoPack;


/**
 * Destroys entities that move a certain distance away from their origin.
 * @author Joseph Gefroh
 */
public class MaxRangeSystem extends AbstractSystem {
	
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
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public MaxRangeSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void work(final long now) {
		checkPassed(now);
	}

	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	public void checkPassed(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(MaxRangeInfoPack.class);
		MaxRangeInfoPack pack = core.getInfoPackOfType(MaxRangeInfoPack.class);
		
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			//Step 1: get the amount it has traveled since last check
			double x = pack.getLastXPos();
			double y = pack.getLastYPos();

			double diffX = x - pack.getXPos();
			double diffY = y - pack.getYPos();

			double distance = Math.sqrt(diffX * diffX + diffY * diffY);

			//Step 2: Add the amount traveled to the total
			pack.setLastXPos(pack.getXPos());
			pack.setLastYPos(pack.getYPos());
			pack.setDistanceTraveled(pack.getDistanceTraveled() + distance);

			//Step 3: Check the amount it has traveled against the max
			//range
			if (pack.getDistanceTraveled() > pack.getMaxRange()) {
				core.removeEntity(pack.getEntity());
			}
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
