package com.jgefroh.infopacks;

import com.jgefroh.components.AbilityComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class AbilityInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private AbilityComponent ac;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this InfoPack.
	 */
	public AbilityInfoPack() {
	}
	
	
	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(AbilityComponent.class) == null) {
			return false;
		}
		return true;
	}



	@Override
	public boolean setEntity(final IEntity entity) {
		this.ac = entity.getComponent(AbilityComponent.class);
		
		if (ac == null) {
			ac = null;
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}
}