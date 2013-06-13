package com.jgefroh.data;


/**
 * Contains data of a single animation for use by the AnimationComponent.
 * @author Joseph Gefroh
 */
public class Animation
{
	//////////
	// DATA
	//////////
	/**The minimum time to wait between updates.*/
	private long	interval;
	
	/**The sequence of sprites in the animation.*/
	private int[] 	sequence;
	
	/**The unique name of the animation.*/
	private String 	name;
	

	//////////
	// INIT
	//////////
	public Animation()
	{
		init();
	}
	
	public void init()
	{
		name = "";
		sequence = new int[0];
		interval = 0;
	}
	

	//////////
	// GETTERS
	//////////
	/**
	 * Get the sequence of sprite indexes that make up this animation.
	 * @return	the sequence of sprite indexes
	 */
	public int[] getSequence()
	{
		if(sequence!=null)
		{			
			return this.sequence;
		}
		else
		{
			return new int[0];
		}
	}
	
	/**
	 * Get the name of this animation.
	 * @return	the name of this animation
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Get the time to wait between updates.
	 * @return	the time to wait between frame updates, in ms.
	 */
	public long getInterval()
	{
		return this.interval;
	}
	
	/**
	 * Get the number of frames in this animation.
	 * @return	the number of frames in this animation.
	 */
	public int getNumberOfFrames()
	{
		return this.sequence.length;
	}
	
	/**
	 * Get the sprite id associated with the given frame of the animation.
	 * Returns -1 if the frame is out of bounds.
	 * @param frame	the current frame of the animation
	 * @return	the sprite id associated with the frame of the animation
	 */
	public int getAnimationSprite(final int frame)
	{
		if(frame>=0&&frame<this.sequence.length)
		{			
			return this.sequence[frame];
		}
		else
		{
			return -1;
		}
	}
	//////////
	// SETTERS
	//////////
	/**
	 * Set the name of this animation.
	 * @param name	a unique name
	 */
	public void setName(final String name)
	{
		if(name!=null)
		{			
			this.name = name;
		}
		else
		{
			this.name = "";
		}
	}
	
	/**
	 * Set the animation sequence.
	 * @param sequence	the sequence of sprite indexes
	 */
	public void setSequence(final int[] sequence)
	{
		if(sequence!=null)
		{			
			this.sequence = sequence;
		}
		else
		{
			this.sequence = new int[0];
		}
	}
	
	/**
	 * Set the minimum time to wait between frame updates.
	 * @param interval	the time to wait between updates, in ms.
	 */
	public void setInterval(final long interval)
	{
		this.interval = interval;
	}
}
