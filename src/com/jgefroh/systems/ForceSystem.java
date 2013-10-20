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
import com.jgefroh.data.Vector;
import com.jgefroh.infopacks.ForceInfoPack;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_GENERATE_FORCE;


/**
 * Creates a one-time force vector to apply to an object.
 * @author Joseph Gefroh
 */
public class ForceSystem extends AbstractSystem {
	
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


	//////////////////////////////////////////////////
	// Initialize
	//////////////////////////////////////////////////
	
	/**
	 * Creates a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public ForceSystem(final Core core) {
		this.core = core;
		setDebugLevel(this.debugLevel);
		init();
	}
	
	//////////////////////////////////////////////////
	// Override
	//////////////////////////////////////////////////
	
	@Override
	public void init() {
		setDebugLevel(this.debugLevel);
		core.setInterested(this, DefaultMessage.COMMAND_GENERATE_FORCE);
	}

	@Override
	public void work(final long now) {
		integrate(now);	//Add all forces together.
	}

	@Override
	public void recv(final IMessage messageType, final Map<IPayload, String> message) {		
		LOGGER.log(Level.FINEST, "Received message: " + messageType);
		if (messageType.getClass() == DefaultMessage.class) {
			DefaultMessage type = (DefaultMessage) messageType;
			switch (type) {
			case COMMAND_GENERATE_FORCE:
				generate(message);
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
	 * Integrates all the vectors acting on a object into a single vector.
	 * @param now	the current time
	 */
	public void integrate(final long now) {
		Iterator<IEntity> packs = core.getEntitiesWithPack(ForceInfoPack.class);
		ForceInfoPack pack = core.getInfoPackOfType(ForceInfoPack.class);
		
		while(packs.hasNext()) {
			if (!pack.setEntity(packs.next())) {
				continue;
			}
			
			Vector vecGenerated = pack.getGeneratedVector();		
			
			if (pack.isRelative()) {
				vecGenerated.setAngle(pack.getBearing());
			}
			
			Vector vecTotal = pack.getSumVector();
			vecTotal.add(vecGenerated);
			if (!pack.isContinuous()) {				
				pack.setGeneratedForce(new Vector());
			}
		}
	}

	
	//////////////////////////////////////////////////
	// Messages
	//////////////////////////////////////////////////
	
	/**
	 * Generates a vector force for a specific entity.
	 * @param message	[0] contains the entityID
	 * 					[1] contains the vector's magnitude (double)
	 * 					[2] contains the vector's angle		(double)
	 */
	private void generate(final Map<IPayload, String> data) {
		if (data == null || data.size() < 2) {
			return;
		}
		
		String entityID = data.get(COMMAND_GENERATE_FORCE.ENTITY_ID);
		double angle = 0;
		try {
			angle = Double.parseDouble(data.get(COMMAND_GENERATE_FORCE.VECTOR_ANG));
		}
		catch(NumberFormatException e) {
			LOGGER.log(Level.WARNING, "Unexpected number of parameters.");
			return;
		}
		
		ForceInfoPack fip 
			= core.getInfoPackFrom(entityID, ForceInfoPack.class);
		
		if (fip != null) {
			Vector generated = fip.getGeneratedVector();	//Get current generated vector...
			Vector vector = new Vector();
			vector.setAngle(angle);
			vector.setMagnitude(fip.getMagnitude());
			vector.setID(0);
			generated.add(vector);
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
