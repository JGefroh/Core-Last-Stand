package com.jgefroh.systems;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AbilityInfoPack;
import com.jgefroh.messages.Message;

/**
 * @author Joseph Gefroh
 */
public class AbilitySystem extends AbstractSystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public AbilitySystem(final Core core)
	{
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		core.setInterested(this, Message.USE_ABILITY);
	}

	@Override
	public void work(final long now)
	{
	}

	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);

		Message msgID = Message.valueOf(id);
		
		switch(msgID)
		{
			case USE_ABILITY:
				processAbility(message);
				break;
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	
	private void processAbility(final String[] message)
	{	
		if(message.length>=1)
		{
			AbilityInfoPack pack = core.getInfoPackFrom(message[0], AbilityInfoPack.class);
			if(pack!=null)
			{
				useAbility(pack);
			}
		}
	}
	
	private void useAbility(final AbilityInfoPack pack)
	{
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 0 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 1*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 2*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 3*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 4*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 5*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 6*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 7*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 8*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 9*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 10*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 11*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 12*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 13*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 14*22.5 + "");
		core.send(Message.CREATE, "BOMBLET", pack.getOwner().getID(), 15*22.5 + "");
	}
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
	
}
