package com.jgefroh.systems;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;



/**
 * Handles the window the game runs in.
 * 
 * This system depends on LWJGL and assumes a LWJGL rendering environment.
 * 
 * Date: 31MAY13
 * @author Joseph Gefroh
 * @version 0.2.0
 */
public class WindowSystem implements ISystem
{
	//TODO: Fix.
	
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
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	/**Flag that shows if vSync is enabled or disabled.*/
	@SuppressWarnings("unused")
	private boolean vSyncEnabled;
	
	/**Flag that shows if the window border is enabled or disabled.*/
	private boolean borderEnabled;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a window with the given width, height, and title.
	 * @param width		the desired width of the window
	 * @param height	the desired height of the window
	 * @param title		the desired title of the window
	 */
	public WindowSystem(final Core core, final int width, final int height, final String title)
	{
		this.core = core;
		init();
		setSize(width, height);
		setTitle(title);
		setFullScreenEnabled(false);
		setDisplayMode(findDisplayMode(width, height));
		//setDisplayMode(new DisplayMode(width, height));
		setVSyncEnabled(false);
		Display.setResizable(true);
		try
		{			
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Unable to create Display; exiting...");
			System.exit(-1);
		}
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		this.isRunning = true;
		core.setInterested(this, "REQUEST_WINDOW_WIDTH");
		core.setInterested(this, "REQUEST_WINDOW_HEIGHT");

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
		if(Display.wasResized())
		{
			core.send("WINDOW_RESIZED", "");
			core.send("WINDOW_WIDTH", getWidth() + "");
			core.send("WINDOW_HEIGHT", getHeight() + "");
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
	}
	
	@Override
	public void setLast(final long last)
	{
		this.last = last;
	}
	
	/**
	 * Acts on the following messages:
	 * REQUEST_WINDOW_HEIGHT
	 * REQUEST_WINDOW_WIDTH
	 */
	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINER, "Received message: " + id);
		if(id.equals("REQUEST_WINDOW_WIDTH"))
		{
			core.send("WINDOW_WIDTH", Display.getWidth() + "");
		}
		else if(id.equals("REQUEST_WINDOW_HEIGHT"))
		{
			core.send("WINDOW_HEIGHT", Display.getHeight() + "");
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Sets the size of the window.
	 * @param width		the pixel width of the window
	 * @param height	the pixel height of the window
	 * @throws IllegalArgumentException	thrown if either width or height <=0
	 */
	public void setSize(final int width, final int height) throws IllegalArgumentException
	{
		if(width>0&&height>0)
		{
			try
			{
				LOGGER.log(Level.FINE, "Window resize request: " 
							+ width + "X" + height);
				Display.setDisplayMode(new DisplayMode(width, height));
			}
			catch(LWJGLException e)
			{
				LOGGER.log(Level.SEVERE, "Unable to resize window to: " 
							+ width + "X" + height);
				e.printStackTrace();
			}
		}
		else
		{
			LOGGER.log(Level.SEVERE, "Bad size provided: " 
					+ width + "wX" + height + "h");
			throw new IllegalArgumentException(
					"Error | Window width and height must be > 0");
		}
	}
	
	/**
	 * Finds a compatible display mode.
	 * @param width		the desired width
	 * @param height	the desired height
	 * @return			a compatible display mode
	 */
	public DisplayMode findDisplayMode(final int width, final int height)
	{
		DisplayMode[] displayModes;
		try
		{
			LOGGER.log(Level.FINE, "Attempting to locate display mode: " 
								+ width + "X" + height);
			displayModes = Display.getAvailableDisplayModes();
			
			for(DisplayMode each:displayModes)
			{
				if(Display.isFullscreen()==true)
				{
					if(each.getWidth()==width&&each.getHeight()==height
							&&each.isFullscreenCapable()==true)
					{
						return each;
					}
				}
				else
				{
					if(each.getWidth()==width&&each.getHeight()==height)
					{
						return each;
					}
				}
			}
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		LOGGER.log(Level.WARNING, "Unable to locate compatible display mode: " 
							+ width + "X" + height);
		return new DisplayMode(width, height);
	}
	
	//////////
	// GETTERS
	//////////
	/**
	 * Returns the width of the window.
	 * @return	the pixel width of the window
	 */
	public int getWidth()
	{
		return Display.getWidth();
	}
	
	/**
	 * Returns the height of the window.
	 * @return	the pixel height of the window
	 */
	public int getHeight()
	{
		return Display.getHeight();
	}
	
	/**
	 * Returns the window title.
	 * @return	the String value of the window title
	 */
	public String getTitle()
	{
		return Display.getTitle();
	}
	
	
	//////////
	// SETTERS
	//////////
	/**
	 * Set the display mode of the window.
	 * @param displayMode	the display mode that was selected
	 */
	public void setDisplayMode(final DisplayMode displayMode)
	{
		try
		{
			Display.setDisplayMode(displayMode);
			core.send("WINDOW_WIDTH", Display.getWidth() + "");
			core.send("WINDOW_HEIGHT", Display.getHeight() + "");
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Enable or disable full screen mode.
	 * @param fullScreen
	 */
	public void setFullScreenEnabled(final boolean fullScreen)
	{
		try
		{
			Display.setFullscreen(fullScreen);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the title of the window.
	 * @param title		the String title of the window
	 */
	public void setTitle(final String title)
	{
		Display.setTitle(title);
	}
	/**
	 * Adjusts whether the window's border is drawn.
	 * @param border	true to draw the border, false to remove.
	 */
	public void setBorderEnabled(final boolean border)
	{
		System.setProperty("org.lwjgl.opengl.Window.undecorated", 
							""+(!borderEnabled));
	}
	
	/**
	 * Adjusts whether Vertical Sync is enabled or disabled.
	 * @param vSync	true to enable Vertical Sync, false to disable.
	 */
	public void setVSyncEnabled(final boolean vSync)
	{
		Display.setVSyncEnabled(vSync);
	}
}
