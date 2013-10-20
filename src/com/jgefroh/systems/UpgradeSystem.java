package com.jgefroh.systems;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Upgrade;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_ADJUST_SCORE;
import com.jgefroh.messages.DefaultMessage.COMMAND_BUY;
import com.jgefroh.messages.DefaultMessage.COMMAND_INQUIRE;
import com.jgefroh.messages.DefaultMessage.DATA_SCORE;
import com.jgefroh.messages.DefaultMessage.DATA_UPGRADE_DESC;
import com.jgefroh.messages.DefaultMessage.EVENT_PLAYER_CREATED;


/**
 * @author Joseph Gefroh
 */
public class UpgradeSystem extends AbstractSystem {
	
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
	
	private int score;
	
	private String playerID = "-1";
	
	private HashMap<String, Upgrade> upgrades;
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public UpgradeSystem(final Core core) {
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
		
		core.setInterested(this, DefaultMessage.COMMAND_BUY);
		core.setInterested(this, DefaultMessage.DATA_SCORE);
		core.setInterested(this, DefaultMessage.EVENT_PLAYER_CREATED);
		core.setInterested(this, DefaultMessage.COMMAND_INQUIRE);
		
		upgrades = new HashMap<String, Upgrade>();
		
		upgrades.put("1", 
				new Upgrade("Repair", "Repairs your ship for 50HP. \n \n Costs 100 points.", 100, DefaultMessage.COMMAND_CHANGE_HEALTH, 50));
		
		upgrades.put("2", 
				new Upgrade("Hull+", "Increases your maximum HP by 25 HP. \n \n Costs 200 points.", 200, DefaultMessage.COMMAND_CHANGE_HEALTH_MAX, 25));
		
		upgrades.put("3", 
				new Upgrade("Batteries", "Increases your maximum shield by 10. \n \n Costs 500 points.", 500, DefaultMessage.COMMAND_CHANGE_SHIELD_MAX, 10));

		upgrades.put("7", 
				new Upgrade("DEV_HEALTH", "Repairs your ship for 9001 HP. \n \n Costs 0 points.", 0, DefaultMessage.COMMAND_CHANGE_HEALTH, 9001));
		
		upgrades.put("8", 
				new Upgrade("DEV_SHIELD", "Increases your maximum shield by 9001. \n \n Costs 0 points.", 0, DefaultMessage.COMMAND_CHANGE_SHIELD_MAX, 9001));
		
		upgrades.put("9", 
				new Upgrade("DEV_HEALTH_MAX", "Increases your maximum health by 9001 HP. \n \n Costs 0 points.", 0, DefaultMessage.COMMAND_CHANGE_HEALTH_MAX, 9001));
		
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case COMMAND_BUY:
					buy(message);
				case COMMAND_INQUIRE:
					processInquiry(message);
					break;
				case DATA_SCORE:
					processScore(message);
					break;
				case EVENT_PLAYER_CREATED:
					this.playerID = message.get(EVENT_PLAYER_CREATED.PLAYER_ENTITY_ID);
					break;
				default:
					break;
			}
		}
	}
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	private void buy(final Map<IPayload, String> data) {
		core.send(DefaultMessage.REQUEST_SCORE, null);
		Upgrade upgrade = upgrades.get(data.get(COMMAND_BUY.PRODUCT_ID));
		if (upgrade != null) {
			if (this.score >= upgrade.getCost()) {
				Map<IPayload, String> parameters = new HashMap<IPayload, String>();
				parameters.put(COMMAND_ADJUST_SCORE.AMOUNT, -upgrade.getCost() + "");
				core.send(DefaultMessage.COMMAND_ADJUST_SCORE, parameters);

				parameters = new HashMap<IPayload, String>();
				//TODO: Resolve purchase issue.
			}
		}
	}
	
	private void processInquiry(final Map<IPayload, String> data)  {
		if (data == null || data.size() < 1) {
			return;
		}
		Upgrade upgrade = upgrades.get(data.get(COMMAND_INQUIRE.INQUIRY_ID));

		Map<IPayload, String> parameters = new HashMap<IPayload, String>();
		String desc = (upgrade == null) ? "" : upgrade.getDesc();
		parameters.put(DATA_UPGRADE_DESC.DESC, desc);
		core.send(DefaultMessage.DATA_UPGRADE_DESC, parameters);
	}

	private void processScore(final Map<IPayload, String> data) {
		if (data == null || data.size() < 1) {
			return;
		}

		try {
			this.score = Integer.parseInt(data.get(DATA_SCORE.SCORE_CUR));
		} 
		catch (NumberFormatException e) {
			LOGGER.log(Level.WARNING, "Unable to update score.");
		}
	}
	
	
	//////////////////////////////////////////////////
	// Debug
	//////////////////////////////////////////////////
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level the Level to set
	 */
	public void setDebugLevel(final Level level) {
		this.LOGGER.setLevel(level);
	}
}
