package com.jgefroh.input;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.InputInfoPack;
import com.jgefroh.messages.Message;





/**
 * This class receives input information from the input systems and acts
 * according to the binds associated with the inputs.
 * @author Joseph Gefroh
 */
public class InputSystem extends AbstractSystem implements IInputSystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	private ArrayList<IInputDevice> devices;
	
	private HashMap<Integer, IBindMap> mappings;
	
	/**The ratio between the screen's dimensions and the world coordinates.*/
	private float screenWorldRatio;
	
	/**The current height of the window.*/
	private int windowHeight;
	
	/**The current width of the window.*/
	private int windowWidth;
	
	/**The original width of the playing area.*/
	private int nativeHeight;
	
	/**The original height of the playing area.*/
	private int nativeWidth;
	
	/***/
	private float widthRatio;
	private float heightRatio;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public InputSystem(final Core core)
	{
		this.core = core;
		init();
	}
	
	
	/**
	 * Initialize binds.
	 */
	private void initBinds()
	{
		//TODO: Move somewhere else.
		BindMap kbs = new BindMap();
		BindMap mbs = new BindMap();
		kbs.bind(Keyboard.KEY_SPACE, Message.FIRE1_PRESSED.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_SPACE, Message.FIRE1_RELEASED.name(), InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_Q, Message.SPECIAL_RELEASED.name(), InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_LCONTROL, Message.SHIELD_PRESSED.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_LCONTROL, Message.SHIELD_RELEASED.name(), InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_W, Message.MOVE_UP.name(), InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_A, Message.MOVE_LEFT.name(), InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_S, Message.MOVE_DOWN.name(), InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_D, Message.MOVE_RIGHT.name(), InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_NUMPAD0, Message.TOGGLE_WIREFRAME.name(), InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_0, Message.BUY_0.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_1, Message.BUY_1.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_2, Message.BUY_2.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_3, Message.BUY_3.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_4, Message.BUY_4.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_5, Message.BUY_5.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_6, Message.BUY_6.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_7, Message.BUY_7.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_8, Message.BUY_8.name(), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_9, Message.BUY_9.name(), InputSystem.PRESS);
		mbs.bind(0, Message.MOUSE0_PRESSED.name(), InputSystem.PRESS);
		mbs.bind(0, Message.MOUSE0_RELEASED.name(), InputSystem.RELEASE);
		
		setBindSystem(IInputSystem.KEYBOARD, kbs);
		setBindSystem(IInputSystem.MOUSE, mbs);	
	}
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		this.devices = new ArrayList<IInputDevice>();
		this.mappings = new HashMap<Integer, IBindMap>();
		InputDevice_Keyboard keyboard 	= new InputDevice_Keyboard(this);
		InputDevice_Mouse mouse 		= new InputDevice_Mouse(this);
		
		devices.add(keyboard);
		devices.add(mouse);
		initBinds();
		
		core.setInterested(this, Message.REQUEST_CURSOR_POSITION);
		core.setInterested(this, Message.WINDOW_WIDTH);
		core.setInterested(this, Message.WINDOW_HEIGHT);
		core.setInterested(this, Message.MOVE_UP);
		core.setInterested(this, Message.MOVE_DOWN);
		core.setInterested(this, Message.MOVE_LEFT);
		core.setInterested(this, Message.MOVE_RIGHT);
		core.setInterested(this, Message.FIRE1_PRESSED);
		core.setInterested(this, Message.FIRE1_RELEASED);
		core.setInterested(this, Message.FIRE2_PRESSED);
		core.setInterested(this, Message.FIRE2_RELEASED);
		core.setInterested(this, Message.SHIELD_PRESSED);
		core.setInterested(this, Message.SHIELD_RELEASED);
		core.setInterested(this, Message.NATIVE_WIDTH);
		core.setInterested(this, Message.NATIVE_HEIGHT);
		core.setInterested(this, Message.SPECIAL_RELEASED);
		this.windowWidth = 1366;
		this.windowHeight = 768;
		this.nativeWidth = 1366;
		this.nativeHeight = 768;
		this.widthRatio = 1;
		this.heightRatio = 1;
		
		core.send(Message.REQUEST_NATIVE_WIDTH, "");
		core.send(Message.REQUEST_NATIVE_HEIGHT, "");
		core.send(Message.REQUEST_WINDOW_WIDTH, "");
		core.send(Message.REQUEST_WINDOW_HEIGHT, "");
	}
	
	@Override
	public void work(final long now)
	{
		if(isRunning())
		{
			pollInputDevices();
		}
	}

	@Override
	public void recv(final String id, final String... message)
	{		
		LOGGER.log(Level.FINEST, "Received message: " + id);

		Message msgID = Message.valueOf(id);
		
		switch(msgID)
		{
			case REQUEST_CURSOR_POSITION:
				core.send(Message.INPUT_CURSOR_POSITION, getMouseX() +"", getMouseY()+"");
				break;
			case WINDOW_WIDTH:
				updateWidthRatio(message);
				break;
			case WINDOW_HEIGHT:
				updateHeightRatio(message);
				break;
			case MOVE_UP:
				core.send(Message.GENERATE_FORCE, message[0], 5 +"", 270+"");	
				break;
			case MOVE_LEFT:
				core.send(Message.GENERATE_FORCE, message[0], 5 +"", 180+"");	
				break;
			case MOVE_RIGHT:
				core.send(Message.GENERATE_FORCE, message[0], 5 +"", 0+"");	
				break;
			case MOVE_DOWN:
				core.send(Message.GENERATE_FORCE, message[0], 5 +"", 90+"");	
				break;
			case FIRE1_PRESSED:
				core.send(Message.REQUEST_FIRE, message[0], true + "");
				break;
			case FIRE1_RELEASED:
				core.send(Message.REQUEST_FIRE, message[0], false + "");
				break;
			case SPECIAL_RELEASED:
				core.send(Message.USE_ABILITY, message[0], true + "");
				break;
			case SHIELD_PRESSED:
				core.send(Message.REQUEST_SHIELD_ACTIVE, message[0], true + "");
				break;
			case SHIELD_RELEASED:
				core.send(Message.REQUEST_SHIELD_ACTIVE, message[0], false + "");
				break;			
		}
	}
	//////////
	// IIINPUTSYSTEM INTERFACE
	//////////
	/**
	 * Notifies the handler than an input event has been generated.
	 * @param device		the ID of the device that has sent the input
	 * @param keyCode		the code of the key that has generated the input
	 * @param typeEvent		the type of input (what has happened to the key)
	 */
	@Override
	public void notify(final int device, final int keyCode, final int typeEvent)
	{
		IBindMap map = mappings.get(device);
		String command = null;
		
		switch(typeEvent)
		{
			case PRESS:
				command = map.getCommandOnPress(keyCode);
				break;
			case HOLD:
				command = map.getCommandOnHold(keyCode);
				break;
			case RELEASE:
				command = map.getCommandOnRelease(keyCode);
				break;
		}
		
		processAction(command);
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////	
	/**
	 * Sends a command with the ID of interested entities.
	 * @param command	the command to send
	 */
	private void processAction(final String command)
	{
		if(command!=null)
		{
			Iterator<InputInfoPack> packs 
				= core.getInfoPacksOfType(InputInfoPack.class);
			while(packs.hasNext())
			{
				InputInfoPack each = packs.next();
				if(each.isInterested(command))
				{
					core.send(Message.valueOf(command), each.getOwner().getID());
				}
			}
			core.send(Message.valueOf(command), "");
		}
		
	}
	
	
	//////////
	// SETTERS
	//////////
	/**
	 * Set the bind system for a specific input device
	 * @param device		the code of the device
	 * @param bindSystem	the bind system
	 */
	public void setBindSystem(final int device, final IBindMap bindSystem)
	{
		mappings.put(device, bindSystem);
	}
	
	private void pollInputDevices()
	{
		Iterator<IInputDevice> inputDevices
			= this.devices.iterator();
		
		while(inputDevices.hasNext())
		{
			IInputDevice device = inputDevices.next();
			device.processNewEvents();
			device.processHeldEvents();
		}
	}
	
	/**
	 * Returns the adjusted mouse x-coordinate that considers window width.
	 * @return	
	 */
	private int getMouseX()
	{
		return (int)(Mouse.getX()*this.widthRatio);
	}
	
	/**
	 * Returns the adjusted mouse y coordinate that considers window height.
	 * @return
	 */
	private int getMouseY()
	{
		return (int)((this.windowHeight-Mouse.getY())*this.heightRatio);
	}
	
	private void updateWidthRatio(final String[] message)
	{
		if(message.length>0)
		{
			try
			{
				this.windowWidth = Integer.parseInt(message[0]);
				this.widthRatio = (float)this.nativeWidth/this.windowWidth;
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.SEVERE, "Error updating dimension.");
			}
		}
		else
		{
			LOGGER.log(Level.SEVERE, "Error updating dimension.");

		}
	}
	
	private void updateHeightRatio(final String[] message)
	{
		if(message.length>0)
		{
			try
			{
				this.windowHeight = Integer.parseInt(message[0]);
				this.heightRatio = (float)this.nativeHeight/this.windowHeight;
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.SEVERE, "Error updating dimension.");
				e.printStackTrace();
			}
		}
		else
		{
			LOGGER.log(Level.SEVERE, "Error updating dimension.");
		}
	}
	
	/**
	 * Sets the native width of the playing area.
	 * @param message	[0] contains the width of the playing area.
	 */
	private void setNativeWidth(final String[] message)
	{
		if(message.length>0)
		{
			try
			{
				this.nativeWidth = Integer.parseInt(message[0]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to set native width to: " 
							+ message[0]);
			}
		}
	}
	
	/**
	 * Sets the native width of the playing area.
	 * @param message	[0] contains the height of the playing area.
	 */
	private void setNativeHeight(final String[] message)
	{
		if(message.length>0)
		{
			try
			{
				this.nativeHeight = Integer.parseInt(message[0]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unable to set native width to: " 
							+ message[0]);
			}
		}
	}
}
