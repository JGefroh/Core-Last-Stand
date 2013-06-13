package com.jgefroh.input;


import java.util.HashMap;
import java.util.Set;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import com.jgefroh.core.ISystem;



/**
 * Receives and processes keyboard events using LWJGL's keyboard class.
 * @author Joseph Gefroh
 * @since 23FEB13
 */
public class InputDevice_Keyboard implements IInputDevice
{
	
	/**Tracks the keys that have been HELD DOWN.*/
	private HashMap<Integer, Boolean> heldKeys;
	private IInputSystem irs;
	
	/**
	 * Prevent instantiation without an associated response system.
	 */
	private InputDevice_Keyboard()
	{
	}

	/**
	 * Creates a LWJGL Keyboard and disables repeat events. If a LWJGL
	 * exception occurs, it will call System.exit(-1).
	 */
	public InputDevice_Keyboard(final IInputSystem irs)
	{
		try
		{
			Keyboard.create();
			Keyboard.enableRepeatEvents(false);
			heldKeys = new HashMap<Integer, Boolean>();
			this.irs = irs;
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Calls methods to process new key events and old key events.
	 * @see #processNewEvents()
	 * @see #processHeldEvents()
	 */
	public void processAllEvents()
	{
		processNewEvents();
		processHeldEvents();
	}
	
	/**
	 * Processes all of the events in the Keyboard event queue.
	 */
	@Override
	public void processNewEvents()
	{
		while(Keyboard.next())
		{//While keyboard has key presses to report
			int keyCode = Keyboard.getEventKey();
			if(Keyboard.getEventKeyState()==true)
			{//if key was pressed
				heldKeys.put(keyCode, true);	//Place key in held category.
				irs.notify(InputSystem.KEYBOARD, keyCode, irs.PRESS);
			}
			else
			{//if key was released
				heldKeys.remove(keyCode);
				irs.notify(InputSystem.KEYBOARD, keyCode, InputSystem.RELEASE);
			}
		}
	}
	
	/**
	 * Process all of the keys that were held since last poll.
	 */
	@Override
	public void processHeldEvents()
	{
		Set<Integer> keys = heldKeys.keySet();
		for(Integer each:keys)
		{
			irs.notify(InputSystem.KEYBOARD, each, irs.HOLD);
		}
	}
	
	/**
	 * Set a new bind system for this keyboard.
	 * @param bindSystem	the IBindSystem to apply
	 */
	@Override
	public void setResponseSystem(final InputSystem irs)
	{
		this.irs = irs;
	}
	
	/**
	 * Get the bind system for this keyboard.
	 * @return	the BindSystem this keyboard is using
	 */
	@Override
	public IInputSystem getResponseSystem()
	{
		return this.irs;
	}
	
	public int getCode(final String name)
	{
		return Keyboard.getKeyIndex(name);
	}
	
	public String getName(final int keyCode)
	{
		return Keyboard.getKeyName(keyCode);
	}

}
