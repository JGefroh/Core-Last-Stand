package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.HealthInfoPack;

/**
 * This system goes through all entities with health and removes dead entities.
 * @author Joseph Gefroh
 */
public class HealthMonitorSystem implements ISystem
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
	public HealthMonitorSystem(final Core core)
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
		core.setInterested(this, "REQUEST_HEALTH");
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
			checkHealth();
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
		if(id.equals("REQUEST_HEALTH"))
		{
			requestHealth(message);
		}

	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Checks the health of all entities and destroy those below 0 health.
	 */
	private void checkHealth()
	{
		Iterator<HealthInfoPack> packs 
		= core.getInfoPacksOfType(HealthInfoPack.class);
		while(packs.hasNext())
		{
			HealthInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				if(each.getCurHealth()<=0)
				{
					core.send("DESTROYING_ENTITY", each.getOwner().getID());
					LOGGER.log(Level.FINE, 
							each.getOwner().getName() + "(" + each.getOwner().getID()
									+ ") destroyed.");
					if(each.getOwner().getName().equalsIgnoreCase("PLAYER"))
					{
						core.removeAllEntities();
						core.getSystem(EntityCreationSystem.class).createPlayer(32, 384);
					}
					core.removeEntity(each.getOwner());
				}
			}
		}
	}
	
	
	/**
	 * Sends a health update of the given entity id
	 * @param message	the health of the entity
	 */
	private void requestHealth(final String[] message)
	{
		if(message.length>=1)
		{
			HealthInfoPack pack 
				= core.getInfoPackFrom(message[0], HealthInfoPack.class);
			
			if(pack!=null)
			{
				core.send("HEALTH_UPDATE", pack.getOwner().getID(), 
							pack.getCurHealth() + "");
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
