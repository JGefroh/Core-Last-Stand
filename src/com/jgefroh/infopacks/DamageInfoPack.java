package com.jgefroh.infopacks;

import com.jgefroh.components.DamageComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class DamageInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private DamageComponent dc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public DamageInfoPack(final IEntity owner)
	{
		this.owner = owner;
		checkDirty();
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
		return this.isDirty;
	}
	
	@Override
	public boolean checkDirty()
	{
		if(owner.hasChanged())
		{
			dc = owner.getComponent(DamageComponent.class);	
			if(dc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}

	public int getDamage()
	{
		return dc.getDamage();
	}
	
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	public void setDamage(final int damage)
	{
		dc.setDamage(damage);
	}
}
