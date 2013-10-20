package com.jgefroh.systems;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.DecayInfoPack;



/**
 * Handles the removal of bodies after a certain time period.
 * Date: 31MAY13
 * @author Joseph Gefroh
 */
public class DecaySystem extends AbstractSystem {
	//TODO: Make this create bodies, too.
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;

	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);


	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public DecaySystem(final Core core) {
		this.core = core;
	}
	

	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void work(final long now) {
		decay(now);
	}

	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	private void decay(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(DecayInfoPack.class);
		DecayInfoPack pack = core.getInfoPackOfType(DecayInfoPack.class);
		
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}

			if (now - pack.getLastUpdateTime() >= pack.getTimeUntilDecay() 
					&& pack.getLastUpdateTime() != 0) {	
				core.removeEntity(pack.getEntity());
			}
			else if (pack.getLastUpdateTime() == 0) {
				pack.setLastUpdateTime(now);
			}
		}
	}

	
	//////////////////////////////////////////////////
	// Debug
	//////////////////////////////////////////////////
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebug(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
}
