package com.jgefroh.infopacks;

import com.jgefroh.components.HealthBarComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;

/**
 * @author Joseph Gefroh
 */
public class HealthBarInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private HealthBarComponent hbc;
	
	/**A component this InfoPack depends on.*/
	private HealthComponent hc;
	
	/**The transform component of the owner of the health bar*/
	private TransformComponent tc;
	
	/**The transform component of the health bar.*/
	private TransformComponent hbtc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public HealthBarInfoPack(final IEntity owner)
	{
		this.owner = owner;
		checkDirty();
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	@Override
	public boolean isDirty()
	{
		return this.isDirty;
	}
	
	@Override
	public boolean checkDirty()
	{
		if(owner.hasChanged())
		{
			hbc = owner.getComponent(HealthBarComponent.class);	
			tc = owner.getComponent(TransformComponent.class);
			hc = owner.getComponent(HealthComponent.class);
			
			if(hbc!=null)
			{//If the health bar component exists
				IEntity bar = hbc.getHealthBar();
				if(bar!=null)
				{
					//If the bar has been created...
					hbtc = bar.getComponent(TransformComponent.class);
				}
			}
			
			if(hbc==null||tc==null||hc==null||hbtc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	public IEntity getHealthBar()
	{
		return hbc.getHealthBar();
	}

	public double getOwnerXPos()
	{
		return tc.getXPos();
	}
	
	public double getOwnerYPos()
	{
		return tc.getYPos();
	}
	
	public int getHealth()
	{
		return hc.getCurHealth();
	}

	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	public void setHealthBar(final IEntity healthBar)
	{
		hbc.setHealthBar(healthBar);
	}
	
	public void setHealthBarXPos(final double xPos)
	{
		hbtc.setXPos(xPos);
	}
	
	public void setHealthBarYPos(final double yPos)
	{
		hbtc.setYPos(yPos);
	}
	
	public void setHealthBarWidth(final int width)
	{
		hbtc.setWidth(width);
	}
	
}
