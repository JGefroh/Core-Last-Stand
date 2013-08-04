package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to identify GUI elements.
 * 
 * 
 * Date: 05JUL13
 * @author Joseph Gefroh
 */
public class GUIComponent extends AbstractComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;

	private String hoverEffect;
	
	private int id;
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

	public int getID()
	{
		return this.id;
	}
	//////////
	// SETTERS
	//////////
	
	public void setID(final int id)
	{
		this.id = id;
	}
	
}
