package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
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
	
	private double distanceToTarget;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code Component}.
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
	
	public IEntity getTarget()
	{
		return this.target;
	}
	
	public double getDistanceToTarget()
	{
		return this.distanceToTarget;
	}
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	public void setTarget(final IEntity target)
	{
		this.target = target;
	}
	
	public void setDistanceToTarget(final double distanceToTarget)
	{
		this.distanceToTarget = distanceToTarget;
	}
}
