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
		core.setInterested(this, "SCORE_UPDATE");
		
		//Health bar settings
		elements.put("HEALTH_BAR_XPOS", "25");
		elements.put("HEALTH_BAR_YPOS", "40");
		elements.put("HEALTH_BAR_WIDTH", "200");
		elements.put("HEALTH_BAR_HEIGHT", "40");
		elements.put("HEALTH_BAR_R", "0");
		elements.put("HEALTH_BAR_G", "128");
		elements.put("HEALTH_BAR_B", "0");
		
		//Shield bar settings
		elements.put("SHIELD_BAR_XPOS", "25");
		elements.put("SHIELD_BAR_YPOS", "16");
		elements.put("SHIELD_BAR_WIDTH", "200");
		elements.put("SHIELD_BAR_HEIGHT", "16");
		elements.put("SHIELD_BAR_RGB", "0,255,0");
		elements.put("SHIELD_BAR_R", "0");
		elements.put("SHIELD_BAR_G", "125");
		elements.put("SHIELD_BAR_B", "255");
		
		//Score settings
		elements.put("SCORE_COUNTER_XPOS", "500");
		elements.put("SCORE_COUNTER_YPOS", "32");
		elements.put("SCORE_COUNTER_WIDTH", "32");
		elements.put("SCORE_COUNTER_HEIGHT", "32");

		//Time settings
		elements.put("TIME_MS_COUNTER_XPOS", "1200");
		elements.put("TIME_MS_COUNTER_YPOS", "32");
		elements.put("TIME_MS_COUNTER_WIDTH", "32");
		elements.put("TIME_MS_COUNTER_HEIGHT", "32");
		
		elements.put("TIME_S_COUNTER_XPOS", "1100");
		elements.put("TIME_S_COUNTER_YPOS", "32");
		elements.put("TIME_S_COUNTER_WIDTH", "32");
		elements.put("TIME_S_COUNTER_HEIGHT", "32");
		
		elements.put("TIME_M_COUNTER_XPOS", "1000");
		elements.put("TIME_M_COUNTER_YPOS", "32");
		elements.put("TIME_M_COUNTER_WIDTH", "32");
		elements.put("TIME_M_COUNTER_HEIGHT", "32");
		
		//Current shield counter settings
		elements.put("SHIELD_CUR_COUNTER_XPOS", "25");
		elements.put("SHIELD_CUR_COUNTER_YPOS", "16");
		elements.put("SHIELD_CUR_COUNTER_WIDTH", "16");
		elements.put("SHIELD_CUR_COUNTER_HEIGHT", "16");
		
		//Maximum shield counter settings
		elements.put("SHIELD_MAX_COUNTER_XPOS", "111");
		elements.put("SHIELD_MAX_COUNTER_YPOS", "16");
		elements.put("SHIELD_MAX_COUNTER_WIDTH", "16");
		elements.put("SHIELD_MAX_COUNTER_HEIGHT", "16");	

		//Current health counter settings
		elements.put("HEALTH_CUR_COUNTER_XPOS", "25");
		elements.put("HEALTH_CUR_COUNTER_YPOS", "40");
		elements.put("HEALTH_CUR_COUNTER_WIDTH", "16");
		elements.put("HEALTH_CUR_COUNTER_HEIGHT", "16");
		
		//Maximum health counter settings
		elements.put("HEALTH_MAX_COUNTER_XPOS", "111");
		elements.put("HEALTH_MAX_COUNTER_YPOS", "40");
		elements.put("HEALTH_MAX_COUNTER_WIDTH", "16");
		elements.put("HEALTH_MAX_COUNTER_HEIGHT", "16");
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
			//Update the data the GUI elements rely on.
			core.send("REQUEST_SCORE");
			core.send("REQUEST_HEALTH", elements.get("PLAYER_ID"));
			core.send("REQUEST_SHIELD", elements.get("PLAYER_ID"));
			
			//Update the GUI elements.
			updateBar("SHIELD_BAR", elements.get("SHIELD_CUR"), elements.get("SHIELD_MAX"));
			updateBar("HEALTH_BAR", elements.get("HEALTH_CUR"), elements.get("HEALTH_MAX"));
			updateCounter("SHIELD_CUR_COUNTER", elements.get("SHIELD_CUR"), 5, true, ' ');
			updateCounter("SHIELD_MAX_COUNTER", elements.get("SHIELD_MAX"), 5, false, ' ');
			updateCounter("HEALTH_CUR_COUNTER", elements.get("HEALTH_CUR"), 5, true, ' ');
			updateCounter("HEALTH_MAX_COUNTER", elements.get("HEALTH_MAX"), 5, false, ' ');
			updateCounter("SCORE_COUNTER", elements.get("SCORE"), 8, true, '0');
			updateCounter("TIME_MS_COUNTER", core.now() + "", 3, true, '0');
			updateCounter("TIME_S_COUNTER", (core.now()/1000)%60 + "", 2, true, '0');
			updateCounter("TIME_M_COUNTER", (core.now()/(1000*60))%60+ "", 2, true, '0');

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
		else if(id.equals("SCORE_UPDATE"))
		{
			updateScoreAmount(message);
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
	
	private void updateBar(final String name, final String curValueAsString, 
								final String maxValueAsString)
	{
		GUIInfoPack pack = core.getInfoPackFrom(elements.get(name), GUIInfoPack.class);
		
		if(pack!=null)
		{
			//If bar has been created...
			double curValue = 1;
			double maxValue = 1;
			double width = 1;
			int xPos = 0;
			try
			{	
				curValue = Integer.parseInt(curValueAsString);
				maxValue = Integer.parseInt(maxValueAsString);
				xPos = Integer.parseInt(elements.get(name + "_XPOS"));
				width = Integer.parseInt(elements.get(name + "_WIDTH"));
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Error parsing GUI bar settings.");
			}

			width = (curValue/maxValue)*width;
			pack.setWidth(width);	//Normalize width
			pack.setXPos(xPos + width/2);	//Reposition bar so it doesn't move	
		}
		else
		{
			int xPos = 0;
			int yPos = 0;
			float r = 1;
			float g = 1;
			float b = 1;
			
			try
			{
				xPos = Integer.parseInt(elements.get(name + "_XPOS"));
				yPos = Integer.parseInt(elements.get(name + "_YPOS"));
				r = Integer.parseInt(elements.get(name + "_R"));
				g = Integer.parseInt(elements.get(name + "_G"));
				b = Integer.parseInt(elements.get(name + "_B"));
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Error creating GUI bar.");
			}		
			//Save health bar
			elements.put(name, 
							core.getSystem(EntityCreationSystem.class)
								.createGUIBar(xPos, yPos, 10, 300, r, g, b));
		}
	}

	/**
	 * Updates a counter.
	 * @param name		the unique name of the counter
	 * @param value		the value to set the counter to
	 * @param desiredNumDigits	the number of digit places the counter has
	 * @param growLeft		true if the number is right justified, false if left
	 * @param replacement	the character used to replace unused digit slots
	 */
	private void updateCounter(final String name, final String value, final int desiredNumDigits, final boolean growLeft, final char replacement)
	{
		int actualNumDigits = 0;
		if(value!=null)
		{			
			actualNumDigits = value.length();
		}
		
		final EntityCreationSystem ecs 
			= core.getSystem(EntityCreationSystem.class);
		
		for(int digitSlot = 0;digitSlot < desiredNumDigits;digitSlot++)
		{
			String slotID = elements.get(name + "_SLOT_" + digitSlot);
			GUIInfoPack pack = core.getInfoPackFrom(slotID, GUIInfoPack.class);
			
			if(pack!=null)
			{
				if(growLeft)
				{
					//If the number is right justified...
					if(digitSlot<desiredNumDigits-actualNumDigits)
					{
						//for all unused number slots...
						pack.setSpriteID(replacement);	//Set to replacement char
					}
					else
					{
						//For each number slot taken up by an actual digit...
						pack.setSpriteID(value.charAt(digitSlot-(desiredNumDigits-actualNumDigits)));
					}
				}
				else
				{
					//If the number is left justified...
					if(digitSlot>=actualNumDigits)
					{
						//For all number slots to the right of the number.
						pack.setSpriteID(replacement);
					}
					else
					{
						pack.setSpriteID(value.charAt(digitSlot));
					}
				}
			}
			else
			{
				int xPos = 0;
				int yPos = 0;
				int width = 0;
				int height = 0;
				//Create the number slot
				try
				{
					xPos = Integer.parseInt(elements.get(name + "_XPOS"));
					yPos = Integer.parseInt(elements.get(name + "_YPOS"));
					width = Integer.parseInt(elements.get(name + "_WIDTH"));
					height = Integer.parseInt(elements.get(name + "_HEIGHT"));
				}
				catch(NumberFormatException e)
				{
					LOGGER.log(Level.WARNING, "Unable to create counter.");
				}
				xPos+=width*digitSlot;
				elements.put(name + "_SLOT_" + digitSlot, ecs.createLetter(xPos, yPos, width, height, ' '));
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
				elements.put("HEALTH_CUR", message[1]);
				
				Integer.parseInt(message[2]);
				elements.put("HEALTH_MAX", message[2]);
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
				elements.put("SHIELD_CUR", message[1]);
				
				Integer.parseInt(message[2]);
				elements.put("SHIELD_MAX", message[2]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad message format.");
			}
		}
	}
	
	private void updateScoreAmount(final String[] message)
	{
		if(message.length>=1)
		{
			try
			{
				Integer.parseInt(message[0]);	//Check to see if number
				elements.put("SCORE", message[0]);
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
