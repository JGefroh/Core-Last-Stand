package com.jgefroh.infopacks;

import com.jgefroh.components.ExplosionComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class ExplosionInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private ExplosionComponent ec;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	

	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public ExplosionInfoPack() {
	}
	
	

	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(TransformComponent.class) == null
				|| entity.getComponent(ExplosionComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.ec = entity.getComponent(ExplosionComponent.class);
		
		if (tc == null
				|| ec == null) {
			tc = null;
			ec = null;
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}

	
	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////
	
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
	
	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
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
