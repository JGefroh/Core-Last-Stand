package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.MouseTrackInfoPack;


/**
 * Rotates entities towards the mouse.
 * @author Joseph Gefroh
 */
public class MouseTrackingSystem implements ISystem
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
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	/**The X world position of the mouse.*/
	private double mouseX;
	
	/**The Y world position of the mouse.*/
	private double mouseY;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public MouseTrackingSystem(final Core core)
	{
		this.core = core;
		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		isRunning = true;
		core.setInterested(this, "INPUT_CURSOR_POSITION");
	}

	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "System started.");
		this.isRunning = true;
	}

	@Override
	public void work(final long now)
	{
		if(this.isRunning)
		{
			turnTowardsMouse(now);			
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		this.isRunning = false;
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
		if(id.equals("INPUT_CURSOR_POSITION"))
		{
			updateCursorPosition(message);
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Turns all entities with a MouseTrackInfoPack towards the mouse.
	 */
	public void turnTowardsMouse(final long now)
	{
		core.send("REQUEST_CURSOR_POSITION");
		Iterator<MouseTrackInfoPack> packs 
			= core.getInfoPacksOfType(MouseTrackInfoPack.class);
		while(packs.hasNext())
		{
			MouseTrackInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				double bearing = calculateBearing(each.getXPos(), each.getYPos());
				each.setBearing(bearing);
			}
		}
	}
	
	/**
	 * Updates this System's last known world mouse coordinates.
	 * @param message
	 */
	private void updateCursorPosition(final String[] message)
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
				LOGGER.log(Level.SEVERE, 
						"Error updating cursor position - bad format."
						+ "Quitting...");
				e.printStackTrace();
				System.exit(-1);
			}
		}
		else
		{
			LOGGER.log(Level.SEVERE, 
					"Error updating cursor position - unexpected response."
					+ "Quitting...");
			System.exit(-1);
		}
	}
	
	/**
	 * Calculates the angle necessary to face the mouse from a given position.
	 * @param x	the x coordinate of the object
	 * @param y	the y coordinate of the object
	 * @return	the angle to turn the object in order to face the mouse
	 */
	private double calculateBearing(final double x, final double y)
	{
		double adj = this.mouseX-x;
		double opp = this.mouseY-y;
		
		double bearing = Math.toDegrees(Math.atan2(opp, adj));
		return bearing;
	}
	
}
