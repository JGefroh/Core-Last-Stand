package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Entities with this component will have a healthbar drawn on screen.
 * @author Joseph Gefroh
 */
public class HealthBarComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**A reference to the HealthBar belonging to this Entity.*/
	private IEntity healthBar;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of this component
	 */
	public HealthBarComponent(final IEntity owner)
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
	
	public IEntity getHealthBar()
	{
		return this.healthBar;
	}
	
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}

	public void setHealthBar(final IEntity healthBar)
	{
		this.healthBar = healthBar;
	}
}
