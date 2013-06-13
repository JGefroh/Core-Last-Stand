package com.jgefroh.components;

import java.util.HashMap;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.Animation;



/**
 * Contains data to keep track of animations for the entity.
 * @author Joseph Gefroh
 */
public class AnimationComponent implements IComponent
{
	//TODO: Make this less complicated.
	
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The ms time the animation was last updated.*/
	private long lastUpdateTime = 0;
	
	/**The name of the animation that is currently being played.*/
	private String currentAnimation;
	
	/**The current frame of the animation.*/
	private int currentFrame;
	
	/**Holds all of the animation definitions for this entity.*/
	private HashMap<String, Animation> animations;

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code Component}.
	 * @param entity	the owner of this component
	 */
	public AnimationComponent(final IEntity entity)
	{	
		setOwner(entity);
		init();
	}

	@Override
	public void init()
	{
		animations = new HashMap<String, Animation>();
	}
	
	
	//////////
	// METHODS
	//////////
	/**
	 * Check to see if the given animation name is a valid animation.
	 * @param animationName	the name of the animation to check
	 * @return	true if it is a valid animation, false otherwise
	 */
	public boolean isValidAnimation(final String animationName)
	{
		if(animations.get(animationName)!=null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Add an animation.
	 * @param name				the unique name of the animation
	 * @param spriteSequence	the sprite indexes that make up the animation
	 * @param interval			the time to wait between frames
	 */
	public void addAnimation(final String name, final int[] spriteSequence, 
								final long interval)
	{
		Animation animation = new Animation();
		animation.setName(name);
		animation.setSequence(spriteSequence);
		animation.setInterval(interval);
		animations.put(name, animation);
	}
	
	/**
	 * Add an animation.
	 * @param animation
	 */
	public void addAnimation(final Animation animation)
	{
		animations.put(animation.getName(), animation);
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Get the current frame of the animation.
	 * @return	the number of the current animation frame
	 */
	public int getCurrentFrame()
	{
		return this.currentFrame;
	}
	
	/**
	 * Get the time the component was last updated.
	 * @return	the time the component was last updated, in ms
	 */
	public long getLastUpdateTime()
	{
		return this.lastUpdateTime;
	}
	
	/**
	 * Get the name of the current animation.
	 * @return	the name of the current animation
	 */
	public String getCurrentAnimation()
	{
		return this.currentAnimation;
	}	
	
	/**
	 * Get the time to wait before changing frames.
	 * @return	the time to wait between frames, in ms
	 */
	public long getInterval()
	{
		Animation animation = animations.get(currentAnimation);
		if(animation!=null)
		{
			return animation.getInterval();
		}
		else
		{
			return -1;
		}
	}
	
	public int getAnimationSprite()
	{
		Animation animation = animations.get(currentAnimation);
		if(animation!=null)
		{
			return animation.getAnimationSprite(currentFrame);
		}
		else
		{
			return -1;
		}
	}
	
	public int getNumberOfFrames()
	{
		Animation animation = animations.get(currentAnimation);
		if(animation!=null)
		{
			return animation.getNumberOfFrames();
		}
		else
		{
			return -1;
		}
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	/**
	 * Set the current frame of the animation.
	 * @param currentFrame	the current frame number of the animation
	 */
	public void setCurrentFrame(final int currentFrame)
	{
		this.currentFrame = currentFrame;
	}
	
	/**
	 * set the time the animation was last updated
	 * @param lastUpdateTime the time the animation was last updated, in ms
	 */
	public void setLastUpdateTime(final long lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public void setCurrentAnimation(final String currentAnimation)
	{
		this.currentAnimation = currentAnimation;
	}
}
