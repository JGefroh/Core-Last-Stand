package com.jgefroh.infopacks;

import com.jgefroh.components.CollisionComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class CollisionInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private CollisionComponent cc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this InfoPack.
	 */
	public CollisionInfoPack() {
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
				|| entity.getComponent(CollisionComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.cc = entity.getComponent(CollisionComponent.class);
		
		if (tc == null
			|| cc == null) {
			tc = null;
			cc = null;
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////
	
	public int getGroup() {
		return cc.getCollisionGroup();
	}
	public double getXPos() {
		return tc.getXPos();
	}

	public double getYPos() {
		return tc.getYPos();
	}

	public double getWidth() {
		return tc.getWidth();
	}

	public double getHeight() {
		return tc.getHeight();
	}
	

	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
	public void setXPos(final int xPos) {
		tc.setXPos(xPos);
	}

	public void setYPos(final int yPos) {
		tc.setYPos(yPos);
	}
	
}