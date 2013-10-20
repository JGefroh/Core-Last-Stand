package com.jgefroh.infopacks;

import com.jgefroh.components.ForceGeneratorComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.Vector;

public class ForceInfoPack extends AbstractInfoPack {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A component this InfoPack depends on.*/
	private ForceGeneratorComponent fgc;
	
	/**A component this InfoPack depends on.*/
	private VelocityComponent vc;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this InfoPack.
	 */
	public ForceInfoPack() {
	}
	
	
	//////////////////////////////////////////////////
	// IInfoPack
	//////////////////////////////////////////////////

	@Override
	public boolean checkComponents(final IEntity entity) {
		if (entity == null) {
			return false;
		}
		
		if (entity.getComponent(ForceGeneratorComponent.class) == null
				|| entity.getComponent(VelocityComponent.class) == null
				|| entity.getComponent(TransformComponent.class) == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean setEntity(final IEntity entity) {
		this.fgc = entity.getComponent(ForceGeneratorComponent.class);
		this.vc = entity.getComponent(VelocityComponent.class);
		this.tc = entity.getComponent(TransformComponent.class);
		
		if (fgc == null
				|| vc == null
				|| tc == null) {
			fgc = null;
			vc = null;
			tc = null;
			entity.setChanged(true);
			return false;		
		}
		
		super.setCurrent(entity);
		return true;
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Getters
	//////////////////////////////////////////////////

	public Vector getGeneratedVector() {
		return fgc.getVector();
	}

	public Vector getSumVector() {
		return vc.getTotalMovementVector();
	}

	public long getLastGenerated() {
		return fgc.getLastUpdated();
	}

	public long getGenerateInterval() {
		return fgc.getInterval();
	}

	public double getMagnitude() {
		return fgc.getMagnitude();
	}

	public boolean isContinuous() {
		return fgc.isContinuous();
	}

	public boolean isRelative() {
		return fgc.isRelative();
	}

	public double getBearing() {
		return tc.getBearing();
	}
	
	
	//////////////////////////////////////////////////
	// Adapter - Setters
	//////////////////////////////////////////////////

	public void setGeneratedForce(final Vector vector) {
		fgc.setVector(vector);
	}

	public void setContinuous(final boolean isContinuous) {
		fgc.setContinuous(isContinuous);
	}

	public void setRelative(final boolean isRelative) {
		fgc.setRelative(isRelative);
	}
}