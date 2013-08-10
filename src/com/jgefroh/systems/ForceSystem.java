package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.data.Vector;
import com.jgefroh.infopacks.ForceInfoPack;
import com.jgefroh.messages.Message;


/**
 * Creates a one-time force vector to apply to an object.
 * @author Joseph Gefroh
 */
public class ForceSystem extends AbstractSystem
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
		setDebugLevel(this.debugLevel);
		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		setDebugLevel(this.debugLevel);
		core.setInterested(this, Message.GENERATE_FORCE);
	}

	@Override
	public void work(final long now)
	{
		if(isRunning())
		{
			integrate(now);	//Add all forces together.
		}
		else
		{
			stop();
		}
	}

	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
		
		Message msgID = Message.valueOf(id);

		switch(msgID)
		{
			case GENERATE_FORCE:
				generate(message);
				break;
		}
	}
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Integrates all the vectors acting on a object into a single vector.
	 * @param now	the current time
	 */
	public void integrate(final long now)
	{
		Iterator<ForceInfoPack> packs
			= core.getInfoPacksOfType(ForceInfoPack.class);
		
		while(packs.hasNext())
		{
			ForceInfoPack each = packs.next();
			
			Vector vecGenerated = each.getGeneratedVector();		
			
			if(each.isRelative()==true)
			{
				vecGenerated.setAngle(each.getBearing());
			}
			
			Vector vecTotal = each.getSumVector();
			vecTotal.add(vecGenerated);
			if(each.isContinuous()==false)
			{				
				each.setGeneratedForce(new Vector());
			}
		}
	}
	
	
	/**
	 * Generates a vector force for a specific entity.
	 * @param message	[0] contains the entityID
	 * 					[1] contains the vector's magnitude (double)
	 * 					[2] contains the vector's angle		(double)
	 */
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
			
			ForceInfoPack fip 
				= core.getInfoPackFrom(entityID, ForceInfoPack.class);
			
			if(fip!=null&&fip.isDirty()==false)
			{
				Vector generated = fip.getGeneratedVector();	//Get current generated vector...
				Vector vector = new Vector();
					vector.setAngle(angle);
					vector.setMagnitude(fip.getMagnitude());
					vector.setID(0);
				generated.add(vector);
				LOGGER.log(Level.FINE, 
						fip.getOwner().getName() + "(" + entityID
								+ ") generated force with " + 
								vecMag + " magnitude at " + angle + " degrees.");
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
