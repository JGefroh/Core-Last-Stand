package com.jgefroh.infopacks;

import com.jgefroh.components.GUICharSlotComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


public class GUICharSlotInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private RenderComponent rc;

	/**A component this InfoPack depends on.*/
	private GUICharSlotComponent gcsc;

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public GUICharSlotInfoPack() {
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
				|| entity.getComponent(RenderComponent.class) == null
				|| entity.getComponent(GUICharSlotComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.rc = entity.getComponent(RenderComponent.class);
		this.gcsc = entity.getComponent(GUICharSlotComponent.class);
		
		if (tc == null
				|| rc == null
				|| gcsc == null) {
			tc = null;
			rc = null;
			gcsc = null;
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

	public int getSlotNum() {
		return gcsc.getSlotNum();
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

	public void setWidth(final double width) {
		tc.setWidth(width);
	}

	public void setHeight(final double height) {
		tc.setHeight(height);
	}

	public void setRGB(final float r, final float g, final float b) {
		rc.setRGB(r, g, b);
	}

	public void setSpriteID(final int id) {
		rc.setSpriteID(id);
	}

	public void setVisible(final boolean isVisible) {
		rc.setVisible(isVisible);
	}

}
