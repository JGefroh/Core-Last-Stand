package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.HealthInfoPack;
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
		core.setInterested(this, "REQUEST_SHIELD_ACTIVE");
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
			updateShields(now);
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
		
		if(id.equals("REQUEST_SHIELD_ACTIVE"))
		{
			setActive(message);
		}
		else if(id.equals("REQUEST_SHIELD"))
		{
			requestShield(message);
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Goes through all entities that can have a shield and updates them.
	 */
	private void updateShields(final long now)
	{
		Iterator<ShieldInfoPack> packs =
				core.getInfoPacksOfType(ShieldInfoPack.class);
		
		while(packs.hasNext())
		{
			ShieldInfoPack pack = packs.next();
			
			if(pack.isShieldActive()
					&&pack.getShieldCur()>0
					&&pack.getShield()!=null)
			{
				//If the shield is requested,
				//and if the shield has energy
				//and if it already exists...
				
				//Move a shield
				moveShield(pack);
				
				if(checkTimeToDrain(pack, now))
				{
					//Drain the shield if enough time has passed.
					decShield(pack);
					pack.setShieldLastDrained(now);
				}
				
				pack.setShieldLastUsed(now);
			}
			else if(pack.isShieldActive()
					&&pack.getShieldCur()>=pack.getShieldMin()
					&&pack.getShield()==null)
			{
				//If the shield is requested,
				//and if the shield has enough to create a new shield
				//and if there is no shield...
				
				//Create a shield
				createShield(pack);
				decShield(pack);					
				pack.setShieldLastUsed(now);
				pack.setShieldLastDrained(now);
			}
			else
			{
				core.removeEntity(pack.getShield());
				pack.setShield(null);	//IMPORTANT
				
				if(checkTimeToCharge(pack, now))
				{//If the proper time has passed to recharge the shield...
					incShield(pack);
					pack.setShieldLastRecharged(now);
				}
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
	 * Decrements the shield's charge according to its values.
	 * @param pack	the ShieldInfoPack of the shield
	 */
	private void decShield(final ShieldInfoPack pack)
	{
		int shield = pack.getShieldCur()-pack.getShieldDec();
		if(shield>=0)
		{
			pack.setShieldCur(shield);
		}
		else
		{
			pack.setShieldCur(0);
		}
	}

	/**
	 * Increments the shield's charge according to its values.
	 * @param pack	the ShieldInfoPack of the shield
	 */
	private void incShield(final ShieldInfoPack pack)
	{
		int shield = pack.getShieldCur()+pack.getShieldInc();
		if(shield<=pack.getShieldMax())
		{
			pack.setShieldCur(shield);
		}
		else
		{
			pack.setShieldCur(pack.getShieldMax());
		}
	}
	
	/**
	 * Checks to see if the right amount of time has passed to charge the shield.
	 * @param pack	the ShieldInfoPack of the shield
	 * @param now	the current time
	 * @return true if the time has passed; false otherwise
	 */
	private boolean checkTimeToCharge(final ShieldInfoPack pack, final long now)
	{
		if(now-pack.getShieldLastUsed()
		>=pack.getShieldRechargeDelay()
		&&now-pack.getShieldLastRecharged()
			>=pack.getShieldRechargeInterval())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if the right amount of time has passed to drain the shield.
	 * @param pack	the ShieldInfoPack of the shield
	 * @param now	the current time
	 * @return	true if the time has passed; false otherwise
	 */
	private boolean checkTimeToDrain(final ShieldInfoPack pack, final long now)
	{
		if(now-pack.getShieldLastDrained()
				>=pack.getShieldDrainInterval())
		{
			return true;
		}
		return false;
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
	 * Sends a shield update of the given entity id
	 * @param message	the shield of the entity
	 */
	private void requestShield(final String[] message)
	{
		if(message.length>=1)
		{
			ShieldInfoPack pack 
				= core.getInfoPackFrom(message[0], ShieldInfoPack.class);
			
			if(pack!=null)
			{
				core.send("SHIELD_UPDATE", pack.getOwner().getID(), 
							pack.getShieldCur() + "",
							pack.getShieldMax() + "");
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
