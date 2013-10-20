package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data that allows entities to be destroyed after moving offscreen.
 * @author Joseph Gefroh
 */
public class OutOfBoundsComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**FLAG: Indicates whether the entity should be checked or not.*/
    private boolean isChecking = false;


    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    public OutOfBoundsComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////

    /**
     * Gets the flag indicating the entity should be checked or not.
     * @return	true if the entity should be checked; false otherwise
     */
    public boolean isChecking() {
        return this.isChecking;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Sets the flag indicating the Entity should be checked or not.
     * @param isChecking true if the entity should be checked; false otherwise
     */
    public void setChecking(final boolean isChecking) {
        this.isChecking = isChecking;
    }
}
