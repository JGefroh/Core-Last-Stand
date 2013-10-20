package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to link an entity to another entity.
 * @author Joseph Gefroh
 */
public class SlaveComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////
    
    /**The master of this Entity.*/
    private IEntity master;

    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    /**
     * Creates a new instance of this {@code Component}.
     */
    public SlaveComponent() {
    }

    
    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Gets the Entity this Entity is linked to.
     * @return	the Entity that this entity follows
     */
    public IEntity getMaster() {
        return this.master;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Sets the Entity this Entity is linked to.
     * @param master	the Entity that this entity follows
     */
    public void setMaster(final IEntity master) {
        this.master = master;
    }

}
