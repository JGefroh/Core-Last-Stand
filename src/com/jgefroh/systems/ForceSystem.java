package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Vector;
import com.jgefroh.infopacks.ForceInfoPack;
import com.jgefroh.infopacks.MovementInfoPack;


/**
 * Creates a one-time force vector to apply to an object.
 * @author Joseph Gefroh
 */
public class ForceSystem implements ISystem
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
	private Level debugLevel = Level.OFF;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);

	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public ForceSystem(final Core core)
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
		core.setInterested(this, "GENERATE_FORCE");
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
			integrate(now);	//Add all forces together.
		}
		else
		{
			stop();
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
		if(id.equals("GENERATE_FORCE"))
		{
			generate(message);
		}
	}
	//////////
	// SYSTEM METHODS
	//////////
	public void integrate(final long now)
	{
		Iterator<ForceInfoPack> packs
			= core.getInfoPacksOfType(ForceInfoPack.class);
		
		while(packs.hasNext())
		{
			ForceInfoPack each = packs.next();
			if(each.isDirty()==false)
			{
				Vector total = each.getSumVector();
				Vector generated = each.getGeneratedForce();
				total.add(generated);
				each.setGeneratedForce(new Vector());
			}
		}
	}
	
	private void generate(final String[] message)
	{
		if(message.length>=3)
		{
			String entityID = message[0];
			double vecMag = 0;
			double angle = 0;
			try
			{
				vecMag = Double.parseDouble(message[1]);
				angle = Double.parseDouble(message[2]);
			}
			catch(NumberFormatException e)
			{
				LOGGER.log(Level.WARNING, "Unexpected number of parameters.");
			}
			
			MovementInfoPack mip 
				= core.getInfoPackFrom(entityID, MovementInfoPack.class);
			ForceInfoPack fip 
				= core.getInfoPackFrom(entityID, ForceInfoPack.class);
			
			if(mip!=null&&fip!=null&&
					mip.isDirty()==false&&fip.isDirty()==false)
			{
				Vector vector = new Vector();
				vector.setAngle(angle);
				vector.setMaxMagnitude(5);
				vector.setMagnitude(vecMag);
				vector.setID(0);
				vector.setContinuous(false);
				fip.getGeneratedForce().add(vector);
			}
		}
	}
}
