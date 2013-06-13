package com.jgefroh.input;



/**
 * Interface for input devices.
 * @author Joseph Gefroh
 * @since 23FEB13
 */
public interface IInputDevice
{

	/**
	 * Processes all of the events in the input event queue.
	 */
	public void processNewEvents();
	
	/**
	 * Process all of the continuing events that were held since last poll.
	 */
	public void processHeldEvents();
	
	/**
	 * Set a new bind system for this keyboard.
	 * @param bindSystem	the IBindSystem to apply
	 */
	public void setResponseSystem(final InputSystem irs);
	
	/**
	 * Get the bind system for this keyboard.
	 * @return	the BindSystem this keyboard is using
	 */
	public IInputSystem getResponseSystem();

}
