package com.jgefroh.input;


/**
 * This interface lists some core functions of the bind system.
 * @author Joseph Gefroh
 * @since 23FEB13
 */
public interface IBindMap
{	
	
	public static final int PRESS = 0;
	public static final int HOLD = 1;
	public static final int RELEASE = 2;
	public static final int ALL = 3;
	
	/**
	 * Associates a command name with a specific key.
	 * @param keyCode	the int keycode of the key to bind with the action
	 * @param action	the command to send
	 * @param type		the type of input this action will be executed by
	 */
	public void bind(final int keyCode, final String action, final int type);
	
	/**
	 * Unbind a specific key and type of input.
	 * @param keyCode	the int keycode of the key to unbind
	 * @param type		the type of event
	 */
	public void unbind(final int keyCode, final int type);
	
	/**
	 * Get the action associated with holding a key.
	 * @param keyCode	the int code of the key
	 * @return	the IAction bound to the key
	 */
	public String getCommandOnHold(final int keyCode);
	
	/**
	 * Get the action associated with pressing a key.
	 * @param keyCode	the int code of the key
	 * @return	the IAction bound to the key
	 */
	public String getCommandOnPress(final int keyCode);
	
	/**
	 * Get the action associated with releasing a key.
	 * @param keyCode	the int code of the key
	 * @return	the IAction bound to the key
	 */
	public String getCommandOnRelease(final int keyCode);
}
