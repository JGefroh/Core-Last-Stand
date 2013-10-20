package com.jgefroh.infopacks;

import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class RenderInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private RenderComponent rc;

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public RenderInfoPack() {
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
				|| entity.getComponent(RenderComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.rc = entity.getComponent(RenderComponent.class);
		
		if (tc == null
				|| rc == null) {
			tc = null;
			rc = null;
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

	public boolean isVisible() {
		return rc.isVisible();
	}

	public int getSpriteID() {
		return rc.getSpriteID();
	}

	public int getTextureID() {
		return rc.getTextureID();
	}

	public String getPath() {
		return rc.getTexturePath();
	}

	public int getBearing() {
		return (int) tc.getBearing();
	}

	public float getR() {
		return rc.getR();
	}

	public float getG() {
		return rc.getG();
	}

	public float getB() {
		return rc.getB();
	}

	public double getXRenderOffset() {
		return rc.getXRenderOffset();
	}

	public double getYRenderOffset() {
		return rc.getYRenderOffset();
	}

	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////

	public void setTextureID(final int textureID) {
		rc.setTextureID(textureID);
	}

	public void setSpriteID(final int id) {
		rc.setSpriteID(id);
	}
}
