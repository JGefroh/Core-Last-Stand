package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.MaxRangeInfoPack;


/**
 * Destroys entities that move a certain distance away from their origin.
 * @author Joseph Gefroh
 */
public class MaxRangeSystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
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
	public void init()
	{
		isRunning = true;
	}

	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "System started.");
		this.isRunning = true;
	}

	@Override
	public void work(final long now)
	{
		if(this.isRunning)
		{
			checkPassed(now);		
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		this.isRunning = false;
	}
	
	@Override
	public long getWait()
	{
		return this.waitTime;
	}

	@Override
	public long	getLast()
	{
		return this.last;
	}
	
	@Override
	public void setWait(final long waitTime)
	{
		this.waitTime = waitTime;
	}
	
	@Override
	public void setLast(final long last)
	{
		this.last = last;
	}
	
	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
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
				double x = each.getInitialXPos();
				double y = each.getInitialYPos();
				
				double diffX = x-each.getXPos();
				double diffY = y-each.getYPos();
				
				double distance = Math.sqrt(diffX*diffX+diffY*diffY);
				if(distance>each.getMaxRange())
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
