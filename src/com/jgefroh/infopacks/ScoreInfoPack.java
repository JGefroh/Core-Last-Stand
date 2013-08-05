package com.jgefroh.infopacks;

import com.jgefroh.components.ScoreComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * @author Joseph Gefroh
 */
public class ScoreInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private ScoreComponent sc;
		
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public ScoreInfoPack(final IEntity owner)
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
			sc = owner.getComponent(ScoreComponent.class);			
			
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
		if(entity.getComponent(ScoreComponent.class)!=null)
		{
			return new ScoreInfoPack(entity);
		}
		return null;
	}
	
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	public int getScore()
	{
		return sc.getScore();
	}
	//////////
	// SETTERS
	//////////
	
}
