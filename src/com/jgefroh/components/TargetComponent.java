package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Marker that indicates the owning Entity is targetable by the AI.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class TargetComponent extends AbstractComponent
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
	 */
	public TargetComponent()
	{
		init();
	}
	
	@Override
	public void init()
	{

	}
	
	
	//////////
	// GETTERS
	//////////
	
	//////////
	// SETTERS
	//////////
}
