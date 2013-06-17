package com.jgefroh.components;

import java.util.ArrayList;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.Weapon;

/**
 * Contains data allowing an entity to have and use weapons.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class WeaponComponent implements IComponent
{
	//TODO: simplify, allow for multiple fire modes
	//////////
	// DATA
	//////////
	/**The owner of the component.*/
	private IEntity owner;

	/**The currently equipped weapon*/
	private Weapon currentWeapon;
	
	/**The weapons this entity owns.*/
	private ArrayList<Weapon> weapons;
	
	/**The amount of time to wait in-between shots, in milliseconds.*/
	private long interval;
	
	/**The time a weapon was last fired.*/
	private long lastUpdated;
	
	/**Flag that indicates a weapon fire request.*/
	private boolean isFireRequested;
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of the component
	 */
	public WeaponComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
		weapons = new ArrayList<Weapon>();
	}
	

	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Gets the update interval of the component.
	 * @return	the time, in ms, to wait before attempting an update
	 */
	public long getInterval()
	{
		return this.interval;
	}
	
	/**
	 * Gets the time the component was last updated.
	 * @return	the time, in ms, the component was last updated
	 */
	public long getLastUpdated()
	{
		return this.lastUpdated;
	}
	
	/**
	 * Gets the currently equipped weapon.
	 * @return	the currently equipped weapon
	 */
	public Weapon getCurrentWeapon()
	{
		return this.currentWeapon;
	}
	
	/**
	 * Gets whether there was a request to fire the weapon.
	 * @return	true if firing was requested; false otherwise
	 */
	public boolean isFireRequested()
	{
		return this.isFireRequested;
	}
	
	/**
	 * Gets the amount of ammunition left for the current weapon.
	 * @return	the amount of ammo left;0 if no current weapon
	 */
	public int getAmmo()
	{
		if(currentWeapon!=null)
		{
			return currentWeapon.getAmmo();
		}
		return 0;
	}
	
	/**
	 * Gets the amount of damage the current weapon does.
	 * @return	the amount of damage the current weapon does
	 */
	public int getDamage()
	{
		if(currentWeapon!=null)
		{
			return currentWeapon.getDamage();
		}
		return 0;
	}
	
	/**
	 * Gets the maximum range of the weapon
	 * @return	the maximum range of the weapon
	 */
	public int getMaxRange()
	{
		if(currentWeapon!=null)
		{
			return currentWeapon.getMaxRange();
		}
		return 0;
	}
	
	/**
	 * Gets the current spread of the weapon, in degrees.
	 * @return	the current spread of the weapon, in degrees.
	 */
	public double getCurSpread()
	{
		if(currentWeapon!=null)
		{
			return currentWeapon.getCurSpread();
		}
		return 0;
	}
	
	/**
	 * Gets the spread increment.
	 * @return	the amount the spread increases by every time it is fired
	 */
	public double getIncSpread()
	{
		if(currentWeapon!=null)
		{			
			return currentWeapon.getIncSpread();
		}
		return 0;
	}
	
	/**
	 * Gets the maximum spread of the weapon.
	 * @return	the maximum spread of the weapon, in degrees
	 */
	public double getMaxSpread()
	{
		if(currentWeapon!=null)
		{			
			return currentWeapon.getMaxSpread();
		}
		return 0;
	}
	
	/**
	 * Gets the number of shots the weapon fires per fire request
	 * @return	the number of shots the weapon fires per fire request
	 */
	public int getNumShots()
	{
		if(currentWeapon!=null)
		{			
			return currentWeapon.getNumShots();
		}
		return 0;
	}
	
	/**
	 * Gets the number of shots fired so far as part of the burst.
	 * @return	the number of shots fired so far
	 */
	public int getShotsFiredThisBurst()
	{
		if(currentWeapon!=null)
		{
			return currentWeapon.getShotsFiredThisBurst();
		}
		return 0;
	}
	
	/**
	 * Gets the number of shots of the burst.
	 * @return the number of shots to fire in the burst
	 */
	public int getBurstSize()
	{
		if(currentWeapon!=null)
		{
			return currentWeapon.getBurstSize();
		}
		return 0;
	}
	
	/**
	 * Gets the delay after the burst.
	 * @return	the time to wait until the gun is ready
	 */
	public long getDelayAfterBurst()
	{
		if(currentWeapon!=null)
		{
			return currentWeapon.getDelayAfterBurst();
		}
		return 0;
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	/**
	 * Set the update interval of the component.
	 * @param interval	the time, in ms, to wait before attempting an update
	 */
	public void setInterval(final long interval)
	{
		this.interval = interval;
	}
	
	/**
	 * Set the time the component was last updated.
	 * @param lastUpdated	the time, in ms, the component was last updated
	 */
	public void setLastUpdated(final long lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}
	
	/**
	 * Sets the flag that indicates whether there was a request to fire.
	 * @param	isFireRequested	true if there is a request to fire; false if not
	 */
	public void setFireRequested(final boolean isFireRequested)
	{
		this.isFireRequested = isFireRequested;
	}
	
	/**
	 * Sets the currently used weapon of the entity.
	 * @param weapon	the name of the weapon
	 */
	public void setCurrentWeapon(final String weapon)
	{
		for(Weapon each:weapons)
		{
			if(each.getName().equals(weapon))
			{
				this.currentWeapon = each;
				setInterval(currentWeapon.getFiringRate());
				setFireRequested(false);
			}
		}
	}
	
	/**
	 * Sets the currently used weapon of the entity.
	 * @param slot	the slot of the weapon
	 */
	public void setCurrentWeapon(final int slot)
	{
		for(Weapon each:weapons)
		{
			if(each.getSlot()==slot)
			{
				this.currentWeapon = each;
				setInterval(currentWeapon.getFiringRate());
				setFireRequested(false);
			}
		}
	}
	
	/**
	 * Sets the amount of ammo the weapon has
	 * @param ammo	the amount of ammo
	 */
	public void setAmmo(final int ammo)
	{
		if(currentWeapon!=null)
		{			
			this.currentWeapon.setAmmo(ammo);
		}
	}
	
	/**
	 * Sets the amount of damage the weapon does
	 * @param damage	the amount of damage
	 */
	public void setDamage(final int damage)
	{
		if(currentWeapon!=null)
		{
			this.currentWeapon.setDamage(damage);
		}
	}
	
	/**
	 * Sets the maximum range of the weapon
	 * @param maxRange	the maximum range
	 */
	public void setMaxRange(final int maxRange)
	{
		if(currentWeapon!=null)
		{
			this.currentWeapon.setMaxRange(maxRange);
		}
	}
	
	/**
	 * Sets the current spread of the weapon
	 * @param curSpread	the current spread, in degrees
	 */
	public void setCurSpread(final double curSpread)
	{
		if(currentWeapon!=null)
		{
			this.currentWeapon.setCurSpread(curSpread);
		}
	}
	
	/**
	 * Sets the maximum spread of the weapon
	 * @param maxSpread	the maximum spread, in degrees
	 */
	public void setMaxSpread(final double maxSpread)
	{
		if(currentWeapon!=null)
		{
			this.currentWeapon.setMaxSpread(maxSpread);
		}
	}
	
	/**
	 * Sets the number of shots fired by the weapon
	 * @param numShots	the number of shots
	 */
	public void setNumShots(final int numShots)
	{
		if(currentWeapon!=null)
		{
			this.currentWeapon.setNumShots(numShots);
		}
	}
	
	/**
	 * Sets the size of the burst.
	 * @param burstSize	the number of shots to fire as part of the burst
	 */
	public void setBurstSize(final int burstSize)
	{
		if(currentWeapon!=null)
		{
			this.currentWeapon.setBurstSize(burstSize);
		}
	}
	
	/**
	 * Sets the number of shots fired this burst.
	 * @param shotsFiredThisBurst	the number of shots fired as part of the burst
	 */
	public void setShotsFiredThisBurst(final int shotsFiredThisBurst)
	{
		if(currentWeapon!=null)
		{
			this.currentWeapon.setShotsFiredThisBurst(shotsFiredThisBurst);
		}
	}
	
	/**
	 * Sets the delay after the burst.
	 * @param delayAfterBurst	the time to wait after the burst.
	 */
	public void setDelayAfterBurst(final long delayAfterBurst)
	{
		if(currentWeapon!=null)
		{
			this.currentWeapon.setDelayAfterBurst(delayAfterBurst);
		}
	}
	//////////
	// METHODS
	//////////
	/**
	 * Adds a weapon to the inventory.
	 * @param weapon the weapon to add
	 */
	public void addWeapon(final Weapon weapon)
	{
		weapons.add(weapon);
	}
}
