package com.jgefroh.components;

import java.util.ArrayList;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;


/**
 * Contains data necessary for an entity to receive input.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class InputComponent extends AbstractComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**Contains all of the commands this entity responds to.*/
	private ArrayList<String> commandList; //TODO: Do without array list?

	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 */
	public InputComponent()
	{
		init();
	}
	
	@Override
	public void init()
	{
		commandList = new ArrayList<String>();
	}


	//////////
	// GETTERS
	//////////	
	/**
	 * Checks to see if the entity is interested in the input command.
	 * @param command	the String command of the input
	 * @return	true if it is interested; false otherwise.
	 */
	public boolean checkInterested(final String command)
	{
		if(commandList.contains(command))
		{
			return true;
		}
		return false;
	}
	
	
	//////////
	// SETTERS
	//////////	
	/**
	 * Marks this Entity as interested in the given input.
	 * @param command the input command
	 */
	public void setInterested(final String command)
	{
		if(checkInterested(command)==false)
		{
			commandList.add(command);
		}
	}
	
	/**
	 * Marks the entity to ignore the command if it is not already.
	 * @param command the input command
	 */
	public void setUninterested(final String command)
	{
		commandList.remove(command);
	}
}
