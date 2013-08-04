package com.jgefroh.components;

import java.util.ArrayList;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to identify GUI elements.
 * 
 * 
 * Date: 05JUL13
 * @author Joseph Gefroh
 */
public class GUICounterComponent extends AbstractComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	private ArrayList<String> childrenIDs;
	
	private int numSlots;
	private int charWidth;
	private int charHeight;
	private char defaultChar;
	private String text;
	//////////
	// INIT
	//////////
	public GUICounterComponent()
	{	
	}

	@Override
	public void init()
	{
	}
	

	//////////
	// GETTERS
	//////////
	public int getCharWidth()
	{
		return this.charWidth;
	}
	
	public int getCharHeight()
	{
		return this.charHeight;
	}
	public ArrayList<String> getChildren()
	{
		return (this.childrenIDs == null) ? new ArrayList<String>() : this.childrenIDs;
	}
	
	public char getDefaultChar()
	{
		return this.defaultChar;
	}
	
	public int getNumSlots()
	{
		return this.numSlots;
	}
	public String getText()
	{
		return (this.text!=null) ? this.text : "";
	}
	//////////
	// SETTERS
	//////////
	public void setChildren(final ArrayList<String> children)
	{
		this.childrenIDs = (children == null) ? new ArrayList<String>() : children;
	}
	public void setCharWidth(final int charWidth)
	{
		this.charWidth = charWidth;
	}
	public void setCharHeight(final int charHeight)
	{
		this.charHeight = charHeight;
	}
	public void setDefaultChar(final char defaultChar)
	{
		this.defaultChar = defaultChar;
	}
	public void setText(final String text)
	{
		this.text = text;
	}
	public void setNumSlots(final int numSlots)
	{
		this.numSlots = numSlots;
	}
}
