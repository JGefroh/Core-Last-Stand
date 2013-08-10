package com.jgefroh.systems;


import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Upgrade;
import com.jgefroh.messages.Message;


/**
 * @author Joseph Gefroh
 */
public class UpgradeSystem extends AbstractSystem
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
	
	private int score;
	
	private String playerID = "-1";
	
	private HashMap<String, Upgrade> upgrades;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public UpgradeSystem(final Core core)
	{
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}

	/////////
	// ISYSTEM INTERFACE
	/////////
	@Override
	public void init()
	{
		LOGGER.log(Level.FINE, "Setting system values to default.");
		
		core.setInterested(this, Message.BUY);
		core.setInterested(this, Message.BUY_0);
		core.setInterested(this, Message.BUY_1);
		core.setInterested(this, Message.BUY_2);
		core.setInterested(this, Message.BUY_3);
		core.setInterested(this, Message.BUY_4);
		core.setInterested(this, Message.BUY_5);
		core.setInterested(this, Message.BUY_6);
		core.setInterested(this, Message.BUY_7);
		core.setInterested(this, Message.BUY_8);
		core.setInterested(this, Message.BUY_9);
		core.setInterested(this, Message.SCORE_UPDATE);
		core.setInterested(this, Message.PLAYER_CREATED);
		core.setInterested(this, Message.INQUIRE);
		
		upgrades = new HashMap<String, Upgrade>();
		
		upgrades.put("1", 
				new Upgrade("Repair", "Repairs your ship for 50HP. \n \n Costs 100 points.", 100, "CHANGE_HEALTH", 50));
		
		upgrades.put("2", 
				new Upgrade("Hull+", "Increases your maximum HP by 25 HP. \n \n Costs 200 points.", 200, "CHANGE_HEALTH_MAX", 25));
		
		upgrades.put("3", 
				new Upgrade("Batteries", "Increases your maximum shield by 10. \n \n Costs 500 points.", 500, "CHANGE_SHIELD_MAX", 10));

		upgrades.put("7", 
				new Upgrade("DEV_HEALTH", "Repairs your ship for 9001 HP. \n \n Costs 0 points.", 0, "CHANGE_HEALTH", 9001));
		
		upgrades.put("8", 
				new Upgrade("DEV_SHIELD", "Increases your maximum shield by 9001. \n \n Costs 0 points.", 0, "CHANGE_SHIELD_MAX", 9001));
		
		upgrades.put("9", 
				new Upgrade("DEV_HEALTH_MAX", "Increases your maximum health by 9001 HP. \n \n Costs 0 points.", 0, "CHANGE_HEALTH_MAX", 9001));
		
	}

	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
		Message msgID = Message.valueOf(id);

		switch(msgID)
		{
		case BUY_0:
			buy("0");
			break;
		case BUY_1:
			buy("1");
			break;
		case BUY_2:
			buy("2");
			break;
		case BUY_3:
			buy("3");
			break;
		case BUY_4:
			buy("4");
			break;
		case BUY_5:
			buy("5");
			break;
		case BUY_6:
			buy("6");
			break;
		case BUY_7:
			buy("7");
			break;
		case BUY_8:
			buy("8");
			break;
		case BUY_9:
			buy("9");
			break;
		case BUY:
			buy(message[0]);
			break;
		case INQUIRE:
			processInquiry(message);
			break;
		case SCORE_UPDATE:
			processScore(message);
			break;
		case PLAYER_CREATED:
			this.playerID = message[0];
			break;
		}
	}
	
	/////////
	// SYSTEM METHODS
	/////////
	private void buy(final String product)
	{
		core.send(Message.REQUEST_SCORE);
		Upgrade upgrade = upgrades.get(product);
		if(upgrade!=null)
		{
			if(this.score>=upgrade.getCost())
			{
				core.send(Message.ADJUST_SCORE, -upgrade.getCost() + "");
				core.send(Message.valueOf(upgrade.getCommand()), playerID, upgrade.getValue() + "");				
			}
		}
	}
	
	private void processInquiry(final String[] message)
	{
		if(message.length>=1)
		{
			Upgrade upgrade = upgrades.get(message[0]);

			if(upgrade!=null)
			{			
				core.send(Message.UPGRADE_DESC_UPDATE, upgrade.getDesc());
			}
			else
			{
				core.send(Message.UPGRADE_DESC_UPDATE, "");
			}
		}
	}
	
	private void processScore(final String[] message)
	{
		if(message.length>=1)
		{
			try
			{
				this.score = Integer.parseInt(message[0]);				
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to update score.");
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
