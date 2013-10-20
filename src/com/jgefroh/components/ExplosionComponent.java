package com.jgefroh.components;

import java.util.HashMap;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data allowing an entity to do damage to another entity.
 * @author Joseph Gefroh
 */
public class ExplosionComponent extends AbstractComponent {

    
    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////
    private double widthMax;
    private double widthInc;
    private double heightMax;
    private double heightInc;
    private long lastUpdated;
    private long updateInterval;
    private int pulse;
    private HashMap<String, Integer> victims = new HashMap<String, Integer>();

    
    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    /**
     * Creates a new instance of this {@code Component}.
     */
    public ExplosionComponent() {
    }

    
    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    public double getWidthMax() {
        return this.widthMax;
    }

    public double getHeightMax() {
        return this.heightMax;
    }

    public double getWidthInc() {
        return this.widthInc;
    }

    public double getHeightInc() {
        return this.heightInc;
    }

    public long getLastUpdated() {
        return this.lastUpdated;
    }

    public long getUpdateInterval() {
        return this.updateInterval;
    }

    public int getPulse() {
        return this.pulse;
    }

    public int getNumHits(final String entityID) {
        Integer numHits = victims.get(entityID);

        if (numHits == null) {
            return 0;
        }
        return numHits;
    }

    
    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    public void setWidthInc(final double widthInc) {
        this.widthInc = widthInc;
    }

    public void setWidthMax(final double widthMax) {
        this.widthMax = widthMax;
    }

    public void setHeightInc(final double heightInc) {
        this.heightInc = heightInc;
    }

    public void setHeightMax(final double heightMax) {
        this.heightMax = heightMax;
    }

    public void setLastUpdated(final long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setUpdateInterval(final long updateInterval) {
        this.updateInterval = updateInterval;
    }

    public void setPulse(final int pulse) {
        this.pulse = pulse;
    }

    public void setNumHits(final String entityID, final int numHits) {
        victims.put(entityID, numHits);
    }
}
