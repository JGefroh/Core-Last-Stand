package com.jgefroh.infopacks;

import java.util.ArrayList;

import com.jgefroh.components.GUICharSlotComponent;
import com.jgefroh.components.GUIComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


public class GUICharSlotInfoPack implements IInfoPack
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
	
	private GUIComponent gc;

	private GUICharSlotComponent gcsc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public GUICharSlotInfoPack(final IEntity owner)
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
			gc = owner.getComponent(GUIComponent.class);
			gcsc = owner.getComponent(GUICharSlotComponent.class);
			if(tc==null||rc==null||gc==null||gcsc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	@Override
	public boolean isDirty()
	{
		return this.isDirty;
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


	public int getSlotNum()
	{
		return gcsc.getSlotNum();
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	public void setXPos(final double xPos)
	{
		tc.setXPos(xPos);
	}
	
	public void setYPos(final double yPos)
	{
		tc.setYPos(yPos);
	}
	
	public void setWidth(final double width)
	{
		tc.setWidth(width);
	}
	
	public void setHeight(final double height)
	{
		tc.setHeight(height);
	}
	
	public void setRGB(final float r, final float g, final float b)
	{
		rc.setRGB(r, g, b);
	}
	
	public void setSpriteID(final int id)
	{
		rc.setSpriteID(id);
	}
	
	
	public void setVisible(final boolean isVisible)
	{
		rc.setVisible(isVisible);
	}

}
