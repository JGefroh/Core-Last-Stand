package com.jgefroh.systems;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;


/**
 * Spawns enemies randomly to create an infinite wave.
 * @author Joseph Gefroh
 */
public class EnemySpawnSystem implements ISystem
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
	public EnemySpawnSystem(final Core core)
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
			decideSpawns();
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
	private void decideSpawns()
	{
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		Random r = new Random();
		for(int x=32;x<600;x+=64)
		{
			for(int y=64;y<700;y+=64)
			{
				double chance = r.nextInt(100);

				if(chance<10)
				{
					ecs.createEnemy(x, y, r.nextInt(10));
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
