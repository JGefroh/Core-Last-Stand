package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to identify GUI elements.
 * 
 * 
 * Date: 24JUL13
 * @author Joseph Gefroh
 */
public class GUICharSlotComponent extends AbstractComponent
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
	
	public int getSlotNum()
	{
		return this.slotNum;
	}

	//////////
	// SETTERS
	//////////
	
	public void setSlotNum(final int slotNum)
	{
		this.slotNum = slotNum;
	}
}
