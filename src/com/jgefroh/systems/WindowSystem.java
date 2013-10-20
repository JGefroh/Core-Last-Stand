package com.jgefroh.systems;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.DATA_WINDOW_RESOLUTION;



/**
 * Handles the window the game runs in.
 * 
 * This system depends on LWJGL and assumes a LWJGL rendering environment.
 * 
 * @author Joseph Gefroh
 */
public class WindowSystem extends AbstractSystem {
	
	//////////////////////////////////////////////////
	// Fields
//////////////////////////////////////////////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.OFF;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	/**Flag that shows if vSync is enabled or disabled.*/
	@SuppressWarnings("unused")
	private boolean vSyncEnabled;
	
	/**Flag that shows if the window border is enabled or disabled.*/
	private boolean borderEnabled;
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a window with the given width, height, and title.
	 * @param width		the desired width of the window
	 * @param height	the desired height of the window
	 * @param title		the desired title of the window
	 */
	public WindowSystem(final Core core, final int width, final int height,
			final String title) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
		setSize(width, height);
		setTitle(title);
		setFullScreenEnabled(false);
		setDisplayMode(findDisplayMode(width, height));
		// setDisplayMode(new DisplayMode(width, height));
		setVSyncEnabled(false);
		Display.setResizable(true);
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Unable to create Display; exiting...");
			System.exit(-1);
		}
	}
	

	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	@Override
	public void init() {
		core.setInterested(this, DefaultMessage.REQUEST_WINDOW_RESOLUTION);
	}

	@Override
	public void work(final long now) {
		if (Display.wasResized()) {
			sendWindowResolution();
		}
	}
	
	/**
	 * Acts on the following messages:
	 */
	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case REQUEST_WINDOW_RESOLUTION:
					sendWindowResolution();
					break;
				default:
					break;
			}
		}
	}

	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	/**
	 * Sets the size of the window.
	 * @param width		the pixel width of the window
	 * @param height	the pixel height of the window
	 * @throws IllegalArgumentException	thrown if either width or height <=0
	 */
	public void setSize(final int width, final int height)
			throws IllegalArgumentException {
		if (width > 0 && height > 0) {
			try {
				LOGGER.log(Level.FINE, "Window resize request: " + width + "X"
						+ height);
				Display.setDisplayMode(new DisplayMode(width, height));
			} 
			catch (LWJGLException e) {
				LOGGER.log(Level.SEVERE, "Unable to resize window to: " + width
						+ "X" + height);
				e.printStackTrace();
			}
		} 
		else {
			LOGGER.log(Level.SEVERE, "Bad size provided: " + width + "wX"
					+ height + "h");
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
	public DisplayMode findDisplayMode(final int width, final int height) {
		DisplayMode[] displayModes;
		try {
			LOGGER.log(Level.FINE, "Attempting to locate display mode: "
					+ width + "X" + height);
			displayModes = Display.getAvailableDisplayModes();

			for (DisplayMode each : displayModes) {
				if (Display.isFullscreen() == true) {
					if (each.getWidth() == width 
							&& each.getHeight() == height
							&& each.isFullscreenCapable()) {
						return each;
					}
				} 
				else {
					if (each.getWidth() == width 
							&& each.getHeight() == height) {
						return each;
					}
				}
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		LOGGER.log(Level.WARNING, "Unable to locate compatible display mode: "
				+ width + "X" + height);
		return new DisplayMode(width, height);
	}
	
	/**
	 * Returns the width of the window.
	 * @return	the pixel width of the window
	 */
	public int getWidth() {
		return Display.getWidth();
	}

	/**
	 * Returns the height of the window.
	 * @return	the pixel height of the window
	 */
	public int getHeight() {
		return Display.getHeight();
	}

	/**
	 * Returns the window title.
	 * @return	the String value of the window title
	 */
	public String getTitle() {
		return Display.getTitle();
	}
	
	private void sendWindowResolution() {
		Map<IPayload, String> parameters = new HashMap<IPayload, String>();
		parameters.put(DATA_WINDOW_RESOLUTION.WINDOW_HEIGHT, Display.getHeight() + "");
		parameters.put(DATA_WINDOW_RESOLUTION.WINDOW_WIDTH, Display.getWidth() + "");
		core.send(DefaultMessage.DATA_WINDOW_RESOLUTION, parameters);
	}
	/**
	 * Set the display mode of the window.
	 * @param displayMode	the display mode that was selected
	 */
	public void setDisplayMode(final DisplayMode displayMode) {
		try {
			Display.setDisplayMode(displayMode);
			sendWindowResolution();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Enable or disable full screen mode.
	 * @param fullScreen
	 */
	public void setFullScreenEnabled(final boolean fullScreen) {
		try {
			Display.setFullscreen(fullScreen);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the title of the window.
	 * @param title		the String title of the window
	 */
	public void setTitle(final String title) {
		Display.setTitle(title);
	}
	/**
	 * Adjusts whether the window's border is drawn.
	 * @param border	true to draw the border, false to remove.
	 */
	public void setBorderEnabled(final boolean border) {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", ""
				+ (!borderEnabled));
	}

	/**
	 * Adjusts whether Vertical Sync is enabled or disabled.
	 * @param vSync	true to enable Vertical Sync, false to disable.
	 */
	public void setVSyncEnabled(final boolean vSync) {
		Display.setVSyncEnabled(vSync);
	}

	//////////////////////////////////////////////////
	// Debug
	//////////////////////////////////////////////////
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level) {
		this.LOGGER.setLevel(level);
	}
}
