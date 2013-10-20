package com.jgefroh.systems;


import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AbilityInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_USE_ABILITY;

/**
 * @author Joseph Gefroh
 */
public class AbilitySystem extends AbstractSystem {
	
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
	public AbilitySystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	

	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		core.setInterested(this, DefaultMessage.COMMAND_USE_ABILITY);
	}

	@Override
	public void work(final long now) {
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		if(messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch(type) {
				case COMMAND_USE_ABILITY:
					processAbility(message);
					break;
				default:
					break;
			}	
		}
	}
	
	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	private void processAbility(final Map<IPayload, String> data) {
		if (data == null || data.size() < 1) {
			return;
		}
		
		String entityID = data.get(COMMAND_USE_ABILITY.ENTITY_ID);
		
		AbilityInfoPack pack = core.getInfoPackFrom(entityID, AbilityInfoPack.class);
		
		if (pack != null) {
			useAbility(pack);
		}
	}
	
	private void useAbility(final AbilityInfoPack pack) {
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
