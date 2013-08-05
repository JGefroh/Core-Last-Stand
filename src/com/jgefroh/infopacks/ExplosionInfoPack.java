package com.jgefroh.infopacks;

import com.jgefroh.components.ExplosionComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

/**
 * @author Joseph Gefroh
 */
public class ExplosionInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private ExplosionComponent ec;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public ExplosionInfoPack(final IEntity owner)
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
			ec = owner.getComponent(ExplosionComponent.class);
			tc = owner.getComponent(TransformComponent.class);
			if(ec==null||tc == null)
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
		if(entity.getComponent(ExplosionComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new ExplosionInfoPack(entity);
		}
		return null;
	}
	public double getHeight()
	{
		return tc.getHeight();
	}
	
	public double getWidth()
	{
		return tc.getWidth();
	}
	
	public double getHeightMax()
	{
		return ec.getHeightMax();
	}
	
	public double getWidthMax()
	{
		return ec.getWidthMax();
	}
	
	public double getWidthInc()
	{
		return ec.getWidthInc();
	}
	
	public double getHeightInc()
	{
		return ec.getHeightInc();
	}
	
	public long getUpdateInterval()
	{
		return ec.getUpdateInterval();
	}
	
	public long getLastUpdated()
	{
		return ec.getLastUpdated();
	}
	
	public int getPulse()
	{
		return ec.getPulse();
	}
	
	public int getNumHits(final String entityID)
	{
		return ec.getNumHits(entityID);
	}
	//////////
	// SETTERS
	//////////
	public void setWidth(final double width)
	{
		tc.setWidth(width);
	}
	public void setHeight(final double height)
	{
		tc.setHeight(height);
	}
	public void setLastUpdated(final long lastUpdated)
	{
		ec.setLastUpdated(lastUpdated);
	}
	public void setPulse(final int pulse)
	{
		ec.setPulse(pulse);
	}
	public void setNumHits(final String entityID, final int numHits)
	{
		ec.setNumHits(entityID, numHits);
	}
	
}
