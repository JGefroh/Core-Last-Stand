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
	private long firingRate;

	/**The amount of ammo the weapon has left.*/
	private int ammo;
	
	/**The amount of damage this weapon does.*/
	private int damage;
	
	/**The maximum range of this weapon.*/
	private int maxRange;
	
	/**The numeric slot of this weapon*/
	private int slot; //TODO: Remove
	
	/**The maximum spread, in degrees, of this weapon.*/
	private double maxSpread;
	
	/**The current spread, in degrees, of this weapon.*/
	private double curSpread;
	
	/**The amount the spread is increased by per shot.*/
	private double incSpread;
	
	/**The number of shots this weapon fires per fire attempt.*/
	private int numShots;
	//////////
	// INIT
	//////////
	public Weapon()
	{
		
	}

	
	//////////
	// GETTERS
	//////////
	public String getName()
	{
		return this.name;
	}
	
	public long getFiringRate()
	{
		return this.firingRate;
	}
	
	public int getAmmo()
	{
		return this.ammo;
	}
	
	public int getDamage()
	{
		return this.damage;
	}
	
	public int getMaxRange()
	{
		return this.maxRange;
	}
	
	public int getSlot()
	{
		return this.slot;
	}
	
	public double getMaxSpread()
	{
		return this.maxSpread;
	}
	
	public double getCurSpread()
	{
		return this.curSpread;
	}
	
	public double getIncSpread()
	{
		return this.incSpread;
	}
	
	public int getNumShots()
	{
		return this.numShots;
	}
	//////////
	// SETTERS
	//////////
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public void setFiringRate(final long firingRate)
	{
		this.firingRate = firingRate;
	}
	
	public void setAmmo(final int ammo)
	{
		this.ammo = ammo;
	}
	
	public void setDamage(final int damage)
	{
		this.damage = damage;
	}
	
	public void setMaxRange(final int maxRange)
	{
		this.maxRange = maxRange;
	}
	
	public void setSlot(final int slot)
	{
		this.slot = slot;
	}
	
	public void setMaxSpread(final double maxSpread)
	{
		this.maxSpread = maxSpread;
	}
	public void setCurSpread(final double curSpread)
	{
		this.curSpread = curSpread;
	}
	public void setIncSpread(final double incSpread)
	{
		this.incSpread = incSpread;
	}
	public void setNumShots(final int numShots)
	{
		this.numShots = numShots;
	}
}
