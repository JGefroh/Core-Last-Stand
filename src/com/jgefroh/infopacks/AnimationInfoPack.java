package com.jgefroh.infopacks;

import com.jgefroh.components.AnimationComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the AnimationSystem.
 * 
 * Controls access to the following components:
 * AnimationComponent
 * RenderComponent
 * 
 * @author Joseph Gefroh
 */
public class AnimationInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private AnimationComponent ac;
	
	/**A component this InfoPack depends on.*/
	private RenderComponent rc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public AnimationInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public boolean isDirty()
	{
		if(owner.hasChanged())
		{
			ac = owner.getComponent(AnimationComponent.class);
			rc = owner.getComponent(RenderComponent.class);			
			if(ac==null||rc==null)
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
	
	/**
	 * @see AnimationComponent#getAnimationSprite()
	 */
	public int getAnimationSprite()
	{
		return ac.getAnimationSprite();
	}
	
	/**
	 * @see AnimationComponent#getCurrentFrame()
	 */
	public int getCurrentFrame()
	{
		return ac.getCurrentFrame();
	}


	/**
	 * @see AnimationComponent#getInterval()
	 */
	public long getInterval()
	{
		return ac.getInterval();
	}


	/**
	 * @see AnimationComponent#getLastUpdateTime()
	 */
	public long getLastUpdateTime()
	{
		return ac.getLastUpdateTime();
	}
	
	/**
	 * @see AnimationComponent#getNumberOfFrames()
	 */
	public int getNumberOfFrames()
	{
		return ac.getNumberOfFrames();
	}


	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	/**
	 * @see RenderComponent#setSpriteID(int)
	 */
	public void setAnimationSprite(final int spriteID)
	{
		rc.setSpriteID(spriteID);
	}
	
	/**
	 * @see AnimationComponent#setCurrentFrame(int)
	 */
	public void setCurrentFrame(final int currentFrame)
	{
		ac.setCurrentFrame(currentFrame);
	}
	
	/**
	 * @see AnimationComponent#setLastUpdateTime(long)
	 */
	public void setLastUpdateTime(final long updateTime)
	{
		ac.setLastUpdateTime(updateTime);
	}
}