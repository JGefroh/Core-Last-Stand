package com.jgefroh.infopacks;

import com.jgefroh.components.GUIBarComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class GUIBarInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private RenderComponent rc;
	
	/**A component this InfoPack depends on.*/
	private GUIBarComponent gbc;

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public GUIBarInfoPack() {
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
				|| entity.getComponent(GUIBarComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.rc = entity.getComponent(RenderComponent.class);
		this.gbc = entity.getComponent(GUIBarComponent.class);
		
		if (tc == null
				|| rc == null
				|| gbc == null) {
			tc = null;
			rc = null;
			gbc = null;
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

	public double getDefXPos() {
		return gbc.getDefXPos();
	}

	public double getDefYPos() {
		return gbc.getDefYPos();
	}

	public double getDefHeight() {
		return gbc.getDefHeight();
	}

	public double getDefWidth() {
		return gbc.getDefWidth();
	}

	public double getMaxValue() {
		return gbc.getMaxValue();
	}

	public double getCurValue() {
		return gbc.getCurValue();
	}

	public double getMaxWidth() {
		return gbc.getMaxWidth();
	}

	public double getMaxHeight() {
		return gbc.getMaxHeight();
	}

	public boolean left() {
		return gbc.left();
	}

	public boolean right() {
		return gbc.right();
	}

	public boolean up() {
		return gbc.up();
	}

	public boolean down() {
		return gbc.down();
	}

	public boolean collapseMiddleH() {
		return gbc.collapseMiddleH();
	}

	public boolean collapseMiddleV() {
		return gbc.collapseMiddleV();
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

	public void setMaxValue(final int maxValue) {
		gbc.setMaxValue(maxValue);
	}

	public void setCurValue(final int curValue) {
		gbc.setCurValue(curValue);
	}

	public void getShrinkDir(final int shrinkDir) {
		gbc.getShrinkDir();
	}
}
