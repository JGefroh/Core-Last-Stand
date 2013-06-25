package com.jgefroh.input;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory; 
import com.jgefroh.infopacks.InputInfoPack;





/**
 * This class receives input information from the input systems and acts
 * according to the binds associated with the inputs.
 * @author Joseph Gefroh
 */
public class InputSystem implements ISystem, IInputSystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
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
	
	/**The original width of the window.*/
	private int windowHeightOrig;
	
	/**The original height of the window.*/
	private int windowWidthOrig;
	
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
		kbs.bind(Keyboard.KEY_SPACE, "+FIRE1", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_SPACE, "-FIRE1", InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_LCONTROL, "+SHIELD", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_LCONTROL, "-SHIELD", InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_W, "MOVE_UP", InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_A, "MOVE_LEFT", InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_S, "MOVE_DOWN", InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_D, "MOVE_RIGHT", InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_1, "SWITCH_WEAPON_1", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_2, "SWITCH_WEAPON_2", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_3, "SWITCH_WEAPON_3", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_4, "SWITCH_WEAPON_4", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_5, "SWITCH_WEAPON_5", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_6, "SWITCH_WEAPON_6", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_7, "SWITCH_WEAPON_7", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_8, "SWITCH_WEAPON_8", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_9, "SWITCH_WEAPON_9", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_0, "SWITCH_WEAPON_0", InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_NUMPAD0, "TOGGLE_WIREFRAME", InputSystem.RELEASE);

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
		
		core.setInterested(this,"REQUEST_CURSOR_POSITION");
		core.setInterested(this,"WINDOW_WIDTH");
		core.setInterested(this,"WINDOW_HEIGHT");
		core.setInterested(this,"MOVE_UP");
		core.setInterested(this,"MOVE_DOWN");
		core.setInterested(this,"MOVE_LEFT");
		core.setInterested(this,"MOVE_RIGHT");
		core.setInterested(this,"+FIRE1");
		core.setInterested(this,"-FIRE1");
		core.setInterested(this,"-SHIELD");
		core.setInterested(this,"+SHIELD");
		
		this.windowWidth = 1366;
		this.windowHeight = 768;
		this.windowWidthOrig = 1366;
		this.windowHeightOrig = 768;
		this.widthRatio = 1;
		this.heightRatio = 1;
		
		core.send("REQUEST_WINDOW_WIDTH", "");
		core.send("REQUEST_WINDOW_HEIGHT", "");
	}
	
	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}
	
	@Override
	public void work(final long now)
	{
		if(isRunning)
		{
			pollInputDevices();
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	
	@Override
	public long getWait()
	{
		return this.waitTime;
	}

	@Override
	public long	getLast()
	{
		return this.last;
	}
	
	@Override
	public void setWait(final long waitTime)
	{
		this.waitTime = waitTime;
	}
	
	@Override
	public void setLast(final long last)
	{
		this.last = last;
	}
	
	@Override
	public void recv(final String id, final String... message)
	{		
		LOGGER.log(Level.FINEST, "Received message: " + id);

		if(id.equals("REQUEST_CURSOR_POSITION"))
		{
			core.send("INPUT_CURSOR_POSITION", getMouseX() +"", getMouseY()+"");
		}
		else if(id.equals("WINDOW_WIDTH"))
		{
			updateWidthRatio(message);
		}
		else if(id.equals("WINDOW_HEIGHT"))
		{
			updateHeightRatio(message);
		}
		else if(id.equals("MOVE_UP"))
		{
			core.send("GENERATE_FORCE", message[0], 5 +"", 270+"");
		}
		else if(id.equals("MOVE_DOWN"))
		{
			core.send("GENERATE_FORCE", message[0], 5 +"", 90+"");
		}
		else if(id.equals("MOVE_LEFT"))
		{
			core.send("GENERATE_FORCE", message[0], 5 +"", 180+"");
		}
		else if(id.equals("MOVE_RIGHT"))
		{
			core.send("GENERATE_FORCE", message[0], 5 +"", 0+"");
		}
		else if(id.equals("+FIRE1"))
		{
			core.send("REQUEST_FIRE", message[0], true + "");
		}
		else if(id.equals("-FIRE1"))
		{
			core.send("REQUEST_FIRE", message[0], false + "");
		}
		else if(id.equals("+SHIELD"))
		{
			core.send("REQUEST_SHIELD", message[0], true + "");
		}
		else if(id.equals("-SHIELD"))
		{
			core.send("REQUEST_SHIELD", message[0], false + "");
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
					core.send(command, each.getOwner().getID());
				}
			}
		}
		core.send(command, "");
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
				this.widthRatio = (float)this.windowWidthOrig/this.windowWidth;
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
				this.heightRatio = (float)this.windowHeightOrig/this.windowHeight;
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
}
