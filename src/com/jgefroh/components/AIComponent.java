package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Acts as a marker to indicate that the AI should control the owning entity.
 * @author Joseph Gefroh
 */
public class AIComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	private double attackChance;
	
	private String aiType;
	//////////
	// INIT
	//////////
	public AIComponent(final IEntity entity)
	{	
		setOwner(entity);
		init();
	}

	@Override
	public void init()
	{
		setAIType("");
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	public void setAttackChance(final double attackChance)
	{
		this.attackChance = attackChance;
	}
	
	public void setAIType(final String aiType)
	{
		if(aiType==null)
		{
			this.aiType = "";
		}
		else
		{			
			this.aiType = aiType;
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
	
	public double getAttackChance()
	{
		return this.attackChance;
	}
	
	public String getAIType()
	{
		return this.aiType;
	}

}
