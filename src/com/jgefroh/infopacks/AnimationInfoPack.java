package com.jgefroh.infopacks;

import com.jgefroh.components.AnimationComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class AnimationInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	/**A component this InfoPack depends on.*/
	private AnimationComponent ac;
	
	/**A component this InfoPack depends on.*/
	private RenderComponent rc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public AnimationInfoPack() {
	}

	
	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(AnimationComponent.class) == null
				|| entity.getComponent(RenderComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.ac = entity.getComponent(AnimationComponent.class);
		this.rc = entity.getComponent(RenderComponent.class);
		
		if (ac == null
				|| rc == null) {
			ac = null;
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
	
	public int getAnimationSprite() {
		return ac.getAnimationSprite();
	}

	public int getCurrentFrame() {
		return ac.getCurrentFrame();
	}

	public long getInterval() {
		return ac.getInterval();
	}

	public long getLastUpdateTime() {
		return ac.getLastUpdateTime();
	}

	public int getNumberOfFrames() {
		return ac.getNumberOfFrames();
	}

	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////

	public void setAnimationSprite(final int spriteID) {
		rc.setSpriteID(spriteID);
	}

	public void setCurrentFrame(final int currentFrame) {
		ac.setCurrentFrame(currentFrame);
	}

	public void setLastUpdateTime(final long updateTime) {
		ac.setLastUpdateTime(updateTime);
	}
}