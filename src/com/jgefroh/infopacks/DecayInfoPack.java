package com.jgefroh.infopacks;

import com.jgefroh.components.DecayComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

/**
 * @author Joseph Gefroh
 */
public class DecayInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private DecayComponent dc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public DecayInfoPack(final IEntity owner)
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
			dc = owner.getComponent(DecayComponent.class);	
			if(dc==null)
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
		if(entity.getComponent(DecayComponent.class)!=null)
		{
			return new DecayInfoPack(entity);
		}
		return null;
	}
	public long getLastUpdateTime()
	{
		return dc.getLastUpdateTime();
	}

	public long getTimeUntilDecay()
	{
		return dc.getTimeUntilDecay();
	}

	//////////
	// SETTERS
	//////////
	
	public void setLastUpdateTime(final long lastUpdateTime)
	{
		dc.setLastUpdateTime(lastUpdateTime);
	}
	
}
