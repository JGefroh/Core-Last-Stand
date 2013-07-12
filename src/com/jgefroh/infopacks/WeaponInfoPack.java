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
	public boolean checkDirty()
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
	public boolean isDirty()
	{
		return this.isDirty;
	}
	
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	public Weapon getCurrentWeapon()
	{
		return wc.getCurrentWeapon();
	}
	
	public long getConsecutiveShotDelay()
	{
		return wc.getConsecutiveShotDelay();
	}
	
	public long getLastFired()
	{
		return wc.getLastFired();
	}
	
	public boolean isFireRequested()
	{
		return wc.isFireRequested();
	}
	
	public int getFireMode()
	{
		return wc.getFireMode();
	}
	
	public int getDamage()
	{
		return wc.getDamage();
	}
	
	public int getMaxRange()
	{
		return wc.getMaxRange();
	}

	public int getBurstSize()
	{
		return wc.getBurstSize();
	}
	
	public int getShotsThisBurst()
	{
		return wc.getShotsThisBurst();
	}
	public boolean isInBurst()
	{
		return wc.isInBurst();
	}
	
	public long getBurstDelay()
	{
		return wc.getBurstDelay();
	}
	
	public int getShotType()
	{
		return wc.getShotType();
	}
	
	public int getNumShots()
	{
		return wc.getNumShots();
	}
	
	public double getRecoilCur()
	{
		return wc.getRecoilCur();
	}
	
	public double getRecoilInc()
	{
		return wc.getRecoilInc();
	}
	
	public double getRecoilDec()
	{
		return wc.getRecoilDec();
	}
	
	public double getRecoilMax()
	{
		return wc.getRecoilMax();
	}
	
	public double getRecoilMin()
	{
		return wc.getRecoilMin();
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	public void setInterval(final long interval)
	{
		wc.setConsecutiveShotDelay(interval);
	}
	
	public void setLastFired(final long lastFired)
	{
		wc.setLastFired(lastFired);
	}
	
	public void setFireRequested(final boolean fireRequested)
	{
		wc.setFireRequested(fireRequested);
	}
	
	public void setCurrentWeapon(final String weaponName)
	{
		wc.setCurrentWeapon(weaponName);
	}

	
	public void setDamage(final int damage)
	{
		wc.setDamage(damage);
	}
	
	public void setMaxRange(final int maxRange)
	{
		wc.setMaxRange(maxRange);
	}
	
	public void setFireMode(final int fireMode)
	{
		wc.setFireMode(fireMode);
	}
	
	public void setBurstSize(final int burstSize)
	{
		wc.setBurstSize(burstSize);
	}
	
	public void setShotsThisBurst(final int shotsThisBurst)
	{
		wc.setShotsThisBurst(shotsThisBurst);
	}
	public void setInBurst(final boolean isInBurst)
	{
		wc.setInBurst(isInBurst);
	}
	
	public void setBurstDelay(final long burstDelay)
	{
		wc.setBurstDelay(burstDelay);
	}
	
	public void setShotType(final int shotType)
	{
		wc.setShotType(shotType);
	}
	
	
}
