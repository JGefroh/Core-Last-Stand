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
	
	/**The speed at which the object turns.*/
	private double turnSpeed;
	
	/**The time the object was last turned.*/
	private long lastTurned;
	
	/**The time to wait in between turns.*/
	private long turnInterval;
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
		this.turnSpeed = 360;
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
	
	/**
	 * Gets the maximum amount to turn by, in degrees.
	 * @return	the maximum amount to turn by, in degrees
	 */
	public double getTurnSpeed()
	{
		return this.turnSpeed;
	}
	
	/**
	 * Gets the time the Entity was last turned.
	 * @return	the time last turned, in ms
	 */
	public long getLastTurned()
	{
		return this.lastTurned;
	}
	
	/**
	 * Gets the time to wait in between turns.
	 * @return the time to wait, in ms
	 */
	public long getTurnInterval()
	{
		return this.turnInterval;
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
	
	/**
	 * Sets the maximum amount to turn by, in degrees.
	 * @param turnSpeed the maximum amount to turn by, in degrees
	 */
	public void setTurnSpeed(final double turnSpeed)
	{
		this.turnSpeed = turnSpeed;
	}
	
	/**
	 * Sets the time the Entity was last turned.
	 * @param lastTurned the time last turned, in ms
	 */
	public void setLastTurned(final long lastTurned)
	{
		this.lastTurned = lastTurned;
	}
	
	/**
	 * Sets the time to wait in between turns.
	 * @param turnInterval the time to wait, in ms
	 */
	public void setTurnInterval(final long turnInterval)
	{
		this.turnInterval = turnInterval;
	}
}
