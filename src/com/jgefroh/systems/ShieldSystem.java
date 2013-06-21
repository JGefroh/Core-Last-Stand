package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.ShieldInfoPack;

/**
 * Controls the display of health bars above entities.
 * @author Joseph Gefroh
 */
public class ShieldSystem implements ISystem
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
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public ShieldSystem(final Core core)
	{
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		core.setInterested(this, "REQUEST_SHIELD");
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
			updateShields();
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
		
		if(id.equals("REQUEST_SHIELD"))
		{
			setActive(message);
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Goes through all entities that can have a shield and updates them.
	 */
	private void updateShields()
	{
		Iterator<ShieldInfoPack> packs =
				core.getInfoPacksOfType(ShieldInfoPack.class);
		
		while(packs.hasNext())
		{
			ShieldInfoPack pack = packs.next();
			
			if(pack.isShieldActive())
			{
				//If the user has requested the shield to be up
				if(pack.getShield()==null)
				{
					//If the shield does not exist, create it...
					createShield(pack);
				}
				else
				{
					//Move the shield to the proper position
					moveShield(pack);
				}
			}
			else
			{
				//If the user has requested to remove the shield...
				core.removeEntity(pack.getShield());
				pack.setShield(null);	//IMPORTANT
			}

		}
	}
	
	/**
	 * Creates a shield for an Entity without a shield.
	 * @param pack	the ShieldInfoPack of the entity
	 */
	private void createShield(final ShieldInfoPack pack)
	{
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		pack.setShield(ecs.createShield(pack.getOwner()));
	}
	
	/**
	 * Moves an entity's shield to the correct position (at the entity)
	 * @param pack	the ShieldInfoPack of the entity
	 */
	private void moveShield(final ShieldInfoPack pack)
	{
		double xPos = pack.getXPos();
		double yPos = pack.getYPos();
		
		pack.setShieldXPos(xPos);
		pack.setShieldYPos(yPos);
	}
	
	/**
	 * Sets an entity's shield request flag.
	 * @param message	[0] contains the ID of the entity
	 * 					[1] contains the boolean value to set the flag to
	 */
	private void setActive(final String[] message)
	{
		if(message.length>1)
		{
			ShieldInfoPack pack = core.getInfoPackFrom(message[0], ShieldInfoPack.class);
			if(pack!=null)
			{
				pack.setActive(Boolean.parseBoolean(message[1]));
			}
		}
	}

	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
	
}
