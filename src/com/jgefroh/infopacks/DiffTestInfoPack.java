package com.jgefroh.infopacks;

import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * Intended to be used by the CollisionSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * CollisionComponent
 * 
 * @author Joseph Gefroh
 */
public class DiffTestInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private CollisionComponent cc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public DiffTestInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}
	
	@Override
	public boolean checkDirty()
	{
		return false;
	}
	
	/**
	 * @see CollisionComponent#getCollisionGroup()
	 */
	public int getGroup()
	{
		return cc.getCollisionGroup();
	}
	
	/**
	 * @see TransformComponent#getXPos()
	 */
	public double getXPos()
	{
		return tc.getXPos();
	}
	
	/**
	 * @see TransformComponent#getYPos()
	 */
	public double getYPos()
	{
		return tc.getYPos();
	}
	
	/**
	 * @see TransformComponent#getWidth()
	 */
	public double getWidth()
	{
		return tc.getWidth();
	}
	
	/**
	 * @see TransformComponent#getHeight()
	 */
	public double getHeight()
	{
		return tc.getHeight();
	}
	
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	/**
	 * @see TransformComponent#setXPos(int)
	 */
	public void setXPos(final int xPos)
	{
		tc.setXPos(xPos);
	}
	
	/**
	 * @see TransformComponent#setYPos(int)
	 */
	public void setYPos(final int yPos)
	{
		tc.setYPos(yPos);
	}
	
}