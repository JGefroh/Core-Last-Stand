package com.jgefroh.components;

import java.util.ArrayList;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to identify GUI elements.
 * 
 * 
 * Date: 05JUL13
 * @author Joseph Gefroh
 */
public class GUIIconComponent implements IComponent
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
	
	private double defXPos;
	private double defYPos;
	private double defHeight;
	private double defWidth;
	
	private int minVal;
	private int maxVal;
	private int curVal;
	
	private ArrayList<String> children;
	//////////
	// INIT
	//////////
	public GUIIconComponent()
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
	
	/**
	 * Gets the default height of this GUI element.
	 * @return	the default height
	 */
	public double getDefHeight()
	{
		return this.defHeight;
	}
	
	/**
	 * Gets the default width of this GUI element.
	 * @return	the default width
	 */
	public double getDefWidth()
	{
		return this.defWidth;
	}
	
	/**
	 * Gets the default X position of this GUI element.
	 * @return	the default X position
	 */
	public double getDefXPos()
	{
		return this.defXPos;
	}
	
	/**
	 * Gets the default Y position of this GUI element.
	 * @return the default Y position
	 */
	public double getDefYPos()
	{
		return this.defYPos;
	}
	
	public int getMinVal()
	{
		return this.minVal;
	}
	
	public int getMaxVal()
	{
		return this.maxVal;
	}
	
	public int getCurVal()
	{
		return this.curVal;
	}
	
	public ArrayList<String> getChildren()
	{
		return (this.children == null) ? new ArrayList<String>() : this.children;
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
	
	public void setDefXPos(final double defXPos)
	{
		this.defXPos = defXPos;
	}
	public void setDefYPos(final double defYPos)
	{
		this.defYPos = defYPos;
	}
	public void setDefWidth(final double defWidth)
	{
		this.defWidth = defWidth;
	}
	public void setDefHeight(final double defHeight)
	{
		this.defHeight= defHeight;
	}
	public void setMinVal(final int minVal)
	{
		this.minVal = minVal;
	}
	
	public void setMaxVal(final int maxVal)
	{
		this.maxVal = maxVal;
	}
	
	public void setCurVal(final int curVal)
	{
		this.curVal = curVal;
	}
	
	public void setChildren(final ArrayList<String> children)
	{
		this.children = (children == null) ? new ArrayList<String>() : children;
	}
}
