package com.jgefroh.components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.Vector;

/**
 * Contains data used to move entities.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class VelocityComponent implements IComponent
{
	//TODO: Clean up, remove unnecessary stuff.
	//////////
	// DATA
	//////////
	/**The owner of the component.*/
	private IEntity owner;

	/**The amount of time to wait in-between movements, in milliseconds.*/
	private long interval;
	
	/**The time the velocity was last updated.*/
	private long lastUpdated;
	
	/**An ArrayList of all forces acting on this object.*/
	private HashMap<Integer, Vector> vectors;
	
	/**The total forces that were left with the object.*/
	private Vector totalMovementVector;
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code Component}.
	 * @param owner	the IEntity owner of the component
	 */
	public VelocityComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
		setLastUpdated(-1);
		setInterval(0);
		vectors = new HashMap<Integer, Vector>();
		setTotalMovementVector(new Vector());
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Gets the update interval of the component.
	 * @return	the time, in ms, to wait before attempting an update
	 */
	public long getInterval()
	{
		return this.interval;
	}
	
	/**
	 * Gets the time the component was last updated.
	 * @return	the time, in ms, the component was last updated
	 */
	public long getLastUpdated()
	{
		return this.lastUpdated;
	}
	
	/**
	 * Gets a vector from a specific source.
	 * @param source	the int ID of the source
	 * @return			the vector from the source; null if nonexistent
	 */
	public Vector getVector(final int source)
	{
		return vectors.get(source);
	}
	
	/**
	 * Gets all vectors applied to this Entity.
	 * @return	all of the vectors
	 */
	public HashMap<Integer, Vector> getVectors()
	{
		return vectors;
	}
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
		
	/**
	 * Sets the update interval of the component.
	 * @param interval	the time, in ms, to wait before attempting a move
	 */
	public void setInterval(final long interval)
	{
		this.interval = interval;
	}
	
	/**
	 * Sets the time the component was last updated.
	 * @param lastUpdated	the time, in ms, the component was last updated
	 */
	public void setLastUpdated(final long lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}
	
	/**
	 * Sets the vector that describes the sum of forces acting on this object
	 * @param vector	the vector that describes final movement
	 */
	public void setTotalMovementVector(final Vector vector)
	{
		this.totalMovementVector = vector;
	}
	//////////
	// METHODS
	//////////
	/**
	 * Checks to see if the vector exists for this object
	 * @param vector	the vector to check
	 * @return
	 */
	public boolean hasVector(final Vector vector)
	{
		return vectors.containsKey(vector.getID());
	}
	
	/**
	 * Adds a vector to this object
	 * @param vector
	 */
	public void addVector(final Vector vector)
	{
		this.vectors.put(vector.getID(), vector);
	}

	
	/**
	 * Removes a vector from applying to this object.
	 */
	public void removeVector(final Vector vector)
	{
		vectors.remove(vector);
	}
	
	/**
	 * Gets the vector that describes the total translation.
	 * @return	the total movement vector
	 */
	public Vector getTotalMovementVector()
	{
		return this.totalMovementVector;
	}
}