package com.jgefroh.systems;


import java.util.HashMap;
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
import com.jgefroh.infopacks.OutOfBoundsInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.DATA_NATIVE_RESOLUTION;
import com.jgefroh.messages.DefaultMessage.EVENT_DESTROYING_ENTITY;
import com.jgefroh.messages.DefaultMessage.EVENT_ENTITY_WITHIN_BOUNDS;


/**
 * Ensures that entities that go out of screen bounds are promptly destroyed.
 * 
 * 
 * @author Joseph Gefroh
 */
public class OutOfBoundsSystem extends AbstractSystem {
	
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
	public OutOfBoundsSystem(final Core core) {
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
		checkOutOfBounds();
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
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
	
	/**
	 * Checks to see if entities have gone outside of bounds.
	 */
	private void checkOutOfBounds()	{
		Iterator<IEntity> packs = core.getEntitiesWithPack(OutOfBoundsInfoPack.class);
		OutOfBoundsInfoPack pack = core.getInfoPackOfType(OutOfBoundsInfoPack.class);
		
		while (packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if (pack.isChecking() && !checkWithinBounds(pack)) {
				//If a checked entity has gone out of bounds...
				Map<IPayload, String> data = new HashMap<IPayload, String>();
				data.put(EVENT_DESTROYING_ENTITY.ENTITY_ID,  pack.getEntity().getID());
				data.put(EVENT_DESTROYING_ENTITY.REASON,  "OUT_OF_BOUNDS");
				core.send(DefaultMessage.EVENT_DESTROYING_ENTITY, data);
				core.removeEntity(pack.getEntity());
			}
			else if (!pack.isChecking() && checkWithinBounds(pack)) {
				//If checking has activated for a previous unchecked entity...
				pack.setChecking(true);
				Map<IPayload, String> data = new HashMap<IPayload, String>();
				data.put(EVENT_ENTITY_WITHIN_BOUNDS.ENTITY_ID,  pack.getEntity().getID());
				data.put(EVENT_ENTITY_WITHIN_BOUNDS.IS_WITHIN_BOUNDS, "true");
				core.send(DefaultMessage.EVENT_ENTITY_WITHIN_BOUNDS, data);
			}
		}
	}
	
	/**
	 * Checks to see if within bounds.
	 * @param pack	the pack set to the entity to check
	 * @return	true if within bounds; false otherwise
	 */
	private boolean checkWithinBounds(final OutOfBoundsInfoPack pack) {
		if (pack.getXPos()-pack.getWidth()/2>=0
			&& pack.getXPos()+pack.getWidth()/2<=nativeWidth
			&& pack.getYPos()-pack.getHeight()/2>=0
			&& pack.getYPos()+pack.getHeight()/2<=nativeHeight) {
			return true;
		}
		return false;
	}

	
	//////////////////////////////////////////////////
	// Message
	//////////////////////////////////////////////////
	
	/**
	 * Sets the native width and height of the playing area.
	 * @param message	the payload
	 */
	private void setNativeResolution(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		
		try {
			int height = Integer.parseInt(data.get(DATA_NATIVE_RESOLUTION.NATIVE_HEIGHT));
			int width = Integer.parseInt(data.get(DATA_NATIVE_RESOLUTION.NATIVE_WIDTH));
			this.nativeHeight = height;
			this.nativeWidth = width;
		}
		catch (NumberFormatException e) {
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