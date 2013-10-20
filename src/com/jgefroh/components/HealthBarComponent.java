package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Entities with this component will have a healthbar drawn on screen.
 * @author Joseph Gefroh
 */
public class HealthBarComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////
    
    /**A reference to the HealthBar belonging to this Entity.*/
    private IEntity healthBar;


    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    
    /**
     * Creates a new instance of this {@code Component}.
     */
    public HealthBarComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Gets the Health Bar of this entity.
     * @return	the Health Bar entity
     */
    public IEntity getHealthBar() {
        return this.healthBar;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    

    /**
     * Sets the Health Bar entity for this entity
     * @param healthBar
     */
    public void setHealthBar(final IEntity healthBar) {
        this.healthBar = healthBar;
    }
}
