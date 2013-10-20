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
import com.jgefroh.infopacks.WeaponInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_CREATE;
import com.jgefroh.messages.DefaultMessage.COMMAND_FIRE;


/**
 * System that handles weapons for entities.
 * @author Joseph Gefroh
 */
public class WeaponSystem extends AbstractSystem {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINER;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	public static enum FireMode 	{SEMI, AUTO, BURST}

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public WeaponSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	@Override
	public void init() {
		core.setInterested(this, DefaultMessage.COMMAND_FIRE);
	}

	@Override
	public void work(final long now) {
		fire(now);
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case COMMAND_FIRE:
					requestFire(message);
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
	 * Fires the weapon of all entities if they requested it.
	 * @param now	the current time
	 */
	public void fire(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(WeaponInfoPack.class);
		WeaponInfoPack pack = core.getInfoPackOfType(WeaponInfoPack.class);
		
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if (pack.isFireRequested() || pack.isInBurst()) {
				if (now - pack.getLastFired() >= pack.getConsecutiveShotDelay()) {
					fire(now, pack);
					pack.setLastFired(now);

					if (pack.getFireMode() == FireMode.SEMI.ordinal()) {
						// Fire mode semi automatic
						pack.setFireRequested(false);
					} 
					else if (pack.getFireMode() == FireMode.BURST.ordinal()) {
						// Fire mode burst fire
						if (pack.getShotsThisBurst() < pack.getBurstSize() - 1) {
							// If still in a burst...
							pack.setInBurst(true);
							pack.setShotsThisBurst(pack.getShotsThisBurst() + 1);
						} 
						else {// If burst is over.
							pack.setInBurst(false);
							pack.setShotsThisBurst(0);
							pack.setLastFired(now + pack.getBurstDelay());
						}
					}
				}
			}
		}
	}
	
	/**
	 * Fires a shot.
	 * @param now	the current time
	 * @param pack	the InfoPack of the entity to fire for
	 */
	private void fire(final long now, final WeaponInfoPack pack) {
		for (int shotNum = 0; shotNum < pack.getNumShots(); shotNum++) {
			int type = pack.getShotType();
			String shotType;

			switch (type) {
				case 9:
					shotType = "EXPLOSIVE";
					break;
				default:
					shotType = "BULLET";
					break;
			}
			Map<IPayload, String> parameters = new HashMap<IPayload, String>();
			parameters.put(COMMAND_CREATE.TYPE_TO_CREATE, shotType);
			parameters.put(COMMAND_CREATE.OWNER_ID, pack.getEntity().getID());
			core.send(DefaultMessage.COMMAND_CREATE, parameters);
		}
	}
	
	/**
	 * Requests a shot to be fired or not.
	 * @param message 	[0] contains the entityID of the entity that is firing
	 * 				 	[1] contains whether a shot is requested or not
	 */
	private void requestFire(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		WeaponInfoPack wip = core.getInfoPackFrom(data.get(COMMAND_FIRE.ENTITY_ID),
				WeaponInfoPack.class);

		if (wip != null) {
			wip.setFireRequested(Boolean.parseBoolean(data.get(COMMAND_FIRE.IS_FIRE_REQUESTED)));
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
