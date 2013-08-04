package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data allowing an entity to do damage to another entity.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class DamageComponent extends AbstractComponent
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
	 */
	public DamageComponent()
	{
	}
	
	@Override
	public void init()
	{
		setDamage(0);
	}

	
	//////////
	// GETTERS
	//////////
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
	/**
	 * Sets the amount of damage done.
	 * @param damage	the amount of damage
	 */
	public void setDamage(final int damage)
	{
		this.damage = damage;
	}


}
