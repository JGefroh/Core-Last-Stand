package com.jgefroh.systems;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.DecayInfoPack;



/**
 * Handles the removal of bodies after a certain time period.
 * Date: 31MAY13
 * @author Joseph Gefroh
 */
public class DecaySystem implements ISystem
{
	//TODO: Make this create bodies, too.
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	
	//////////
	// INIT
	//////////
	public DecaySystem(final Core core)
	{
		this.core = core;
		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		this.isRunning = true;
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
			decay(now);
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
	}
	//////////
	// SYSTEM METHODS
	//////////
	private void decay(final long now)
	{
		Iterator<DecayInfoPack> infoPacks = 
				core.getInfoPacksOfType(DecayInfoPack.class);
		while(infoPacks.hasNext())
		{
			DecayInfoPack pack = infoPacks.next();

			if(now-pack.getLastUpdateTime()>=pack.getTimeUntilDecay()&&pack.getLastUpdateTime()!=0)
			{	
				core.removeEntity(pack.getOwner());
			}
			else if(pack.getLastUpdateTime()==0)
			{
				pack.setLastUpdateTime(now);
			}
		}
	}
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebug(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
}
