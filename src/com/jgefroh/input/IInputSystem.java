package com.jgefroh.input;

public interface IInputSystem
{
	/**The code indicating that an action will be performed on a move event.*/
	public static final int MOVE = 4;
	/**The code indicating that an action will be performed on a press event.*/
	public static final int PRESS = 0;
	/**The code indicating that an action will be performed on a hold event.*/
	public static final int HOLD = 1;
	/**The code indicating that an action will be performed on a release event.*/
	public static final int RELEASE = 2;
	/**The code indicating that an action will be performed on any input event.*/
	public static final int ALL = 3;
	/**The code indicating the originating device is a keyboard.*/
	public static final int KEYBOARD = 100;
	/**The code indicating the originating device is a mouse.*/
	public static final int MOUSE	= 200;
	
	/**
	 * Notify the input system to respond to a given input.
	 * @param device	the int code of the device where the input originated
	 * @param keyCode	the int key id
	 * @param type		the int type of input
	 */
	public void notify(final int device, final int keyCode, final int type);
}
