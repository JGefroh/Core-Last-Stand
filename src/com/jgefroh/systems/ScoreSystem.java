package com.jgefroh.systems;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.ScoreInfoPack;
import com.jgefroh.messages.Message;


/**
 * @author Joseph Gefroh
 */
public class ScoreSystem extends AbstractSystem
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
	
	/**The amount of points you have.*/
	private int score = 1000;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public ScoreSystem(final Core core)
	{
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}

	/////////
	// ISYSTEM INTERFACE
	/////////
	@Override
	public void init()
	{
		LOGGER.log(Level.FINE, "Setting system values to default.");		
		core.setInterested(this, Message.REQUEST_SCORE);
		core.setInterested(this, Message.DESTROYING_ENTITY);
		core.setInterested(this, Message.RESET_GAME);
		core.setInterested(this, Message.ADJUST_SCORE);
	}

	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
		
		Message msgID = Message.valueOf(id);

		switch(msgID)
		{
			case REQUEST_SCORE:
				core.send(Message.SCORE_UPDATE, score + "");
				break;
			case DESTROYING_ENTITY:
				changeScore(message);
				break;
			case RESET_GAME:
				this.score = 1000;
				break;
			case ADJUST_SCORE:
				adjustScore(message);
				break;
		}
	}
	
	private void adjustScore(final String[] message)
	{
		if(message.length>=1)
		{
			try
			{
				int adjust = Integer.parseInt(message[0]);
				this.score+=adjust;
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Bad score.");
			}
		}
	}
	private void changeScore(final String[] message)
	{
		if(message.length>=2)
		{
			String entityID = message[0];
			
			if(message[1].equals("NO_HEALTH")==false)
			{
				return;
			}
			
			ScoreInfoPack pack = core.getInfoPackFrom(entityID, ScoreInfoPack.class);
			
			if(pack!=null)
			{
				this.score+=pack.getScore();
			}
		}
	}
	/////////
	// SYSTEM METHODS
	/////////
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
}
