package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data that keeps entities inside the bounds.
 * @author Joseph Gefroh
 */
public class KeepInBoundsComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**The last valid X position of the entity.*/
    private double lastX;

    /**The last valid Y position of the entity.*/
    private double lastY;


    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    public KeepInBoundsComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Gets the last X Position of the entity.
     * @return	the last X position
     */
    public double getLastX() {
        return this.lastX;
    }

    /**
     * Gets the last Y position of the entity.
     * @return	the last Y position
     */
    public double getLastY() {
        return this.lastY;
    }

    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Gets the last X Position of the entity.
     * @param lastX the last X position
     */
    public void setLastX(final double lastX) {
        this.lastX = lastX;
    }

    /**
     * Gets the last Y position of the entity.
     * @param lastY	the last Y position
     */
    public void setLastY(final double lastY) {
        this.lastY = lastY;
    }
}
