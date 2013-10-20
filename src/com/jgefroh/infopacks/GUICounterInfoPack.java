package com.jgefroh.infopacks;

import java.util.Iterator;

import com.jgefroh.components.GUICounterComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


public class GUICounterInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private RenderComponent rc;
	
	/**A component this InfoPack depends on.*/
	private GUICounterComponent gcc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public GUICounterInfoPack()	{
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
				|| entity.getComponent(GUICounterComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.rc = entity.getComponent(RenderComponent.class);
		this.gcc = entity.getComponent(GUICounterComponent.class);
		
		if (tc == null
				|| rc == null
				|| gcc == null) {
			tc = null;
			rc = null;
			gcc = null;
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

	public int getCharWidth() {
		return gcc.getCharWidth();
	}

	public int getCharHeight() {
		return gcc.getCharHeight();
	}

	public Iterator<String> getChildren() {
		return gcc.getChildren().iterator();
	}

	public char getDefaultChar() {
		return gcc.getDefaultChar();
	}

	public String getText() {
		return gcc.getText();
	}

	public int getNumSlots() {
		return gcc.getNumSlots();
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

	public void setCharWidth(final int charWidth) {
		gcc.setCharWidth(charWidth);
	}

	public void setCharHeight(final int charHeight) {
		gcc.setCharHeight(charHeight);
	}

	public void setRGB(final float r, final float g, final float b) {
		rc.setRGB(r, g, b);
	}

	public void setSpriteID(final int id) {
		rc.setSpriteID(id);
	}

	public void setDefaultChar(final char defaultChar) {
		gcc.setDefaultChar(defaultChar);
	}

	public void setText(final String text) {
		gcc.setText(text);
	}
}
