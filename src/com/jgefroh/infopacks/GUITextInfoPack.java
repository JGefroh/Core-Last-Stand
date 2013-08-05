package com.jgefroh.infopacks;

import java.util.Iterator;

import com.jgefroh.components.GUITextComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


public class GUITextInfoPack extends AbstractInfoPack
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
	private GUITextComponent gtc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public GUITextInfoPack(final IEntity owner)
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
			gtc = owner.getComponent(GUITextComponent.class);
			if(tc==null||rc==null||gtc==null)
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
		if(entity.getComponent(GUITextComponent.class)!=null
				&&entity.getComponent(RenderComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new GUITextInfoPack(entity);
		}
		return null;
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
	
	public int getCharWidth()
	{
		return gtc.getCharWidth();
	}

	public int getCharHeight()
	{
		return gtc.getCharHeight();
	}
	
	public Iterator<String> getChildren()
	{
		return gtc.getChildren().iterator();
	}
	
	public char getDefaultChar()
	{
		return gtc.getDefaultChar();
	}
	
	public String getText()
	{
		return gtc.getText();
	}
	
	public int getNumCharsPerLine()
	{
		return gtc.getNumCharsPerLine();
	}
	
	public int getNumLines()
	{
		return gtc.getNumLines();
	}
	//////////
	// SETTERS
	//////////
	
	public void setXPos(final double xPos)
	{
		tc.setXPos(xPos);
	}
	
	public void setYPos(final double yPos)
	{
		tc.setYPos(yPos);
	}
	
	public void setCharWidth(final int charWidth)
	{
		gtc.setCharWidth(charWidth);
	}
	
	public void setCharHeight(final int charHeight)
	{
		gtc.setCharHeight(charHeight);
	}
	
	public void setRGB(final float r, final float g, final float b)
	{
		rc.setRGB(r, g, b);
	}
	
	public void setSpriteID(final int id)
	{
		rc.setSpriteID(id);
	}
	
	public void setDefaultChar(final char defaultChar)
	{
		gtc.setDefaultChar(defaultChar);
	}
	
	public void setText(final String text)
	{
		gtc.setText(text);
	}
}
