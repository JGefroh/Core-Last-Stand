package com.jgefroh.systems;

import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AIInfoPack;


/**
 * This system controls the behavior of the AI for the enemies.
 * Date: 16JUNE13
 * @author Joseph Gefroh
 */
public class AISystem implements ISystem
{	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.INFO;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), Level.ALL);

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this System.
	 * @param core	a reference to the Core controlling this system
	 */
	public AISystem(final Core core)
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
		core.setInterested(this, "IN_RANGE_OF_TARGET");
		core.setInterested(this, "IS_WITHIN_BOUNDS");
		this.isRunning = true;

	}
	
	@Override
	public void start()
	{
		isRunning = true;
		LOGGER.log(Level.INFO, "System started.");
	}

	@Override
	public void work(final long now)
	{
		if(isRunning)
		{
			attack();
		}
	}
	
	@Override
	public void stop()
	{
		isRunning = false;
		LOGGER.log(Level.INFO, "System stopped.");
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
		
		if(id.equals("IN_RANGE_OF_TARGET"))
		{
			setInRange(message);
		}
		else if(id.equals("IS_WITHIN_BOUNDS"))
		{
			setActive(message);
		}
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Goes through all AI entities and attempts an attack.
	 */
	private void attack()
	{
		Iterator<AIInfoPack> packs = core.getInfoPacksOfType(AIInfoPack.class);
		
		while(packs.hasNext())
		{
			AIInfoPack each = packs.next();
			
			if(each.isInRangeOfTarget()&&each.isActive())
			{				
				rollForAttack(each);
			}
		}
	}
	
	/**
	 * Checks to see if the unit gets to attack this turn.
	 * @param each	the AIInfoPack of the entity that is rolling
	 */
	private void rollForAttack(final AIInfoPack each)
	{		
		core.send("REQUEST_FIRE", each.getOwner().getID(), true + "");
	}
	
	/**
	 * Sets the flag that indicates the entity is in range of its target.
	 * @param message	[0] contains the entity's ID
	 * 					[1] contains the boolean flag indicating if in range
	 */
	private void setInRange(final String[] message)
	{
		if(message.length>=1)
		{
			AIInfoPack pack 
				= core.getInfoPackFrom(message[0], AIInfoPack.class);
			
			if(pack!=null)
			{
				boolean isInRange = Boolean.parseBoolean(message[1]);
				if(pack.isInRangeOfTarget()!=isInRange)
				{
					pack.setInRangeOfTarget(isInRange);
					LOGGER.log(Level.FINE, 
							pack.getOwner().getName() + "(" + message[0]
									+ ") in range?: " + isInRange);
				}
			}
		}
	}
	
	/**
	 * Sets the flag that indicates the entity should be processed by the AI.
	 * @param message	[0] contains the entity's ID
	 * 					[1] contains the boolean flag 
	 */
	private void setActive(final String[] message)
	{
		if(message.length>=1)
		{
			AIInfoPack pack 
				= core.getInfoPackFrom(message[0], AIInfoPack.class);

			if(pack!=null)
			{				
				boolean isActive = Boolean.parseBoolean(message[1]);
				pack.setActive(isActive);
				LOGGER.log(Level.FINE, 
						pack.getOwner().getName() + "(" + message[0]
								+ ") is active?: " + isActive);
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