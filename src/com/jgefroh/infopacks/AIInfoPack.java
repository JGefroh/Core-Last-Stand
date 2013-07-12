package com.jgefroh.infopacks;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the AISystem.
 * 
 * Controls access to the following components:
 * AIComponent
 * TransformComponent
 * 
 * @author Joseph Gefroh
 */
public class AIInfoPack implements IInfoPack
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
	public IEntity getOwner()
	{
		return this.owner;
	}

	public double getAttackChance()
	{
		return ac.getAttackChance();
	}
	
	@Override
	public boolean isDirty()
	{
		return this.isDirty;
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
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
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
