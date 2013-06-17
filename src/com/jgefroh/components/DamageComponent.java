package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data allowing an entity to do damage to another entity.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class DamageComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The amount of damage.*/
	private int damage;
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of this component
	 */
	public DamageComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
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
	 * Gets the amount of damage done.
	 * @return	the amount of damage
	 */
	public int getDamage()
	{
		return this.damage;
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
	 * Sets the amount of damage done.
	 * @param damage	the amount of damage
	 */
	public void setDamage(final int damage)
	{
		this.damage = damage;
	}


}
