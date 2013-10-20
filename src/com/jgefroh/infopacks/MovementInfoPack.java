package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.Vector;


/**
 * @author Joseph Gefroh
 */
public class MovementInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private VelocityComponent vc;
	

	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public MovementInfoPack() {
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
				|| entity.getComponent(VelocityComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.vc = entity.getComponent(VelocityComponent.class);
		
		if (tc == null
				|| vc == null) {
			tc = null;
			vc = null;
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

	public long getInterval() {
		return vc.getInterval();
	}

	public double getBearing() {
		return tc.getBearing();
	}

	public long getLastUpdated() {
		return vc.getLastUpdated();
	}

	public Vector getTotalMovementVector() {
		return vc.getTotalMovementVector();
	}

	public boolean isContinuous() {
		return vc.isContinuous();
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////

	public void setXPos(final double xPos) {
		tc.setXPos(xPos);
	}

	public void setYPos(final double yPos) {
		tc.setYPos(yPos);
	}

	public void setInterval(final long interval) {
		vc.setInterval(interval);
	}

	public void setLastUpdated(final long lastUpdated) {
		vc.setLastUpdated(lastUpdated);
	}

	public void setTotalMovementVector(final Vector totalMovementVector) {
		vc.setTotalMovementVector(totalMovementVector);
	}

	public void setContinuous(final boolean isContinuous) {
		vc.setContinuous(isContinuous);
	}
}