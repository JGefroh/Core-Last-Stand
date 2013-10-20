package com.jgefroh.infopacks;

import com.jgefroh.components.SlaveComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class SlaveInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private SlaveComponent sc;
	

	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public SlaveInfoPack() {
	}
	
	
	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(SlaveComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.sc = entity.getComponent(SlaveComponent.class);
		
		if (sc == null) {
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}


	//////////////////////////////////////////////////
	// Adapter - setters
	//////////////////////////////////////////////////
	
	public void setMaster(final IEntity master) {
		sc.setMaster(master);
	}
	
}