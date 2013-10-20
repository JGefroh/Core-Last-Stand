package com.jgefroh.infopacks;

import com.jgefroh.components.TargetTrackComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class TargetTrackInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private TargetTrackComponent ttc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public TargetTrackInfoPack() {
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
				|| entity.getComponent(TargetTrackComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.ttc = entity.getComponent(TargetTrackComponent.class);
		
		if (tc == null
				|| ttc == null) {
			tc = null;
			ttc = null;
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

	public IEntity getTarget() {
		return ttc.getTarget();
	}

	public double getTargetRange() {
		return ttc.getTargetRange();
	}

	public long getLastTurned() {
		return ttc.getLastTurned();
	}

	public long getTurnInterval() {
		return ttc.getTurnInterval();
	}

	public double getTurnSpeed() {
		return ttc.getTurnSpeed();
	}

	public double getBearing() {
		return tc.getBearing();
	}

	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
	public void setBearing(final double bearing) {
		tc.setBearing(bearing);
	}

	public void setTarget(final IEntity target) {
		ttc.setTarget(target);
	}

	public void setTargetRange(final double targetRange) {
		ttc.setTargetRange(targetRange);
	}

	public void setLastTurned(final long lastTurned) {
		ttc.setLastTurned(lastTurned);
	}
}