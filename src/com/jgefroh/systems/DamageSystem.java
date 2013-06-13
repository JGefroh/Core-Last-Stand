package com.jgefroh.systems;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.DamageInfoPack;
import com.jgefroh.infopacks.HealthInfoPack;


/**
 * This system is in charge of dealing damage.
 * @author Joseph Gefroh
 */
public class DamageSystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	@SuppressWarnings("unused")
	private boolean isRunning;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.OFF;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public DamageSystem(final Core core)
	{
		this.core = core;
		init();
	}

	/////////
	// ISYSTEM INTERFACE
	/////////
	@Override
	public void init()
	{
		LOGGER.log(Level.FINE, "Setting system values to default.");
		isRunning = true;		
		core.setInterested(this, "BULLET_HIT");
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

		if(id.equals("BULLET_HIT"))
		{
			calculateDamage(message);
		}
	}
	/////////
	// SYSTEM METHODS
	/////////

	private void calculateDamage(final String[] message)
	{
		if(message.length>=2)
		{
			String bulletID = message[0];
			String victimID = message[1];
			
			HealthInfoPack victimHealth 
				= core.getInfoPackFrom(victimID, HealthInfoPack.class);
			
			DamageInfoPack sourceDamage
				= core.getInfoPackFrom(bulletID, DamageInfoPack.class);
			
			if(victimHealth!=null&&sourceDamage!=null)
			{				
				victimHealth.setCurHealth(victimHealth.getCurHealth()
						-sourceDamage.getDamage());
			}
			
			core.removeEntity(bulletID);
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
