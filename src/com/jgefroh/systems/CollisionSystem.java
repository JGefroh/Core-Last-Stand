package com.jgefroh.systems;


import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.effects.BulletHitEffect;
import com.jgefroh.effects.IEffect;
import com.jgefroh.infopacks.CollisionInfoPack;
import com.jgefroh.tests.Benchmark;

/**
 * This system handles collision checking for entities.
 * @author Joseph Gefroh
 */
public class CollisionSystem implements ISystem
{
	//TODO: Handle rotated quad collision
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
	
	//Combine with below?
	/**Contains the collision pairs that determines whether objects collide.*/
	private boolean[][] collisionTable;
	
	/**Stores effects to execute when collisions are detected.*/
	private ArrayList<IEffect> effects;
	
	private Benchmark bench = new Benchmark(this.getClass().getName(), true);

	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public CollisionSystem(final Core core)
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
		collisionTable = new boolean[9][9];	
		isRunning = true;
		effects = new ArrayList<IEffect>();
		trackEffect(new BulletHitEffect(core));
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
			int numEntities = checkAll();
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
	 * Go through all of the collidable objects and check for collisions.
	 */
	public int checkAll()
	{
		int numEntities = 0;
		//TODO: Change to smarter collision
		Iterator<CollisionInfoPack> checkThese =
				core.getInfoPacksOfType(CollisionInfoPack.class);

		while(checkThese.hasNext())
		{
			numEntities++;
			CollisionInfoPack cipA = checkThese.next();
			
			if(cipA.isDirty()==false)
			{
				Iterator<CollisionInfoPack> checkAgainst =
						core.getInfoPacksOfType(CollisionInfoPack.class);
				
				while(checkAgainst.hasNext())
				{
					CollisionInfoPack cipB = checkAgainst.next();
					
					if(cipB.isDirty()==false)
					{
						
						if(checkCollidesWith(cipA.getGroup(), cipB.getGroup()) 
												&& cipA!=cipB)
						{
							if(checkCollided(cipA, cipB))
							{
								executeCollisionEffects(cipA, cipB);
							}
						}
					}
				}
			}
		}
		return numEntities;
	}
	
	
	/**
	 * Set the collision possibility of a pair of collision groups.
	 * @param groupOne	the id of the first group
	 * @param groupTwo	the id of the second group
	 * @param collides	true if they should collide, false otherwise
	 */
	public void setCollision(final int groupOne, final int groupTwo, 
								final boolean collides)
	{
		if(groupOne>=0&&groupTwo>=0
				&&groupOne<collisionTable.length
				&&groupTwo<collisionTable.length)
		{
			collisionTable[groupOne][groupTwo] = collides;
			collisionTable[groupTwo][groupOne] = collides;
		}
	}
	
	/**
	 * Return the collision possibility between two collision groups.
	 * @param groupOne	the id of the first group
	 * @param groupTwo	the id of the second group
	 * @return	true if they should collide, false otherwise
	 */
	private boolean checkCollidesWith(final int groupOne, final int groupTwo)
	{
		return collisionTable[groupOne][groupTwo];
	}

	/**
	 * Check to see if a collision occurred between two entities.
	 * @param packOne	the CollisionInfoPack belonging to the first entity
	 * @param packTwo	the CollisionInfoPack belonging to the second entity
	 * @return	true if the entities are colliding, false otherwise
	 */
	private boolean checkCollided(final CollisionInfoPack packOne, 
			final CollisionInfoPack packTwo)
	{
		Rectangle r1 = new Rectangle((int)(packOne.getXPos()-(packOne.getWidth()/2)), (int)(packOne.getYPos()-(packOne.getHeight()/2)),
				(int)packOne.getWidth(), (int)packOne.getHeight());
		Rectangle r2 = new Rectangle((int)(packTwo.getXPos()-(packTwo.getWidth()/2)),(int)(packTwo.getYPos()-(packTwo.getHeight()/2)),
				(int)packTwo.getWidth(), (int)packTwo.getHeight());
		if(r1.intersects(r2))
		{

			return true;
		}
		return false;
	}
	
	/**
	 * Start tracking an effect.
	 * @param effect 	the effect to track
	 */
	private void trackEffect(final IEffect effect)
	{
		effects.add(effect);
	}
	
	/**
	 * Executes the effects associated with a collision pair.
	 * @param packA	the first entity's InfoPack
	 * @param packB	the second entity's InfoPack
	 */
	private void executeCollisionEffects(final CollisionInfoPack packA, final CollisionInfoPack packB)
	{
		for(IEffect each:effects)
		{
			if(each.check("COLLISION", packA.getOwner(), packB.getOwner()))
			{
				each.execute(packA.getOwner(), packB.getOwner());
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
