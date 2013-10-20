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
import com.jgefroh.infopacks.ShieldInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_CHANGE_SHIELD_MAX;
import com.jgefroh.messages.DefaultMessage.COMMAND_SHIELD_ACTIVE;
import com.jgefroh.messages.DefaultMessage.DATA_SHIELD;
import com.jgefroh.messages.DefaultMessage.REQUEST_SHIELD;

/**
 * @author Joseph Gefroh
 */
public class ShieldSystem extends AbstractSystem {

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
	public ShieldSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		core.setInterested(this, DefaultMessage.REQUEST_SHIELD);
		core.setInterested(this, DefaultMessage.COMMAND_SHIELD_ACTIVE);
		core.setInterested(this, DefaultMessage.COMMAND_CHANGE_SHIELD_MAX);
	}

	@Override
	public void work(final long now) {
		updateShields(now);
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case COMMAND_SHIELD_ACTIVE:
					setActive(message);
					break;
				case REQUEST_SHIELD:
					requestShield(message);
					break;
				case COMMAND_CHANGE_SHIELD_MAX:
					adjustShieldMax(message);
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
	 * Goes through all entities that can have a shield and updates them.
	 */
	private void updateShields(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(ShieldInfoPack.class);
		ShieldInfoPack pack = core.getInfoPackOfType(ShieldInfoPack.class);
		
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if (pack.isShieldActive() 
					&& pack.getShieldCur() > 0
					&& pack.getShield() != null) {
				//If the shield is requested,
				//and if the shield has energy
				//and if it already exists...
				
				//Move a shield
				moveShield(pack);

				if (checkTimeToDrain(pack, now)) {
					//Drain the shield if enough time has passed.
					decShield(pack);
					pack.setShieldLastDrained(now);
				}
				
				pack.setShieldLastUsed(now);
			} 
			else if (pack.isShieldActive()
					&& pack.getShieldCur() >= pack.getShieldMin()
					&& pack.getShield() == null) {
				// If the shield is requested,
				//and if the shield has enough to create a new shield
				//and if there is no shield...
				
				//Create a shield
				createShield(pack);
				decShield(pack);					
				pack.setShieldLastUsed(now);
				pack.setShieldLastDrained(now);
			}
			else {
				core.removeEntity(pack.getShield());
				pack.setShield(null);	//IMPORTANT
				
				if (checkTimeToCharge(pack, now)) {
					// If the proper time has passed to recharge the shield...
					incShield(pack);
					pack.setShieldLastRecharged(now);
				}
			}
		}
	}
	
	//TODO: Fix shield
	/**
	 * Creates a shield for an Entity without a shield.
	 * @param pack	the ShieldInfoPack of the entity
	 */
	private void createShield(final ShieldInfoPack pack) {
		// TODO: CHANGE
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		pack.setShield(ecs.createShield(pack.getEntity()));
	}
	
	/**
	 * Moves an entity's shield to the correct position (at the entity)
	 * @param pack	the ShieldInfoPack of the entity
	 */
	private void moveShield(final ShieldInfoPack pack) {
		
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();
		
		pack.setShieldXPos(xPos);
		pack.setShieldYPos(yPos);
	}
	
	/**
	 * Decrements the shield's charge according to its values.
	 * @param pack	the ShieldInfoPack of the shield
	 */
	private void decShield(final ShieldInfoPack pack) {
		int shield = pack.getShieldCur() - pack.getShieldDec();
		if (shield >= 0) {
			pack.setShieldCur(shield);
		} 
		else {
			pack.setShieldCur(0);
		}
	}

	/**
	 * Increments the shield's charge according to its values.
	 * @param pack	the ShieldInfoPack of the shield
	 */
	private void incShield(final ShieldInfoPack pack) {
		int shield = pack.getShieldCur() + pack.getShieldInc();
		if (shield <= pack.getShieldMax()) {
			pack.setShieldCur(shield);
		}
		else {
			pack.setShieldCur(pack.getShieldMax());
		}
	}
	
	/**
	 * Checks to see if the right amount of time has passed to charge the shield.
	 * @param pack	the ShieldInfoPack of the shield
	 * @param now	the current time
	 * @return true if the time has passed; false otherwise
	 */
	private boolean checkTimeToCharge(final ShieldInfoPack pack, final long now) {
		return (now - pack.getShieldLastUsed() >= pack.getShieldRechargeDelay()
					&& now - pack.getShieldLastRecharged() >= pack.getShieldRechargeInterval());
	}
	
	/**
	 * Checks to see if the right amount of time has passed to drain the shield.
	 * @param pack	the ShieldInfoPack of the shield
	 * @param now	the current time
	 * @return	true if the time has passed; false otherwise
	 */
	private boolean checkTimeToDrain(final ShieldInfoPack pack, final long now) {
		return (now-pack.getShieldLastDrained()	>= pack.getShieldDrainInterval());
	}
	
	/**
	 * Sets an entity's shield request flag.
	 */
	private void setActive(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		ShieldInfoPack pack = core.getInfoPackFrom(data.get(COMMAND_SHIELD_ACTIVE.ENTITY_ID), ShieldInfoPack.class);
		if (pack != null) {
			pack.setActive(Boolean.parseBoolean(data.get(COMMAND_SHIELD_ACTIVE.IS_ACTIVE)));
		}
	}


	/**
	 * Sends a shield update of the given entity id
	 * @param message	the shield of the entity
	 */
	private void requestShield(final Map<IPayload, String> data) {
		if (data == null || data.size() < 1) {
			return;
		}
			ShieldInfoPack pack 
				= core.getInfoPackFrom(data.get(REQUEST_SHIELD.ENTITY_ID), ShieldInfoPack.class);

		if (pack != null) {
			Map<IPayload, String> parameters = new HashMap<IPayload, String>();
			parameters.put(DATA_SHIELD.ENTITY_ID, pack.getEntity().getID());
			parameters.put(DATA_SHIELD.SHIELD_CUR, pack.getShieldCur() + "");
			parameters.put(DATA_SHIELD.SHIELD_MAX, pack.getShieldMax() + "");
			core.send(DefaultMessage.DATA_SHIELD, parameters);
		}
	}
	
	private void adjustShieldMax(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		ShieldInfoPack pack 
			= core.getInfoPackFrom(data.get(COMMAND_CHANGE_SHIELD_MAX.ENTITY_ID), ShieldInfoPack.class);
		
		if (pack != null) {
			try {
				int amount = Integer.parseInt(data.get(COMMAND_CHANGE_SHIELD_MAX.AMOUNT));
				pack.setShieldMax(pack.getShieldMax() + amount);
			} 
			catch (NumberFormatException e) {
				LOGGER.log(Level.WARNING, "Unable to change shield max.");
			}
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
