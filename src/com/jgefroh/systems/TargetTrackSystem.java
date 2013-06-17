package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.TargetInfoPack;
import com.jgefroh.infopacks.TargetTrackInfoPack;


/**
 * Rotates entities towards a specific target.
 * @author Joseph Gefroh
 */
public class TargetTrackSystem implements ISystem
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

	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public TargetTrackSystem(final Core core)
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
		isRunning = true;
	}

	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "System started.");
		this.isRunning = true;
	}

	@Override
	public void work(final long now)
	{
		if(this.isRunning)
		{
			track();	
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		this.isRunning = false;
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
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Selects and tracks targets.
	 */
	private void track()
	{
		Iterator<TargetTrackInfoPack> packs 
			= core.getInfoPacksOfType(TargetTrackInfoPack.class);
		while(packs.hasNext())
		{
			//Get all trackers
			TargetTrackInfoPack each = packs.next();
			
			//Get target if it already has one...
			TargetInfoPack target 
				= core.getInfoPackFrom(each.getTarget(), TargetInfoPack.class);
			
			if(target==null)
			{//If the entity does not already have a target...
				target = pickTarget(each);	//Pick a target...
				if(target!=null)
				{//if a target was succesfully chosen...
					each.setTarget(target.getOwner());
				}
			}
			
			//If the target is in range...
			if(checkTargetInRange(each, target))
			{
				turnTowardsTarget(each, target);
			}
		}
	}
	
	/**
	 * Selects a target from the available targets.
	 * @param each	the InfoPack of the entity that wants a target
	 * @return		a target if one is found;null otherwise
	 */
	private TargetInfoPack pickTarget(final TargetTrackInfoPack each)
	{
		Iterator<TargetInfoPack> possibleTargets 
			= core.getInfoPacksOfType(TargetInfoPack.class);
	
		while(possibleTargets.hasNext())
		{
			TargetInfoPack target = possibleTargets.next();
			//Put target criteria here.
			return target;
		}
		return null;
	}
	
	/**
	 * Turns entities towards their targets.
	 * @param each		the tracker
	 * @param target	the target
	 */
	private void turnTowardsTarget(final TargetTrackInfoPack each,
									final TargetInfoPack target)
	{
		if(target!=null)
		{
			double adj = target.getXPos()-each.getXPos();
			double opp = target.getYPos()-each.getYPos();
			double bearing = Math.toDegrees(Math.atan2(opp, adj));
			each.setBearing(bearing);
		}
	}
	
	/**
	 * Checks to see if the target is in range.
	 * @param each		the tracker
	 * @param target	the target
	 * @return			true if the target is in range; false otherwise
	 */
	private boolean checkTargetInRange(final TargetTrackInfoPack each, final TargetInfoPack target)
	{
		if(target!=null)
		{
			double adj = target.getXPos()-each.getXPos();
			double opp = target.getYPos()-each.getYPos();
			double dist = Math.sqrt(adj*adj+opp*opp);
			
			if(dist<=each.getTargetRange())
			{
				core.send("IN_RANGE_OF_TARGET", each.getOwner().getID(), true + "");
				return true;
			}
			else
			{
				core.send("IN_RANGE_OF_TARGET", each.getOwner().getID(), false + "");
				return false;
			}
		}
		return false;
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
