package com.jgefroh.infopacks;

import java.util.ArrayList;

import com.jgefroh.components.GUIBarComponent;
import com.jgefroh.components.GUIComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


public class GUIBarInfoPack implements IInfoPack
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
	
	/**A component this InfoPack depends on.*/
	private GUIBarComponent gbc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public GUIBarInfoPack(final IEntity owner)
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
			gbc = owner.getComponent(GUIBarComponent.class);
			if(tc==null||rc==null||gbc==null)
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
	
	public double getXPos()
	{
		return tc.getXPos();
	}	
	
	public double getYPos()
	{
		return tc.getYPos();
	}
	
	public double getZPos()
	{
		return tc.getZPos();
	}
	
	public double getWidth()
	{
		return tc.getWidth();
	}
	
	public double getHeight()
	{
		return tc.getHeight();
	}
	
	public double getDefXPos()
	{
		return gbc.getDefXPos();
	}
	
	public double getDefYPos()
	{
		return gbc.getDefYPos();
	}
	
	public double getDefHeight()
	{
		return gbc.getDefHeight();
	}
	
	public double getDefWidth()
	{
		return gbc.getDefWidth();
	}
	
	public double getMaxValue()
	{
		return gbc.getMaxValue();
	}
	
	public double getCurValue()
	{
		return gbc.getCurValue();
	}
	
	public double getMaxWidth()
	{
		return gbc.getMaxWidth();
	}
	
	public double getMaxHeight()
	{
		return gbc.getMaxHeight();
	}

	public boolean left()
	{
		return gbc.left();
	}
	
	public boolean right()
	{
		return gbc.right();
	}
	
	public boolean up()
	{
		return gbc.up();
	}
	
	public boolean down()
	{
		return gbc.down();
	}
	
	public boolean collapseMiddleH()
	{
		return gbc.collapseMiddleH();
	}
	
	public boolean collapseMiddleV()
	{
		return gbc.collapseMiddleV();
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
	
	public void setMaxValue(final int maxValue)
	{
		gbc.setMaxValue(maxValue);
	}
	
	public void setCurValue(final int curValue)
	{
		gbc.setCurValue(curValue);
	}
	
	public void getShrinkDir(final int shrinkDir)
	{
		gbc.getShrinkDir();
	}
}
