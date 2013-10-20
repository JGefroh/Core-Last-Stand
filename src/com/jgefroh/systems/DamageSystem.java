package com.jgefroh.systems;


import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.DamageInfoPack;
import com.jgefroh.infopacks.HealthInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_DAMAGE;


/**
 * This system is in charge of dealing damage.
 * @author Joseph Gefroh
 */
public class DamageSystem extends AbstractSystem {

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
	public DamageSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}

	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		setDebugLevel(this.debugLevel);
		LOGGER.log(Level.FINE, "Setting system values to default.");
		core.setInterested(this, DefaultMessage.COMMAND_DAMAGE);
	}
	
	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
			case COMMAND_DAMAGE:
				calculateDamage(message);
				break;
			default:
				break;
			}
		}
	}	
	
	
	//////////////////////////////////////////////////
	// Message
	//////////////////////////////////////////////////
	
	/**
	 * Deals damage to an entity based on source's damage value.
	 * @param message	[0] contains the entityID of the bullet
	 * 					[1] contains the entityID of the victim
	 */
	private void calculateDamage(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		
		String sourceID = data.get(COMMAND_DAMAGE.SOURCE_ENTITY_ID);
		String victimID = data.get(COMMAND_DAMAGE.VICTIM_ENTITY_ID);
		
		HealthInfoPack victimHealth 
			= core.getInfoPackFrom(victimID, HealthInfoPack.class);
		
		DamageInfoPack sourceDamage
			= core.getInfoPackFrom(sourceID, DamageInfoPack.class);
		
		if (victimHealth != null && sourceDamage != null) {				
			victimHealth.setCurHealth(victimHealth.getCurHealth()
					-sourceDamage.getDamage());
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
