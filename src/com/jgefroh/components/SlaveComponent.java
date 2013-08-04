package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to link an entity to another entity.
 * 
 * 
 * Date: 21JUN13
 * @author Joseph Gefroh
 */
public class SlaveComponent extends AbstractComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The master of this Entity.*/
	private IEntity master;
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 */
	public SlaveComponent()
	{
		init();
	}
	
	@Override
	public void init()
	{
	}

	/**
	 * Gets the Entity this Entity is linked to.
	 * @return	the Entity that this entity follows
	 */
	public IEntity getMaster()
	{
		return this.master;
	}
	
	//////////
	// GETTERS
	//////////
	

	//////////
	// SETTERS
	//////////	
	/**
	 * Sets the Entity this Entity is linked to.
	 * @param master	the Entity that this entity follows
	 */
	public void setMaster(final IEntity master)
	{
		this.master = master;
	}
	
}
