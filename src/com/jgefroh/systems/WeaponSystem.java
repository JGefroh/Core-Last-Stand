package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.WeaponInfoPack;


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
	private Level debugLevel = Level.OFF;
	
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
	public WeaponSystem(final Core core)
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
		core.setInterested(this, "REQUEST_FIRE");
		core.setInterested(this, "CEASE_FIRE");
		core.setInterested(this, "SWITCH_WEAPON_1");
		core.setInterested(this, "SWITCH_WEAPON_2");
		core.setInterested(this, "SWITCH_WEAPON_3");
		core.setInterested(this, "SWITCH_WEAPON_4");
		core.setInterested(this, "SWITCH_WEAPON_5");
		core.setInterested(this, "SWITCH_WEAPON_6");
		core.setInterested(this, "SWITCH_WEAPON_7");
		core.setInterested(this, "SWITCH_WEAPON_8");
		core.setInterested(this, "SWITCH_WEAPON_9");
		core.setInterested(this, "SWITCH_WEAPON_0");
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
		else if(id.startsWith("SWITCH_WEAPON_"))
		{
			switchWeapon(message, id.charAt(id.length()-1));
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	public void fire(final long now)
	{
		Iterator<WeaponInfoPack> packs
			= core.getInfoPacksOfType(WeaponInfoPack.class);
		
		while(packs.hasNext())
		{
			WeaponInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				if(now-each.getLastUpdated()>=each.getInterval())
				{
					//If the weapon has cycled...
					if(each.isFireRequested())
					{
						//If the trigger was pulled...
						each.setLastUpdated(now);
						if(each.getAmmo()>0)
						{							
							//If there is enough ammo...
							fire(now, each);	//Fire
							decAmmo(each);		//Decrement the ammo
							incSpread(each);	//Add recoil
						}
					}
					else
					{
						decSpread(each);	//Decrement the recoil (change)
						each.setShotsFiredThisBurst(0);
					}
				}
			}
		}
	}
	
	private void fire(final long now, final WeaponInfoPack pack)
	{
		if(pack.getShotsFiredThisBurst()<pack.getBurstSize())
		{
			//If firing as part of a burst or single request
			EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
			for(int shot = 0;shot<pack.getNumShots();shot++)
			{			
				ecs.createBullet(pack.getOwner(), pack.getDamage(), pack.getMaxRange(), pack.getCurSpread());	
			}
			pack.setShotsFiredThisBurst(pack.getShotsFiredThisBurst()+1);
		}
		else if(pack.getBurstSize()==0)
		{
			EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
			for(int shot = 0;shot<pack.getNumShots();shot++)
			{			
				ecs.createBullet(pack.getOwner(), pack.getDamage(), pack.getMaxRange(), pack.getCurSpread());	
			}
			pack.setFireRequested(false);
		}
		else
		{
			//If burst limit reached
			pack.setShotsFiredThisBurst(0);
			pack.setFireRequested(false);
			pack.setLastUpdated(now+pack.getDelayAfterBurst());
		}
	}
	
	private void requestFire(final String[] message)
	{
		if(message.length>0)
		{
			WeaponInfoPack wip = 
					core.getInfoPackFrom(message[0], WeaponInfoPack.class);

			if(wip!=null)
			{	
				wip.setFireRequested(true);
			}
		}
	}
	
	private void switchWeapon(final String[] message, final char weapon)
	{
		if(message.length>0)
		{
			WeaponInfoPack wip = 
					core.getInfoPackFrom(message[0], WeaponInfoPack.class);
			
			if(wip!=null)
			{	
				try
				{
					int slot = Integer.parseInt(weapon+"");
					wip.setCurrentWeapon(slot);
					System.out.println("Equipped: " + weapon);
				}
				catch(NumberFormatException e)
				{
					LOGGER.log(Level.WARNING, "Can't switch weapon: bad num.");
				}
			}
		}
	}
	
	private void decAmmo(final WeaponInfoPack pack)
	{
		pack.setAmmo(pack.getAmmo()-1);
		pack.setCurSpread(pack.getCurSpread()+pack.getIncSpread());
	}
	
	private void incSpread(final WeaponInfoPack pack)
	{
		if(pack.getCurSpread()+pack.getIncSpread()<pack.getMaxSpread())
		{
			pack.setCurSpread(pack.getCurSpread()+pack.getIncSpread());
		}
		else
		{
			pack.setCurSpread(pack.getMaxSpread());
		}
	}
	
	private void decSpread(final WeaponInfoPack pack)
	{
		if(pack.getCurSpread()-pack.getIncSpread()>0)
		{
			pack.setCurSpread(pack.getCurSpread()-pack.getIncSpread());
		}
		else
		{
			pack.setCurSpread(0);
		}
	}
}
