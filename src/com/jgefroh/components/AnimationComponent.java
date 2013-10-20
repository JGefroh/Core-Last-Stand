package com.jgefroh.components;

import java.util.HashMap;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.data.Animation;


/**
 * Contains data to keep track of animations for the entity.
 * @author Joseph Gefroh
 */
public class AnimationComponent extends AbstractComponent {

    //////////////////////////////////////////////////
    // Fields
    //////////////////////////////////////////////////
    
    
    /**The ms time the animation was last updated.*/
    private long lastUpdateTime = 0;

    /**The name of the animation that is currently being played.*/
    private String currentAnimation;

    /**The current frame of the animation.*/
    private int currentFrame;

    /**Holds all of the animation definitions for this entity.*/
    private HashMap<String, Animation> animations;

    
    //////////////////////////////////////////////////
    // Constructors
    //////////////////////////////////////////////////
    
    /**
     * Creates a new instance of this {@code Component}.
     */
    public AnimationComponent() {
        init();
    }

    public void init() {
        animations = new HashMap<String, Animation>();
    }

    
    //////////////////////////////////////////////////
    // Methods
    //////////////////////////////////////////////////
    
    /**
     * Checks to see if the given animation name is a valid animation.
     * @param animationName	the name of the animation to check
     * @return	true if it is a valid animation, false otherwise
     */
    public boolean isValidAnimation(final String animationName) {
        if (animations.get(animationName) != null) {
            return true;
        }
        return false;
    }

    /**
     * Adds an animation.
     * @param name				the unique name of the animation
     * @param spriteSequence	the sprite indexes that make up the animation
     * @param interval			the time to wait between frames
     */
    public void addAnimation(final String name, final int[] spriteSequence, final long interval) {
        Animation animation = new Animation();
        animation.setName(name);
        animation.setSequence(spriteSequence);
        animation.setInterval(interval);
        animations.put(name, animation);
    }

    /**
     * Adds an animation.
     * @param animation
     */
    public void addAnimation(final Animation animation) {
        animations.put(animation.getName(), animation);
    }


    //////////////////////////////////////////////////
    // Getters
    //////////////////////////////////////////////////
    
    /**
     * Gets the current frame of the animation.
     * @return	the number of the current animation frame
     */
    public int getCurrentFrame() {
        return this.currentFrame;
    }

    /**
     * Gets the time the component was last updated.
     * @return	the time the component was last updated, in ms
     */
    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    /**
     * Gets the name of the current animation.
     * @return	the name of the current animation
     */
    public String getCurrentAnimation() {
        return this.currentAnimation;
    }

    /**
     * Gets the time to wait before changing frames.
     * @return	the time to wait between frames, in ms
     */
    public long getInterval() {
        Animation animation = animations.get(currentAnimation);
        if (animation != null) {
            return animation.getInterval();
        }
        else {
            return -1;
        }
    }

    /**
     * Gets the sprite index of the current animation frame.
     * @return	the sprite index if an animation is playing; null otherwise
     */
    public int getAnimationSprite() {
        Animation animation = animations.get(currentAnimation);
        if (animation != null) {
            return animation.getAnimationSprite(currentFrame);
        }
        else {
            return -1;
        }
    }

    /**
     * Gets the number of frames in the current animation.
     * @return	the number of frames; -1 if no animation is playing
     */
    public int getNumberOfFrames() {
        Animation animation = animations.get(currentAnimation);
        if (animation != null) {
            return animation.getNumberOfFrames();
        }
        else {
            return -1;
        }
    }

    //////////////////////////////////////////////////
    // Setters
    //////////////////////////////////////////////////
    
    /**
     * Sets the current frame of the animation.
     * @param currentFrame	the current frame number of the animation
     */
    public void setCurrentFrame(final int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Sets the time the animation was last updated
     * @param lastUpdateTime the time the animation was last updated, in ms
     */
    public void setLastUpdateTime(final long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * Sets the current animation.
     * @param currentAnimation	the name of the animation to set as current
     */
    public void setCurrentAnimation(final String currentAnimation) {
        this.currentAnimation = currentAnimation;
    }
}
