package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to identify GUI elements.
 * 
 * 
 * Date: 24JUL13
 * @author Joseph Gefroh
 */
public class GUICharSlotComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;

	private int slotNum;
	//////////
	// INIT
	//////////
	public GUICharSlotComponent()
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
	
	public int getSlotNum()
	{
		return this.slotNum;
	}

	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	public void setSlotNum(final int slotNum)
	{
		this.slotNum = slotNum;
	}
}
