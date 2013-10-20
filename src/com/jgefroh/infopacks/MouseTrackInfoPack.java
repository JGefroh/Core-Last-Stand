package com.jgefroh.infopacks;

import com.jgefroh.components.MouseTrackComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class MouseTrackInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private MouseTrackComponent mc;

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public MouseTrackInfoPack() {
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
				|| entity.getComponent(MouseTrackComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.mc = entity.getComponent(MouseTrackComponent.class);
		
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
	

	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
	public void setBearing(final double bearing) {
		tc.setBearing(bearing);
	}
}