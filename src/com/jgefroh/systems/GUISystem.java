package com.jgefroh.systems;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.GUIBarInfoPack;
import com.jgefroh.infopacks.GUICharSlotInfoPack;
import com.jgefroh.infopacks.GUICounterInfoPack;
import com.jgefroh.infopacks.GUIInfoPack;
import com.jgefroh.infopacks.GUITextInfoPack;
import com.jgefroh.tests.Benchmark;

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
	private Benchmark bench = new Benchmark(this.getClass().getName(), true);

	int mouseX;
	int mouseY;
	
	private HashMap<String, String> elementIDs;
	
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
		elementIDs = new HashMap<String, String>();
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);

		createGUIElements();

		core.setInterested(this, "PLAYER_CREATED");
		core.setInterested(this, "HEALTH_UPDATE");
		core.setInterested(this, "SHIELD_UPDATE");
		core.setInterested(this, "SCORE_UPDATE");
		core.setInterested(this, "UPGRADE_DESC_UPDATE");
		core.setInterested(this, "INPUT_CURSOR_POSITION");
		core.setInterested(this, "UPGRADE_DESC_UPDATE");
		core.setInterested(this, "RESET_GAME");

		



		
		//Words.
		elements.put("HK_1_XPOS", "317");
		elements.put("HK_1_YPOS", "670");
		elements.put("HK_1_WIDTH", "10");
		elements.put("HK_1_HEIGHT", "10");
		elements.put("HK_1_CHARSPERLINE", "2");
		elements.put("HK_1_NUMLINES", "1");
		elements.put("HK_1_HASCHANGED", "true");

		elements.put("HK_2_XPOS", "352");
		elements.put("HK_2_YPOS", "670");
		elements.put("HK_2_WIDTH", "10");
		elements.put("HK_2_HEIGHT", "10");
		elements.put("HK_2_HASCHANGED", "true");
		elements.put("HK_2_CHARSPERLINE", "2");
		elements.put("HK_2_NUMLINES", "1");
		
		elements.put("HK_3_XPOS", "387");
		elements.put("HK_3_YPOS", "670");
		elements.put("HK_3_WIDTH", "10");
		elements.put("HK_3_HEIGHT", "10");
		elements.put("HK_3_HASCHANGED", "true");
		elements.put("HK_3_CHARSPERLINE", "2");
		elements.put("HK_3_NUMLINES", "1");

		elements.put("BUY_DESC", "");
		elements.put("BUY_DESC_XPOS", "450");
		elements.put("BUY_DESC_YPOS", "700");
		elements.put("BUY_DESC_WIDTH", "10");
		elements.put("BUY_DESC_HEIGHT", "10");
		elements.put("BUY_DESC_CHARSPERLINE", "50");
		elements.put("BUY_DESC_NUMLINES", "10");
		elements.put("BUY_DESC_SPACEY", "5");
		elements.put("BUY_DESC_HASCHANGED", "true");
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
			long startTime = System.nanoTime();
			

			//Update the data the GUI elements rely on.
			core.send("REQUEST_SCORE");
			core.send("REQUEST_HEALTH", elements.get("PLAYER_ID"));
			core.send("REQUEST_SHIELD", elements.get("PLAYER_ID"));

			//Update the GUI elements.
			updateBar("SHIELD_BAR", "SHIELD_DATA");
			updateBar("HEALTH_BAR", "HEALTH_DATA");
			
			startTime = System.nanoTime();

			GUIInfoPack pack = core.getInfoPackFrom(elementIDs.get("SHIELD_DATA"), GUIInfoPack.class);

			if(pack!=null)
			{
				updateCounter("SHIELD_CUR_COUNTER", pack.getCurVal() + "", false);
				updateCounter("SHIELD_MAX_COUNTER", pack.getMaxVal() + "", true);				
			}
			
			pack = core.getInfoPackFrom(elementIDs.get("HEALTH_DATA"), GUIInfoPack.class);
			if(pack!=null)
			{
				updateCounter("HEALTH_CUR_COUNTER", pack.getCurVal() + "", false);
				updateCounter("HEALTH_MAX_COUNTER", pack.getMaxVal() + "", true);				
			}
			
			pack = core.getInfoPackFrom(elementIDs.get("SCORE_DATA"), GUIInfoPack.class);
			if(pack!=null)
			{				
				updateCounter("SCORE_COUNTER", pack.getCurVal() + "", false);
			}
			
			updateCounter("TIMER_MS_COUNTER", core.now() + "", false);
			updateCounter("TIMER_S_COUNTER", (core.now()/1000)%60 + "", false);
			updateCounter("TIMER_M_COUNTER", (core.now()/(1000*60))%60+ "", false);
			System.out.println("COUNTER TIMES:" + (double)(System.nanoTime()-startTime)/1000000);

			updateCharSlot("SHIELD_COUNTER_/", '/');
			updateCharSlot("HEALTH_COUNTER_/", '/');
			updateCharSlot("TIME_COUNTER_:_0", ':');
			updateCharSlot("TIME_COUNTER_:_1",':');

					startTime = System.nanoTime();
			//updateText("TEST_TEXT", "300 400 500");
			System.out.println("NEW_TEXT_AREA TIMES:" + (double)(System.nanoTime()-startTime)/1000000);
			
			startTime = System.nanoTime();
			//updateTextArea("HK_1", "1");	//WHY IS THIS TAKING SO LONG
			//updateTextArea("HK_2", "2"); 	
			//updateTextArea("HK_3", "3");
			System.out.println("TEXT_HOTKEY TIMES:" + (double)(System.nanoTime()-startTime)/1000000);

			//updateTextArea("TEST_DESC", "ALPHA BRAVO CHARLIE ALPHABRAVOCHARLIE DELTA ECHO");
			startTime = System.nanoTime();
			//checkDesc();
			//updateTextArea("BUY_DESC", elements.get("BUY_DESC"));
			System.out.println("TEXT_DESC TIMES:" + (double)(System.nanoTime()-startTime)/1000000);

			bench.benchmark(System.nanoTime()-startTime, 0);
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
			processHealthUpdate(message);
		}
		else if(id.equals("SHIELD_UPDATE"))
		{
			processShieldUpdate(message);
		}
		else if(id.equals("SCORE_UPDATE"))
		{
			processScoreUpdate(message);
		}
		else if(id.equals("PLAYER_CREATED"))
		{
			elements.put("PLAYER_ID", message[0]);
			elements.remove("PLAYER_HEALTH_BAR");
			elements.remove("PLAYER_SHIELD_BAR");
		}
		else if(id.equals("UPGRADE_DESC_UPDATE"))
		{
			//updateDesc(message);
		}
		else if(id.equals("INPUT_CURSOR_POSITION"))
		{
			updateCursorPos(message);
		}
		else if(id.equals("RESET_GAME"))
		{
			createGUIElements();
		}
	}

	private void createGUIElements()
	{
		Iterator<String> values = elementIDs.values().iterator();
		while(values.hasNext())
		{
			core.removeEntity(values.next());
		}
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		elementIDs.put("SHIELD_DATA", ecs.createGUIData());
		elementIDs.put("HEALTH_DATA", ecs.createGUIData());
		elementIDs.put("SCORE_DATA", ecs.createGUIData());

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
		
		elementIDs.put("TEST_TEXT", ecs.createGUITextArea(200, 300, 5, 10, 16, 16, ' '));
		
		elementIDs.put("SHIELD_COUNTER_/", ecs.createGUICharSlot(105, 16, 16, 16, '/', 1));
		elementIDs.put("HEALTH_COUNTER_/", ecs.createGUICharSlot(105, 40, 16, 16, '/', 1));
		elementIDs.put("TIME_COUNTER_:_0", ecs.createGUICharSlot(1064, 32, 32, 32, ':', 1));
		elementIDs.put("TIME_COUNTER_:_1", ecs.createGUICharSlot(1164, 32, 32, 32, ':', 1));
		
		elementIDs.put("REPAIR_ICON", ecs.createIcon(325, 700, 32, 32, 1, "INQUIRE", "1"));
		elementIDs.put("REPAIR_MAX_ICON", ecs.createIcon(360, 700, 32, 32, 2, "INQUIRE", "2"));
		elementIDs.put("SHIELD_MAX_ICON", ecs.createIcon(395, 700, 32, 32, 3, "INQUIRE", "3"));
	}
	
	/**
	 * Creates a bar GUI element.
	 * @param elementName	the unique name of this element
	 * @param dataName		the unique name of the data this element uses
	 */
	private void updateBar(final String elementName, final String dataName)
	{
		GUIBarInfoPack barPack = core.getInfoPackFrom(elementIDs.get(elementName), GUIBarInfoPack.class);
		GUIInfoPack dataPack = core.getInfoPackFrom(elementIDs.get(dataName), GUIInfoPack.class);
		
		if(barPack==null || dataPack == null)
		{
			//The element doesn't exist.
			return;
		}
		
		//Get current and max value the bar represents
		double curVal = dataPack.getCurVal();	
		double maxVal = (dataPack.getMaxVal()>0) ? dataPack.getMaxVal() : 1;
		
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
		
		barPack.setCurValue(dataPack.getCurVal());	//Not required
		barPack.setMaxValue(dataPack.getMaxVal());	//Not required
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
		
		if(pack==null || pack.getText().equals(value))
		{
			return;
		}
		
		int curTextIndex = 0;
		int curSlot = 0;
		int numSlots = pack.getNumSlots();
		
		Iterator<String> childrenIDs = pack.getChildren();
		
		while(childrenIDs.hasNext())
		{
			GUIInfoPack charSlot = core.getInfoPackFrom(childrenIDs.next(), GUIInfoPack.class);
			
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
		}
	}
	
	/**
	 * Updates a single, independent character slot (not for use with multichar)
	 * @param name
	 * @param newChar
	 */
	private void updateCharSlot(final String name, final char newChar)
	{
		GUICharSlotInfoPack pack = core.getInfoPackFrom(elementIDs.get(name), GUICharSlotInfoPack.class);
		
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


	private void updateText(final String name, final String newText)
	{
		//Convert to upper case (until I get lower case sprites)
		String upperText = newText.toUpperCase();
		
		//Get the text area
		GUITextInfoPack pack = core.getInfoPackFrom(elementIDs.get(name), GUITextInfoPack.class);
		
		boolean alignLeft = false;
		if(pack==null||pack.getText().equals(newText))
		{
			//If the area doesn't exist or the text hasn't changed...
			return;
		}

		//Get all of the text "slots"
		Iterator<String> slots = pack.getChildren();
		int textIndex = 0;
		int slotIndex = 0;
		int lineNum = 0;
		int slotOnLineNum = 0;
		
		int numSlotsPerLine = pack.getNumCharsPerLine();
		int numLines = pack.getNumLines();
		int numSlots = numLines*numSlotsPerLine;
		int numCharsToWrite = upperText.length();
		while(slots.hasNext())
		{
			//Go through each slot...
			GUIInfoPack slot = 
					core.getInfoPackFrom(slots.next(), GUIInfoPack.class);
			
			char charToWrite = '\0';
			
			//Figure out which character to write...
			if(alignLeft)
			{//If aligned left, write normally...
				if(textIndex<upperText.length())
				{
					charToWrite = upperText.charAt(textIndex);
					textIndex++;
				}
			}
			else
			{//If aligned right, write empty spaces first...
				if(slotIndex<numSlotsPerLine-numCharsToWrite)
				{					
					charToWrite = pack.getDefaultChar();
				}
				else
				{
					charToWrite = upperText.charAt(textIndex);
					textIndex++;
				}
			}
			
			//Set to default
			if(charToWrite=='\0')
			{
				charToWrite=pack.getDefaultChar();
			}
			
			//Write it.
			if(charToWrite!=' ' && charToWrite!='\0')
			{
				slot.setSpriteID(charToWrite);
				slot.setVisible(true);
			}
			else
			{					
				slot.setVisible(false);
			}
			slotIndex++;
		}
	}
	
	/*
	 * 	private void updateText(final String name, final String newText)
	{
		//Convert to upper case (until I get lower case sprites)
		String upperText = newText.toUpperCase();
		
		//Get the text area
		GUITextInfoPack pack = core.getInfoPackFrom(elementIDs.get(name), GUITextInfoPack.class);
		
		boolean alignLeft;
		if(pack==null||pack.getText().equals(newText))
		{
			//If the area doesn't exist or the text hasn't changed...
			return;
		}

		//Get all of the text "slots"
		Iterator<String> slots = pack.getChildren();
		int index = 0;
		
		while(slots.hasNext())
		{
			//Go through each slot...
			GUIInfoPack slot = 
					core.getInfoPackFrom(slots.next(), GUIInfoPack.class);
			
			//Figure out which character to write...
			char charToWrite;
			
			if(alignLeft)
			{
				if(index<upperText.length())
				{					
					charToWrite = upperText.charAt(index);
				}
			}
			else
			{
				
			}
			//Write it.
			slot.setSpriteID(charToWrite);
			if(index<upperText.length())
			{				
				//Set the character to the proper one...
				char nextChar = newText.charAt(index);
				slot.setSpriteID(nextChar);
				slot.setVisible(true);
				
				if(true)
				{			
					index
				}
				else
				{
					index++;
				}
			}
			else
			{	
				//Replace unused slots with default char
				if(pack.getDefaultChar()==' ')
				{
					//If space is default, set inivislbe
					slot.setVisible(false);
				}
				else
				{					
					slot.setVisible(true);
					slot.setSpriteID(pack.getDefaultChar());
				}
			}
		}
	}
	 */
	/*
	private void updateTextArea(final String name, final String text)
	{
		if(Boolean.parseBoolean(elements.get(name +"_HASCHANGED"))==false)
		{
			return;
		}
		//Step 1: Convert to upper case.
		String upperText = text.toUpperCase();
		
		//Step 2: Split into individual words.
		String[] words = upperText.split(" ");
		
		//Step 3: Get the settings of the text area.
		int maxCharsPerLine = 20;
		int maxNumLines = 10;
		
		try
		{
			maxCharsPerLine = Integer.parseInt(elements.get(name +"_CHARSPERLINE"));
			maxNumLines= Integer.parseInt(elements.get(name +"_NUMLINES"));
		}
		catch(NumberFormatException e)
		{
			LOGGER.log(Level.SEVERE, "Error setting up text area.");
			elements.put(name +"_CHARSPERLINE", maxCharsPerLine + "");
			elements.put(name +"_NUMLINES", maxNumLines + "");
		}
		//Step 4: Set the temp variables needed to put text in the text area.
		int charSlot = 0;
		int line = 0;
		boolean addSpace = false;
		
		for(String word:words)
		{
			//For each word...
			if(word.length()<=maxCharsPerLine-charSlot)
			{
				if(line<maxNumLines)
				{
					//If the word fits in the remaining space...
					writeWord(name, word, charSlot, line);
					charSlot+=word.length();
					addSpace = true;
				}
			}
			else if(word.length()>maxCharsPerLine)
			{
				//If the word cannot fit on a single line...
				//Go to the beginning of the next line...
				line++;	
				charSlot = 0;
				int numLines = word.length()/maxCharsPerLine;
				int charsProcessed = 0;
				int charsRemaining = 0;
				
				//For each line required to write the entire word...
				for(int index=0;index<=numLines;index++)
				{
					charsRemaining = word.length()-charsProcessed;
					if(line<maxNumLines)
					{
						//If there are still lines left...
	
						if(charsRemaining>=maxCharsPerLine)
						{						
							//If the remaining portion still needs a single line or more.
							writeWord(name, word.substring(charsProcessed, charsProcessed+maxCharsPerLine-1) + "-", charSlot, line);
							
							//Go to the beginning of the next line
							line++;	
							charSlot = 0;
							
							//Set the number of characters processed this iteration.
							charsProcessed+=maxCharsPerLine-1;
						}
						else
						{
							//If the remaining portion can be written now...
							writeWord(name, word.substring(charsProcessed, word.length()), charSlot, line);
							charSlot+=charsRemaining;	//Move cursor to end of word
							addSpace = true;
						}
					}
					else
					{
						index = numLines+1;
					}
				}
			}
			else if(word.length()>maxCharsPerLine-charSlot)
			{
				//If the word fits on the next line...
				line++;
				charSlot = 0;
				if(line<maxNumLines)
				{
					//If there are still lines left...
					writeWord(name, word, charSlot, line); //If the word will fit on the next line.
					charSlot+=word.length();
					addSpace = true;
				}
			}
			
			if(addSpace==true)
			{				
				writeWord(name, " ", charSlot, line);
				charSlot++;
				addSpace = false;
			}
		}
		
		for(int remainingLines = line;remainingLines<maxNumLines;remainingLines++)
		{
			for(int index=charSlot;index<maxCharsPerLine;index++)
			{
				GUIInfoPack pack 
					= core.getInfoPackFrom(elements.get(name + "_" + index + "_" + line), GUIInfoPack.class);
				if(pack!=null)
				{
					pack.setSpriteID(-1);
				}
			}
		}
		elements.put("BUY_DESC_HASCHANGED", "false");
	}
*/

	/*

	private void checkDesc()
	{
		core.send("REQUEST_CURSOR_POSITION");
		
		Iterator<GUIInfoPack> packs = core.getInfoPacksOfType(GUIInfoPack.class);
		boolean found = false;	//While loop sentinel
		while(packs.hasNext() && found == false)
		{
			GUIInfoPack pack = packs.next();
			
			if(pack!=null)
			{
				if(pack.getCategory().equals("HOVERABLE"))
				{
					if(pack.getXPos()-pack.getWidth()/2 <= this.mouseX
							&& pack.getXPos()+pack.getWidth()/2>=this.mouseX
							&&pack.getYPos()+pack.getHeight()/2>=this.mouseY
							&&pack.getYPos()-pack.getHeight()/2<=this.mouseY)
					{
						//If the cursor is hovering over this object...
						core.send(pack.getCommandOnHover(), pack.getValueOnHover());
						found = true;	//Sentinel to break loop early
					}
				}
			}
		}
		
		if(found==false)
		{
			elements.put("BUY_DESC", " ");
		//	elements.put("BUY_DESC_HASCHANGED", "true");
		}
	}
*/
/*
	private void updateDesc(final String[] message)
	{
		if(message.length>=1)
		{			
			if(message[0].equals(elements.get("BUY_DESC"))==false)
			{
				elements.put("BUY_DESC", message[0]);			
				elements.put("BUY_DESC_HASCHANGED", "true");
			}
		}
	}*/


	private void processHealthUpdate(final String[] message)
	{
		if(message.length>=3)
		{
			try
			{
				GUIInfoPack pack 
					= core.getInfoPackFrom(elementIDs.get("HEALTH_DATA"), GUIInfoPack.class);
				if(pack!=null)
				{
					pack.setCurVal(Integer.parseInt(message[1]));
					pack.setMaxVal(Integer.parseInt(message[2]));					
				}
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad values for health.");
			}
		}
	}
	
	private void processShieldUpdate(final String[] message)
	{
		if(message.length>=3)
		{
			try
			{
				GUIInfoPack pack 
					= core.getInfoPackFrom(elementIDs.get("SHIELD_DATA"), GUIInfoPack.class);
				if(pack!=null)
				{
					pack.setCurVal(Integer.parseInt(message[1]));					
					pack.setMaxVal(Integer.parseInt(message[2]));
				}
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad values for shield.");
			}
		}
	}
	
	private void processScoreUpdate(final String[] message)
	{
		if(message.length>=1)
		{
			try
			{
				GUIInfoPack pack 
					= core.getInfoPackFrom(elementIDs.get("SCORE_DATA"), GUIInfoPack.class);
				if(pack!=null)
				{
					pack.setCurVal(Integer.parseInt(message[0]));		
				}
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad values for score.");
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
