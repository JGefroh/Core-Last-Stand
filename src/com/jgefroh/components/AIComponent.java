package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;

/**
 * @author Joseph Gefroh
 */
public class AIComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////

    /**The probability that the AI will attack in a given turn.*/
    private double attackChance;

    /**The type of AI.*/
    private String aiType = "";

    /**Flag: Indicates whether the AI is in range of its target.*/
    private boolean isInRangeOfTarget;

    /**Flag: Indicates whether the AI should be processed normally.*/
    private boolean isActive;


    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////

    /**
     * Creates a new instance of this {@code Component}.
     */
    public AIComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////

    /**
     * Gets the type of AI.
     * @return	the type of AI
     */
    public String getAIType() {// TODO: Scrap
        return this.aiType;
    }

    /**
     * Gets the probability that the AI will attack.
     * @return	the probability where 1 is always attack and 0 is never attack
     */
    public double getAttackChance() {
        return this.attackChance;
    }

    /**
     * Gets the flag that indicates the AI is active and working.
     * @return	true if the AI is active; false otherwise
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Gets the flag that indicates whether the AI is in range of its target.
     * @return	true if it is in range; false otherwise
     */
    public boolean isInRangeOfTarget() {
        return this.isInRangeOfTarget;
    }


    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////

    /**
     * Sets the probability the AI will attack per turn.
     * @param attackChance	the probability where 1 is always attack and 
     * 						0 is never attack
     */
    public void setAttackChance(final double attackChance) {
        this.attackChance = attackChance;
    }

    /**
     * Sets the type of AI.
     * 
     * 
     * If the AI type passed is null, the AI will default to a blank String.
     * @param aiType	the type of AI
     */
    public void setAIType(final String aiType) {
        if (aiType == null) {
            this.aiType = "";
        }
        else {
            this.aiType = aiType;
        }
    }

    /**
     * Sets the flag that indicates the entity is in range of its target.
     * @param isInRangeOfTarget	true if in range; false otherwise
     */
    public void setInRangeOfTarget(final boolean isInRangeOfTarget) {
        this.isInRangeOfTarget = isInRangeOfTarget;
    }

    /**
     * Sets the flag that indicates the AI should be processed by the AI System.
     * @param isActive	true if the AI is active; false otherwise
     */
    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }
}
