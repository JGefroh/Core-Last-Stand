package com.jgefroh.systems;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AIInfoPack;
import com.jgefroh.tests.Benchmark;


/**
 * This system controls the behavior of the AI for the enemies.
 * Date: 16JUNE13
 * @author Joseph Gefroh
 */
public class AISystem extends AbstractSystem
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
	
	private Benchmark bench = new Benchmark(this.getClass().getName(), false);

	
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

	}

	@Override
	public void work(final long now)
	{
		long startTime = System.nanoTime();
		int numEntities = attack();
		bench.benchmark(System.nanoTime()-startTime, numEntities);
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
	private int attack()
	{
		Iterator<AIInfoPack> packs = core.getInfoPacksOfType(AIInfoPack.class);
		
		int numEntities = 0;
		while(packs.hasNext())
		{
			AIInfoPack each = packs.next();
			
			if(each.isInRangeOfTarget()&&each.isActive())
			{				
				rollForAttack(each);
			}
			numEntities++;
		}
		return numEntities;
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
					LOGGER.log(Level.FINEST, 
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
				LOGGER.log(Level.FINEST, 
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
