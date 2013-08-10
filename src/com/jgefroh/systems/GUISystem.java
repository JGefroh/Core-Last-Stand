package com.jgefroh.systems;


import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.GUIBarInfoPack;
import com.jgefroh.infopacks.GUICharSlotInfoPack;
import com.jgefroh.infopacks.GUICounterInfoPack;
import com.jgefroh.infopacks.GUIInfoPack;
import com.jgefroh.infopacks.GUITextInfoPack;
import com.jgefroh.messages.Message;
import com.jgefroh.tests.Benchmark;

/**
 * Controls the display of the GUI during normal gameplay.
 * @author Joseph Gefroh
 */
public class GUISystem extends AbstractSystem
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
		= LoggerFactory.getLogger(this.getClass(), Level.INFO);
	
	private Benchmark bench = new Benchmark(this.getClass().getName(), false);

	int mouseX;
	int mouseY;
	boolean isPressed;
	private HashMap<String, String> elementIDs;

	private HashMap<String, String> dataValues;
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
		elementIDs = new HashMap<String, String>();
		dataValues = new HashMap<String, String>();

		createGUIElements();

		core.setInterested(this, Message.PLAYER_CREATED);
		core.setInterested(this, Message.HEALTH_UPDATE);
		core.setInterested(this, Message.SHIELD_UPDATE);
		core.setInterested(this, Message.SCORE_UPDATE);
		core.setInterested(this, Message.UPGRADE_DESC_UPDATE);
		core.setInterested(this, Message.INPUT_CURSOR_POSITION);
		core.setInterested(this, Message.RESET_GAME);
		core.setInterested(this, Message.MOUSE0_PRESSED);
		core.setInterested(this, Message.MOUSE0_RELEASED);
	}

	@Override
	public void work(final long now)
	{
		long startTime = System.nanoTime();
		

		//Update the data the GUI elements rely on.
		long checkTime = System.nanoTime();
		core.send(Message.REQUEST_SCORE);
		core.send(Message.REQUEST_HEALTH, dataValues.get("PLAYER_ID"));
		core.send(Message.REQUEST_SHIELD, dataValues.get("PLAYER_ID"));
	//	System.out.println("REQUEST: " + (double)(System.nanoTime()-checkTime)/1000000 + "ms");
		
		checkTime = System.nanoTime();
		//Update the GUI elements.
		updateBar("SHIELD_BAR", dataValues.get("SHIELD_CUR"), dataValues.get("SHIELD_MAX"));
		updateBar("HEALTH_BAR", dataValues.get("HEALTH_CUR"), dataValues.get("HEALTH_MAX"));
		//System.out.println("BARS: " + (double)(System.nanoTime()-checkTime)/1000000 + "ms");
		
		checkTime = System.nanoTime();
		updateCounter("SHIELD_CUR_COUNTER", dataValues.get("SHIELD_CUR"), false);
		updateCounter("SHIELD_MAX_COUNTER", dataValues.get("SHIELD_MAX"), true);				
		updateCounter("HEALTH_CUR_COUNTER", dataValues.get("HEALTH_CUR"), false);
		updateCounter("HEALTH_MAX_COUNTER", dataValues.get("HEALTH_MAX"), true);							
		updateCounter("SCORE_COUNTER", dataValues.get("SCORE_CUR"), false);
		//System.out.println("COUNTERS: " + (double)(System.nanoTime()-checkTime)/1000000 + "ms");
		
		checkTime = System.nanoTime();
		updateCounter("TIMER_MS_COUNTER", core.now() + "", false);
		updateCounter("TIMER_S_COUNTER", (core.now()/1000)%60 + "", false);
		updateCounter("TIMER_M_COUNTER", (core.now()/(1000*60))%60+ "", false);
		//System.out.println("TIMER: " + (double)(System.nanoTime()-checkTime)/1000000 + "ms");
		
		checkTime = System.nanoTime();
		updateCharSlot(elementIDs.get("SHIELD_COUNTER_/"), '/');
		updateCharSlot(elementIDs.get("HEALTH_COUNTER_/"), '/');
		updateCharSlot(elementIDs.get("TIME_COUNTER_:_0"), ':');
		updateCharSlot(elementIDs.get("TIME_COUNTER_:_1"),':');
		//System.out.println("SEPARATORS: " + (double)(System.nanoTime()-checkTime)/1000000 + "ms");
		
		checkTime = System.nanoTime();
		updateCharSlot(elementIDs.get("HK_1"), '1');
		updateCharSlot(elementIDs.get("HK_2"), '2');
		updateCharSlot(elementIDs.get("HK_3"), '3');	
		//System.out.println("HOTKEYS: " + (double)(System.nanoTime()-checkTime)/1000000 + "ms");
		
		checkTime = System.nanoTime();
		updateText("UPGRADE_DESC", dataValues.get("UPGRADE_DESC"));
		//updateText("UPGRADE_DESC", null);
		//System.out.println("TEXT: " + (double)(System.nanoTime()-checkTime)/1000000 + "ms");
		//System.out.println("ALL: " + (double)(System.nanoTime()-checkTime)/1000000 + "ms");
		checkHover();
		
		bench.benchmark(System.nanoTime()-startTime, 0);
	}

	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);

		Message msgID = Message.valueOf(id);

		switch(msgID)
		{
			case HEALTH_UPDATE:
				processHealthUpdate(message);
				break;
			case SHIELD_UPDATE:
				processShieldUpdate(message);
				break;
			case SCORE_UPDATE:
				processScoreUpdate(message);
				break;
			case PLAYER_CREATED:
				dataValues.put("PLAYER_ID", message[0]);
				dataValues.remove("PLAYER_HEALTH_BAR");
				dataValues.remove("PLAYER_SHIELD_BAR");
				break;
			case INPUT_CURSOR_POSITION:
				updateCursorPos(message);
				break;
			case UPGRADE_DESC_UPDATE:
				processDescUpdate(message);
				break;
			case RESET_GAME:
				createGUIElements();
				break;
			case MOUSE0_PRESSED:
				isPressed = true;
				break;
			case MOUSE0_RELEASED:
				isPressed = false;
				break;	
		}
	}

	private void checkHover()
	{
		Iterator<GUIInfoPack> packs = core.getInfoPacksOfType(GUIInfoPack.class);
		
		core.send(Message.REQUEST_CURSOR_POSITION);
		while(packs.hasNext())
		{
			GUIInfoPack pack = packs.next();

			double xPos = pack.getXPos();
			double yPos = pack.getYPos();
			double width = pack.getWidth()/2;
			double height = pack.getHeight()/2;
			
			if(this.mouseX<=xPos+width
					&&this.mouseX>=xPos-width
					&&this.mouseY>=yPos-height
					&&this.mouseY<=yPos+height)
			{
				executeHoverEffect(pack);
				if(isPressed)
				{
					executeOnSubmitEffect(pack);
					isPressed = false;
				}
				return;
			}
		}
		core.send(Message.INQUIRE, 0 + "");
	}
	
	private void executeHoverEffect(final GUIInfoPack pack)
	{
		core.send(Message.INQUIRE, pack.getID() + "");
	}
	
	private void executeOnSubmitEffect(final GUIInfoPack pack)
	{
		core.send(Message.BUY, pack.getID() + "");
	}
	private void createGUIElements()
	{
		Iterator<String> values = elementIDs.values().iterator();
		while(values.hasNext())
		{
			core.removeEntity(values.next());
		}
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);

		elementIDs.put("SHIELD_BAR", ecs.createGUIBar(25, 16, 200, 16, 0, 0, 125, 255));
		elementIDs.put("HEALTH_BAR", ecs.createGUIBar(25, 40, 200, 16, 0, 0, 128, 0));
		
		elementIDs.put("SHIELD_CUR_COUNTER", ecs.createGUICounter(25, 16, 16, 16, 5, ' '));
		elementIDs.put("SHIELD_MAX_COUNTER", ecs.createGUICounter(121, 16, 16, 16, 5, ' '));
		elementIDs.put("HEALTH_CUR_COUNTER", ecs.createGUICounter(25, 40, 16, 16, 5, ' '));
		elementIDs.put("HEALTH_MAX_COUNTER", ecs.createGUICounter(121, 40, 16, 16, 5, ' '));
		
		elementIDs.put("SCORE_COUNTER", ecs.createGUICounter(700, 32, 32, 32, 8, '0'));
		
		elementIDs.put("TIMER_M_COUNTER", ecs.createGUICounter(1000, 32, 32, 32, 2, '0'));
		elementIDs.put("TIMER_S_COUNTER", ecs.createGUICounter(1100, 32, 32, 32, 2, '0'));
		elementIDs.put("TIMER_MS_COUNTER", ecs.createGUICounter(1200, 32, 32, 32, 3, '0'));
		
		
		elementIDs.put("SHIELD_COUNTER_/", ecs.createGUICharSlot(105, 16, 16, 16, '/', 1));
		elementIDs.put("HEALTH_COUNTER_/", ecs.createGUICharSlot(105, 40, 16, 16, '/', 1));
		elementIDs.put("TIME_COUNTER_:_0", ecs.createGUICharSlot(1064, 32, 32, 32, ':', 1));
		elementIDs.put("TIME_COUNTER_:_1", ecs.createGUICharSlot(1164, 32, 32, 32, ':', 1));
		
		elementIDs.put("REPAIR_ICON", ecs.createGUIIcon(325, 700, 32, 32, 1, "INQUIRE", "1", 1));
		elementIDs.put("REPAIR_MAX_ICON", ecs.createGUIIcon(360, 700, 32, 32, 2, "INQUIRE", "2", 2));
		elementIDs.put("SHIELD_MAX_ICON", ecs.createGUIIcon(395, 700, 32, 32, 3, "INQUIRE", "3", 3));
		
		elementIDs.put("HK_1", ecs.createGUICharSlot(325, 668, 16, 16, ' ', 1));
		elementIDs.put("HK_2", ecs.createGUICharSlot(360, 668, 16, 16, ' ', 1));
		elementIDs.put("HK_3", ecs.createGUICharSlot(395, 668, 16, 16, ' ', 1));
		
		elementIDs.put("UPGRADE_DESC", ecs.createGUITextArea(450, 684, 3, 50, 10, 10, 10, ' '));
		
		elementIDs.put("FPS_COUNTER", ecs.createGUICounter(1300, 700, 16, 16, 4, ' '));
	}
	
	/**
	 * Creates a bar GUI element.
	 * @param elementName	the unique name of this element
	 * @param dataName		the unique name of the data this element uses
	 */
	private void updateBar(final String elementName, final String curValAsStr, final String maxValAsStr)
	{
		GUIBarInfoPack barPack = core.getInfoPackFrom(elementIDs.get(elementName), GUIBarInfoPack.class);

		if(barPack==null || curValAsStr == null || maxValAsStr == null)
		{
			//The element doesn't exist.
			return;
		}

		//Get current and max value the bar represents
		double curVal = 0;
		double maxVal = 0;
		try
		{
			curVal = Integer.parseInt(curValAsStr);
			maxVal = Integer.parseInt(maxValAsStr);			
		}
		catch(NumberFormatException e)
		{
		}
		
		//Shrink based on direction
		if(barPack.left())
		{
			double width = barPack.getMaxWidth();	//Get the max size
			double xPos = barPack.getDefXPos();	//Get the desired X pos
			width = (curVal/maxVal)*width;		//Normalize width to max size
			xPos += width/2;					//Reposition so it remains in place
			barPack.setWidth(width);
			barPack.setXPos(xPos);

		}
		else if(barPack.right())
		{
			double width = barPack.getMaxWidth();	//Get the max size
			double xPos = barPack.getDefXPos();	//Get the desired X pos
			width = (curVal/maxVal)*width;		//Normalize width to max size
			xPos -= width/2;					//Reposition so it remains in place
			barPack.setWidth(width);
			barPack.setXPos(xPos);
		}
		else if(barPack.up())
		{
			double height = barPack.getMaxHeight();	//Get the max size
			double yPos = barPack.getDefYPos();	//Get the desired y pos

			height = (curVal/maxVal)*height;	//Normalize height to max size
			yPos += height/2;					//Reposition so it remains in place
			barPack.setHeight(height);
			barPack.setYPos(yPos);
		}
		else if(barPack.down())
		{
			double height = barPack.getMaxHeight();	//Get the max size
			double yPos = barPack.getDefYPos();	//Get the desired y pos
			height = (curVal/maxVal)*height;	//Normalize height to max size
			yPos -= height/2;					//Reposition so it remains in place
			barPack.setHeight(height);
			barPack.setYPos(yPos);
		}
		else if(barPack.collapseMiddleH())
		{
			double width = barPack.getMaxWidth();	//Get the max size
			width = (curVal/maxVal)*width;		//Normalize width to max size
			barPack.setWidth(width);
		}
		else if(barPack.collapseMiddleV())
		{
			double height = barPack.getMaxHeight();	//Get the max size
			height = (curVal/maxVal)*height;		//Normalize height to max size
			barPack.setHeight(height);				//Set new height
		}
	}

	/**
	 * Creates a numeric counter. For text, use something else.
	 * @param name
	 * @param value
	 * @param alignLeft
	 */
	private void updateCounter(final String name, final String value, final boolean alignLeft)
	{
		GUICounterInfoPack pack = core.getInfoPackFrom(elementIDs.get(name), GUICounterInfoPack.class);
		
		if(pack==null || value==null || pack.getText().equals(value))
		{
			return;
		}
		
		int curTextIndex = 0;
		int curSlot = 0;
		int numSlots = pack.getNumSlots();
		
		Iterator<String> childrenIDs = pack.getChildren();
		
		while(childrenIDs.hasNext())
		{
			GUICharSlotInfoPack charSlot = core.getInfoPackFrom(childrenIDs.next(), GUICharSlotInfoPack.class);
			
			if(alignLeft)
			{
				if(curTextIndex<value.length())
				{					
					//Write character normally
					charSlot.setSpriteID(value.charAt(curTextIndex));
					curTextIndex++;
				}
				else
				{
					//Write the default character
					charSlot.setSpriteID(pack.getDefaultChar());
				}
			}
			else
			{
				//If the number is right justified
				if(curSlot<numSlots-value.length())
				{
					//for all unused number slots...
					charSlot.setSpriteID(pack.getDefaultChar());	//Set to replacement char
				}
				else
				{
					//For each number slot taken up by an actual digit...
					charSlot.setSpriteID(value.charAt(curSlot-(numSlots-value.length())));
				}
			}
			curSlot++;
			charSlot.setVisible(true);
		}
	}
	
	/**
	 * Updates a single, independent character slot (not for use with multichar)
	 * @param name
	 * @param newChar
	 */
	private void updateCharSlot(final String id, final char newChar)
	{
		GUICharSlotInfoPack pack = core.getInfoPackFrom(id, GUICharSlotInfoPack.class);
		
		if(pack==null)
		{
			return;
		}

		if(newChar=='\0' || newChar==' ')
		{
			pack.setVisible(false);
		}
		else
		{			
			pack.setSpriteID(newChar);
			pack.setVisible(true);
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	private void updateCursorPos(final String[] message)
	{
		if(message.length>=2)
		{
			try
			{
				this.mouseX = Integer.parseInt(message[0]);
				this.mouseY = Integer.parseInt(message[1]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Error updating mouse coords.");
			}
		}
	}
	
	private void updateText(final String name, final String text)
	{
		GUITextInfoPack pack = core.getInfoPackFrom(elementIDs.get(name), GUITextInfoPack.class);

		if(pack==null
				||(pack.getText()!=null&&pack.getText().equals(text))
				||(pack.getText()==null&&text==null))
		{
			return;	//If it hasn't changed, don't change it.
		}
		pack.setText(text);

		String[] words = {};
		if(text!=null)
		{
			words = text.toUpperCase().split(" ");
		}

		Iterator<String> slots = pack.getChildren();
		int numSlotsRemaining = pack.getNumCharsPerLine();
		int curWordIndex = 0;
		
		while(curWordIndex<words.length)
		{//For each word in the list of words to write...
			String word = words[curWordIndex];	//Get the word
			if(word.equals("\n"))
			{//new line
				for(int slotIndex = 0;slotIndex<numSlotsRemaining;slotIndex++)
				{
					if(slots.hasNext())
					{						
						updateCharSlot(slots.next(), '\0');
					}
				}
				numSlotsRemaining = pack.getNumCharsPerLine();
				curWordIndex++;
				continue;
			}
						
			int wordSize = word.length();
			if(wordSize<=numSlotsRemaining)
			{//If the word will fit on the current line...
				if(wordSize<numSlotsRemaining)
				{//If the word doesn't take up all the space...
					wordSize++;	//Use up an extra slot than necessary.
				}
				
				String[] freeSlots = new String[wordSize];
				for(int index=0;index<freeSlots.length;index++)
				{
					if(slots.hasNext())
					{						
						freeSlots[index] = slots.next();
					}
				}
				writeWord(word, freeSlots);
				numSlotsRemaining-=wordSize;
				curWordIndex++;
			}
			else if(wordSize>numSlotsRemaining&&wordSize<=pack.getNumCharsPerLine())
			{//If the word won't fit on the current line but will on the next
				for(int index=numSlotsRemaining;index>0;index--)
				{
					if(slots.hasNext())
					{					
						updateCharSlot(slots.next(), '\0');
					}
				}
				numSlotsRemaining = pack.getNumCharsPerLine();
			}
			else
			{//If the word won't fit on any line, write on separate lines.
				String[] freeSlots = new String[wordSize];
				for(int index=0;index<freeSlots.length;index++)
				{
					if(slots.hasNext())
					{						
						freeSlots[index] = slots.next();
					}
				}
				writeWord(word, freeSlots);
				numSlotsRemaining = pack.getNumCharsPerLine()-(wordSize-numSlotsRemaining)%pack.getNumCharsPerLine();
				curWordIndex++;
			}
		}
		
		while(slots.hasNext())
		{//Blank out unused slots.
			updateCharSlot(slots.next(), '\0');
		}
	}
	
	/**
	 * Used by the updateText() method to update character slots.
	 * @param word				the word to write
	 * @param charSlotIDs		the entity IDs of the slots to use
	 */
	private void writeWord(final String word, final String... charSlotIDs)
	{
		int index=0;
		for(String id:charSlotIDs)
		{//For each slot...
			GUICharSlotInfoPack pack = core.getInfoPackFrom(id, GUICharSlotInfoPack.class);
			if(pack!=null)
			{
				if(index<word.length())
				{
					pack.setSpriteID(word.charAt(index));
					pack.setVisible(true);
					index++;
				}
				else
				{//set all extra slots blank.
					pack.setVisible(false);
				}
			}
			else
			{
				return;
			}
		}
	}
	
	/**
	 * Stores the health's current and maximum values for GUI elements to use.
	 * @param message	[0] the entity ID of the health's owner
	 * 					[1] the current health value (int)
	 * 					[2] the max health value (int)
	 */
	private void processHealthUpdate(final String[] message)
	{
		if(message.length>=3)
		{
			dataValues.put("HEALTH_CUR", message[1]);
			dataValues.put("HEALTH_MAX", message[2]);
		}
	}
	
	/**
	 * Stores the shield's current and maximum values for GUI elements to use.
	 * @param message	[0] the entity ID of the shield's owner
	 * 					[1] the current shield value (int)
	 * 					[2] the max shield value (int)
	 */
	private void processShieldUpdate(final String[] message)
	{
		if(message.length>=3)
		{
				dataValues.put("SHIELD_CUR", message[1]);
				dataValues.put("SHIELD_MAX", message[2]);
		}
	}
	
	/**
	 * Stores the passed score so that GUI elements can refer to it.
	 * @param message	[0] the integer score
	 */
	private void processScoreUpdate(final String[] message)
	{
		if(message.length>=1)
		{
			dataValues.put("SCORE_CUR", message[0]);
		}
	}
	
	private void processDescUpdate(final String[] message)
	{
		if(message.length>=1)
		{
			dataValues.put("UPGRADE_DESC", message[0]);
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
