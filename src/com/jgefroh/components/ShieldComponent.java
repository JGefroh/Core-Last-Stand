package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data related to shields.
 * 
 * 
 * Date: 17JUN13
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


}
