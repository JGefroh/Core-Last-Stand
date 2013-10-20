package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data allowing an entity to do damage to another entity.
 * @author Joseph Gefroh
 */
public class DamageComponent extends AbstractComponent {
    
    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**The amount of damage.*/
    private int damage;

    
    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    
    /**
     * Creates a new instance of this {@code Component}.
     */
    public DamageComponent() {
    }

    
    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Gets the amount of damage done.
     * @return	the amount of damage
     */
    public int getDamage() {
        return this.damage;
    }

    
    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Sets the amount of damage done.
     * @param damage	the amount of damage
     */
    public void setDamage(final int damage) {
        this.damage = damage;
    }
}
