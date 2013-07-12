package com.jgefroh.systems;


import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.GUIInfoPack;

/**
 * Controls the display of the GUI during normal gameplay.
 * @author Joseph Gefroh
 */
public class GUISystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
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
	
	private HashMap<String, String> elements;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public GUISystem(final Core core)
	{
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		elements = new HashMap<String, String>();
		
		core.setInterested(this, "PLAYER_CREATED");
		core.setInterested(this, "HEALTH_UPDATE");
		core.setInterested(this, "SHIELD_UPDATE");
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
		if(isRunning)
		{
			updateHealthBar();
			updateShieldBar();
		}
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
		
		if(id.equals("HEALTH_UPDATE"))
		{
			updateHealthAmount(message);
		}
		else if(id.equals("SHIELD_UPDATE"))
		{
			updateShieldAmount(message);
		}
		else if(id.equals("PLAYER_CREATED"))
		{
			elements.put("PLAYER_ID", message[0]);
			elements.remove("PLAYER_HEALTH_BAR");
			elements.remove("PLAYER_SHIELD_BAR");
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	
	private void updateHealthBar()
	{
		//Update health bar
		String id = elements.get("PLAYER_HEALTH_BAR");
		String playerID = elements.get("PLAYER_ID");
		if(id!=null&&playerID!=null)
		{
			//Get the amount of health the entity has.
			core.send("REQUEST_HEALTH", playerID);
			
			try
			{				
				int health = Integer.parseInt(elements.get("PLAYER_HEALTH"));
				GUIInfoPack pack = core.getInfoPackFrom(id, GUIInfoPack.class);
				if(pack!=null)
				{
					//Set the size of the health bar to reflect HP
					pack.setHeight(health);
				}
				else
				{
					elements.remove("PLAYER_HEALTH_BAR");
				}
			}
			catch(NumberFormatException e)
			{
				
			}
		}
		else if(id==null)
		{//Health bar does not yet exist			
			//Create health bar
			String healthBarID 
				= core.getSystem(EntityCreationSystem.class).createGUIHealthBar();
			
			//Save health bar
			elements.put("PLAYER_HEALTH_BAR", healthBarID);
		}
	}
	
	private void updateHealthAmount(final String[] message)
	{
		if(message.length>1)
		{
			try
			{
				Integer.parseInt(message[1]);	//Check to see if number
				elements.put("PLAYER_HEALTH", message[1]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad message format.");
			}
		}
	}
	
	private void updateShieldAmount(final String[] message)
	{
		if(message.length>1)
		{
			try
			{
				Integer.parseInt(message[1]);	//Check to see if number
				elements.put("PLAYER_SHIELD", message[1]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad message format.");
			}
		}
	}
	
	private void updateShieldBar()
	{
		String id = elements.get("PLAYER_SHIELD_BAR");
		String playerID = elements.get("PLAYER_ID");
		
		if(id!=null&&playerID!=null)
		{
			//Get the amount of shield the entity has.
			core.send("REQUEST_SHIELD", playerID);
			try
			{				
				elements.get("PLAYER_SHIELD");
				int shield = Integer.parseInt(elements.get("PLAYER_SHIELD"));
				GUIInfoPack pack = core.getInfoPackFrom(id, GUIInfoPack.class);
				if(pack!=null)
				{
					//Set the size of the shield bar to reflect shield energy
					pack.setHeight(shield);
				}
				else
				{
					elements.remove("PLAYER_SHIELD_BAR");
				}
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad message format.");
			}
		}
		else if(id==null)
		{//Shield bar does not yet exist			
			//Create shield bar
			String shieldBarID 
				= core.getSystem(EntityCreationSystem.class).createGUIShieldBar();
			
			//Save shield bar
			elements.put("PLAYER_SHIELD_BAR", shieldBarID);
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
