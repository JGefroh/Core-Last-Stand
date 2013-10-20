package com.jgefroh.infopacks;

import com.jgefroh.components.ScoreComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class ScoreInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private ScoreComponent sc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public ScoreInfoPack() {
	}
	

	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(ScoreComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.sc = entity.getComponent(ScoreComponent.class);
		
		if (sc == null) {
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}


	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////
	
	public int getScore() {
		return sc.getScore();
	}
}
