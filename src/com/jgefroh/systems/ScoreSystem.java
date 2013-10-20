package com.jgefroh.systems;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.ScoreInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_ADJUST_SCORE;
import com.jgefroh.messages.DefaultMessage.DATA_SCORE;
import com.jgefroh.messages.DefaultMessage.EVENT_DESTROYING_ENTITY;


/**
 * @author Joseph Gefroh
 */
public class ScoreSystem extends AbstractSystem {
	
	//////////////////////////////////////////////////
	// Fields
	//////////////////////////////////////////////////
	
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	/**The amount of points you have.*/
	private int score = 1000;
	
	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
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

	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init()
	{
		LOGGER.log(Level.FINE, "Setting system values to default.");		
		core.setInterested(this, DefaultMessage.REQUEST_SCORE);
		core.setInterested(this, DefaultMessage.EVENT_DESTROYING_ENTITY);
		core.setInterested(this, DefaultMessage.COMMAND_RESET_GAME);
		core.setInterested(this, DefaultMessage.COMMAND_ADJUST_SCORE);
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
				case REQUEST_SCORE:
					Map<IPayload, String> parameters = new HashMap<IPayload, String>();
					parameters.put(DATA_SCORE.SCORE_CUR, score + "");
					core.send(DefaultMessage.DATA_SCORE, parameters);
					break;
				case EVENT_DESTROYING_ENTITY:
					changeScore(message);
					break;
				case COMMAND_RESET_GAME:
					this.score = 1000;
					break;
				case COMMAND_ADJUST_SCORE:
					adjustScore(message);
					break;
				default:
					break;
			}
		}
	}

	
	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	private void adjustScore(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		try {
			int adjust = Integer.parseInt(data.get(COMMAND_ADJUST_SCORE.AMOUNT));
			this.score+=adjust;
		}
		catch(NumberFormatException e) {
			LOGGER.log(Level.WARNING, "Bad score.");
		}
	}
	
	private void changeScore(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		
		String entityID = data.get(EVENT_DESTROYING_ENTITY.ENTITY_ID);
		
		if("NO_HEALTH".equals(data.get(EVENT_DESTROYING_ENTITY.REASON))) {
			return;
		}
		
		ScoreInfoPack pack = core.getInfoPackFrom(entityID, ScoreInfoPack.class);
		
		if (pack != null) {
			this.score += pack.getScore();
		}
	}

	
	//////////////////////////////////////////////////
	// Debug
	//////////////////////////////////////////////////
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level)
	{
		this.LOGGER.setLevel(level);
	}
}
