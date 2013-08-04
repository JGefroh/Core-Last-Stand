package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;



/**
 * Contains data to keep track of how much an entity is worth (score)
 * @author Joseph Gefroh
 */
public class ScoreComponent extends AbstractComponent
{
	//TODO: Make this less complicated.
	
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The amount of points this entity is worth.*/
	private int score;
	//////////
	// INIT
	//////////
	/**
	 * Create a new animation component.
	 */
	public ScoreComponent()
	{	
		init();
	}

	@Override
	public void init()
	{
		score = 0;
	}
	
	
	//////////
	// METHODS
	//////////
	
	
	//////////
	// GETTERS
	//////////
	public int getScore()
	{
		return this.score;
	}
	
	//////////
	// SETTERS
	//////////
	
	public void setScore(final int score)
	{
		this.score = score;
	}
}
