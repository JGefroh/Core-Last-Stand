package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.HealthBarInfoPack;

/**
 * Controls the display of health bars above entities.
 * @author Joseph Gefroh
 */
public class GUIHealthBarSystem implements ISystem
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
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public GUIHealthBarSystem(final Core core)
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
		core.setInterested(this, "DESTROYING_ENTITY");
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
		if(isRunning)
		{
			updateHealthBars();
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
		LOGGER.log(Level.FINE, "Wait interval set to: " + waitTime + " ms");
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
		
		if(id.equals("DESTROYING_ENTITY"))
		{
			destroyHealthBar(message);
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	
	private void updateHealthBars()
	{
		Iterator<HealthBarInfoPack> packs 
			= core.getInfoPacksOfType(HealthBarInfoPack.class);
		
		while(packs.hasNext())
		{
			HealthBarInfoPack each = packs.next();
			updatePosition(each);
			updateHealth(each);		
		}
	}
	
	private void updatePosition(final HealthBarInfoPack each)
	{
		double xPos = each.getOwnerXPos();
		double yPos = each.getOwnerYPos();
		
		each.setHealthBarXPos(xPos);
		each.setHealthBarYPos(yPos-32);
	}
	
	private void updateHealth(final HealthBarInfoPack each)
	{
		int health = each.getHealth();
		each.setHealthBarWidth(health);
	}

	private void destroyHealthBar(final String[] message)
	{
		if(message.length>=1)
		{
			HealthBarInfoPack pack 
				= core.getInfoPackFrom(message[0], HealthBarInfoPack.class);
			
			if(pack!=null)
			{
				core.removeEntity(pack.getHealthBar());
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
