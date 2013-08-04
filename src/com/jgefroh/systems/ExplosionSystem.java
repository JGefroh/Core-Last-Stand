package com.jgefroh.systems;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.components.CollisionComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.ExplosionInfoPack;



/**
 * This is a temporary system used to destroy entities.
 * @author Joseph Gefroh
 */
public class ExplosionSystem implements ISystem
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
	
	private double mouseX;
	private double mouseY;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public ExplosionSystem(final Core core)
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
		setDebugLevel(this.debugLevel);
		core.setInterested(this, "DETONATE");
		core.setInterested(this, "EXPLOSION_CONTACT");
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
		updateExplosions(now);
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
		if(id.equals("DETONATE"))
		{
			processDetonate(message);
		}
		else if(id.equals("EXPLOSION_CONTACT"))
		{
			processContact(message);
		}
	}
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Increases the size of explosions at certain increments.
	 * @param now	the current time
	 */
	private void updateExplosions(final long now)
	{
		Iterator<ExplosionInfoPack> packs = 
				core.getInfoPacksOfType(ExplosionInfoPack.class);
		
		while(packs.hasNext())
		{
			ExplosionInfoPack pack = packs.next();
			
			if(now-pack.getLastUpdated()>=pack.getUpdateInterval()&&pack.getLastUpdated()!=0)
			{//If time to update...
				
				if(pack.getWidth()<pack.getWidthMax())
				{//Increase width by X amount
					pack.setWidth(pack.getWidth()+pack.getWidthInc());
				}

				if(pack.getHeight()<pack.getHeightMax())
				{//Increase height by Y amount
					pack.setHeight(pack.getHeight()+pack.getHeightInc());
				}
				pack.setLastUpdated(now);
			}
			else if(pack.getLastUpdated()==0)
			{
				pack.setLastUpdated(now);
			}
		}
	}
	
	/**
	 * Turns a bullet into an explosion using the ECS.
	 * @param message	[0] contains the ID of the bullet/shot.
	 */
	private void processDetonate(final String[] message)
	{
		if(message.length>=2)
		{
			core.send("CREATE", "EXPLOSION", message[0]);
		}
	}
	
	/**
	 * Deals damage to entities caught in the explosion if allowed.
	 * @param message	[0] Contains the ID of the explosion
	 * 					[1] Contains the ID of the entity in the explosion
	 */
	private void processContact(final String[] message)
	{
		if(message.length>=2)
		{
			String sourceID = message[0];
			String victimID = message[1];
			
			ExplosionInfoPack pack = core.getInfoPackFrom(sourceID, ExplosionInfoPack.class);
			
			if(pack!=null)
			{
				int numHits = pack.getNumHits(victimID);
				
				if(numHits==0)
				{//If the entity has not already been damaged by this explosion...
					core.send("DAMAGE", sourceID, victimID);
					pack.setNumHits(victimID, numHits+1);
				}
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
