package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AnimationInfoPack;
import com.jgefroh.tests.Benchmark;


/**
 * This system handles changing the sprite image at the required intervals.
 * 
 * 
 * 
 * DATE: 05JUN13
 * @author Joseph Gefroh
 * @version 0.3.0
 */
public class AnimationSystem implements ISystem
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
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);
	
	private Benchmark bench = new Benchmark(this.getClass().getName(), true);

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this System.
	 * @param core	a reference to the Core controlling this system
	 */
	public AnimationSystem(final Core core)
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
		LOGGER.log(Level.FINE, "Setting system values to default.");
		isRunning = true;
		core.setInterested(this, "ADVANCE_FRAME");
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
			long startTime = System.nanoTime();
			int numEntities = animate(now);
			bench.benchmark(System.nanoTime()-startTime, numEntities);
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
		LOGGER.log(Level.FINE, "Wait interval set to: " + waitTime + " ms");
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

		if(id.equals("ADVANCE_FRAME"))
		{
			nextFrame(core.getInfoPackFrom(message[0], AnimationInfoPack.class));
		}
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Updates all animations and advances their frames.
	 * @param now	the current time
	 */
	private int animate(final long now)
	{
		Iterator<AnimationInfoPack> infoPacks = 
				core.getInfoPacksOfType(AnimationInfoPack.class);
		
		int numEntities = 0;
		while(infoPacks.hasNext())
		{
			AnimationInfoPack pack = infoPacks.next();
			if(pack.isDirty()==false)
			{
				if(now-pack.getLastUpdateTime()>=pack.getInterval())
				{	
					nextFrame(pack);
					pack.setLastUpdateTime(now);
				}
			}
			numEntities ++;
		}
		return numEntities;
	}
	
	/**
	 * Advances the frame of the animation.
	 * @param pack	the AnimationInfoPack of the entity
	 */
	private void nextFrame(final AnimationInfoPack pack)
	{
		if(pack!=null)
		{
			int currentFrame = pack.getCurrentFrame();
			int numberOfFrames = pack.getNumberOfFrames();
			if(currentFrame<=numberOfFrames-1)
			{
				//If the end of the animation has not yet been reached...
				pack.setAnimationSprite(pack.getAnimationSprite());
				pack.setCurrentFrame(currentFrame+1);
			}
			else
			{
				//Restart the animation from the first frame.
				pack.setCurrentFrame(0);
			}
		}
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
