package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data to identify GUI elements.
 * @author Joseph Gefroh
 */
public class GUIComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////
    private int id;

    
    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    public GUIComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////

    public int getID() {
        return this.id;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////

    public void setID(final int id) {
        this.id = id;
    }

}
