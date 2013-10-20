package com.jgefroh.infopacks;

import com.jgefroh.components.MaxRangeComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class MaxRangeInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private MaxRangeComponent mc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public MaxRangeInfoPack() {
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
				|| entity.getComponent(MaxRangeComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.mc = entity.getComponent(MaxRangeComponent.class);
		
		if (tc == null
				|| mc == null) {
			tc = null;
			mc = null;
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}

	
	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////

	public double getXPos() {
		return tc.getXPos();
	}

	public double getYPos() {
		return tc.getYPos();
	}

	public double getLastXPos() {
		return mc.getLastXPos();
	}

	public double getLastYPos() {
		return mc.getLastYPos();
	}

	public int getMaxRange() {
		return mc.getMaxRange();
	}

	public double getDistanceTraveled() {
		return mc.getDistanceTraveled();
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
	public void setLastXPos(final double lastXPos) {
		mc.setLastXPos(lastXPos);
	}

	public void setLastYPos(final double lastYPos) {
		mc.setLastYPos(lastYPos);
	}

	public void setDistanceTraveled(final double distanceTraveled) {
		mc.setDistanceTraveled(distanceTraveled);
	}
	
}