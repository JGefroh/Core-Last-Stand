package com.jgefroh.data;


/**
 * Contains data identifying a single subsection of a texture.
 * @author Joseph Gefroh
 */
public class Sprite
{
	//////////
	// DATA
	//////////
	/**The ID of the sprite.*/
	private int spriteID;
	
	/**The human readable name of the sprite.*/
	private String name;
	
	/**The pixel X coordinate for the top left corner of the sprite.*/
	private int xMin;
	
	/**The pixel X coordinate for the bottom right corner of the sprite.*/
	private int xMax;
	
	/**The pixel Y coordinate for the top left corner of the sprite.*/
	private int yMin;
	
	/**The pixel Y coordinate for the bottom right corner of the sprite.*/
	private int yMax;
	
	/**The texture u-coordinate for the top left corner of the sprite.*/
	private float uMin;
	
	/**The texture u-coordinate for the bottom right corner of the sprite.*/
	private float uMax;
	
	/**The texture v-coordinate for the top left corner of the sprite.*/
	private float vMin;
	
	/**The texture v-coordinate for the bottom right corner of the sprite.*/
	private float vMax;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new texture data object for a texture.
	 */
	public Sprite()
	{
	}
	
	
	//////////
	// GETTERS
	//////////
	/**
	 * Gets the unique ID of the sprite.
	 * @return	the ID of the sprite
	 */
	public int getSpriteID()
	{
		return this.spriteID;
	}
	
	/**
	 * Gets the human readable name of the sprite.
	 * @return the human readble name of the sprite.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Gets the pixel X-coordinate for the top left corner of the sprite.
	 * @return	the pixel x coordinate
	 */
	public int getXMin()
	{
		return this.xMin;
	}
	
	/**
	 * Gets the pixel X-coordinate for the bottom right corner of the sprite.
	 * @return	the pixel x coordinate
	 */
	public int getXMax()
	{
		return this.xMax;
	}
	
	/**
	 * Gets the pixel Y-coordinate for the top left corner of the sprite.
	 * @return	the pixel Y-coordinate
	 */
	public int getYMin()
	{
		return this.yMin;
	}
	
	/**
	 * Gets the pixel Y-coordinate for the bottom right corner of the sprite.
	 * @return	the pixel Y-coordinate
	 */
	public int getYMax()
	{
		return this.yMax;
	}
	
	/**
	 * Gets the texture U-coordinate for the top left corner of the sprite.
	 * @return	the texture U-coordinate
	 */
	public float getUMin()
	{
		return this.uMin;
	}
	
	/**
	 * Gets the texture U-coordinate for the bottom right corner of the sprite.
	 * @return	the texture U-coordinate
	 */
	public float getUMax()
	{
		return this.uMax;
	}
	
	/**
	 * Gets the texture V-coordinate for the top left corner of the sprite.
	 * @return	the texture V-coordinate
	 */
	public float getVMin()
	{
		return this.vMin;
	}
	
	/**
	 * Gets the texture V-coordinate for the bottom right corner of the sprite.
	 * @return	the texture V-coordinate
	 */
	public float getVMax()
	{
		return this.vMax;
	}
	
	
	//////////
	// SETTERS
	//////////
	/**
	 * Sets the unique ID of the sprite. It should be positive.
	 * @param spriteID	the ID of the sprite
	 */
	public void setSpriteID(final int spriteID)
	{
		this.spriteID = spriteID;
	}
	
	/**
	 * Sets the human readable name of the sprite.
	 * @param name	the human readable name of the sprite
	 */
	public void setName(final String name)
	{
		this.name = name;
	}
	/**
	 * Sets the pixel X-coordinate for the top left corner of the sprite.
	 * @param xMin	the pixel X-coordinate
	 */
	public void setXMin(final int xMin)
	{
		this.xMin = xMin;
	}
	
	/**
	 * Sets the pixel X-coordinate for the bottom right corner of the sprite.
	 * @param xMax	the pixel X-coordinate
	 */
	public void setXMax(final int xMax)
	{
		this.xMax = xMax;
	}
	
	/**
	 * Sets the pixel Y-coordinate for the top left corner of the sprite.
	 * @param yMin	the pixel Y-coordinate
	 */
	public void setYMin(final int yMin)
	{
		this.yMin = yMin;
	}
	
	/**
	 * Sets the pixel Y-coordinate for the bottom right corner of the sprite.
	 * @param yMax	the pixel Y-coordinate
	 */
	public void setYMax(final int yMax)
	{
		this.yMax = yMax;
	}
	
	/**
	 * Sets the texture U-coordinate for the top left corner of the sprite.
	 * @param uMin	the pixel U-coordinate
	 */
	public void setUMin(final float uMin)
	{
		this.uMin = uMin;
	}
	
	/**
	 * Sets the texture U-coordinate for the bottom right corner of the sprite.
	 * @param uMax	the pixel U-coordinate
	 */
	public void setUMax(final float uMax)
	{
		this.uMax = uMax;
	}
	
	/**
	 * Sets the texture V-coordinate for the top left corner of the sprite.
	 * @param vMin	the pixel V-coordinate
	 */
	public void setVMin(final float vMin)
	{
		this.vMin = vMin;
	}
	
	/**
	 * Sets the texture V-coordinate for the bottom right corner of the sprite.
	 * @param vMax	the pixel V-coordinate
	 */
	public void setVMax(final float vMax)
	{
		this.vMax = vMax;
	}
}
