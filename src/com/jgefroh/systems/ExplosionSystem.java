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
import com.jgefroh.infopacks.ExplosionInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_CREATE;
import com.jgefroh.messages.DefaultMessage.COMMAND_DAMAGE;
import com.jgefroh.messages.DefaultMessage.COMMAND_DETONATE;
import com.jgefroh.messages.DefaultMessage.EVENT_EXPLOSION_CONTACT;



/**
 * @author Joseph Gefroh
 */
public class ExplosionSystem extends AbstractSystem {

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
	public ExplosionSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	

	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		core.setInterested(this, DefaultMessage.COMMAND_DETONATE);
		core.setInterested(this, DefaultMessage.EVENT_EXPLOSION_CONTACT);
	}

	
	@Override
	public void work(final long now) {
		updateExplosions(now);
	}
	
	
	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
			case COMMAND_DETONATE:
				processDetonate(message);
				break;
			case EVENT_EXPLOSION_CONTACT:
				processContact(message);
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
	 * Increases the size of explosions at certain increments.
	 * @param now	the current time
	 */
	private void updateExplosions(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(ExplosionInfoPack.class);
		ExplosionInfoPack pack = core.getInfoPackOfType(ExplosionInfoPack.class);
		
		while (packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if (now - pack.getLastUpdated() >= pack.getUpdateInterval() 
					&& pack.getLastUpdated() != 0) {
				//If time to update...
				
				if (pack.getWidth() < pack.getWidthMax()) {
					//Increase width by X amount
					pack.setWidth(pack.getWidth() + pack.getWidthInc());
				}

				if (pack.getHeight() < pack.getHeightMax()) {
					//Increase height by Y amount
					pack.setHeight(pack.getHeight() + pack.getHeightInc());
				}
				pack.setLastUpdated(now);
			}
			else if (pack.getLastUpdated() == 0) {
				pack.setLastUpdated(now);
			}
		}
	}
	

	//////////////////////////////////////////////////
	// Messages
	//////////////////////////////////////////////////
	
	/**
	 * Turns a bullet into an explosion using the ECS.
	 * @param message	[0] contains the ID of the bullet/shot.
	 */
	private void processDetonate(final Map<IPayload, String> data) {
		if (data == null || data.size() < 1) {
			return;
		}
		
		Map<IPayload, String> parameters = new HashMap<IPayload, String>();
		parameters.put(COMMAND_CREATE.TYPE_TO_CREATE, "EXPLOSION");
		parameters.put(COMMAND_CREATE.OWNER_ID, data.get(COMMAND_DETONATE.ENTITY_ID));
		core.send(DefaultMessage.COMMAND_CREATE, parameters);
	}
	
	/**
	 * Deals damage to entities caught in the explosion if allowed.
	 * @param message	[0] Contains the ID of the explosion
	 * 					[1] Contains the ID of the entity in the explosion
	 */
	private void processContact(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		
		String sourceID = data.get(EVENT_EXPLOSION_CONTACT.SOURCE_ID);
		String victimID = data.get(EVENT_EXPLOSION_CONTACT.VICTIM_ID);
		ExplosionInfoPack pack = core.getInfoPackFrom(sourceID, ExplosionInfoPack.class);
			
		if (pack!=null) {
			int numHits = pack.getNumHits(victimID);
			
			if (numHits == 0) {
				//If the entity has not already been damaged by this explosion...
				Map<IPayload, String> parameters = new HashMap<IPayload, String>();
				parameters.put(COMMAND_DAMAGE.SOURCE_ENTITY_ID, sourceID);
				parameters.put(COMMAND_DAMAGE.VICTIM_ENTITY_ID, victimID);
				core.send(DefaultMessage.COMMAND_DAMAGE, parameters);
				pack.setNumHits(victimID, numHits + 1);
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
	public void setDebugLevel(final Level level) {
		this.LOGGER.setLevel(level);
	}
}
