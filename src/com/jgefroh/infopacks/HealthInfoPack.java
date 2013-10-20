package com.jgefroh.infopacks;

import com.jgefroh.components.HealthComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph
 */
public class HealthInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	/**A component this InfoPack depends on.*/
	private HealthComponent hc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public HealthInfoPack()	{
	}
	
	
	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////
	
	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(HealthComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.hc = entity.getComponent(HealthComponent.class);
		
		if (hc == null) {
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////
	
	public int getCurHealth() {
		return hc.getCurHealth();
	}

	public int getMaxHealth() {
		return hc.getMaxHealth();
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
	public void setCurHealth(final int curHealth) {
		hc.setCurHealth(curHealth);
	}

	public void setMaxHealth(final int maxHealth) {
		hc.setMaxHealth(maxHealth);
	}
}
