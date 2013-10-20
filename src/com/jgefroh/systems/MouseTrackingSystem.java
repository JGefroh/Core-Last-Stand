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
import com.jgefroh.infopacks.MouseTrackInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.DATA_INPUT_CURSOR_POSITION;


/**
 * Rotates entities towards the mouse.
 * @author Joseph Gefroh
 */
public class MouseTrackingSystem extends AbstractSystem {
	
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
	
	/**The X world position of the mouse.*/
	private double mouseX;
	
	/**The Y world position of the mouse.*/
	private double mouseY;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public MouseTrackingSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}

	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		core.setInterested(this, DefaultMessage.DATA_INPUT_CURSOR_POSITION);
	}

	@Override
	public void work(final long now) {
		turnTowardsMouse(now);			
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case DATA_INPUT_CURSOR_POSITION:
					updateCursorPosition(message);
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
	 * Turns all entities with a MouseTrackInfoPack towards the mouse.
	 */
	public void turnTowardsMouse(final long now) {
		core.send(DefaultMessage.REQUEST_CURSOR_POSITION, null);
		
		Iterator<IEntity> packs = core.getEntitiesWithPack(MouseTrackInfoPack.class);
		MouseTrackInfoPack pack = core.getInfoPackOfType(MouseTrackInfoPack.class);
		
		while (packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			double bearing = calculateBearing(pack.getXPos(), pack.getYPos());
			pack.setBearing(bearing);
		}
	}
	
	/**
	 * Updates this System's last known world mouse coordinates.
	 * @param message
	 */
	private void updateCursorPosition(final Map<IPayload, String> data)  {
		if (data == null || data.size() < 2) {
			return;
		}
		
		try {
			int yPos = Integer.parseInt(data.get(DATA_INPUT_CURSOR_POSITION.MOUSE_Y));
			int xPos = Integer.parseInt(data.get(DATA_INPUT_CURSOR_POSITION.MOUSE_X));
			this.mouseX = xPos;
			this.mouseY = yPos;
		}
		catch(NumberFormatException e) {
			LOGGER.log(Level.SEVERE, 
					"Error updating cursor position - bad format."
					+ "Quitting...");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Calculates the angle necessary to face the mouse from a given position.
	 * @param x	the x coordinate of the object
	 * @param y	the y coordinate of the object
	 * @return	the angle to turn the object in order to face the mouse
	 */
	private double calculateBearing(final double x, final double y) {
		double adj = this.mouseX-x;
		double opp = this.mouseY-y;
		
		double bearing = Math.toDegrees(Math.atan2(opp, adj));
		return bearing;
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
