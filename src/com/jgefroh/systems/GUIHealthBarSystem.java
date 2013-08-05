package com.jgefroh.systems;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.AbstractSystem;
import com.jgefroh.core.Core;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.HealthBarInfoPack;

/**
 * Controls the display of health bars above entities.
 * @author Joseph Gefroh
 */
public class GUIHealthBarSystem extends AbstractSystem
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
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public GUIHealthBarSystem(final Core core)
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
		core.setInterested(this, "DESTROYING_ENTITY");
	}

	@Override
	public void work(final long now)
	{
		updateHealthBars();
	}
	
	@Override
	public void recv(final String id, final String... message)
	{
		LOGGER.log(Level.FINEST, "Received message: " + id);
		
		if(id.equals("DESTROYING_ENTITY"))
		{
			destroyHealthBar(message);
		}
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Updates the position and display of all health bars.
	 */
	private void updateHealthBars()
	{
		Iterator<HealthBarInfoPack> packs 
			= core.getInfoPacksOfType(HealthBarInfoPack.class);
		
		while(packs.hasNext())
		{
			HealthBarInfoPack each = packs.next();
			updatePosition(each);
			updateHealth(each);		
		}
	}
	
	/**
	 * Updates the position of a specific health bar.
	 * @param each	the InfoPack of the health bar to update
	 */
	private void updatePosition(final HealthBarInfoPack each)
	{
		double xPos = each.getOwnerXPos();
		double yPos = each.getOwnerYPos();
		
		each.setHealthBarXPos(xPos);
		each.setHealthBarYPos(yPos-32);	//TODO: set to height
	}
	
	/**
	 * Updates the amount of health displayed by the health bar.
	 * @param each	the InfoPack of the health bar to update
	 */
	private void updateHealth(final HealthBarInfoPack each)
	{
		int health = each.getHealth();
		each.setHealthBarWidth(health);	//TODO: Normalize to 100% or add layers to avoid super long health bars
	}
	
	/**
	 * Removes a health bar from the screen when its owner has been destroyed
	 * @param message	[0] contains the entityID of the destroyed owner
	 */
	private void destroyHealthBar(final String[] message)
	{
		if(message.length>=1)
		{
			HealthBarInfoPack pack 
				= core.getInfoPackFrom(message[0], HealthBarInfoPack.class);
			
			if(pack!=null)
			{
				core.removeEntity(pack.getHealthBar());
				LOGGER.log(Level.FINE, 
						pack.getOwner().getName() + "(" + message[0]
								+ ")'s health bar destroyed.");
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
