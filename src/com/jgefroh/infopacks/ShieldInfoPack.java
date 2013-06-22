package com.jgefroh.infopacks;

import com.jgefroh.components.ShieldComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * @author Joseph Gefroh
 */
public class ShieldInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent stc;
	
	/**A component this InfoPack depends on.*/
	private ShieldComponent sc;
		
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public ShieldInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public boolean isDirty()
	{
		if(owner.hasChanged())
		{
			tc = owner.getComponent(TransformComponent.class);
			sc = owner.getComponent(ShieldComponent.class);			
			
			if(tc==null||sc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	public double getXPos()
	{
		return tc.getXPos();
	}
	
	public double getYPos()
	{
		return tc.getYPos();
	}
	
	public boolean isShieldActive()
	{
		return sc.isShieldActive();
	}
	
	public IEntity getShield()
	{
		return sc.getShield();
	}
	
	public int getShieldCur()
	{
		return sc.getShieldCur();
	}

	public int getShieldMin()
	{
		return sc.getShieldMin();
	}

	public int getShieldMax()
	{
		return sc.getShieldMax();
	}
	public int getShieldInc()
	{
		return sc.getShieldInc();
	}
	
	public int getShieldDec()
	{
		return sc.getShieldDec();
	}
	
	public long getShieldRechargeInterval()
	{
		return sc.getShieldRechargeInterval();
	}
	
	public long getShieldDrainInterval()
	{
		return sc.getShieldDrainInterval();
	}
	
	public long getShieldRechargeDelay()
	{
		return sc.getShieldRechargeDelay();
	}

	public long getShieldLastUsed()
	{
		return sc.getShieldLastUsed();
	}

	public long getShieldLastRecharged()
	{
		return sc.getShieldLastRecharged();
	}
	
	public long getShieldLastDrained()
	{
		return sc.getShieldLastDrained();
	}
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	public void setActive(final boolean isShieldActive)
	{
		sc.setShieldActive(isShieldActive);
	}
	
	public void setShield(final IEntity shield)
	{
		sc.setShield(shield);
		if(shield!=null)
		{			
			stc = shield.getComponent(TransformComponent.class);
		}
		else
		{
			stc = null;
		}
	}
	
	public void setShieldXPos(final double xPos)
	{
		if(stc!=null)
		{
			stc.setXPos(xPos);
		}
	}
	
	public void setShieldYPos(final double yPos)
	{
		if(stc!=null)
		{
			stc.setYPos(yPos);
		}
	}
	
	public void setShieldCur(final int shieldCur)
	{
		sc.setShieldCur(shieldCur);
	}

	public void setShieldMin(final int shieldMin)
	{
		sc.setShieldMin(shieldMin);
	}

	public void setShieldMax(final int shieldMax)
	{
		sc.setShieldMax(shieldMax);
	}
	
	public void setShieldInc(final int shieldInc)
	{
		sc.setShieldInc(shieldInc);
	}
	
	public void setShieldDec(final int shieldDec)
	{
		sc.setShieldDec(shieldDec);
	}

	public void setShieldLastUsed(final long shieldLastUsed)
	{
		sc.setShieldLastUsed(shieldLastUsed);
	}

	public void setShieldLastRecharged(final long shieldLastRecharged)
	{
		sc.setShieldLastRecharged(shieldLastRecharged);
	}

	public void setShieldLastDrained(final long shieldLastDrained)
	{
		sc.setShieldLastDrained(shieldLastDrained);
	}
}
