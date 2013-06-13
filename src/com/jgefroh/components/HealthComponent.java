package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data related to the health of an entity using a
 * traditional "hit points" perspective.
 * @author Joseph Gefroh
 */
public class HealthComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The current number of health points the entity has.*/
	private int curHealth;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new HealthComponent.
	 * @param owner	the IEntity owner of this component
	 */
	public HealthComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
		setCurHealth(0);
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
	 * Get the current number of health points of the entity.
	 * @return	the current number of health points of the entity
	 */
	public int getCurHealth()
	{
		return this.curHealth;
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
	 * Set the current number of health points of the entity.
	 * @param curHealth	the number of health points
	 */
	public void setCurHealth(final int curHealth)
	{
		this.curHealth = curHealth;
	}
}
