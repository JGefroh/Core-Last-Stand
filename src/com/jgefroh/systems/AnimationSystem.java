package com.jgefroh.systems;


import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IMessage;
import com.jgefroh.core.IPayload;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AnimationInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_ADVANCE_FRAME;
import com.jgefroh.tests.Benchmark;


/**
 * This system handles changing the sprite image at the required intervals.
 * @author Joseph Gefroh
 */
public class AnimationSystem extends AbstractSystem {
	
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
	
	private Benchmark bench = new Benchmark(this.getClass().getName(), true);

	
	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public AnimationSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);

		init();
	}
	

	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		LOGGER.log(Level.FINE, "Setting system values to default.");
		core.setInterested(this, DefaultMessage.COMMAND_ADVANCE_FRAME);
	}
	
	@Override
	public void work(final long now) {				
		long startTime = System.nanoTime();
		int numEntities = animate(now);
		bench.benchmark(System.nanoTime()-startTime, numEntities);
	}
	
	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch(type) {
				case COMMAND_ADVANCE_FRAME:
					nextFrame(message);
					break;
				default:
					break;
			}
		}
	}
	

	//////////////////////////////////////////////////
	// Methods
	//////////////////////////////////////////////////
	
	/**
	 * Updates all animations and advances their frames.
	 * @param now	the current time
	 */
	private int animate(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(AnimationInfoPack.class);
		AnimationInfoPack pack = core.getInfoPackOfType(AnimationInfoPack.class);
		
		int numEntities = 0;
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			if (now-pack.getLastUpdateTime()>=pack.getInterval()) {	
				nextFrame(pack);
				pack.setLastUpdateTime(now);
			}
			numEntities++;
		}
		return numEntities;
	}
	
	/**
	 * Advances the frame of the animation.
	 * @param pack	the AnimationInfoPack of the entity
	 */
	private void nextFrame(final AnimationInfoPack pack) {
		if(pack!=null) {
			int currentFrame = pack.getCurrentFrame();
			int numberOfFrames = pack.getNumberOfFrames();
			if (currentFrame <= numberOfFrames-1) {
				//If the end of the animation has not yet been reached...
				pack.setAnimationSprite(pack.getAnimationSprite());
				pack.setCurrentFrame(currentFrame+1);
			}
			else {
				//Restart the animation from the first frame.
				pack.setCurrentFrame(0);
			}
		}
	}
	
	private void nextFrame(final Map<IPayload, String> data) {
		if (data == null || data.size() < 1) {
			return;
		}
		
		AnimationInfoPack pack 
			= core.getInfoPackFrom(data.get(COMMAND_ADVANCE_FRAME.ENTITY_ID), AnimationInfoPack.class);
		nextFrame(pack);
	}
	
	
	//////////////////////////////////////////////////
	// Debug
	//////////////////////////////////////////////////
	
	/**
	 * Sets the debug level of this {@code System}.
	 * @param level	the Level to set
	 */
	public void setDebugLevel(final Level level) {
		this.LOGGER.setLevel(level);
	}
}
