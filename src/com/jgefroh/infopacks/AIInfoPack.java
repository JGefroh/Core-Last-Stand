package com.jgefroh.infopacks;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;


/**
 * Intended to be used by the AISystem.
 * 
 * Controls access to the following components:
 * AIComponent
 * TransformComponent
 * 
 * @author Joseph Gefroh
 */
public class AIInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private AIComponent ac;
	
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
	public AIInfoPack(final IEntity owner)
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
			tc = owner.getComponent(TransformComponent.class);
			ac = owner.getComponent(AIComponent.class);			
			if(tc==null||ac==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}

	@Override
	public AIInfoPack generate(final IEntity entity)
	{
		if(entity!=null
				&&entity.getComponent(AIComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{			
			return new AIInfoPack(entity);
		}
		return null;
	}

	public double getAttackChance()
	{
		return ac.getAttackChance();
	}
	
	public String getAIType()
	{
		return ac.getAIType();
	}
	
	public boolean isInRangeOfTarget()
	{
		return ac.isInRangeOfTarget();
	}
	
	public boolean isActive()
	{
		return ac.isActive();
	}
	
	
	//////////
	// SETTERS
	//////////	
	public void setAttackChance(final double attackChance)
	{
		ac.setAttackChance(attackChance);
	}
	
	public void setAIType(final String aiType)
	{
		ac.setAIType(aiType);
	}
	
	public void setInRangeOfTarget(final boolean isInRangeOfTarget)
	{
		ac.setInRangeOfTarget(isInRangeOfTarget);
	}
	
	public void setActive(final boolean isActive)
	{
		ac.setActive(isActive);
	}
	

}
