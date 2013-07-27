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
public class GUITextComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	private ArrayList<String> childrenIDs;
	
	private int numCharsPerLine;
	private int numLines;
	private int charWidth;
	private int charHeight;
	private char defaultChar;
	private String text;
	//////////
	// INIT
	//////////
	public GUITextComponent()
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
	
	public int getNumLines()
	{
		return this.numLines;
	}
	
	public int getNumCharsPerLine()
	{
		return this.numCharsPerLine;
	}
	
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
	
	public String getText()
	{
		return this.text;
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	public void setChildren(final ArrayList<String> children)
	{
		this.childrenIDs = (children == null) ? new ArrayList<String>() : children;
	}
	
	public void setNumLines(final int numLines)
	{
		this.numLines = numLines;
	}
	
	public void setNumCharsPerLine(final int numCharsPerLine)
	{
		this.numCharsPerLine = numCharsPerLine;
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
}
