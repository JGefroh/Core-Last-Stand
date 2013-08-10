package com.jgefroh.systems;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.messages.Message;



/**
 * This is a temporary system used to destroy entities.
 * @author Joseph Gefroh
 */
public class EntityDestructionSystem extends AbstractSystem
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
	 * Create a new EntityCreationSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public EntityDestructionSystem(final Core core)
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
		core.setInterested(this, Message.BEARING_TO_MOUSE);
	}

	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
		Message msgID = Message.valueOf(id);

		switch(msgID)
		{
			case REQUEST_DESTROY:
				processDestroyRequest(message);
				break;
		}
	}
	//////////
	// SYSTEM METHODS
	//////////
	
	private void processDestroyRequest(final String[] message)
	{
		if(message.length>=1)
		{
			String entityID = message[0];
			
			IEntity entity = core.getEntityWithID(entityID);
			
			if(entity.getName().equalsIgnoreCase("BULLET"))
			{
				//EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
				//ecs.addToPool(entity);
				core.removeEntity(entityID);

			}
			else
			{
				core.removeEntity(entityID);
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
