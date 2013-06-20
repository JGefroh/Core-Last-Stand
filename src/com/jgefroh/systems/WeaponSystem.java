package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.WeaponInfoPack;
import com.jgefroh.tests.Benchmark;


/**
 * System that handles weapons for entities.
 * @author Joseph Gefroh
 */
public class WeaponSystem implements ISystem
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
	private Level debugLevel = Level.FINER;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	public static enum FireMode {SEMI, AUTO, BURST}
	

	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public WeaponSystem(final Core core)
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
		isRunning = true;
		core.setInterested(this, "REQUEST_FIRE");
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
			fire(now);
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
		
		if(id.equals("REQUEST_FIRE"))
		{
			requestFire(message);
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Fires the weapon of all entities if they requested it.
	 * @param now	the current time
	 */
	public void fire(final long now)
	{
		Iterator<WeaponInfoPack> packs
			= core.getInfoPacksOfType(WeaponInfoPack.class);
		
		while(packs.hasNext())
		{
			WeaponInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				if(each.isFireRequested()||each.isInBurst())
				{
					if(now-each.getLastFired()>=each.getConsecutiveShotDelay())
					{
						fire(now, each);
						each.setLastFired(now);
							
						if(each.getFireMode()==FireMode.SEMI.ordinal())
						{//Fire mode = semi automatic
							each.setFireRequested(false);
						}
						else if(each.getFireMode()==FireMode.BURST.ordinal())
						{//Fire mode = burst fire
							if(each.getShotsThisBurst()<each.getBurstSize()-1)
							{//If still in a burst...
								each.setInBurst(true);
								each.setShotsThisBurst(each.getShotsThisBurst()+1);
							}
							else
							{//If burst is over.
								each.setInBurst(false);
								each.setShotsThisBurst(0);
								each.setLastFired(now+each.getBurstDelay());
							}
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
	private void fire(final long now, final WeaponInfoPack pack)
	{
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		for(int shotNum = 0; shotNum < pack.getNumShots(); shotNum++)
		{
			ecs.createBullet(pack.getOwner(),
					pack.getShotType(),
					pack.getDamage(),
					pack.getMaxRange());
		}
	}
	
	/**
	 * Requests a shot to be fired or not.
	 * @param message 	[0] contains the entityID of the entity that is firing
	 * 				 	[1] contains whether a shot is requested or not
	 */
	private void requestFire(final String[] message)
	{
		if(message.length>1)
		{
			WeaponInfoPack wip = 
					core.getInfoPackFrom(message[0], WeaponInfoPack.class);

			if(wip!=null)
			{	
				wip.setFireRequested(Boolean.parseBoolean(message[1]));
			}
		}
	}
	
	private void requestMode(final String[] message)
	{
		if(message.length>1)
		{
			WeaponInfoPack wip =
					core.getInfoPackFrom(message[0], WeaponInfoPack.class);

			if(wip!=null)
			{
				try
				{				
					int fireMode = Integer.parseInt(message[1]);
					wip.setFireMode(fireMode);
				}
				catch(NumberFormatException e)
				{
					LOGGER.log(Level.WARNING, "Error switching fire mode.");
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
