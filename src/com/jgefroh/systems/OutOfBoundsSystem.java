package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.AIInfoPack;
import com.jgefroh.infopacks.OutOfBoundsInfoPack;
import com.jgefroh.tests.Benchmark;


/**
 * @author Joseph Gefroh
 */
public class OutOfBoundsSystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	@SuppressWarnings("unused")
	private boolean isRunning;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINER;
	
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
	public OutOfBoundsSystem(final Core core)
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
		isRunning = true;		
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
		checkOutOfBounds();
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
	}
	/////////
	// SYSTEM METHODS
	/////////
	
	private void checkOutOfBounds()
	{
		Iterator<OutOfBoundsInfoPack> packs
			= core.getInfoPacksOfType(OutOfBoundsInfoPack.class);
		
		while(packs.hasNext())
		{
			OutOfBoundsInfoPack pack = packs.next();
			
			if(pack.isChecking()&&
					(pack.getXPos()+pack.getWidth()/2<0
					|| pack.getXPos()-pack.getWidth()/2>1366
					|| pack.getYPos()+pack.getHeight()/2<0
					|| pack.getYPos()-pack.getHeight ()/2>768))
			{
				core.send("DESTROYING_ENTITY", pack.getOwner().getID());
				LOGGER.log(Level.FINEST, 
					"Entity " + pack.getOwner().getID() + " out of bounds.");
				core.removeEntity(pack.getOwner());
			}
			else if(pack.isChecking()==false)
			{
				boolean isChecking = checkWithinBounds(pack);
				if(isChecking==true)
				{					
					pack.setChecking(isChecking);
					core.send("IS_WITHIN_BOUNDS", 
							pack.getOwner().getID(), 
							true + "");
					LOGGER.log(Level.FINEST, 
							pack.getOwner().getName() + "(" 
									+ pack.getOwner().getID()
									+ ") crossed threshold.");
				}
			}
		}
	}
	
	private boolean checkWithinBounds(final OutOfBoundsInfoPack pack)
	{
		if(pack.getXPos()-pack.getWidth()/2>=0
		&& pack.getXPos()+pack.getWidth()/2<=1366
		&& pack.getYPos()-pack.getHeight()/2>=0
		&& pack.getYPos()+pack.getHeight()/2<=768)
		{
			return true;
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
