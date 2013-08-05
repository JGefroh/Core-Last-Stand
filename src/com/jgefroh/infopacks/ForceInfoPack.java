package com.jgefroh.infopacks;

import com.jgefroh.components.ForceGeneratorComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.data.Vector;

public class ForceInfoPack extends AbstractInfoPack
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
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
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
	public boolean checkDirty()
	{
		if(owner.hasChanged())
		{
			fgc = owner.getComponent(ForceGeneratorComponent.class);
			vc = owner.getComponent(VelocityComponent.class);
			tc = owner.getComponent(TransformComponent.class);
			if(fgc==null || vc==null || tc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}

	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(VelocityComponent.class)!=null
				&&entity.getComponent(ForceGeneratorComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new ForceInfoPack(entity);
		}
		return null;
	}
	public Vector getGeneratedVector()
	{
		return fgc.getVector();
	}
	
	public Vector getSumVector()
	{
		return vc.getTotalMovementVector();
	}
	
	public long getLastGenerated()
	{
		return fgc.getLastUpdated();
	}
	
	public long getGenerateInterval()
	{
		return fgc.getInterval();
	}
	
	public double getMagnitude()
	{
		return fgc.getMagnitude();
	}
	
	public boolean isContinuous()
	{
		return fgc.isContinuous();
	}
	
	public boolean isRelative()
	{
		return fgc.isRelative();
	}
	
	public double getBearing()
	{
		return tc.getBearing();
	}
	//////////
	// SETTERS
	//////////
	public void setGeneratedForce(final Vector vector)
	{
		fgc.setVector(vector);
	}
	
	public void setContinuous(final boolean isContinuous)
	{
		fgc.setContinuous(isContinuous);
	}
	

	public void setRelative(final boolean isRelative)
	{
		fgc.setRelative(isRelative);
	}
	//////////
	// METHODS
	//////////


}