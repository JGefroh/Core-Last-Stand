package com.jgefroh.infopacks;

import com.jgefroh.components.OutOfBoundsComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


public class OutOfBoundsInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private OutOfBoundsComponent oc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public OutOfBoundsInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public boolean isDirty()
	{
		if(owner.hasChanged())
		{
			tc = owner.getComponent(TransformComponent.class);
			oc = owner.getComponent(OutOfBoundsComponent.class);			
			if(tc==null||oc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	@Override
	public IEntity getOwner()
	{
		return this.owner;
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
	 * @see TransformComponent#getZPos() 
	 */
	public double getZPos()
	{
		return tc.getZPos();
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

	public boolean isChecking()
	{
		return oc.isChecking();
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	public void setChecking(final boolean isChecking)
	{
		oc.setChecking(isChecking);
	}

}
