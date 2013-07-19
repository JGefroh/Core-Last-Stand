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
		elements.put("PLAYER_SHIELD_BAR_XPOS", "25");
		elements.put("PLAYER_SHIELD_BAR_YPOS", "16");
		elements.put("PLAYER_SHIELD_BAR_WIDTH", "200");
		elements.put("PLAYER_SHIELD_BAR_HEIGHT", "16");
		
		elements.put("PLAYER_SHIELD_BAR_NUMBER_XPOS", "-36");
		elements.put("PLAYER_SHIELD_BAR_NUMBER_YPOS", "16");
		elements.put("PLAYER_SHIELD_BAR_NUMBER_WIDTH", "16");
		elements.put("PLAYER_SHIELD_BAR_NUMBER_HEIGHT", "16");
		elements.put("PLAYER_SHIELD_BAR_NUMBER_SPACING", "16");
		elements.put("PLAYER_SHIELD_BAR_NUMBER_RIGHTJUSTIFIED", "true");
		
		elements.put("PLAYER_HEALTH_BAR_XPOS", "25");
		elements.put("PLAYER_HEALTH_BAR_YPOS", "40");
		elements.put("PLAYER_HEALTH_BAR_WIDTH", "200");
		elements.put("PLAYER_HEALTH_BAR_HEIGHT", "40");
		
		elements.put("PLAYER_HEALTH_BAR_NUMBER_XPOS", "-36");
		elements.put("PLAYER_HEALTH_BAR_NUMBER_YPOS", "40");
		elements.put("PLAYER_HEALTH_BAR_NUMBER_WIDTH", "16");
		elements.put("PLAYER_HEALTH_BAR_NUMBER_HEIGHT", "16");
		elements.put("PLAYER_HEALTH_BAR_NUMBER_SPACING", "16");
		elements.put("PLAYER_HEALTH_BAR_NUMBER_RIGHTJUSTIFIED", "true");
		
		elements.put("PLAYER_HEALTH_BAR_MAX_NUMBER_XPOS", "144");
		elements.put("PLAYER_HEALTH_BAR_MAX_NUMBER_YPOS", "40");
		elements.put("PLAYER_HEALTH_BAR_MAX_NUMBER_WIDTH", "16");
		elements.put("PLAYER_HEALTH_BAR_MAX_NUMBER_HEIGHT", "16");
		elements.put("PLAYER_HEALTH_BAR_MAX_NUMBER_SPACING", "16");
		elements.put("PLAYER_HEALTH_BAR_MAX_NUMBER_RIGHTJUSTIFIED", "false");
		

		elements.put("PLAYER_SHIELD_BAR_MAX_NUMBER_XPOS", "144");
		elements.put("PLAYER_SHIELD_BAR_MAX_NUMBER_YPOS", "16");
		elements.put("PLAYER_SHIELD_BAR_MAX_NUMBER_WIDTH", "16");
		elements.put("PLAYER_SHIELD_BAR_MAX_NUMBER_HEIGHT", "16");
		elements.put("PLAYER_SHIELD_BAR_MAX_NUMBER_SPACING", "16");
		elements.put("PLAYER_SHIELD_BAR_MAX_NUMBER_RIGHTJUSTIFIED", "false");
	
		elements.put("SCORE_NUMBER", Integer.MAX_VALUE + "");
		elements.put("SCORE_NUMBER_YPOS", "32");
		elements.put("SCORE_NUMBER_XPOS", "500");
		elements.put("SCORE_NUMBER_WIDTH", "32");
		elements.put("SCORE_NUMBER_HEIGHT", "32");
		elements.put("SCORE_NUMBER_SPACING", "162");
		elements.put("SCORE_NUMBER_LEADING", "true");
		elements.put("SCORE_NUMBER_RIGHTJUSTIFIED", "true");

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
			updateCounter("SCORE");
			updateCounter("PLAYER_HEALTH_BAR");
			updateCounter("PLAYER_SHIELD_BAR");
			updateCounter("PLAYER_HEALTH_BAR_MAX");
			updateCounter("PLAYER_SHIELD_BAR_MAX");
			updateCounterSeparators();
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
			GUIInfoPack pack = core.getInfoPackFrom(id, GUIInfoPack.class);			
			if(pack!=null)
			{
				//If the GUI element exists...
				//Update the amount of health the entity has
				core.send("REQUEST_HEALTH", playerID);
				
				try
				{				
					//Get the amount of health (double to avoid shifting on odd)
					double health = Integer.parseInt(elements.get("PLAYER_HEALTH_BAR_NUMBER"));
					//Get the position the bar should be.
					int healthXPos = Integer.parseInt(elements.get("PLAYER_HEALTH_BAR_XPOS"));
					//Set the size of the health bar to reflect hp
					double width = Integer.parseInt(elements.get("PLAYER_HEALTH_BAR_WIDTH"));
					double maxHealth = Integer.parseInt(elements.get("PLAYER_HEALTH_BAR_MAX_NUMBER"));
					width = (health/maxHealth)*width;
					pack.setWidth(width);	
					//Reposition bar so it does not move
					pack.setXPos(healthXPos + width/2);	
					
					if(health/maxHealth<0.25)
					{
						pack.setRGB(255, 0, 0);
					}
				}
				catch(NumberFormatException e)
				{
					LOGGER.log(Level.WARNING, "Bad message format.");
				}
			}
			else
			{
				elements.remove("PLAYER_HEALTH_BAR");
			}
		}
		else if(id==null)
		{//Health bar does not yet exist
			try
			{
				int xPos = Integer.parseInt(elements.get("PLAYER_HEALTH_BAR_XPOS"));
				int yPos = Integer.parseInt(elements.get("PLAYER_HEALTH_BAR_YPOS"));
				
				//Create health bar
				String healthBarID 
					= core.getSystem(EntityCreationSystem.class).createGUIBar(xPos, yPos, 10, 300, 0, 128, 0);
				
				//Save health bar
				elements.put("PLAYER_HEALTH_BAR", healthBarID);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to create health bar.");
			}

		}
	}
	
	private void updateShieldBar()
	{
		//Update shield bar
		String id = elements.get("PLAYER_SHIELD_BAR");
		String playerID = elements.get("PLAYER_ID");
		
		if(id!=null&&playerID!=null)
		{
			GUIInfoPack pack = core.getInfoPackFrom(id, GUIInfoPack.class);
			if(pack!=null)
			{			
				//If the GUI element exists....
				//Update the amount of shield the entity has.
				core.send("REQUEST_SHIELD", playerID);
				
				try
				{				
					//Get the amount of shield (double to avoid shifting on odd)
					double shield = Integer.parseInt(elements.get("PLAYER_SHIELD_BAR_NUMBER"));
					//Get the correct draw position
					int shieldXPos = Integer.parseInt(elements.get("PLAYER_SHIELD_BAR_XPOS"));
					//Set the size of the shield bar to reflect shield energy
					double width = Integer.parseInt(elements.get("PLAYER_SHIELD_BAR_WIDTH"));
					double max = Integer.parseInt(elements.get("PLAYER_SHIELD_BAR_MAX_NUMBER"));
					width = (shield/max)*width;
					pack.setWidth(width);	
					//Reposition to prevent moving the bar
					pack.setXPos(shieldXPos + width/2);
				}
				catch(NumberFormatException e)
				{
					LOGGER.log(Level.WARNING, "Bad message format.");
				}
			}
			else
			{
				elements.remove("PLAYER_SHIELD_BAR");
			}
		}
		else if(id==null)
		{//Shield bar does not yet exist			
			try
			{
				int xPos = Integer.parseInt(elements.get("PLAYER_SHIELD_BAR_XPOS"));
				int yPos = Integer.parseInt(elements.get("PLAYER_SHIELD_BAR_YPOS"));
				
				//Create shield bar
				String shieldBarID 
					= core.getSystem(EntityCreationSystem.class).createGUIBar(xPos, yPos, 10, 300, 0, 100, 255);
				
				//Save shield bar
				elements.put("PLAYER_SHIELD_BAR", shieldBarID);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to create shield bar.");
			}
		}
	}

	private void updateCounter(final String source)
	{
		String number = elements.get(source+"_NUMBER");
		
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		
		boolean rightJustified = Boolean.parseBoolean(elements.get(source+"_NUMBER_RIGHTJUSTIFIED"));
		boolean hasLeadingZeros = Boolean.parseBoolean(elements.get(source+"_NUMBER_LEADING"));
		if(rightJustified)
		{
			//Add leading spaces to match slots.
			number = String.format("%10s", number);
			
			if(hasLeadingZeros)
			{
				//Change spaces to zeros.
				number = number.replace(' ', '0');
			}
		}
		
		for(int slot = 0;slot<10;slot++)
		{
			//For each of the number slots...
			String id = elements.get(source + "_NUMBER_DIGIT_" + slot);
			GUIInfoPack pack = core.getInfoPackFrom(id, GUIInfoPack.class);

			if(pack!=null)
			{
				//If the slot already exists...
				if(slot<number.length())
				{
					//If the string has a symbol at the position
					pack.setSpriteID(number.charAt(slot));
				}
				else
				{
					//Set as blank (don't use -1 as a legit sprite index)
					pack.setSpriteID(-1);				
				}
			}
			else
			{
				//Create the slots.
				int xPos = Integer.parseInt(elements.get(source + "_NUMBER_XPOS"));
				int yPos = Integer.parseInt(elements.get(source + "_NUMBER_YPOS"));
				int width = Integer.parseInt(elements.get(source + "_NUMBER_WIDTH"));
				int height = Integer.parseInt(elements.get(source + "_NUMBER_HEIGHT"));
								
				xPos = xPos+width*slot;	//Position each slot correctly.
							
				elements.put(source + "_NUMBER_DIGIT_" + slot, ecs.createLetter(xPos, yPos, width, height, ' '));
			}
		}
	}
	
	private void updateCounterSeparators()
	{
		String sep = elements.get("HEALTH_BAR_COUNTER_SEPARATOR");
		GUIInfoPack pack = core.getInfoPackFrom(sep, GUIInfoPack.class);
		
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		if(pack==null)
		{			
			elements.put("HEALTH_BAR_COUNTER_SEPARATOR", ecs.createLetter(125,40,16,16,'/'));	// / separator for health
		}
		
		sep = elements.get("SHIELD_BAR_COUNTER_SEPARATOR");
		pack = core.getInfoPackFrom(sep, GUIInfoPack.class);
		
		if(pack==null)
		{			
			elements.put("SHIELD_BAR_COUNTER_SEPARATOR", ecs.createLetter(125,16,16,16,'/'));	// / separator for shield
		}
		
	}
	private void updateHealthAmount(final String[] message)
	{
		if(message.length>1)
		{
			try
			{
				Integer.parseInt(message[1]);	//Check to see if number
				elements.put("PLAYER_HEALTH_BAR_NUMBER", message[1]);
				
				Integer.parseInt(message[2]);
				elements.put("PLAYER_HEALTH_BAR_MAX_NUMBER", message[2]);
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
				elements.put("PLAYER_SHIELD_BAR_NUMBER", message[1]);
				
				Integer.parseInt(message[2]);
				elements.put("PLAYER_SHIELD_BAR_MAX_NUMBER", message[2]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad message format.");
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
