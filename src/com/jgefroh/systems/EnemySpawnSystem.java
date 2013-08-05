package com.jgefroh.systems;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;


/**
 * Spawns enemies randomly to create an infinite wave.
 * @author Joseph Gefroh
 */
public class EnemySpawnSystem extends AbstractSystem
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
	public void work(final long now)
	{
		if(isRunning())
		{
			decideSpawns(now);
		}
	}
	//////////
	// SYSTEM METHODS
	//////////
	private void decideSpawns(final long now)
	{
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		Random r = new Random();
		int numEnemies = 0;
		for(int x=32;x<600;x+=32)
		{
			for(int y=64;y<500;y+=64)
			{
				double chance = r.nextInt(100);

				if(chance<10)
				{
					ecs.createEnemy(x, y, r.nextInt(11));
					numEnemies++;
				}
			}
		}
		LOGGER.log(Level.FINER, "Spawned " + numEnemies +" enemies.");
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
