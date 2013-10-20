package com.jgefroh.systems;


import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.KeepInBoundsInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.DATA_NATIVE_RESOLUTION;


/**
 * Keeps objects within the confines of the screen.
 * @author Joseph Gefroh
 */
public class KeepInBoundsSystem extends AbstractSystem {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
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

	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public KeepInBoundsSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}

	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		LOGGER.log(Level.FINE, "Setting system values to default.");
		core.setInterested(this, DefaultMessage.DATA_NATIVE_RESOLUTION);
		core.send(DefaultMessage.REQUEST_NATIVE_RESOLUTION, null);
	}

	@Override
	public void work(final long now) {
		checkPositions();
	}
	
	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case DATA_NATIVE_RESOLUTION:
					setNativeResolution(message);
					break;
				default:
					break;
			}
		}
	}
	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	private void checkPositions() {
		Iterator<IEntity> packs = core.getEntitiesWithPack(KeepInBoundsInfoPack.class);
		KeepInBoundsInfoPack pack = core.getInfoPackOfType(KeepInBoundsInfoPack.class);
		
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if (pack.getXPos() - pack.getWidth() / 2 < 0) {
				//Check if too far to the left
				pack.setXPos(0 + pack.getWidth() / 2);
			}
			else if (pack.getXPos() + pack.getWidth() / 2 > nativeWidth) {
				///Check if too far to the right
				pack.setXPos(nativeWidth - pack.getWidth() / 2);
			}

			if (pack.getYPos() - pack.getHeight() / 2 < 0) {
				//Check if too far up
				pack.setYPos(0 + pack.getHeight() / 2);
			}
			else if (pack.getYPos() + pack.getHeight() / 2 > nativeHeight) {
				//Check if too far down
				pack.setYPos(nativeHeight - pack.getHeight() / 2);
			}
		}
	}
	
	/**
	 * Sets the native width of the playing area.
	 * @param message	[0] contains the width of the playing area.
	 */
	private void setNativeResolution(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		try {
			int width = Integer.parseInt(data.get(DATA_NATIVE_RESOLUTION.NATIVE_WIDTH));
			int height = Integer.parseInt(data.get(DATA_NATIVE_RESOLUTION.NATIVE_HEIGHT));
			this.nativeWidth = width;
			this.nativeHeight = height;
		}
		catch(NumberFormatException e) {
			LOGGER.log(Level.WARNING, "Unable to set native resolution.");
		}
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
