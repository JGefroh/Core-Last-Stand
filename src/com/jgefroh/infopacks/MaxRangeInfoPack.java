package com.jgefroh.infopacks;

import com.jgefroh.components.MaxRangeComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * @author Joseph Gefroh
 */
public class MaxRangeInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private MaxRangeComponent mc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;	
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public MaxRangeInfoPack(final IEntity owner)
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
			tc = owner.getComponent(TransformComponent.class);
			mc = owner.getComponent(MaxRangeComponent.class);			
			if(tc==null||mc==null)
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
		if(entity.getComponent(TransformComponent.class)!=null
				&&entity.getComponent(MaxRangeComponent.class)!=null)
		{
			return new MaxRangeInfoPack(entity);
		}
		return null;
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
	
	public double getLastXPos()
	{
		return mc.getLastXPos();
	}

	public double getLastYPos()
	{
		return mc.getLastYPos();
	}
	
	public int getMaxRange()
	{
		return mc.getMaxRange();
	}
	
	public double getDistanceTraveled()
	{
		return mc.getDistanceTraveled();
	}
	//////////
	// SETTERS
	//////////
	
	public void setLastXPos(final double lastXPos)
	{
		mc.setLastXPos(lastXPos);
	}
	
	public void setLastYPos(final double lastYPos)
	{
		mc.setLastYPos(lastYPos);
	}
	public void setDistanceTraveled(final double distanceTraveled)
	{
		mc.setDistanceTraveled(distanceTraveled);
	}
	
}