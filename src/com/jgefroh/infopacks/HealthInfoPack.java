package com.jgefroh.infopacks;

import com.jgefroh.components.HealthComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

public class HealthInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private HealthComponent hc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public HealthInfoPack(final IEntity owner)
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
			hc = owner.getComponent(HealthComponent.class);	
			if(hc==null)
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
		if(entity.getComponent(HealthComponent.class)!=null)
		{
			return new HealthInfoPack(entity);
		}
		return null;
	}
	
	/**
	 * @see HealthComponent#getCurHealth()
	 */
	public int getCurHealth()
	{
		return hc.getCurHealth();
	}
	
	public int getMaxHealth()
	{
		return hc.getMaxHealth();
	}
	
	//////////
	// SETTERS
	//////////
	
	/**
	 * @see HealthComponent#setCurHealth(int)
	 */
	public void setCurHealth(final int curHealth)
	{
		hc.setCurHealth(curHealth);
	}
	
	public void setMaxHealth(final int maxHealth)
	{
		hc.setMaxHealth(maxHealth);
	}
}
