package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.SlaveInfoPack;

/**
 * Slaves entities to a master entity.
 * @author Joseph Gefroh
 */
public class SlaveSystem implements ISystem
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
	public SlaveSystem(final Core core)
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
		core.setInterested(this, "REQUEST_SHIELD");
	}
	
	@Override
	public void start() 
	{
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}

	@Override
	public void work(final long now)
	{
		if(isRunning)
		{
			destroyFreed();
			positionSlaves();
		}
	}

	@Override
	public void stop()
	{	
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
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
		LOGGER.log(Level.FINE, "Wait interval set to: " + waitTime + " ms");
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
	/**
	 * Destroys all slave entities without masters.
	 */
	public void destroyFreed()
	{
		Iterator<SlaveInfoPack> packs
			= core.getInfoPacksOfType(SlaveInfoPack.class);
		
		while(packs.hasNext())
		{
			SlaveInfoPack pack = packs.next();
			
			if(pack.getMaster()==null)
			{
				core.removeEntity(pack.getOwner());
			}
		}
	}
	
	/**
	 * Positions all slave entities to be placed on top of their masters.
	 */
	public void positionSlaves()
	{
		
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
