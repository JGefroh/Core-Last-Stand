package com.jgefroh.infopacks;

import com.jgefroh.components.ForceGeneratorComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.data.Vector;

public class ForceInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private ForceGeneratorComponent fgc;
	
	/**A component this InfoPack depends on.*/
	private VelocityComponent vc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;	
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public ForceInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	@Override
	public boolean isDirty()
	{
		if(owner.hasChanged())
		{
			fgc = owner.getComponent(ForceGeneratorComponent.class);
			vc = owner.getComponent(VelocityComponent.class);
			if(fgc==null || vc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}

	public Vector getGeneratedForce()
	{
		return fgc.getVector();
	}
	
	public double getMaxMagnitude()
	{
		return fgc.getMaxMagnitude();
	}
	
	public double getIncrement()
	{
		return fgc.getIncrement();
	}
	
	public Vector getSumVector()
	{
		return vc.getTotalMovementVector();
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	public void setGeneratedForce(final Vector vector)
	{
		fgc.setVector(vector);
	}
	//////////
	// METHODS
	//////////


}