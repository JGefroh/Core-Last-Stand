package com.jgefroh.infopacks;

import com.jgefroh.components.DecayComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class DecayInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private DecayComponent dc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public DecayInfoPack() {
	}
	
	
	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(DecayComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.dc = entity.getComponent(DecayComponent.class);
		
		if (dc == null) {
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}

	
	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////
	
	public long getLastUpdateTime() {
		return dc.getLastUpdateTime();
	}

	public long getTimeUntilDecay() {
		return dc.getTimeUntilDecay();
	}

	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////

	public void setLastUpdateTime(final long lastUpdateTime) {
		dc.setLastUpdateTime(lastUpdateTime);
	}
}
