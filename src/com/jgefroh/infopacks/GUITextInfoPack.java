package com.jgefroh.infopacks;

import java.util.Iterator;

import com.jgefroh.components.GUITextComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


public class GUITextInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private RenderComponent rc;
	
	/**A component this InfoPack depends on.*/
	private GUITextComponent gtc;
	

	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public GUITextInfoPack() {
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
				|| entity.getComponent(GUITextComponent.class) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.rc = entity.getComponent(RenderComponent.class);
		this.gtc = entity.getComponent(GUITextComponent.class);
		
		if (tc == null
				|| rc == null
				|| gtc == null) {
			tc = null;
			rc = null;
			gtc = null;
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
		return gtc.getCharWidth();
	}

	public int getCharHeight() {
		return gtc.getCharHeight();
	}

	public Iterator<String> getChildren() {
		return gtc.getChildren().iterator();
	}

	public char getDefaultChar() {
		return gtc.getDefaultChar();
	}

	public String getText() {
		return gtc.getText();
	}

	public int getNumCharsPerLine() {
		return gtc.getNumCharsPerLine();
	}

	public int getNumLines() {
		return gtc.getNumLines();
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
		gtc.setCharWidth(charWidth);
	}

	public void setCharHeight(final int charHeight) {
		gtc.setCharHeight(charHeight);
	}

	public void setRGB(final float r, final float g, final float b) {
		rc.setRGB(r, g, b);
	}

	public void setSpriteID(final int id) {
		rc.setSpriteID(id);
	}

	public void setDefaultChar(final char defaultChar) {
		gtc.setDefaultChar(defaultChar);
	}

	public void setText(final String text) {
		gtc.setText(text);
	}
}
