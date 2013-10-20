package com.jgefroh.input;


import java.util.HashMap;
import java.util.Set;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;



/**
 * Receives and processes all mouse events using LWJGL's mouse class.
 * Currently does not process scroll wheel events.
 * @author Joseph Gefroh
 * @since 23FEB13
 */
public class InputDevice_Mouse implements IInputDevice
{
	private HashMap<Integer, Boolean> heldBtns;
	private IInputSystem irs;
	
	/**
	 * Prevent instantiation without associated bind system.
	 */
	private InputDevice_Mouse() {
	}
	
	/**
	 * Creates a LWJGL Mouse object.
	 * Will call System.exit(-1) if a LWJGLException is thrown.
	 */
	public InputDevice_Mouse(final IInputSystem irs) {
		try {
			Mouse.create(); 
			heldBtns = new HashMap<Integer, Boolean>();
			this.irs = irs;
		}
		catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void processAllEvents() {
		processNewEvents();
		processHeldEvents();
	}
	
	/**
	 * Process all of the events in the mouse event queue.
	 */
	@Override
	public void processNewEvents() {
		while (Mouse.next()) {
			if (Mouse.getEventButton() == -1) {
				//If mouse movement 
				irs.notify(InputSystem.MOUSE,-1, -1);
			} 
			else {//If mouse click
				int btnCode = Mouse.getEventButton();
				if (Mouse.getEventButtonState() == true) {
					//If button was pressed
					heldBtns.put(btnCode, true);
					irs.notify(InputSystem.MOUSE, btnCode, InputSystem.PRESS);
				} 
				else {
					//f button was released
					heldBtns.remove(btnCode);
					irs.notify(InputSystem.MOUSE, btnCode, InputSystem.RELEASE);
				}
			}
		}
	}
	
	/**
	 * Return the last polled X-coordinate location of the mouse.
	 * @return	the int X coordinate of the mouse
	 */
	public int getX() {
		return Mouse.getX();
	}
		
	/**
	 * Return the last polled Y-coordinate location of the mouse.
	 * @return	the int Y coordinate of the mouse
	 */
	public int getY() {
		return Mouse.getY();
	}

	@Override
	public void processHeldEvents() {
		Set<Integer> keys = heldBtns.keySet();
		for (Integer each : keys) {
			irs.notify(InputSystem.MOUSE, each, InputSystem.HOLD);
		}
	}

	/**
	 * Set a new response system for this mouse.
	 * @param irs the InputResponseSystem to apply
	 */
	@Override
	public void setResponseSystem(final IInputSystem irs) {
		this.irs = irs;
	}
	
	/**
	 * Get the response system for this mouse.
	 * @return	the InputResponseSystem the mouse is using
	 */
	@Override
	public IInputSystem getResponseSystem() {
		return this.irs;
	}

}
