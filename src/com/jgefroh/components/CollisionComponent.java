package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data that allows objects to collide.
 * @author Joseph Gefroh
 */
public class CollisionComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**Used to determine what collides with what.*/
    private int collisionGroup;


    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////

    /**
     * Creates a new instance of this {@code Component}.
     */
    public CollisionComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////

    /**
     * Returns the collision group the entity belongs to.
     * @return	the collision group the entity is a part of
     */
    public int getCollisionGroup() {
        return this.collisionGroup;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////

    /**
     * Sets the collision group the entity belongs to.
     * @param collisionGroup	the collision group the entity belongs to
     */
    public void setCollisionGroup(final int collisionGroup) {
        this.collisionGroup = collisionGroup;
    }
}
