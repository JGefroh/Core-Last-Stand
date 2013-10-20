package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;


/**
 * Contains data to keep track of how much an entity is worth (score)
 * @author Joseph Gefroh
 */
public class ScoreComponent extends AbstractComponent {
    
    //////////////////////////////////////////////////
    // Data
    //////////////////////////////////////////////////

    /**The amount of points this entity is worth.*/
    private int score;

    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    /**
     * Create a new animation component.
     */
    public ScoreComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////

    public int getScore() {
        return this.score;
    }

    
    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////

    public void setScore(final int score) {
        this.score = score;
    }
}
