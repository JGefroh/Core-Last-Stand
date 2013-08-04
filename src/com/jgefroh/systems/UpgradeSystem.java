package com.jgefroh.systems;


import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Upgrade;


/**
 * @author Joseph Gefroh
 */
public class UpgradeSystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	@SuppressWarnings("unused")
	private boolean isRunning;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
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
		core.setInterested(this, "SCORE_UPDATE");
		core.setInterested(this, "PLAYER_CREATED");
		core.setInterested(this, "BUY_1");
		core.setInterested(this, "BUY_2");
		core.setInterested(this, "BUY_3");
		core.setInterested(this, "BUY_4");
		core.setInterested(this, "BUY_5");
		core.setInterested(this, "BUY_6");
		core.setInterested(this, "BUY_7");
		core.setInterested(this, "BUY_8");
		core.setInterested(this, "BUY_9");

		core.setInterested(this, "INQUIRE");
		
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
	public void start()
	{
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}

	@Override
	public void work(final long now)
	{
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	
	@Override
	public long getWait()
	{
		return this.waitTime;
	}

	@Override
	public long	getLast()
	{
		return this.last;
	}
	
	@Override
	public void setWait(final long waitTime)
	{
		this.waitTime = waitTime;
		LOGGER.log(Level.FINE, "Wait interval set to: " + waitTime + " ms");
	}
	
	@Override
	public void setLast(final long last)
	{
		this.last = last;
	}
	
	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
		if(id.startsWith("BUY_"))
		{
			buy(id.charAt(id.length()-1) + "", message);
		}
		else if(id.equals("INQUIRE"))
		{
			processInquiry(message);
		}
		else if(id.equals("SCORE_UPDATE"))
		{
			processScore(message);
		}
		else if(id.equals("PLAYER_CREATED"))
		{
			this.playerID = message[0];
		}
	}
	
	/////////
	// SYSTEM METHODS
	/////////
	private void buy(final String product, final String[] message)
	{
		core.send("REQUEST_SCORE");
		
		if(message.length>=0)
		{
			Upgrade upgrade = upgrades.get(product);
			if(upgrade!=null)
			{
				if(this.score>=upgrade.getCost())
				{
					core.send("ADJUST_SCORE", -upgrade.getCost() + "");
					core.send(upgrade.getCommand(), playerID, upgrade.getValue() + "");				
				}
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
				core.send("UPGRADE_DESC_UPDATE", upgrade.getDesc());
			}
			else
			{
				core.send("UPGRADE_DESC_UPDATE", "");
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
