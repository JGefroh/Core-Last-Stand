package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data related to the health of an entity.
 * 
 * 
 * Date: 17JUN13
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
	
	/**The maximum number of health points the entity can have.*/
	private int maxHealth = 0;
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
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
	 * Gets the current number of health points of the entity.
	 * @return	the current number of health points of the entity
	 */
	public int getCurHealth()
	{
		return this.curHealth;
	}
	
	/**
	 * Gets the maximum number of health points of the entity.
	 * @return the maximum number of health
	 */
	public int getMaxHealth()
	{
		return this.maxHealth;
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
	 * Sets the current number of health points of the entity.
	 * @param curHealth	the number of health points
	 */
	public void setCurHealth(final int curHealth)
	{
		this.curHealth = curHealth;
	}
	
	/**
	 * Sets the max number of health points of the entity.
	 * @param maxHealth	the number of health points
	 */
	public void setMaxHealth(final int maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	
	
}
