package com.jgefroh.infopacks;

import com.jgefroh.components.KeepInBoundsComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


public class KeepInBoundsInfoPack extends AbstractInfoPack {

	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private KeepInBoundsComponent kibc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public KeepInBoundsInfoPack() {
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
				|| entity.getComponent(KeepInBoundsComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.kibc = entity.getComponent(KeepInBoundsComponent.class);
		
		if (tc == null
				|| kibc == null) {
			tc = null;
			kibc = null;
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

	public double getLastX() {
		return kibc.getLastX();
	}

	public double getLastY() {
		return kibc.getLastY();
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
	public void setLastX(final double lastX) {
		kibc.setLastX(lastX);
	}

	public void setLastY(final double lastY) {
		kibc.setLastY(lastY);
	}

	public void setXPos(final double xPos) {
		tc.setXPos(xPos);
	}

	public void setYPos(final double yPos) {
		tc.setYPos(yPos);
	}

}
