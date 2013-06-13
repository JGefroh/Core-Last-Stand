package com.jgefroh.data;

import java.util.HashMap;
import java.util.Iterator;


/**
 * Contains data related to a texture, including sprite coordinates.
 * @author Joseph Gefroh
 */
public class Texture
{
	//TODO: I like hashmaps too much.
	//////////
	// DATA
	//////////
	/**The OpenGL assigned texture ID.*/
	private int textureID;
	
	/**The pixel width of the source image.*/
	private int imageWidth;	
	
	/**The pixel height of the source image.*/
	private int imageHeight;

	/**The file path of the source image.*/
	private String path;
	
	/**Holds data related to the sprites and positions.*/
	private HashMap<Integer, Sprite> sprites;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new texture data object for a texture.
	 */
	public Texture()
	{
		textureID = -1;
		imageWidth = -1;
		imageHeight = -1;
		path = "";
		sprites = new HashMap<Integer, Sprite>();
	}
	
	//////////
	// GETTERS
	//////////
	/**
	 * Get the OpenGL assigned texture id.
	 * @return	the OpenGL assigned texture id
	 */
	public int getTextureID()
	{
		return this.textureID;
	}
	
	/**
	 * Get the width of the image.
	 * @return	the pixel width of the image
	 */
	public int getImageWidth()
	{
		return this.imageWidth;
	}
	
	/**
	 * Get the height of the image.
	 * @return	the pixel height of the image
	 */
	public int getImageHeight()
	{
		return this.imageHeight;
	}
	
	/**
	 * Get the file path of the source image.
	 * @return	the file path to the source image
	 */
	public String getPath()
	{
		return this.path;
	}
	
	/**
	 * Get the calculated uMin texture coordinate for a sprite on this texture.
	 * @param spriteID	the id of the sprite
	 * @return	the uMin texture coordinate, or -1 if there was an error
	 */
	public float getUMin(final int spriteID)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			return sprite.getUMin();
		}
		return -1;
	}
	

	/**
	 * Get the calculated uMax texture coordinate for a sprite on this texture.
	 * @param spriteID	the id of the sprite
	 * @return	the uMax texture coordinate, or -1 if there was an error
	 */
	public float getUMax(final int spriteID)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			return sprite.getUMax();
		}
		return -1;
	}
	
	/**
	 * Get the calculated vMin texture coordinate for a sprite on this texture.
	 * @param spriteID	the id of the sprite
	 * @return	the vMin texture coordinate, or -1 if there was an error
	 */
	public float getVMin(final int spriteID)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			return sprite.getVMin();
		}
		return -1;
	}

	/**
	 * Get the calculated vMax texture coordinate for a sprite on this texture.
	 * @param spriteID	the id of the sprite
	 * @return	the vMax texture coordinate, or -1 if there was an error
	 */
	public float getVMax(final int spriteID)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			return sprite.getVMax();
		}
		return -1;
	}
	
	/**
	 * Get the xMin pixel coordinate for a sprite on this texture.
	 * @param spriteID	the ID of the sprite
	 * @return	the xMin pixel coordinate. -1 if there was an error.
	 */
	public int getXMin(final int spriteID)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			return sprite.getXMin();
		}
		return -1;
	}
	
	/**
	 * Get the xMax pixel coordinate for a sprite on this texture.
	 * @param spriteID	the ID of the sprite
	 * @return	the xMax pixel coordinate. -1 if there was an error.
	 */
	public int getXMax(final int spriteID)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			return sprite.getXMax();
		}
		return -1;
	}
	
	/**
	 * Get the yMin pixel coordinate for a sprite on this texture.
	 * @param spriteID	the ID of the sprite
	 * @return	the yMin pixel coordinate. -1 if there was an error.
	 */
	public int getYMin(final int spriteID)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			return sprite.getYMin();
		}
		return -1;
	}
	
	/**
	 * Get the yMax pixel coordinate for a sprite on this texture.
	 * @param spriteID	the ID of the sprite
	 * @return	the yMax pixel coordinate. -1 if there was an error.
	 */
	public int getYMax(final int spriteID)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			return sprite.getYMax();
		}
		return -1;
	}
	
	/**
	 * Get the number of sprites stored for this texture.
	 * @return	the number of sprites associated with this texture
	 */
	public int getNumSprites()
	{
		return sprites.size();
	}
	//////////
	// SETTERS
	//////////
	/**
	 * Set the texture ID associated with this texture.
	 * @param textureID	the ID of the texture
	 */
	public void setTextureID(final int textureID)
	{
		this.textureID = textureID;
	}
	
	
	/**
	 * Set the width of the source image.
	 * @param imageWidth	the pixel width of the source image
	 */
	public void setImageWidth(final int imageWidth)
	{
		this.imageWidth = imageWidth;
	}
	
	/**
	 * Set the height of the source image.
	 * @param imageHeight	the pixel height of the source image
	 */
	public void setImageHeight(final int imageHeight)
	{
		this.imageHeight = imageHeight;
	}
	
	/**
	 * Set the image path of this texture.
	 * @param path	the file path of the source image
	 */
	public void setPath(final String path)
	{
		if(path!=null)
		{
			this.path = path;
		}
		else
		{
			this.path = "";
		}
	}
	
	/**
	 * Set the sprite texture coordinate.
	 * @param spriteID	the ID of the sprite
	 * @param uMin	the texture coordinate
	 */
	public void setSpriteUMin(final int spriteID, final float uMin)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			sprite.setUMin(uMin);
		}
		
	}
	
	/**
	 * Set the sprite texture coordinate.
	 * @param spriteID	the ID of the sprite
	 * @param uMax	the texture coordinate
	 */
	public void setSpriteUMax(final int spriteID, final float uMax)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			sprite.setUMax(uMax);
		}
		
	}
	
	/**
	 * Set the sprite texture coordinate.
	 * @param spriteID	the ID of the sprite
	 * @param vMin	the texture coordinate
	 */
	public void setSpriteVMin(final int spriteID, final float vMin)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			sprite.setVMin(vMin);
		}
		
	}
	
	/**
	 * Set the sprite texture coordinate.
	 * @param spriteID	the ID of the sprite
	 * @param vMax	the texture coordinate
	 */
	public void setSpriteVMax(final int spriteID, final float vMax)
	{
		Sprite sprite = sprites.get(spriteID);
		if(sprite!=null)
		{
			sprite.setVMax(vMax);
		}
		
	}
	
	/**
	 * Set the sprites.
	 * @param sprites	
	 */
	public void setSprites(final HashMap<Integer, Sprite> sprites)
	{
		if(sprites==null)
		{
			this.sprites = new HashMap<Integer, Sprite>();
		}
		else
		{			
			this.sprites = sprites;
		}
	}
	
	/**
	 * Set the width and the height of the image. Convenience method.
	 * @param imageWidth
	 * @param imageHeight
	 */
	public void setImageDimensions(final int imageWidth, final int imageHeight)
	{
		setImageWidth(imageWidth);
		setImageHeight(imageHeight);
	}
	
	
	//////////
	// METHODS
	//////////
	public Iterator<Sprite> getSpriteIterator()
	{
		return sprites.values().iterator();
	}
}
