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
import com.jgefroh.infopacks.AIInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_FIRE;
import com.jgefroh.messages.DefaultMessage.EVENT_ENTITY_WITHIN_BOUNDS;
import com.jgefroh.messages.DefaultMessage.EVENT_IN_RANGE_OF_TARGET;


/**
 * @author Joseph Gefroh
 */
public class AISystem extends AbstractSystem {

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
	 * Create a new instance of this System.
	 * @param core	a reference to the Core controlling this system
	 */
	public AISystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);
		init();
	}

	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {		
		LOGGER.log(Level.FINE, "Setting system values to default.");
		core.setInterested(this, DefaultMessage.EVENT_IN_RANGE_OF_TARGET);
		core.setInterested(this, DefaultMessage.EVENT_ENTITY_WITHIN_BOUNDS);

	}

	@Override
	public void work(final long now) {
		attack();
	}
	
	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch(type) {
				case EVENT_IN_RANGE_OF_TARGET:
					setInRange(message);
					break;
				case EVENT_ENTITY_WITHIN_BOUNDS:
					setActive(message);
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
	 * Goes through all AI entities and attempts an attack.
	 */
	private int attack() {
		Iterator<IEntity> packs = core.getEntitiesWithPack(AIInfoPack.class);
		AIInfoPack pack = core.getInfoPackOfType(AIInfoPack.class);
		
		int numEntities = 0;
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if(pack.isInRangeOfTarget() && pack.isActive()) {				
				rollForAttack(pack);
			}
			numEntities++;
		}
		return numEntities;
	}
	
	/**
	 * Checks to see if the unit gets to attack this turn.
	 * @param each	the AIInfoPack of the entity that is rolling
	 */
	private void rollForAttack(final AIInfoPack each) {
		Map<IPayload, String> data = new HashMap<IPayload, String>();
		data.put(COMMAND_FIRE.ENTITY_ID, each.getEntity().getID());
		data.put(COMMAND_FIRE.IS_FIRE_REQUESTED, true + "");
		core.send(DefaultMessage.COMMAND_FIRE, data);
	}

	
	//////////////////////////////////////////////////
	// Message
	//////////////////////////////////////////////////
	
	/**
	 * Sets the flag that indicates the entity is in range of its target.
	 * @param data the data
	 */
	private void setInRange(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		
		AIInfoPack pack 
			= core.getInfoPackFrom(data.get(EVENT_IN_RANGE_OF_TARGET.SOURCE_ENTITY_ID), AIInfoPack.class);
		
		if(pack!=null) {
			boolean isInRange = Boolean.parseBoolean(data.get(EVENT_IN_RANGE_OF_TARGET.IS_IN_RANGE));
			if(pack.isInRangeOfTarget() != isInRange) {
				pack.setInRangeOfTarget(isInRange);
			}
		}
	}
	
	/**
	 * Sets the flag that indicates the entity should be processed by the AI.
	 * @param data the data
	 */
	private void setActive(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
	
		AIInfoPack pack 
			= core.getInfoPackFrom(data.get(EVENT_ENTITY_WITHIN_BOUNDS.ENTITY_ID), AIInfoPack.class);

		if(pack!=null) {				
			boolean isActive = Boolean.parseBoolean(data.get(EVENT_ENTITY_WITHIN_BOUNDS.IS_WITHIN_BOUNDS));
			pack.setActive(isActive);
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
