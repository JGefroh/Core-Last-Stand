package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data allowing an Entity to turn towards and track a target.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class TargetTrackComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**Flag to tell if the entity is visible or invisible.*/
	private boolean isVisible;
	
	/**The target to track.*/
	private IEntity target;
	
	/**The range to the target.*/
	private double targetRange;
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of this component
	 */
	public TargetTrackComponent(final IEntity owner)
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
	 * Gets the Entity that is currently being targeted.
	 * @return	the entity that is currently being targeted
	 */
	public IEntity getTarget()
	{
		return this.target;
	}
	
	/**
	 * Gets the range to the targeted Entity.
	 * @return	gets the range to the target
	 */
	public double getTargetRange()
	{
		return this.targetRange;
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
	 * Sets the target of this {@code Entity}.
	 * @param target	the targeted Entity
	 */
	public void setTarget(final IEntity target)
	{
		this.target = target;
	}
	
	/**
	 * Sets the range to the target.
	 * @param targetRange	the range to the target
	 */
	public void setTargetRange(final double targetRange)
	{
		this.targetRange = targetRange;
	}
}
