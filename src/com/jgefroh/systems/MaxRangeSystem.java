package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.MaxRangeInfoPack;


/**
 * Destroys entities that move a certain distance away from their origin.
 * @author Joseph Gefroh
 */
public class MaxRangeSystem extends AbstractSystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public MaxRangeSystem(final Core core)
	{
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void work(final long now)
	{
		checkPassed(now);		
	}

	//////////
	// SYSTEM METHODS
	//////////
	public void checkPassed(final long now)
	{
		Iterator<MaxRangeInfoPack> packs 
			= core.getInfoPacksOfType(MaxRangeInfoPack.class);
		
		while(packs.hasNext())
		{
			MaxRangeInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				
				//Step 1: get the amount it has traveled since last  check
				double x = each.getLastXPos();
				double y = each.getLastYPos();
				
				double diffX = x-each.getXPos();
				double diffY = y-each.getYPos();
				
				double distance = Math.sqrt(diffX*diffX+diffY*diffY);
				
				//Step 2: Add the amount traveled to the total
				each.setLastXPos(each.getXPos());
				each.setLastYPos(each.getYPos());
				each.setDistanceTraveled(each.getDistanceTraveled()+distance);
				
				//Step 3: Check the amount it has traveled against the max range
				if(each.getDistanceTraveled()>each.getMaxRange())
				{
					LOGGER.log(Level.FINE, 
							each.getOwner().getName() + "(" 
									+ each.getOwner().getID()
									+ ") destroyed (reached max range).");
					core.removeEntity(each.getOwner());
				}
			}
		}
	}
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
	
}
