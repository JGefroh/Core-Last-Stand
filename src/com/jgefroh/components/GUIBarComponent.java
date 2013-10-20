package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data to identify GUI elements.
 * @author Joseph Gefroh
 */
public class GUIBarComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////
    public static final int LEFT = 0;
    public static final int RIGHT = 2;
    public static final int UP = 1;
    public static final int DOWN = 4;
    public static final int MIDDLEH = 5;
    public static final int MIDDLEV = 6;

    /**The default X position of this GUI bar.*/
    private double defXPos;

    /**The default Y position of this GUI bar.*/
    private double defYPos;

    /**The default height of this GUI bar.*/
    private double defHeight;

    /**The default width of this GUI bar.*/
    private double defWidth;

    /**The current value this bar represents.*/
    private double curValue;

    /**The maximum value this bar can represent.*/
    private double maxValue;

    /**The maximum width of this GUI bar.*/
    private double maxWidth;

    /**The maximum height of this GUI bar.*/
    private double maxHeight;

    /**The shrink direction.*/
    private int shrinkDir;

    
    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    public GUIBarComponent() {
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Gets the default height of this GUI element.
     * @return	the default height
     */
    public double getDefHeight() {
        return this.defHeight;
    }

    /**
     * Gets the default width of this GUI element.
     * @return	the default width
     */
    public double getDefWidth() {
        return this.defWidth;
    }

    /**
     * Gets the default X position of this GUI element.
     * @return	the default X position
     */
    public double getDefXPos() {
        return this.defXPos;
    }

    /**
     * Gets the default Y position of this GUI element.
     * @return the default Y position
     */
    public double getDefYPos() {
        return this.defYPos;
    }

    /**
     * Gets the current value this bar represents.
     * @return	the current value
     */
    public double getCurValue() {
        return this.curValue;
    }

    /**
     * Gets the maximum value this bar represents.
     * @return	the maximum value
     */
    public double getMaxValue() {
        return this.maxValue;
    }

    /**
     * Gets the maximum width of this bar.
     * @return	the maximum width
     */
    public double getMaxWidth() {
        return this.maxWidth;
    }

    /**
     * Gets the maximum height of this bar
     * @return	the maximum height
     */
    public double getMaxHeight() {
        return this.maxHeight;
    }

    public int getShrinkDir() {
        return this.shrinkDir;
    }

    public boolean left() {
        return (this.shrinkDir == GUIBarComponent.LEFT) ? true : false;
    }

    public boolean right() {
        return (this.shrinkDir == GUIBarComponent.RIGHT) ? true : false;
    }

    public boolean up() {
        return (this.shrinkDir == GUIBarComponent.UP) ? true : false;
    }

    public boolean down() {
        return (this.shrinkDir == GUIBarComponent.DOWN) ? true : false;
    }

    public boolean collapseMiddleH() {
        return (this.shrinkDir == GUIBarComponent.MIDDLEH) ? true : false;
    }

    public boolean collapseMiddleV() {
        return (this.shrinkDir == GUIBarComponent.MIDDLEV) ? true : false;
    }

    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    public void setDefXPos(final double defXPos) {
        this.defXPos = defXPos;
    }

    public void setDefYPos(final double defYPos) {
        this.defYPos = defYPos;
    }

    public void setDefWidth(final double defWidth) {
        this.defWidth = defWidth;
    }

    public void setDefHeight(final double defHeight) {
        this.defHeight = defHeight;
    }

    public void setMaxValue(final double maxValue) {
        this.maxValue = (maxValue > 0) ? maxValue : 1;
    }

    public void setCurValue(final double curValue) {
        this.curValue = curValue;
    }

    public void setMaxWidth(final double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setMaxHeight(final double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public void setShrinkDir(final int shrinkDir) {
        this.shrinkDir = shrinkDir;
    }
}
