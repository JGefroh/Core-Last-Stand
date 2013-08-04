package com.jgefroh.components;

import com.jgefroh.core.AbstractComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data necessary for AI logic to control the owning entity.
 * 
 * 
 * Date: 17JUN13
 * @author Joseph Gefroh
 */
public class AIComponent extends AbstractComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The probability that the AI will attack in a given turn.*/	//TODO: fix
	private double attackChance;
	
	/**The type of AI.*/ //TODO: Implement
	private String aiType;
	
	/**Flag: Indicates whether the AI is in range of its target.*/
	private boolean isInRangeOfTarget;
	
	/**Flag: Indicates whether the AI wants to fire its weapon.*/
	private boolean intendsToFire;
	
	/**Flag: Indicates whether the AI should be processed normally.*/
	private boolean isActive;
	
	
	//////////
	// INIT
	//////////
	public AIComponent()
	{	
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
	/**
	 * Gets the type of AI.
	 * @return	the type of AI
	 */
	public String getAIType()
	{//TODO: Scrap
		return this.aiType;
	}

	/**
	 * Gets the probability that the AI will attack.
	 * @return	the probability where 1 is always attack and 0 is never attack
	 */
	public double getAttackChance()
	{
		return this.attackChance;
	}
	
	/**
	 * Gets the flag that indicates the AI is active and working.
	 * @return	true if the AI is active; false otherwise
	 */
	public boolean isActive()
	{
		return this.isActive;
	}
	
	/**
	 * Gets the flag that indicates whether the AI is in range of its target.
	 * @return	true if it is in range; false otherwise
	 */
	public boolean isInRangeOfTarget()
	{
		return this.isInRangeOfTarget;
	}

	
	//////////
	// SETTERS
	//////////
	/**
	 * Sets the probability the AI will attack per turn.
	 * @param attackChance	the probability where 1 is always attack and 
	 * 						0 is never attack
	 */
	public void setAttackChance(final double attackChance)
	{
		this.attackChance = attackChance;
	}
	
	/**
	 * Sets the type of AI.
	 * 
	 * 
	 * If the AI type passed is null, the AI will default to a blank String.
	 * @param aiType	the type of AI
	 */
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
	
	/**
	 * Sets the flag that indicates the entity is in range of its target.
	 * @param isInRangeOfTarget	true if in range; false otherwise
	 */
	public void setInRangeOfTarget(final boolean isInRangeOfTarget)
	{
		this.isInRangeOfTarget = isInRangeOfTarget;
	}
	
	/**
	 * Sets the flag that indicates the AI should be processed by the AI System.
	 * @param isActive	true if the AI is active; false otherwise
	 */
	public void setActive(final boolean isActive)
	{
		this.isActive = isActive;
	}
}
