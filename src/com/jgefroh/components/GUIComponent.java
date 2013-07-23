package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to identify GUI elements.
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
	
	/**The command to execute when hovered over.*/
	private String commandOnHover = "";
	
	/**The category of this GUI element.*/
	private String category = "";
	
	/**The value of this GUI element.*/
	private String valueOnHover = "";
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
	
	/**
	 * Gets the command that is executed when this GUI element is hovered over.
	 * @return	the command to execute
	 */
	public String getCommandOnHover()
	{
		return this.commandOnHover;
	}
	
	/**
	 * Gets the category of this GUI element.
	 * @return	the category
	 */
	public String getCategory()
	{
		return this.category;
	}
	
	/**
	 * Gets the value of the GUI element when hovered over.
	 * @return	the value
	 */
	public String getValueOnHover()
	{
		return this.valueOnHover;
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
	 * Sets the command on hover of this GUI element.
	 * @param commandOnHover	the command to execute
	 */
	public void setCommandOnHover(final String commandOnHover)
	{
		this.commandOnHover = (commandOnHover!=null) ? commandOnHover : "";
	}
	
	/**
	 * Sets the category of this GUI element.
	 * @param category	the category
	 */
	public void setCategory(final String category)
	{
		this.category = (category !=null) ? category : "";
	}
	
	public void setHoverValue(final String valueOnHover)
	{
		this.valueOnHover = (valueOnHover!=null) ? valueOnHover : "";
	}
}
