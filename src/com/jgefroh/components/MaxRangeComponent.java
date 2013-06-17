package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data that allows entities to be destroyed after moving X distance.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class MaxRangeComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;

	/**The maximum range of the entity that owns this component.*/
	private int maxRange;
	
	/**The starting X-coordinate position of the entity.*/
	private double initialXPos;
	
	/**The starting Y-coordinate position of the entity.*/
	private double initialYPos;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of this component
	 */
	public MaxRangeComponent(final IEntity owner)
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
	 * Gets the maximum movement range of the owning {@code Entity}.
	 * @return	the maximum movement range
	 */
	public int getMaxRange()
	{
		return this.maxRange;
	}
	
	/**
	 * Gets the initial X position of the owning {@code Entity}.
	 * @return	the initial X position
	 */
	public double getInitialXPos()
	{
		return this.initialXPos;
	}
	
	/**
	 * Gets the initial Y position of the owning {@code Entity}.
	 * @return	the initial Y position
	 */
	public double getInitialYPos()
	{
		return this.initialYPos;
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
	 * Sets the maximum movement range of the owning {@code Entity}.
	 * @param maxRange
	 */
	public void setMaxRange(final int maxRange)
	{
		this.maxRange = maxRange;
	}
	
	/**
	 * Sets the initial X position of the owning {@code Entity}.
	 * @param initialXPos	the initial X position
	 */
	public void setInitialXPos(final double initialXPos)
	{
		this.initialXPos = initialXPos;
	}
	
	
	/**
	 * Sets the initial Y position of the owning {@code Entity}.
	 * @param initialYPos	the initial Y position
	 */
	public void setInitialYPos(final double initialYPos)
	{
		this.initialYPos = initialYPos;
	}
}
