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
	
	/**The last X-coordinate position of the entity.*/
	private double lastXPos;
	
	/**The last Y-coordinate position of the entity.*/
	private double lastYPos;
	
	/**The distance this entity has traveled so far.*/
	private double distanceTraveled;
	
	
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
	 * Gets the last X position of the owning {@code Entity}.
	 * @return	the last X position
	 */
	public double getLastXPos()
	{
		return this.lastXPos;
	}
	
	/**
	 * Gets the last Y position of the owning {@code Entity}.
	 * @return	the last Y position
	 */
	public double getLastYPos()
	{
		return this.lastYPos;
	}
	
	/**
	 * Gets the distance this entity has traveled so far.
	 * @return	the amount of units this entity has traveled so far
	 */
	public double getDistanceTraveled()
	{
		return this.distanceTraveled;
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
	 * Sets the last X position of the owning {@code Entity}.
	 * @param lastXPos	the last X position
	 */
	public void setLastXPos(final double lastXPos)
	{
		this.lastXPos = lastXPos;
	}
	
	
	/**
	 * Sets the last Y position of the owning {@code Entity}.
	 * @param lastYPos	the last Y position
	 */
	public void setLastYPos(final double lastYPos)
	{
		this.lastYPos = lastYPos;
	}
	
	/**
	 * Sets the distance traveled by this entity so far.
	 * @param distanceTraveled	the amount traveled
	 */
	public void setDistanceTraveled(final double distanceTraveled)
	{
		this.distanceTraveled = distanceTraveled;
	}
}
