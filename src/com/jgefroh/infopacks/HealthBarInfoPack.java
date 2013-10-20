package com.jgefroh.infopacks;

import com.jgefroh.components.HealthBarComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class HealthBarInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////

	/**A component this InfoPack depends on.*/
	private HealthBarComponent hbc;
	
	/**A component this InfoPack depends on.*/
	private HealthComponent hc;
	
	/**The transform component of the owner of the health bar*/
	private TransformComponent tc;
	
	/**The transform component of the health bar.*/
	private TransformComponent hbtc;

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public HealthBarInfoPack() {
	}
	
	
	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		HealthBarComponent barComp = entity.getComponent(HealthBarComponent.class);
		if (barComp != null) {
			IEntity bar = barComp.getHealthBar();
			if (bar != null) {
				hbtc = bar.getComponent(TransformComponent.class);
			}
		}
		
		if (barComp == null
				|| entity.getComponent(TransformComponent.class) == null
				|| entity.getComponent(HealthComponent.class) == null
				|| entity.getComponent(HealthBarComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.tc = entity.getComponent(TransformComponent.class);
		this.hc = entity.getComponent(HealthComponent.class);
		this.hbc = entity.getComponent(HealthBarComponent.class);
		this.hbtc = null;

		if (hbc != null) {
			IEntity bar = hbc.getHealthBar();
			if (bar != null) {
				hbtc = bar.getComponent(TransformComponent.class);
			}
		}
		
		if (tc == null
				|| hc == null
				|| hbc == null
				|| hbtc == null) {
			tc = null;
			hc = null;
			hbc = null;
			hbtc = null;
			entity.setChanged(true);
			return false;		
		}

		super.setCurrent(entity);
		return true;
	}


	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////

	public IEntity getHealthBar() {
		return hbc.getHealthBar();
	}

	public double getOwnerXPos() {
		return tc.getXPos();
	}

	public double getOwnerYPos() {
		return tc.getYPos();
	}

	public int getHealth() {
		return hc.getCurHealth();
	}
	

	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////
	
	public void setHealthBar(final IEntity healthBar) {
		hbc.setHealthBar(healthBar);
	}

	public void setHealthBarXPos(final double xPos) {
		hbtc.setXPos(xPos);
	}

	public void setHealthBarYPos(final double yPos) {
		hbtc.setYPos(yPos);
	}

	public void setHealthBarWidth(final int width) {
		hbtc.setWidth(width);
	}
	
}
