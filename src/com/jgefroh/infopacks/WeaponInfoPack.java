package com.jgefroh.infopacks;

import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.data.Weapon;


/**
 * Intended to be used by the WeaponSystem.
 * 
 * Controls access to the following components:
 * WeaponComponent
 * 
 * @author Joseph Gefroh
 */
public class WeaponInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private WeaponComponent wc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public WeaponInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public boolean isDirty()
	{
		if(owner.hasChanged())
		{
			wc = owner.getComponent(WeaponComponent.class);
			if(wc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * @see WeaponComponent#getCurrentWeapon()
	 */
	public Weapon getCurrentWeapon()
	{
		return wc.getCurrentWeapon();
	}
	
	/**
	 * @see WeaponComponent#getInterval() 
	 */
	public long getInterval()
	{
		return wc.getInterval();
	}
	
	/**
	 * @see WeaponComponent#getLastUpdated()
	 */
	public long getLastUpdated()
	{
		return wc.getLastUpdated();
	}
	
	/**
	 * @see WeaponComponent#isFireRequested()
	 */
	public boolean isFireRequested()
	{
		return wc.isFireRequested();
	}
	
	/**
	 * @see WeaponComponent#getAmmo()
	 */
	public int getAmmo()
	{
		return wc.getAmmo();
	}
	
	public int getDamage()
	{
		return wc.getDamage();
	}
	
	public int getMaxRange()
	{
		return wc.getMaxRange();
	}
	
	public double getCurSpread()
	{
		return wc.getCurSpread();
	}
	
	public double getIncSpread()
	{
		return wc.getIncSpread();
	}
	
	public double getMaxSpread()
	{
		return wc.getMaxSpread();
	}
	
	public int getNumShots()
	{
		return wc.getNumShots();
	}
	
	public int getShotsFiredThisBurst()
	{
		return wc.getShotsFiredThisBurst();
	}
	
	public int getBurstSize()
	{
		return wc.getBurstSize();
	}
	
	public long getDelayAfterBurst()
	{
		return wc.getDelayAfterBurst();
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	/**
	 * @see WeaponComponent#setInterval(long) 
	 */
	public void setInterval(final long interval)
	{
		wc.setInterval(interval);
	}
	
	/**
	 * @see WeaponComponent#setLastUpdated(long)
	 */
	public void setLastUpdated(final long lastUpdateTime)
	{
		wc.setLastUpdated(lastUpdateTime);
	}
	
	/**
	 * @see WeaponComponent#setFireRequested(boolean)
	 */
	public void setFireRequested(final boolean fireRequested)
	{
		wc.setFireRequested(fireRequested);
	}
	
	/**
	 * @see WeaponComponent#setCurrentWeapon(String) 
	 */
	public void setCurrentWeapon(final String weaponName)
	{
		wc.setCurrentWeapon(weaponName);
	}
	
	public void setCurrentWeapon(final int slot)
	{
		wc.setCurrentWeapon(slot);
	}
	
	/**
	 * @see WeaponComponent#setAmmo(int)
	 */
	public void setAmmo(final int ammo)
	{
		wc.setAmmo(ammo);
	}
	
	public void setDamage(final int damage)
	{
		wc.setDamage(damage);
	}
	
	public void setMaxRange(final int maxRange)
	{
		wc.setMaxRange(maxRange);
	}
	
	public void setCurSpread(final double curSpread)
	{
		wc.setCurSpread(curSpread);
	}
	
	public void setMaxSpread(final double maxSpread)
	{
		wc.setMaxSpread(maxSpread);
	}
	
	public void setNumShots(final int numShots)
	{
		wc.setNumShots(numShots);
	}
	
	public void setBurstSize(final int burstSize)
	{
		wc.setBurstSize(burstSize);
	}
	
	public void setShotsFiredThisBurst(final int shotsFiredThisBurst)
	{
		wc.setShotsFiredThisBurst(shotsFiredThisBurst);
	}
	
	public void setDelayAfterBurst(final long delayAfterBurst)
	{
		wc.setDelayAfterBurst(delayAfterBurst);
	}
	
}
