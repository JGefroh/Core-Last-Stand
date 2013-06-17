package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data that allows entities to be destroyed after moving offscreen.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class OutOfBoundsComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**FLAG: Indicates whether the entity should be checked or not.*/
	private boolean isChecking = false;
	
	
	//////////
	// INIT
	//////////
	public OutOfBoundsComponent(final IEntity entity)
	{	
		setOwner(entity);
		init();
	}

	@Override
	public void init()
	{
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
	 * Gets the flag indicating the entity should be checked or not.
	 * @return	true if the entity should be checked; false otherwise
	 */
	public boolean isChecking()
	{
		return this.isChecking;
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
	 * Sets the flag indicating the Entity should be checked or not.
	 * @param isChecking true if the entity should be checked; false otherwise
	 */
	public void setChecking(final boolean isChecking)
	{
		this.isChecking = isChecking;
	}
}
