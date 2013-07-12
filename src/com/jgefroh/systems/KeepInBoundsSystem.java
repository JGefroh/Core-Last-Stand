package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AIInfoPack;
import com.jgefroh.infopacks.KeepInBoundsInfoPack;
import com.jgefroh.tests.Benchmark;


/**
 * Keeps objects within the confines of the screen.
 * 
 * 
 * @author Joseph Gefroh
 */
public class KeepInBoundsSystem implements ISystem
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
		core.setInterested(this, "NATIVE_WIDTH");
		core.setInterested(this, "NATIVE_HEIGHT");
		core.send("REQUEST_NATIVE_WIDTH", "");
		core.send("REQUEST_NATIVE_HEIGHT", "");
		isRunning = true;		
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
		checkPositions();
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
		if(id.equals("NATIVE_WIDTH"))
		{
			setNativeWidth(message);
		}
		else if(id.equals("NATIVE_HEIGHT"))
		{
			setNativeHeight(message);
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
