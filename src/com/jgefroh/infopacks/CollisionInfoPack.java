package com.jgefroh.infopacks;

import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

/**
 * Intended to be used by the CollisionSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * CollisionComponent
 * 
 * @author Joseph Gefroh
 */
public class CollisionInfoPack extends AbstractInfoPack
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
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public CollisionInfoPack(final IEntity owner)
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
	public boolean checkDirty()
	{
		if(owner.hasChanged())
		{
			cc = owner.getComponent(CollisionComponent.class);
			tc = owner.getComponent(TransformComponent.class);			
			if(tc==null||cc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity!=null
			&&entity.getComponent(TransformComponent.class)!=null
			&&entity.getComponent(CollisionComponent.class)!=null)
		{
			return new CollisionInfoPack(entity);
		}
		return null;
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