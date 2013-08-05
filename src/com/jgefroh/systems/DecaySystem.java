package com.jgefroh.systems;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.DecayInfoPack;



/**
 * Handles the removal of bodies after a certain time period.
 * Date: 31MAY13
 * @author Joseph Gefroh
 */
public class DecaySystem extends AbstractSystem
{
	//TODO: Make this create bodies, too.
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;

	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	
	//////////
	// INIT
	//////////
	public DecaySystem(final Core core)
	{
		this.core = core;
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////

	@Override
	public void work(final long now)
	{
		decay(now);
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	private void decay(final long now)
	{
		Iterator<DecayInfoPack> infoPacks = 
				core.getInfoPacksOfType(DecayInfoPack.class);
		while(infoPacks.hasNext())
		{
			DecayInfoPack pack = infoPacks.next();

			if(now-pack.getLastUpdateTime()>=pack.getTimeUntilDecay()&&pack.getLastUpdateTime()!=0)
			{	
				core.removeEntity(pack.getOwner());
			}
			else if(pack.getLastUpdateTime()==0)
			{
				pack.setLastUpdateTime(now);
			}
		}
	}
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebug(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
}
