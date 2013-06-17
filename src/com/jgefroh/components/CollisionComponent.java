package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data that allows objects to collide.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class CollisionComponent implements IComponent
{	
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**Used to determine what collides with what.*/
	private int collisionGroup;

	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of this component
	 */
	public CollisionComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}

	@Override
	public void init()
	{	
		setCollisionGroup(0);
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
	 * Returns the collision group the entity belongs to.
	 * @return	the collision group the entity is a part of
	 */
	public int getCollisionGroup()
	{
		return this.collisionGroup;
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
	 * Sets the collision group the entity belongs to.
	 * @param collisionGroup	the collision group the entity belongs to
	 */
	public void setCollisionGroup(final int collisionGroup)
	{
		this.collisionGroup = collisionGroup;
	}
}
