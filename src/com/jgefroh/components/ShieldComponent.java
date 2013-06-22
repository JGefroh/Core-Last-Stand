package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data related to shields.
 * 
 * 
 * Date: 21JUN13
 * @author Joseph Gefroh
 */
public class ShieldComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The current amount of shields an entity has.*/
	private int shieldCur;
	
	/**Gets the highest possible charge the shield can have.*/
	private int shieldMax;
	
	/**Gets the minimum charge necessary to create a shield (use can go to 0)*/
	private int shieldMin;
	
	/**The amount the shield recharges by.*/
	private int shieldInc;
	
	/**The amount the shield decreases by.*/
	private int shieldDec;
	
	/**The time to wait after usage before recharging the shield, in ms.*/
	private long shieldRechargeDelay;
	
	/**The amount of time to wait between shield recharges, in ms.*/
	private long shieldRechargeInterval;
	
	/**The amount of time to wait between shield drains, in ms.*/
	private long shieldDrainInterval;
	
	/**The time, in ms, the shield was last used.*/
	private long shieldLastUsed;
	
	/**The time, in ms, the shield was last recharged.*/
	private long shieldLastRecharged;
	
	/**The time, in ms, the shield was last drained.*/
	private long shieldLastDrained;
	
	/**FLAG: Indicates shield is on or off.*/
	private boolean isShieldActive;
	
	/**The Entity reference of the shield*/
	private IEntity shield;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of this component
	 */
	public ShieldComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
		this.shieldCur = 0;
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
	 * Gets the amount of shield the entity currently has.
	 * @return	the amount of shield
	 */
	public int getShieldCur()
	{
		return this.shieldCur;
	}
	
	/**
	 * Gets the minimum charge the shield needs to be created.
	 * @return	the minimum charge
	 */
	public int getShieldMin()
	{
		return this.shieldMin;
	}
	
	/**
	 * Gets the maximum charge the shield can store.
	 * @return	the maximum charge
	 */
	public int getShieldMax()
	{
		return this.shieldMax;
	}
	
	/**
	 * Gets the flag that indicates whether the shield is active or not.
	 * @return	true if the shield is active; false otherwise
	 */
	public boolean isShieldActive()
	{
		return this.isShieldActive;
	}
	
	/**
	 * Gets the Entity reference to the shield
	 * @return	the shield entity
	 */
	public IEntity getShield()
	{
		return this.shield;
	}
	
	/**
	 * Gets the amount the shield recharges by.
	 * @return	the shield recharge amount
	 */
	public int getShieldInc()
	{
		return this.shieldInc;
	}
	
	/**
	 * Gets the amount the shield drains by
	 * @return	the shield drain amount
	 */
	public int getShieldDec()
	{
		return this.shieldDec;
	}
	
	/**
	 * Gets the time to wait after using the shield before starting to recharge.
	 * @return	the recharge delay, in ms
	 */
	public long getShieldRechargeDelay()
	{
		return this.shieldRechargeDelay;
	}
	
	/**
	 * Gets the time to wait between recharges.
	 * @return	the recharge interval, in ms
	 */
	public long getShieldRechargeInterval()
	{
		return this.shieldRechargeInterval;
	}
	
	/**
	 * Gets the time to wait between drains.
	 * @return	the drain interval, in ms
	 */
	public long getShieldDrainInterval()
	{
		return this.shieldDrainInterval;
	}
	
	/**
	 * Gets the time the shield was last used.
	 * @return	the time the shield was last used, in ms
	 */
	public long getShieldLastUsed()
	{
		return this.shieldLastUsed;
	}
	
	/**
	 * Gets the time the shield was last recharged.
	 * @return	the time the shield was last recharged, in ms
	 */
	public long getShieldLastRecharged()
	{
		return this.shieldLastRecharged;
	}
	
	/**
	 * Gets the time the shield was last drained.
	 * @return	the time the shield was last drained, in ms
	 */
	public long getShieldLastDrained()
	{
		return this.shieldLastDrained;
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
	 * Sets the amount of shield an entity currently has.
	 * @param shieldCur
	 */
	public void setShieldCur(final int shieldCur)
	{
		this.shieldCur = shieldCur;
	}
	
	/**
	 * Sets the minimum charge the shield needs to be created.
	 * @param shieldMin	the minimum charge
	 */
	public void setShieldMin(final int shieldMin)
	{
		this.shieldMin = shieldMin;
	}
	
	/**
	 * Sets the maximum charge the shield can store.
	 * @param shieldMax the maximum charge
	 */
	public void setShieldMax(final int shieldMax)
	{
		this.shieldMax = shieldMax;
	}
	
	/**
	 * Sets the flag that indicates whether the shield is active or not.
	 * @return	true if the shield is active; false otherwise
	 */
	public void setShieldActive(final boolean isShieldActive)
	{
		this.isShieldActive = isShieldActive;
	}
	
	
	/**
	 * Sets the Entity reference to the shield
	 * @return	the shield entity
	 */
	public void setShield(final IEntity shield)
	{
		this.shield = shield;
	}
	
	/**
	 * Sets the amount the shield recharges by.
	 * @param shieldInc	the shield recharge amount
	 */
	public void setShieldInc(final int shieldInc)
	{
		this.shieldInc = shieldInc;
	}
	
	/**
	 * Gets the amount the shield drains by.
	 * @param shieldDec the shield drain amount
	 */
	public void setShieldDec(final int shieldDec)
	{
		this.shieldDec = shieldDec;
	}
	
	/**
	 * Sets the time to wait after using the shield before starting to recharge.
	 * @return shieldRechargeDelay	the recharge delay, in ms
	 */
	public void setShieldRechargeDelay(final long shieldRechargeDelay)
	{
		this.shieldRechargeDelay = shieldRechargeDelay;
	}
	
	/**
	 * Sets the time to wait between recharges.
	 * @param shieldRechargeInterval the recharge interval, in ms
	 */
	public void setShieldRechargeInterval(final long shieldRechargeInterval)
	{
		this.shieldRechargeInterval = shieldRechargeInterval;
	}
	
	/**
	 * Sets the time to wait between drains.
	 * @param	shieldDrainInterval	the drain interval, in ms
	 */
	public void setShieldDrainInterval(final long shieldDrainInterval)
	{
		this.shieldDrainInterval = shieldDrainInterval;
	}
	
	/**
	 * Sets the time the shield was last used.
	 * @return	the time the shield was last used, in ms
	 */
	public void setShieldLastUsed(final long shieldLastUsed)
	{
		this.shieldLastUsed = shieldLastUsed;
	}
	
	/**
	 * Sets the time the shield was last recharged.
	 * @return	the time the shield was last recharged, in ms
	 */
	public void setShieldLastRecharged(final long shieldLastRecharged)
	{
		this.shieldLastRecharged = shieldLastRecharged;
	}	
	
	/**
	 * Sets the time the shield was last drained.
	 * @return	the time the shield was last drained, in ms
	 */
	public void setShieldLastDrained(final long shieldLastDrained)
	{
		this.shieldLastDrained = shieldLastDrained;
	}
}
