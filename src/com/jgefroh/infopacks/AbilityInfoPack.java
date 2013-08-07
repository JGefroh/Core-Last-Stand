package com.jgefroh.infopacks;

import com.jgefroh.components.AbilityComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

/**
 * @author Joseph Gefroh
 */
public class AbilityInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////	
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private AbilityComponent ac;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public AbilityInfoPack(final IEntity owner)
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
			ac = owner.getComponent(AbilityComponent.class);
			if(ac==null)
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
		if(entity!=null
			&&entity.getComponent(AbilityComponent.class)!=null)
		{
			return new AbilityInfoPack(entity);
		}
		return null;
	}
	
	
	
	//////////
	// SETTERS
	//////////	
}