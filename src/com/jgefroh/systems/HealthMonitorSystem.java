package com.jgefroh.systems;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.HealthInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_CHANGE_HEALTH;
import com.jgefroh.messages.DefaultMessage.COMMAND_CHANGE_HEALTH_MAX;
import com.jgefroh.messages.DefaultMessage.DATA_HEALTH;
import com.jgefroh.messages.DefaultMessage.EVENT_DESTROYING_ENTITY;
import com.jgefroh.messages.DefaultMessage.REQUEST_HEALTH;

/**
 * This system goes through all entities with health and removes dead entities.
 * @author Joseph Gefroh
 */
public class HealthMonitorSystem extends AbstractSystem {

	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.OFF;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	

	//////////////////////////////////////////////////
	// Initialize 
	//////////////////////////////////////////////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public HealthMonitorSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	

	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		core.setInterested(this, DefaultMessage.REQUEST_HEALTH);
		core.setInterested(this, DefaultMessage.COMMAND_CHANGE_HEALTH);
		core.setInterested(this, DefaultMessage.COMMAND_CHANGE_HEALTH_MAX);
	}


	@Override
	public void work(final long now) {
		checkHealth();
	}
	
	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case REQUEST_HEALTH:
					requestHealth(message);
					break;
				case COMMAND_CHANGE_HEALTH:
					changeHealth(message);
					break;
				case COMMAND_CHANGE_HEALTH_MAX:
					changeHealthMax(message);
					break;
				default:
					break;
			}
		}
	}

	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	/**
	 * Checks the health of all entities and destroy those below 0 health.
	 */
	private void checkHealth() {
		Iterator<IEntity> packs = core.getEntitiesWithPack(HealthInfoPack.class);
		HealthInfoPack pack = core.getInfoPackOfType(HealthInfoPack.class);
		
		while (packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if (pack.getCurHealth() <= 0) {
				Map<IPayload, String> parameters = new HashMap<IPayload, String>();
				parameters.put(EVENT_DESTROYING_ENTITY.ENTITY_ID, pack.getEntity().getID());
				parameters.put(EVENT_DESTROYING_ENTITY.REASON, "NO_HEALTH");
				core.send(DefaultMessage.EVENT_DESTROYING_ENTITY, parameters);
				
				if (pack.getEntity().getName().equalsIgnoreCase("PLAYER")) {
					core.removeAllEntities();
					core.send(DefaultMessage.COMMAND_RESET_GAME, null);
					core.getSystem(EntityCreationSystem.class).createPlayer(32,
							384);
				}
				core.removeEntity(pack.getEntity());
			}
		}
	}

	
	//////////////////////////////////////////////////
	// Messages
	//////////////////////////////////////////////////
	
	/**
	 * Sends a health update of the given entity id
	 * @param message	the health of the entity
	 */
	private void requestHealth(final Map<IPayload, String> data) {
		if (data == null || data.size() < 1) {
			return;
		}
		
		HealthInfoPack pack 
				= core.getInfoPackFrom(data.get(REQUEST_HEALTH.ENTITY_ID), HealthInfoPack.class);
			
		if (pack!=null) {
			Map<IPayload, String> parameters = new HashMap<IPayload, String>();
			parameters.put(DATA_HEALTH.ENTITY_ID, pack.getEntity().getID());
			parameters.put(DATA_HEALTH.HEALTH_CUR, pack.getCurHealth() + "");
			parameters.put(DATA_HEALTH.HEALTH_MAX, pack.getMaxHealth() + "");
			core.send(DefaultMessage.DATA_HEALTH, parameters);
		}
	}
	
	private void changeHealth(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
	
		HealthInfoPack pack 
			= core.getInfoPackFrom(data.get(COMMAND_CHANGE_HEALTH.ENTITY_ID), HealthInfoPack.class);
		
		int amount = 0;
		try {				
			amount = Integer.parseInt(data.get(COMMAND_CHANGE_HEALTH.AMOUNT));
		}
		catch(NumberFormatException e) {
			LOGGER.log(Level.WARNING, "Unable to change health.");
		}

		if (pack != null) {
			if (pack.getCurHealth() + amount <= pack.getMaxHealth()) {
				pack.setCurHealth(pack.getCurHealth() + amount);
			} else {
				pack.setCurHealth(pack.getMaxHealth());
			}
		}

	}
	
	private void changeHealthMax(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		HealthInfoPack pack 
			= core.getInfoPackFrom(data.get(COMMAND_CHANGE_HEALTH_MAX.ENTITY_ID), HealthInfoPack.class);
	
		int amount = 0;
		try {				
			amount = Integer.parseInt(data.get(COMMAND_CHANGE_HEALTH_MAX.AMOUNT));
		}
		catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING, "Unable to change health max.");
		}

		if (pack != null) {
			pack.setMaxHealth(pack.getMaxHealth() + amount);
		}
	}
	

	//////////////////////////////////////////////////
	// Debug
	//////////////////////////////////////////////////
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
	
}
