package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data necessary to render the entity.
 * 
 * 
 * DATE: 17JUN13
 * @author Joseph Gefroh
 */
public class RenderComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**Flag to tell if the entity is visible or invisible.*/
	private boolean isVisible;
	
	/**The absolute path to the image of the entity.*/
	private String texturePath;
	
	/**The texture ID assigned by the TextureSystem when it loaded the image.*/
	private int textureID;
	
	/**The index of the sprite used to render*/
	private int spriteID;
	
	/**The red value of this entity.*/
	private float r = 255;
	
	/**The green value of this entity.*/
	private float g = 255;
	
	/**The blue value of this entity.*/
	private float b = 255;
	
	/**The render offset for the X coordinate.*/
	private double xRenderOffset;
	
	/**The render offset for the Y coordinate.*/
	private double yRenderOffset;
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of this component
	 */
	public RenderComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
		setVisible(true);
		setTextureID(-1);
		setSpriteID(-1);
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Returns the ID of the texture this entity uses.
	 * @return
	 */
	public int getTextureID()
	{
		
		return this.textureID;
	}
	
	/**
	 * Returns the absolute path of the texture.
	 * @return
	 */
	public String getTexturePath()
	{
		return this.texturePath;
	}

	/**
	 * Gets the sprite index of this entity.
	 * @return	the sprite index of this entity
	 */
	public int getSpriteID()
	{
		return this.spriteID;
	}
	
	/**
	 * Returns the visibility flag of this entity.
	 * @return	true if visible, false otherwise.
	 */
	public boolean isVisible()
	{
		return this.isVisible;
	}
	
	/**
	 * Returns the red color value.
	 * @return	the color value, from 0 to 1.
	 */
	public float getR()
	{
		return this.r;
	}
	
	/**
	 * Returns the green color value.
	 * @return	the color value, from 0 to 1.
	 */
	public float getG()
	{
		return this.g;
	}
	
	/**
	 * Returns the blue color value.
	 * @return	the color value, from 0 to 1.
	 */
	public float getB()
	{
		return this.b;
	}
	
	/**Returns the X render offset for this entity.
	 * @return the render offset
	 */
	public double getXRenderOffset()
	{
		return this.xRenderOffset;
	}	
	
	/**Returns the Y render offset for this entity.
	 * @return the render offset
	 */
	public double getYRenderOffset()
	{
		return this.yRenderOffset;
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	/**
	 * Sets the sprite ID of the image being drawn.
	 * @param spriteID	the ID of the sprite to draw
	 */
	public void setSpriteID(final int spriteID)
	{
		this.spriteID = spriteID;
	}

	/**
	 * Sets the visibility of this entity.
	 * @param isVisible	true if visible; false otherwise.
	 */
	public void setVisible(final boolean isVisible)
	{
		this.isVisible = isVisible;
	}
	
	/**
	 * Sets the ID of the texture.
	 * @param textureID	the integer ID of the texture
	 */
	public void setTextureID(final int textureID)
	{
		this.textureID = textureID;
	}
	
	/**
	 * Sets the absolute path of the texture.
	 * @param texturePath	the path of the texture
	 */
	public void setTexturePath(final String texturePath)
	{
		this.texturePath = texturePath;
	}
	
	/**Sets the X render offset for this entity.
	 * @param xRenderOffset the render offset
	 */
	public void setXRenderOffset(final double xRenderOffset)
	{
		this.xRenderOffset = xRenderOffset;
	}		
	
	/**Sets the Y render offset for this entity.
	 * @param xRenderOffset the render offset
	 */
	public void setYRenderOffset(final double yRenderOffset)
	{
		this.yRenderOffset = yRenderOffset;
	}	
	
	/**
	 * Sets the RGB color for this object.
	 * @param r	the red
	 * @param g	the green
	 * @param b	the blue
	 */
	public void setRGB(final float r, final float g, final float b)
	{
		if(r>=0)
		{
			this.r = r;
		}
		if(g>=0)
		{
			this.g = g;
		}
		if(b>=0)
		{
			this.b = b;
		}
	}

}
