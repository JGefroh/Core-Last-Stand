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
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);

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
		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		isRunning = true;
		core.setInterested(this, "CALC_DISTANCE_TO_TARGET");
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
		
		if(id.equals("GET_DISTANCE_TO_TARGET"))
		{
			calcDistanceToTarget(message);
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	private void track()
	{
		Iterator<TargetTrackInfoPack> packs 
			= core.getInfoPacksOfType(TargetTrackInfoPack.class);
		while(packs.hasNext())
		{
			TargetTrackInfoPack each = packs.next();
			
			TargetInfoPack target 
				= core.getInfoPackFrom(each.getTarget(), TargetInfoPack.class);
			
			if(target==null)
			{
				target = pickTarget(each);
				if(target!=null)
				{					
					each.setTarget(target.getOwner());
				}
			}
			turnTowardsTarget(each, target);
		}
		
	}
	
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
	
	private void turnTowardsTarget(final TargetTrackInfoPack each,
									final TargetInfoPack target)
	{
		if(target!=null)
		{
			double bearing 
			= calculateBearing(each.getXPos(), each.getYPos(), 
								target.getXPos(), target.getYPos());
			
			each.setBearing(bearing);
		}
	}
	
	/**
	 * Calculates the angle necessary to face the mouse from a given position.
	 * @param x	the x coordinate of the object
	 * @param y	the y coordinate of the object
	 * @return	the angle to turn the object in order to face the mouse
	 */
	private double calculateBearing(final double xSource, final double ySource,
									final double xTarget, final double yTarget)
	{
		double adj = xTarget-xSource;
		double opp = yTarget-ySource;
		
		double bearing = Math.toDegrees(Math.atan2(opp, adj));
		return bearing;
	}
	
	private void calcDistanceToTarget(final String[] message)
	{
		if(message.length>0)
		{
			String entityID = message[0];
			
			TargetTrackInfoPack pack 
				= core.getInfoPackFrom(entityID, TargetTrackInfoPack.class);
			
			if(pack!=null)
			{
				TargetInfoPack target 
					= core.getInfoPackFrom(pack.getTarget(), TargetInfoPack.class);
				if(target!=null)
				{
					double adj = pack.getXPos()-target.getXPos();
					double opp = pack.getYPos()-target.getYPos();
					
					double dist = Math.sqrt(adj*adj+opp*opp);
					pack.setDistanceToTarget(dist);
				}
			}
		}
	}
	
}
