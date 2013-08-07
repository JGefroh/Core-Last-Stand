package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data that allows objects to collide.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class CollisionComponent extends AbstractComponent
{	
	//////////
	// DATA
	//////////	
	/**Used to determine what collides with what.*/
	private int collisionGroup;

	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 */
	public CollisionComponent()
	{
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
	/**
	 * Sets the collision group the entity belongs to.
	 * @param collisionGroup	the collision group the entity belongs to
	 */
	public void setCollisionGroup(final int collisionGroup)
	{
		this.collisionGroup = collisionGroup;
	}
}
