package com.jgefroh.components;

import java.util.ArrayList;
import java.util.HashMap;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.data.Animation;



/**
 * Contains data to keep track of how much an entity is worth (score)
 * @author Joseph Gefroh
 */
public class ScoreComponent implements IComponent
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
	 * @param entity	the owner of this component
	 */
	public ScoreComponent(final IEntity entity)
	{	
		setOwner(entity);
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
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	public void setScore(final int score)
	{
		this.score = score;
	}
}
