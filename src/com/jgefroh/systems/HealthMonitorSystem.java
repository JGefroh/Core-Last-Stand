package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.HealthInfoPack;
import com.jgefroh.messages.Message;

/**
 * This system goes through all entities with health and removes dead entities.
 * @author Joseph Gefroh
 */
public class HealthMonitorSystem extends AbstractSystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.OFF;
	
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
		core.setInterested(this, Message.REQUEST_HEALTH);
		core.setInterested(this, Message.CHANGE_HEALTH);
		core.setInterested(this, Message.CHANGE_HEALTH_MAX);
	}


	@Override
	public void work(final long now)
	{
		checkHealth();
	}
	
	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
		
		Message msgID = Message.valueOf(id);
		
		switch(msgID)
		{
			case REQUEST_HEALTH:
				requestHealth(message);
				break;
			case CHANGE_HEALTH:
				changeHealth(message);
				break;
			case CHANGE_HEALTH_MAX:
				changeHealthMax(message);
				break;
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
					core.send(Message.DESTROYING_ENTITY, each.getOwner().getID(), "NO_HEALTH");
					LOGGER.log(Level.FINE, 
							each.getOwner().getName() + "(" + each.getOwner().getID()
									+ ") destroyed.");
					if(each.getOwner().getName().equalsIgnoreCase("PLAYER"))
					{
						core.removeAllEntities();
						core.send(Message.RESET_GAME);
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
				core.send(Message.HEALTH_UPDATE, pack.getOwner().getID(), 
							pack.getCurHealth() + "",
							pack.getMaxHealth() + "");
			}
		}
	}
	
	private void changeHealth(final String[] message)
	{
		if(message.length>=2)
		{
			HealthInfoPack pack 
				= core.getInfoPackFrom(message[0], HealthInfoPack.class);
			
			int amount = 0;
			try
			{				
				amount = Integer.parseInt(message[1]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to change health.");
			}

			if(pack!=null)
			{
				if(pack.getCurHealth()+amount<=pack.getMaxHealth())
				{
					pack.setCurHealth(pack.getCurHealth()+amount);
				}
				else
				{
					pack.setCurHealth(pack.getMaxHealth());
				}
				LOGGER.log(Level.FINE, 
						"Added " + amount + " health to: " + message[0]);
			}

		}
	}
	
	
	private void changeHealthMax(final String[] message)
	{
		if(message.length>=2)
		{
			HealthInfoPack pack 
				= core.getInfoPackFrom(message[0], HealthInfoPack.class);
			
			int amount = 0;
			try
			{				
				amount = Integer.parseInt(message[1]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to change health max.");
			}

			if(pack!=null)
			{
				pack.setMaxHealth(pack.getMaxHealth()+amount);
				LOGGER.log(Level.FINE, 
						"Added " + amount + " health to: " + message[0]);
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
