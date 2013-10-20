package com.jgefroh.infopacks;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class AIInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	/**A component this InfoPack depends on.*/
	private AIComponent ac;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this InfoPack.
	 */
	public AIInfoPack() {
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
				|| entity.getComponent(AIComponent.class) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.ac = entity.getComponent(AIComponent.class);
		
		if (tc == null || ac == null) {
			tc = null;
			ac = null;
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}

	
	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////

	public double getAttackChance() {
		return ac.getAttackChance();
	}

	public String getAIType() {
		return ac.getAIType();
	}

	public boolean isInRangeOfTarget() {
		return ac.isInRangeOfTarget();
	}

	public boolean isActive() {
		return ac.isActive();
	}

	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////

	public void setAttackChance(final double attackChance) {
		ac.setAttackChance(attackChance);
	}

	public void setAIType(final String aiType) {
		ac.setAIType(aiType);
	}

	public void setInRangeOfTarget(final boolean isInRangeOfTarget) {
		ac.setInRangeOfTarget(isInRangeOfTarget);
	}

	public void setActive(final boolean isActive) {
		ac.setActive(isActive);
	}
}
