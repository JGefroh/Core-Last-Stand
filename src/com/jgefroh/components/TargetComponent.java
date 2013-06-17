package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Marker that indicates the owning Entity is targetable by the AI.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class TargetComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**Flag to tell if the entity is visible or invisible.*/
	private boolean isVisible;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of this component
	 */
	public TargetComponent(final IEntity owner)
	{
		setOwner(owner);
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
	
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
}
