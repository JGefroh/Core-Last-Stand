package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class AbilityComponent extends AbstractComponent
{
	//////////
	// DATA
	//////////	
	private String abilityName;
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 */
	public AbilityComponent()
	{
	}
	
	@Override
	public void init()
	{
		this.abilityName = "";
	}

	
	//////////
	// GETTERS
	//////////
	
	//////////
	// SETTERS
	//////////


}
