package com.jgefroh.input;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.effects.IAction;
import com.jgefroh.infopacks.InputInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_BUY;
import com.jgefroh.messages.DefaultMessage.COMMAND_FIRE;
import com.jgefroh.messages.DefaultMessage.COMMAND_GENERATE_FORCE;
import com.jgefroh.messages.DefaultMessage.COMMAND_SHIELD_ACTIVE;
import com.jgefroh.messages.DefaultMessage.DATA_INPUT_CURSOR_POSITION;
import com.jgefroh.messages.DefaultMessage.DATA_WINDOW_RESOLUTION;


/**
 * This class receives input information from the input systems and acts
 * according to the binds associated with the inputs.
 * @author Joseph Gefroh
 */
public class InputSystem extends AbstractSystem implements IInputSystem {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	private List<IInputDevice> devices;
	
	private Map<Integer, IBindMap> mappings;
	
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
	
	public enum InputAction implements IAction {
		REQUEST_FIRE_START {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_FIRE.IS_FIRE_REQUESTED, "true");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_FIRE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_FIRE, parameters);	
			}
		},
		REQUEST_FIRE_STOP {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_FIRE.IS_FIRE_REQUESTED, "false");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_FIRE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_FIRE, parameters);	
			}
		},
		MOVE_UP {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_GENERATE_FORCE.VECTOR_ANG, "270");
				parameters.put(COMMAND_GENERATE_FORCE.VECTOR_MAG, "5");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_GENERATE_FORCE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_GENERATE_FORCE, parameters);	
			}
		},
		MOVE_LEFT {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_GENERATE_FORCE.VECTOR_ANG, "180");
				parameters.put(COMMAND_GENERATE_FORCE.VECTOR_MAG, "5");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_GENERATE_FORCE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_GENERATE_FORCE, parameters);	
			}
		},
		MOVE_RIGHT {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_GENERATE_FORCE.VECTOR_ANG, "0");
				parameters.put(COMMAND_GENERATE_FORCE.VECTOR_MAG, "5");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_GENERATE_FORCE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_GENERATE_FORCE, parameters);	
			}
		},
		MOVE_DOWN {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_GENERATE_FORCE.VECTOR_ANG, "90");
				parameters.put(COMMAND_GENERATE_FORCE.VECTOR_MAG, "5");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_GENERATE_FORCE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_GENERATE_FORCE, parameters);	
			}
		},
		REQUEST_SHIELD_START {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_SHIELD_ACTIVE.IS_ACTIVE, "true");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_SHIELD_ACTIVE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_SHIELD_ACTIVE, parameters);	
			}
		},
		REQUEST_SHIELD_STOP {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_SHIELD_ACTIVE.IS_ACTIVE, "false");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_SHIELD_ACTIVE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_SHIELD_ACTIVE, parameters);	
			}
		},
		REQUEST_SPECIAL {
			private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
				parameters.put(COMMAND_SHIELD_ACTIVE.IS_ACTIVE, "false");
			}
			
			@Override
			public void execute(final Core core) {
				parameters.put(COMMAND_SHIELD_ACTIVE.ENTITY_ID, getTarget());
				core.send(DefaultMessage.COMMAND_SHIELD_ACTIVE, parameters);	
			}
		},
		
		BUY_1 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "1");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
		},
        
        BUY_2 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "2");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },
        
        BUY_3 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "3");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },
        
        BUY_4 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "4");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },
        
        BUY_5 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "5");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },
        
        BUY_6 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "6");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },
        
        BUY_7 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "7");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },

        BUY_8 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "8");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },
        
        BUY_9 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "9");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },
        
        BUY_0 {
            private Map<IPayload, String> parameters = new HashMap<IPayload, String>(); {
                parameters.put(COMMAND_BUY.PRODUCT_ID, "0");
            }
            
            @Override
            public void execute(final Core core) {
                core.send(DefaultMessage.COMMAND_BUY, parameters);    
            }
        },
        
		
		;

		private String entityID;
		public abstract void execute(final Core core);
		
		public void setTarget(final String entityID) {
			this.entityID = entityID;
		}
		
		public String getTarget() {
			return this.entityID;
		}
	}

	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public InputSystem(final Core core) {
		this.core = core;
		init();
	}
	
	/**
	 * Initialize binds.
	 */
	private void initBinds() {
		// TODO: Move somewhere else.
		BindMap kbs = new BindMap();
		BindMap mbs = new BindMap();
		kbs.bind(Keyboard.KEY_W, InputAction.MOVE_UP, InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_A, InputAction.MOVE_LEFT, InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_S, InputAction.MOVE_DOWN, InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_D, InputAction.MOVE_RIGHT, InputSystem.HOLD);

		kbs.bind(Keyboard.KEY_LCONTROL, InputAction.REQUEST_SHIELD_START, InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_LCONTROL, InputAction.REQUEST_SHIELD_STOP, InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_SPACE, InputAction.REQUEST_FIRE_START, InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_SPACE, InputAction.REQUEST_FIRE_STOP, InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_Q, InputAction.REQUEST_SPECIAL, InputSystem.RELEASE);
		
		kbs.bind(Keyboard.KEY_0, InputAction.BUY_0, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_1, InputAction.BUY_1, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_2, InputAction.BUY_2, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_3, InputAction.BUY_3, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_4, InputAction.BUY_4, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_5, InputAction.BUY_5, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_6, InputAction.BUY_6, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_7, InputAction.BUY_7, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_8, InputAction.BUY_8, InputSystem.RELEASE);
        kbs.bind(Keyboard.KEY_9, InputAction.BUY_9, InputSystem.RELEASE);
		
		setBindSystem(IInputSystem.KEYBOARD, kbs);
		setBindSystem(IInputSystem.MOUSE, mbs);	
	}

	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		this.devices = new ArrayList<IInputDevice>();
		this.mappings = new HashMap<Integer, IBindMap>();
		InputDevice_Keyboard keyboard 	= new InputDevice_Keyboard(this);
		InputDevice_Mouse mouse 		= new InputDevice_Mouse(this);
		
		devices.add(keyboard);
		devices.add(mouse);
		initBinds();
		
		core.setInterested(this, DefaultMessage.REQUEST_CURSOR_POSITION);
		core.setInterested(this, DefaultMessage.DATA_WINDOW_RESOLUTION);
		core.setInterested(this, DefaultMessage.DATA_NATIVE_RESOLUTION);
		this.windowWidth = 1366;
		this.windowHeight = 768;
		this.nativeWidth = 1366;
		this.nativeHeight = 768;
		this.widthRatio = 1;
		this.heightRatio = 1;
		
		core.send(DefaultMessage.REQUEST_NATIVE_RESOLUTION, null);
		core.send(DefaultMessage.REQUEST_WINDOW_RESOLUTION, null);
	}
	
	@Override
	public void work(final long now) {
		pollInputDevices();
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case REQUEST_CURSOR_POSITION:
					Map<IPayload, String> parameters = new HashMap<IPayload, String>();
					parameters.put(DATA_INPUT_CURSOR_POSITION.MOUSE_X, getMouseX() + "");
					parameters.put(DATA_INPUT_CURSOR_POSITION.MOUSE_Y, getMouseY() + "");
					core.send(DefaultMessage.DATA_INPUT_CURSOR_POSITION, parameters);
					break;
				case DATA_WINDOW_RESOLUTION:
					updateWindowRatio(message);
					break;
				default:
					break;
			}
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
	public void notify(final int device, final int keyCode, final int typeEvent) {
		IBindMap map = mappings.get(device);
		IAction action = null;

		switch (typeEvent) {
			case PRESS:
				action = map.getCommandOnPress(keyCode);
				break;
			case HOLD:
				action = map.getCommandOnHold(keyCode);
				break;
			case RELEASE:
				action = map.getCommandOnRelease(keyCode);
				break;
		}

		processAction(action);
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////	
	/**
	 * Sends a command with the ID of interested entities.
	 * @param command	the command to send
	 */
	private void processAction(final IAction action) {
		if (action == null) {
			return;
		}
		
		Iterator<IEntity> packs = core.getEntitiesWithPack(InputInfoPack.class);
		InputInfoPack pack = core.getInfoPackOfType(InputInfoPack.class);
		
		while (packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if(pack.isInterested(action.toString())) {
				action.setTarget(pack.getEntity().getID());
				action.execute(core);
			}
		}
		action.setTarget("");
		action.execute(core);

	}
	
	
	//////////
	// SETTERS
	//////////
	/**
	 * Set the bind system for a specific input device
	 * @param device		the code of the device
	 * @param bindSystem	the bind system
	 */
	public void setBindSystem(final int device, final IBindMap bindSystem) {
		mappings.put(device, bindSystem);
	}

	private void pollInputDevices() {
		Iterator<IInputDevice> inputDevices = this.devices.iterator();

		while (inputDevices.hasNext()) {
			IInputDevice device = inputDevices.next();
			device.processNewEvents();
			device.processHeldEvents();
		}
	}
	
	/**
	 * Returns the adjusted mouse x-coordinate that considers window width.
	 * @return	
	 */
	private int getMouseX() {
		return (int) (Mouse.getX() * this.widthRatio);
	}
	
	/**
	 * Returns the adjusted mouse y coordinate that considers window height.
	 * @return
	 */
	private int getMouseY() {
		return (int) ((this.windowHeight - Mouse.getY()) * this.heightRatio);
	}
	
	private void updateWindowRatio(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
	
		try	{
			int height = Integer.parseInt(data.get(DATA_WINDOW_RESOLUTION.WINDOW_HEIGHT));
			int width = Integer.parseInt(data.get(DATA_WINDOW_RESOLUTION.WINDOW_WIDTH));
			this.windowWidth = width;
			this.windowHeight = height;
			this.widthRatio = (float)this.nativeWidth/this.windowWidth;
			this.heightRatio = (float)this.nativeHeight/this.windowHeight;
		}
		catch(NumberFormatException e) {
			LOGGER.log(Level.SEVERE, "Error updating dimension.");
		}
	}
}
