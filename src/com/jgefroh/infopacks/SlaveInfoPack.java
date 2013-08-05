package com.jgefroh.infopacks;

import com.jgefroh.components.SlaveComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 *
 * 
 * @author Joseph Gefroh
 */
public class SlaveInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private SlaveComponent sc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;	
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public SlaveInfoPack(final IEntity owner)
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
	
	public IEntity getMaster()
	{
		return this.getMaster();
	}
	
	@Override
	public boolean checkDirty()
	{
		if(owner.hasChanged())
		{
			sc = owner.getComponent(SlaveComponent.class);
			if(sc==null)
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
		if(entity.getComponent(SlaveComponent.class)!=null)
		{
			return new SlaveInfoPack(entity);
		}
		return null;
	}

	
	
	//////////
	// SETTERS
	//////////
	public void setMaster(final IEntity master)
	{
		sc.setMaster(master);
	}
	
}