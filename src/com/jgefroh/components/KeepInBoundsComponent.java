package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data that keeps entities inside the bounds.
 * 
 * 
 * Date: 05JUL13
 * @author Joseph Gefroh
 */
public class KeepInBoundsComponent extends AbstractComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The last valid X position of the entity.*/
	private double lastX;
	
	/**The last valid Y position of the entity.*/
	private double lastY;
	
	
	//////////
	// INIT
	//////////
	public KeepInBoundsComponent()
	{	
	}

	@Override
	public void init()
	{
		setLastX(0);
		setLastY(0);
	}
	
	
	//////////
	// GETTERS
	//////////
	/**
	 * Gets the last X Position of the entity.
	 * @return	the last X position
	 */
	public double getLastX()
	{
		return this.lastX;
	}
	
	/**
	 * Gets the last Y position of the entity.
	 * @return	the last Y position
	 */
	public double getLastY()
	{
		return this.lastY;
	}
	//////////
	// SETTERS
	//////////	
	/**
	 * Gets the last X Position of the entity.
	 * @param lastX the last X position
	 */
	public void setLastX(final double lastX)
	{
		this.lastX = lastX;
	}
	
	/**
	 * Gets the last Y position of the entity.
	 * @param lastY	the last Y position
	 */
	public void setLastY(final double lastY)
	{
		this.lastY = lastY;
	}
}
