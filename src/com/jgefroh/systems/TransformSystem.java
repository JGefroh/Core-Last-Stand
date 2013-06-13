package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Vector;
import com.jgefroh.infopacks.MovementInfoPack;


/**
 * System that handles movement and repositioning of entities.
 * @author Joseph Gefroh
 */
public class TransformSystem implements ISystem
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
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new TransformSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public TransformSystem(final Core core)
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
			move(now);			
		}
		else
		{
			stop();
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
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Moves all entities that are requesting moves based on their velocities.
	 */
	public void move(final long now)
	{
		Iterator<MovementInfoPack> packs 
			= core.getInfoPacksOfType(MovementInfoPack.class);

		while(packs.hasNext())
		{
			MovementInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				if(now-each.getLastUpdated()>=each.getInterval())
				{
					each.setLastUpdated(now);

					Vector totalV = each.getTotalMovementVector();
					LOGGER.log(Level.FINEST, "Translating " + each.getOwner().getName() + " by " + totalV);
					each.setXPos((each.getXPos()+totalV.getVX()));
					each.setYPos((each.getYPos()+totalV.getVY()));					
					//Translate object by total vector amount.
					if(totalV.isContinuous()==false)
					{						
						each.setTotalMovementVector(new Vector());
					}
				}
			}
		}
	}
	

	public void setInterval(final IEntity entity, final long interval)
	{
		MovementInfoPack pack =
				core.getInfoPackFrom(entity, MovementInfoPack.class);
		pack.setInterval(interval);
	}
}
