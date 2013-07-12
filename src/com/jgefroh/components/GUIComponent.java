package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data that keeps entities inside the bounds.
 * 
 * 
 * Date: 05JUL13
 * @author Joseph Gefroh
 */
public class GUIComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	//////////
	// INIT
	//////////
	public GUIComponent()
	{	
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

	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
}
