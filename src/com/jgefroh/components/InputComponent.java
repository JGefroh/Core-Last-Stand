package com.jgefroh.components;

import java.util.ArrayList;
import java.util.List;

import com.jgefroh.core.AbstractComponent;


/**
 * Contains data necessary for an entity to receive input.
 * @author Joseph Gefroh
 */
public class InputComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**Contains all of the commands this entity responds to.*/
    private List<String> commandList = new ArrayList<String>();


    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    /**
     * Creates a new instance of this {@code Component}.
     */
    public InputComponent() {
    }

    
    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Checks to see if the entity is interested in the input command.
     * @param command	the String command of the input
     * @return	true if it is interested; false otherwise.
     */
    public boolean checkInterested(final String command) {
        if (commandList.contains(command)) {
            return true;
        }
        return false;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Marks this Entity as interested in the given input.
     * @param command the input command
     */
    public void setInterested(final String command) {
        if (checkInterested(command) == false) {
            commandList.add(command);
        }
    }

    /**
     * Marks the entity to ignore the command if it is not already.
     * @param command the input command
     */
    public void setUninterested(final String command) {
        commandList.remove(command);
    }
}
