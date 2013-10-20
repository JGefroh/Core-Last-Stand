package com.jgefroh.components;

import java.util.ArrayList;
import java.util.List;

import com.jgefroh.core.AbstractComponent;

/**
 * Contains data to identify GUI elements.
 * @author Joseph Gefroh
 */
public class GUITextComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////
    private List<String> childrenIDs = new ArrayList<String>();

    private int numCharsPerLine;
    private int numLines;
    private int charWidth;
    private int charHeight;
    private char defaultChar;
    private String text;

    
    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    public GUITextComponent() {
    }

    
    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    public int getNumLines() {
        return this.numLines;
    }

    public int getNumCharsPerLine() {
        return this.numCharsPerLine;
    }

    public int getCharWidth() {
        return this.charWidth;
    }

    public int getCharHeight() {
        return this.charHeight;
    }

    public List<String> getChildren() {
        return this.childrenIDs;
    }

    public char getDefaultChar() {
        return this.defaultChar;
    }

    public String getText() {
        return this.text;
    }

    
    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////

    public void setChildren(final List<String> children) {
        this.childrenIDs = (children == null) ? new ArrayList<String>() : children;
    }

    public void setNumLines(final int numLines) {
        this.numLines = numLines;
    }

    public void setNumCharsPerLine(final int numCharsPerLine) {
        this.numCharsPerLine = numCharsPerLine;
    }

    public void setCharWidth(final int charWidth) {
        this.charWidth = charWidth;
    }

    public void setCharHeight(final int charHeight) {
        this.charHeight = charHeight;
    }

    public void setDefaultChar(final char defaultChar) {
        this.defaultChar = defaultChar;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
