package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data necessary to render the entity.
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
	
	/**The pixel WIDTH of the entity.*/
	private int width;
	
	/**The pixel HEIGHT of the entity.*/
	private int height;
	
	/**The absolute path to the image of the entity.*/
	private String texturePath;
	
	/**The texture ID assigned by the TextureSystem when it loaded the image.*/
	private int textureID;
	
	/**The index of the sprite used to render*/
	private int spriteID;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new Render component.
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
		setTexturePath(null);
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
	 * Return the ID of the texture this entity uses.
	 * @return
	 */
	public int getTextureID()
	{
		
		return this.textureID;
	}
	
	/**
	 * Return the absolute path of the texture.
	 * @return
	 */
	public String getTexturePath()
	{
		return this.texturePath;
	}

	/**
	 * Get the sprite index of this entity.
	 * @return	the sprite index of this entity
	 */
	public int getSpriteID()
	{
		return this.spriteID;
	}

	/**
	 * Get the pixel width of this entity.
	 * @return	the int width of this entity
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * Get the pixel height of this entity.
	 * @return	the int height of this entity
	 */
	public int getHeight()
	{
		return this.height;
	}
	
	/**
	 * Return the visibility flag of this entity.
	 * @return	true if visible, false otherwise.
	 */
	public boolean isVisible()
	{
		return this.isVisible;
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
	 * Set the sprite index of the image being drawn.
	 * @param spriteID
	 */
	public void setSpriteID(final int spriteID)
	{
		this.spriteID = spriteID;
	}

	/**
	 * Set the visibility of this entity.
	 * @param isVisible	true if visible, false otherwise.
	 */
	public void setVisible(final boolean isVisible)
	{
		this.isVisible = isVisible;
	}
	
	/**
	 * Set the ID of the texture.
	 * @param textureID	the integer ID of the texture
	 */
	public void setTextureID(final int textureID)
	{
		this.textureID = textureID;
	}
	
	/**
	 * Set the absolute path of the texture.
	 * @param texturePath
	 */
	public void setTexturePath(final String texturePath)
	{
		this.texturePath = texturePath;
	}


}
