package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
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
	 * Create a new instance of this {@code Component}.
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
	
	public void setDamage(final int damage)
	{
		this.damage = damage;
	}


}
