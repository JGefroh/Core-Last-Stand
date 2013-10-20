package com.jgefroh.data;

import com.jgefroh.core.IMessage;

public class Upgrade {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////
    
    /**The name of this upgrade.*/
    private String name = "";

    /**A short description of this upgrade.*/
    private String desc;

    /**The cost of this upgrade.*/
    private int cost;

    /**The command to send when the upgrade is purchased.*/
    private IMessage command;

    /**The amount this upgrade will do.*/
    private int value;


    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    
    private Upgrade() {
    }

    public Upgrade(final String name, final String desc, final int cost, final IMessage command, final int value) {
        setName(name);
        setDesc(desc);
        setCost(cost);
        setCommand(command);
        setValue(value);
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Gets the name of this upgrade.
     * @return	the name of this upgrade
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Gets the description of this upgrade.
     * @return	the description of this upgrade
     */
    public final String getDesc() {
        return this.desc;
    }

    /**
     * Gets the cost of this upgrade.
     * @return	the cost of this upgrade
     */
    public final int getCost() {
        return this.cost;
    }

    /**
     * Gets the command this upgrade executes.
     * @return	the command of this upgrade
     */
    public final IMessage getCommand() {
        return this.command;
    }

    /**
     * Gets the value of this upgrade.
     * @return	the value of this upgrade
     */
    public final int getValue() {
        return this.value;
    }

    
    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    /**
     * Gets the name of this upgrade.
     * @param name	the name of this upgrade
     */
    public final void setName(final String name) {
        this.name = (name != null) ? name : "";
    }

    /**
     * Sets the cost of this upgrade.
     * @param cost the cost of this upgrade
     */
    public final void setCost(final int cost) {
        this.cost = (cost >= 0 ? cost : 0);
    }

    /**
     * Sets the description of this upgrade.
     * @param desc the description of this upgrade
     */
    public final void setDesc(final String desc) {
        this.desc = (desc != null ? desc : "N/A");
    }

    /**
     * Sets the command that is executed when this upgrade is purchased.
     * @param command	the command to execute
     */
    public final void setCommand(final IMessage command) {
        this.command = command;
    }

    /**
     * Sets the value of this upgrade.
     * @param value	the value of this upgrade
     */
    public final void setValue(final int value) {
        this.value = value;
    }
}