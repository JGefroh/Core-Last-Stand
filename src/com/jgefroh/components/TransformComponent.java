package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data related to the object's internal position.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class TransformComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of the component.*/
	private IEntity owner;
	
	/**The global X position of the object.*/
	private double xPos;
	
	/**The global Y position of the object.*/
	private double yPos;
	
	/**The global Z position of the object.*/
	private double zPos;
	
	/**The last global X position of the object.*/
	private int lastXPos;
	
	/**The last global Y position of the object.*/
	private int lastYPos;
	
	/**The last global Z position of the object.*/
	private int lastZPos;
	
	/**The global width of the object.*/
	private double width;
	
	/**The global height of the object.*/
	private double height;

	/**The direction the object is facing.*/
	private double bearing;
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of the component
	 */
	public TransformComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{	
		setXPos(0);
		setYPos(0);
		setZPos(0);

		setWidth(0);
		setHeight(0);
		setBearing(0);
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
	 * Gets the direction the object is facing, in degrees.
	 * @return	the direction the object is facing, in degrees
	 */
	public double getBearing()
	{
		return this.bearing;
	}
	/**
	 * Gets the X position of the entity
	 * @return	the X coordinate of the entity
	 */
	public double getXPos()
	{
		return this.xPos;
	}
	
	/**
	 * Gets the Y position of the entity
	 * @return	the Y coordinate of the entity
	 */
	public double getYPos()
	{
		return this.yPos;
	}
	
	/**
	 * Gets the Z position of the entity
	 * @return	the Z coordinate of the entity
	 */
	public double getZPos()
	{
		return this.zPos;
	}
	
	/**
	 * Gets the previous X position of the entity
	 * @return	the previous X position of the entity
	 */
	public int getLastXPos()
	{
		return this.lastXPos;
	}
	
	/**
	 * Gets the previous Y position of the entity
	 * @return	the previous Y position of the entity
	 */
	public int getLastYPos()
	{
		return this.lastYPos;
	}
	
	/**
	 * Gets the previous Z position of the entity
	 * @return	the previous Z position of the entity
	 */
	public int getLastZPos()
	{
		return this.lastZPos;
	}
	
	/**
	 * Gets the width of the entity
	 * @return	the width of the entity
	 */
	public double getWidth()
	{
		return this.width;
	}
	
	/**
	 * Gets the height of the entity
	 * @return	the height of the entity
	 */
	public double getHeight()
	{
		return this.height;
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
	 * Sets the direction the object is facing, in degrees
	 * @param bearing	the direction the object is facing, in degrees
	 */
	public void setBearing(final double bearing)
	{
		this.bearing = bearing;
	}
	/**
	 * Sets the X coordinate position of the entity.
	 * @param xPos	the X coordinate of the entity
	 */
	public void setXPos(final double xPos)
	{
		this.xPos = xPos;
	}
	
	/**
	 * Sets the Y coordinate position of the entity.
	 * @param yPos	the Y coordinate of the entity
	 */
	public void setYPos(final double yPos)
	{
		this.yPos = yPos;
	}
	
	/**
	 * Sets the Z coordinate position of the entity.
	 * @param zPos	the Z coordinate of the entity
	 */
	public void setZPos(final double zPos)
	{
		this.zPos = zPos;
	}
	
	/**
	 * Sets the width of the entity.
	 * @param width	the width of the entity
	 */
	public void setWidth(final double width)
	{
		this.width = width;
	}
	
	/**
	 * Sets the height of the entity.
	 * @param height the height of the entity
	 */
	public void setHeight(final double height)
	{
		this.height = height;
	}
}
