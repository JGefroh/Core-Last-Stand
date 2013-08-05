package com.jgefroh.infopacks;

import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the RenderSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * RenderComponent
 * 
 * @author Joseph Gefroh
 */
public class RenderInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
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
	public RenderInfoPack(final IEntity owner)
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
			rc = owner.getComponent(RenderComponent.class);			
			if(tc==null||rc==null)
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
		if(entity.getComponent(RenderComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{

			return new RenderInfoPack(entity);
		}
		return null;
	}
	
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * @see TransformComponent#getXPos() 
	 */
	public double getXPos()
	{
		return tc.getXPos();
	}	
	
	/**
	 * @see TransformComponent#getYPos() 
	 */
	public double getYPos()
	{
		return tc.getYPos();
	}
	
	/**
	 * @see TransformComponent#getZPos() 
	 */
	public double getZPos()
	{
		return tc.getZPos();
	}
	
	/**
	 * @see TransformComponent#getWidth() 
	 */
	public double getWidth()
	{
		return tc.getWidth();
	}
	
	/**
	 * @see TransformComponent#getHeight() 
	 */
	public double getHeight()
	{
		return tc.getHeight();
	}
	
	/**
	 * @see RenderComponent#isVisible() 
	 */
	public boolean isVisible()
	{
		return rc.isVisible();
	}
	
	/**
	 * @see RenderComponent#getSpriteID() 
	 */	
	public int getSpriteID()
	{
		return rc.getSpriteID();
	}
	
	/**
	 * @see RenderComponent#getTextureID() 
	 */
	public int getTextureID()
	{
		return rc.getTextureID();
	}
	
	/**
	 * @see RenderComponent#getTexturePath()
	 */
	public String getPath()
	{
		return rc.getTexturePath();
	}

	/**
	 * @see TransformComponent#getBearing()
	 */
	public int getBearing()
	{
		return (int)tc.getBearing();
	}
	
	public float getR()
	{
		return rc.getR();
	}
	public float getG()
	{
		return rc.getG();
	}
	public float getB()
	{
		return rc.getB();
	}
	
	public double getXRenderOffset()
	{
		return rc.getXRenderOffset();
	}
	
	public double getYRenderOffset()
	{
		return rc.getYRenderOffset();
	}
	//////////
	// SETTERS
	//////////
	/**
	 * @see RenderComponent#setTextureID(int)
	 */
	public void setTextureID(final int textureID)
	{
		rc.setTextureID(textureID);
	}

	/**
	 * @see RenderComponent#setSpriteID(int)
	 */
	public void setSpriteID(final int id)
	{
		rc.setSpriteID(id);
	}
}
