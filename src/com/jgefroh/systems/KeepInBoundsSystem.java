package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.KeepInBoundsInfoPack;
import com.jgefroh.messages.Message;


/**
 * Keeps objects within the confines of the screen.
 * 
 * 
 * @author Joseph Gefroh
 */
public class KeepInBoundsSystem extends AbstractSystem
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
	
	/**The native width of the playing area.*/
	private int nativeWidth = 1366;
	
	/**The native height of the playing area.*/
	private int nativeHeight = 768;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public KeepInBoundsSystem(final Core core)
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
		core.setInterested(this, Message.NATIVE_WIDTH);
		core.setInterested(this, Message.NATIVE_HEIGHT);
		core.send(Message.REQUEST_NATIVE_WIDTH, "");
		core.send(Message.REQUEST_NATIVE_HEIGHT,"");
	}

	@Override
	public void work(final long now)
	{
		checkPositions();
	}
	
	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
		
		Message msgID = Message.valueOf(id);
		switch(msgID)
		{
			case NATIVE_WIDTH:
				setNativeWidth(message);
				break;
			case NATIVE_HEIGHT:
				setNativeHeight(message);
				break;
		}
	}
	/////////
	// SYSTEM METHODS
	/////////
	
	private void checkPositions()
	{
		Iterator<KeepInBoundsInfoPack> packs
			= core.getInfoPacksOfType(KeepInBoundsInfoPack.class);
		
		while(packs.hasNext())
		{
			KeepInBoundsInfoPack pack = packs.next();
			
			//Check if too far to the left
			if(pack.getXPos()-pack.getWidth()/2<0)
			{
				pack.setXPos(0+pack.getWidth()/2);
			}
			///Check if too far to the right
			else if(pack.getXPos()+pack.getWidth()/2>nativeWidth)
			{
				pack.setXPos(nativeWidth-pack.getWidth()/2);
			}
			
			//Check if too far up
			if(pack.getYPos()-pack.getHeight()/2<0)
			{
				pack.setYPos(0+pack.getHeight()/2);
			}
			//Check if too far down
			else if(pack.getYPos()+pack.getHeight()/2>nativeHeight)
			{
				pack.setYPos(nativeHeight-pack.getHeight()/2);
			}
		}
	}
	
	/**
	 * Sets the native width of the playing area.
	 * @param message	[0] contains the width of the playing area.
	 */
	private void setNativeWidth(final String[] message)
	{
		if(message.length>0)
		{
			try
			{
				this.nativeWidth = Integer.parseInt(message[0]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to set native width to: " 
							+ message[0]);
			}
		}
	}
	
	/**
	 * Sets the native width of the playing area.
	 * @param message	[0] contains the height of the playing area.
	 */
	private void setNativeHeight(final String[] message)
	{
		if(message.length>0)
		{
			try
			{
				this.nativeHeight = Integer.parseInt(message[0]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to set native width to: " 
							+ message[0]);
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
