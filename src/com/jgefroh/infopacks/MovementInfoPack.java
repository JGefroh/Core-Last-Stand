package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.data.Vector;


/**
 * Intended to be used by the TransformSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * VelocityComponent
 * 
 * @author Joseph Gefroh
 */
public class MovementInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private VelocityComponent vc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;	
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public MovementInfoPack(final IEntity owner)
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
			vc = owner.getComponent(VelocityComponent.class);			
			if(tc==null||vc==null)
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
				&& entity.getComponent(VelocityComponent.class)!=null)
		{
			return new MovementInfoPack(entity);
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
	
	/**
	 * @see VelocityComponent#getInterval()
	 */
	public long getInterval()
	{
		return vc.getInterval();
	}
	
	/**
	 * @see TransformComponent#getBearing()
	 */
	public double getBearing()
	{
		return tc.getBearing();
	}
	
	/**
	 * @see VelocityComponent#getLastUpdated()
	 */
	public long getLastUpdated()
	{
		return vc.getLastUpdated();
	}
	
	public Vector getTotalMovementVector()
	{
		return vc.getTotalMovementVector();
	}
	
	public boolean isContinuous()
	{
		return vc.isContinuous();
	}
	//////////
	// SETTERS
	//////////
	
	/**
	 * @see TransformComponent#setXPos(double)
	 */
	public void setXPos(final double xPos)
	{
		tc.setXPos(xPos);
	}
	
	/**
	 * @see TransformComponent#setYPos(double)
	 */
	public void setYPos(final double yPos)
	{
		tc.setYPos(yPos);
	}
	
	/**
	 * @see VelocityComponent#setInterval(long)
	 */
	public void setInterval(final long interval)
	{
		vc.setInterval(interval);
	}
	
	/**
	 * @see VelocityComponent#setLastUpdated(long)
	 */
	public void setLastUpdated(final long lastUpdated)
	{
		vc.setLastUpdated(lastUpdated);
	}
	
	public void setTotalMovementVector(final Vector totalMovementVector)
	{
		vc.setTotalMovementVector(totalMovementVector);
	}
	
	public void setContinuous(final boolean isContinuous)
	{
		vc.setContinuous(isContinuous);
	}
}