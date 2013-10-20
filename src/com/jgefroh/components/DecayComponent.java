package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;


/**
 * Contains data that controls decay times.
 * @author Joseph Gefroh
 */
public class DecayComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**The ms time the component was last updated.*/
    private long lastUpdateTime = 0;

    /**Time until the entity is removed.*/
    private long timeUntilDecay;

    
    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    
    /**
     * Create a new decay component.
     * @param entity	the owner of this component
     */
    public DecayComponent() {
    }

    
    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////

    /**
     * Gets the time the component was last updated.
     * @return	the time the component was last updated, in ms
     */
    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    /**
     * Gets the time to wait before removing the object
     * @return	the time to wait until the decay is removed.
     */
    public long getTimeUntilDecay() {
        return this.timeUntilDecay;
    }

    
    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Sets the time the component was last updated
     * @param lastUpdateTime the time the component was last updated, in ms
     */
    public void setLastUpdateTime(final long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * Sets the time until decay.
     * @param timeUntilDecay    the time until decay, in ms.
     */
    public void setTimeUntilDecay(final long timeUntilDecay) {
        this.timeUntilDecay = timeUntilDecay;
    }
}
