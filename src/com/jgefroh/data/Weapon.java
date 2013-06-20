package com.jgefroh.data;

/**
 * Represents a weapon.
 * @author Joseph Gefroh
 */
public class Weapon
{
	//////////
	// DATA
	//////////
	/**The human readable name of this weapon.*/
	private String name;
	
	/**The time, in ms, to wait in between between shots*/
	private long consecutiveShotDelay;

	/**The amount of ammo this weapon has left.*/
	private int ammo;
	
	/**The amount of damage this weapon does.*/
	private int damage;
	
	/**The maximum range of this weapon.*/
	private int maxRange;
	
	/**The firing mode of the weapon.*/
	private int fireMode;
	
	/**The number of shots to fire per burst.*/
	private int burstSize;
	
	/**The number of shots fired this burst.*/
	private int shotsThisBurst;
	
	/**The amount of time this weapon needs to wait after firing a burst.*/
	private long burstDelay;
	
	/**The type of shot this weapon fires.*/
	private int shotType;
	
	/**The number of shots fired by a single fire request.*/
	private int numShots;
	//////////
	// INIT
	//////////
	public Weapon()
	{	
		init();
	}

	private void init()
	{
		this.consecutiveShotDelay = 1000;
		this.damage = 10;
		this.maxRange = 1500;
		this.fireMode = 0;
		this.burstSize = 0;
		this.shotsThisBurst = 0;
		this.burstDelay = 500;
		this.shotType = 0;
		this.numShots = 1;
	}
	
	//////////
	// GETTERS
	//////////
	/**
	 * Get the human readable name of this {@code Weapon}.
	 * @return	the human readable name, if it has one
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Gets the time to wait in between shots, in ms.
	 * @return	the time to wait between shots, in ms
	 */
	public long getConsecutiveShotDelay()
	{
		return this.consecutiveShotDelay;
	}
	
	/**
	 * Gets the fire mode of the weapon.
	 * @return	the fire mode
	 */
	public int getFireMode()
	{
		return this.fireMode;
	}
	
	/**
	 * Gets the damage done by this weapon.
	 * @return	the amount of damage done by this weapon
	 */
	public int getDamage()
	{
		return this.damage;
	}
	
	/**
	 * Gets the maximum range projectiles from this weapon can travel.
	 * @return	the maximum range
	 */
	public int getMaxRange()
	{
		return this.maxRange;
	}	
	
	/**
	 * Gets the number of shots fired in a single burst by this {@code Weapon}.
	 * @return	the burst size
	 */
	public int getBurstSize()
	{
		return this.burstSize;
	}

	/**
	 * Gets the number of shots fired so far in the burst.
	 * @return	the number of shots fired within the last burst
	 */
	public int getShotsThisBurst()
	{
		return this.shotsThisBurst;
	}
	
	/**
	 * Gets the amount of time the weapon must wait after a burst to fire.
	 * @return	the amount of time, in ms
	 */
	public long getBurstDelay()
	{
		return this.burstDelay;
	}
	
	/**
	 * Gets the type of shot this weapon fires.
	 * @return	the type of shot
	 */
	public int getShotType()
	{
		return this.shotType;
	}
	
	/**
	 * Gets the number of shots fired by a single fire request.
	 * @return	the number of shots
	 */
	public int getNumShots()
	{
		return this.numShots;
	}
	//////////
	// SETTERS
	//////////
	/**
	 * Sets the human readable name of this {@code Weapon}.
	 * @param name	the human readable name of this {@code Weapon}.
	 */
	public void setName(final String name) 
	{
		this.name = name;
	}
	 
	/**
	 * Sets the time to wait in between shots, in ms.
	 * @param consecutiveShotDelay	the time to wait between shots, in ms
	 */
	public void setConsecutiveShotDelay(final long consecutiveShotDelay)
	{
		this.consecutiveShotDelay = consecutiveShotDelay;
	}	
	
	/**
	 * Sets the fire mode of the weapon.
	 * @param fireMode	the fire mode of the weapon
	 */
	public void setFireMode(final int fireMode)
	{
		this.fireMode = fireMode;
	}
	
	/**
	 * Sets the amount of damage done by this weapon.
	 * @param damage	the amount of damage this weapon does
	 */
	public void setDamage(final int damage)
	{
		this.damage = damage;
	}
	
	/**
	 * Sets the maximum range of this {@code Weapon}.
	 * @param maxRange	the maximum range of this weapon
	 */
	public void setMaxRange(final int maxRange)
	{
		this.maxRange = maxRange;
	}
	
	/**
	 * Sets the number of shots fired in a single burst by this {@code Weapon}.
	 * @param burstSize	the burst size
	 */
	public void setBurstSize(final int burstSize)
	{
		this.burstSize = burstSize;
	}
	
	/**
	 * Sets the number of shots fired so far in the burst.
	 * @param shotsThisBurst	the number of shots fired so far
	 */
	public void setShotsThisBurst(final int shotsThisBurst)
	{
		this.shotsThisBurst = shotsThisBurst;
	}
	
	/**
	 * Sets the amount of time the weapon must wait after a burst to fire.
	 * @param burstDelay	the amount of time, in ms
	 */
	public void setBurstDelay(final long burstDelay)
	{
		this.burstDelay = burstDelay;
	}
	
	/**
	 * Sets the type of shot this weapon fires.
	 * @param shotType the type of shot
	 */
	public void setShotType(final int shotType)
	{
		this.shotType = shotType;
	}
}
