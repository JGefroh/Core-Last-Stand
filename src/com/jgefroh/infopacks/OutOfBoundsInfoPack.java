package com.jgefroh.infopacks;

import com.jgefroh.components.OutOfBoundsComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class OutOfBoundsInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private OutOfBoundsComponent oc;

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this InfoPack.
	 */
	public OutOfBoundsInfoPack() {
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
				|| entity.getComponent(OutOfBoundsComponent.class) == null) {
			return false;
		}
		
		return true;
	}



	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.oc = entity.getComponent(OutOfBoundsComponent.class);
		
		if (tc == null || oc == null) {
			tc = null;
			oc = null;
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
	
	public double getZPos() {
		return tc.getZPos();
	}
	
	public double getWidth() {
		return tc.getWidth();
	}
	
	public double getHeight() {
		return tc.getHeight();
	}

	public boolean isChecking() {
		return oc.isChecking();
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////

	public void setChecking(final boolean isChecking) {
		oc.setChecking(isChecking);
	}


}
