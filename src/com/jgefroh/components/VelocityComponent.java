package com.jgefroh.components;

import java.util.HashMap;
import java.util.Map;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.data.Vector;

/**
 * Contains data used to move entities.
 * 
 * @author Joseph Gefroh
 */
public class VelocityComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**The amount of time to wait in-between movements, in milliseconds.*/
    private long interval;

    /**The time the velocity was last updated.*/
    private long lastUpdated = -1;

    /**An map of all forces acting on this object.*/
    private Map<Integer, Vector> vectors = new HashMap<Integer, Vector>();

    /**The total forces that were left with the object.*/
    private Vector totalMovementVector;

    /**Flag that indicates whether the movement should be repeated after use.*/
    private boolean isContinuous;

    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    /**
     * Creates a new instance of this {@code Component}.
     */
    public VelocityComponent() {
        setTotalMovementVector(new Vector());
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    /**
     * Gets the update interval of the component.
     * @return	the time, in ms, to wait before attempting an update
     */
    public long getInterval() {
        return this.interval;
    }

    /**
     * Gets the time the component was last updated.
     * @return	the time, in ms, the component was last updated
     */
    public long getLastUpdated() {
        return this.lastUpdated;
    }

    /**
     * Gets a vector from a specific source.
     * @param source	the int ID of the source
     * @return			the vector from the source; null if nonexistent
     */
    public Vector getVector(final int source) {
        return vectors.get(source);
    }

    /**
     * Gets all vectors applied to this Entity.
     * @return	all of the vectors
     */
    public Map<Integer, Vector> getVectors() {
        return vectors;
    }

    /**
     * Gets the flag that indicates the movement should be repeated.
     * @return	true if the movement should repeat; false otherwise
     */
    public boolean isContinuous() {
        return this.isContinuous;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Sets the update interval of the component.
     * @param interval	the time, in ms, to wait before attempting a move
     */
    public void setInterval(final long interval) {
        this.interval = interval;
    }

    /**
     * Sets the time the component was last updated.
     * @param lastUpdated	the time, in ms, the component was last updated
     */
    public void setLastUpdated(final long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Sets the vector that describes the sum of forces acting on this object
     * @param vector	the vector that describes final movement
     */
    public void setTotalMovementVector(final Vector vector) {
        this.totalMovementVector = vector;
    }


    /**
     * Sets the flag that indicates the movement should be repeated.
     * @param isContinuous	true if it should be repeated; false otherwise
     */
    public void setContinuous(final boolean isContinuous) {
        this.isContinuous = isContinuous;
    }


    //////////////////////////////////////////////////
    // Methods
    //////////////////////////////////////////////////
    /**
     * Checks to see if the vector exists for this object
     * @param vector	the vector to check
     * @return
     */
    public boolean hasVector(final Vector vector) {
        return vectors.containsKey(vector.getID());
    }

    /**
     * Adds a vector to this object
     * @param vector
     */
    public void addVector(final Vector vector) {
        this.vectors.put(vector.getID(), vector);
    }


    /**
     * Removes a vector from applying to this object.
     */
    public void removeVector(final Vector vector) {
        vectors.remove(vector);
    }

    /**
     * Gets the vector that describes the total translation.
     * @return	the total movement vector
     */
    public Vector getTotalMovementVector() {
        return this.totalMovementVector;
    }

}