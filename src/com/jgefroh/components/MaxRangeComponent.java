package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
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
	 * Create a new instance of this {@code Component}.
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
	
	public int getMaxRange()
	{
		return this.maxRange;
	}
	
	public double getInitialXPos()
	{
		return this.initialXPos;
	}
	
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
	
	public void setMaxRange(final int maxRange)
	{
		this.maxRange = maxRange;
	}
	
	public void setInitialXPos(final double initialXPos)
	{
		this.initialXPos = initialXPos;
	}
	
	public void setInitialYPos(final double initialYPos)
	{
		this.initialYPos = initialYPos;
	}
}
