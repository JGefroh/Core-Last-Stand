package com.jgefroh.systems;


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
import com.jgefroh.infopacks.HealthBarInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.EVENT_DESTROYING_ENTITY;

/**
 * Controls the display of health bars above entities.
 * @author Joseph Gefroh
 */
public class GUIHealthBarSystem extends AbstractSystem {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
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
	public GUIHealthBarSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	@Override
	public void init() {
		core.setInterested(this, DefaultMessage.EVENT_DESTROYING_ENTITY);
	}

	@Override
	public void work(final long now) {
		updateHealthBars();
	}
	
	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case EVENT_DESTROYING_ENTITY:
					destroyHealthBar(message);
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
	 * Updates the position and display of all health bars.
	 */
	private void updateHealthBars() {
		Iterator<IEntity> packs = core.getEntitiesWithPack(HealthBarInfoPack.class);
		HealthBarInfoPack pack = core.getInfoPackOfType(HealthBarInfoPack.class);
		
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			updatePosition(pack);
			updateHealth(pack);		
		}
	}
	
	/**
	 * Updates the position of a specific health bar.
	 * @param each	the InfoPack of the health bar to update
	 */
	private void updatePosition(final HealthBarInfoPack each) {
		double xPos = each.getOwnerXPos();
		double yPos = each.getOwnerYPos();
		
		each.setHealthBarXPos(xPos);
		each.setHealthBarYPos(yPos-32);	//TODO: set to height
	}
	
	/**
	 * Updates the amount of health displayed by the health bar.
	 * @param each	the InfoPack of the health bar to update
	 */
	private void updateHealth(final HealthBarInfoPack each)	{
		int health = each.getHealth();
		each.setHealthBarWidth(health);	//TODO: Normalize to 100% or add layers to avoid super long health bars
	}

	
	//////////////////////////////////////////////////
	// Message
	//////////////////////////////////////////////////
	
	/**
	 * Removes a health bar from the screen when its owner has been destroyed
	 * @param message	[0] contains the entityID of the destroyed owner
	 */
	private void destroyHealthBar(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		
		HealthBarInfoPack pack 
			= core.getInfoPackFrom(data.get(EVENT_DESTROYING_ENTITY.ENTITY_ID), HealthBarInfoPack.class);
		
		if (pack != null) {
			core.removeEntity(pack.getHealthBar());
		}
	}

	
	//////////////////////////////////////////////////
	// Debug
	//////////////////////////////////////////////////
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level) {
		this.LOGGER.setLevel(level);
	}
	
}
