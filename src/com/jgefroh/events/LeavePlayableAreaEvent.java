package com.jgefroh.events;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class LeavePlayableAreaEvent implements IEvent
{
	private final String EVENT = "LEAVE_PLAYABLE";
	private Core core;
	
	public LeavePlayableAreaEvent(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT))
		{
					return true;
		}
		return false;
	}
	public void execute(final IEntity entity, final IEntity target)
	{		
		TransformComponent tc = entity.getComponent(TransformComponent.class);
		if(tc.getXPos()+tc.getWidth()<=0)
		{
			tc.setXPos(1680);
		}
		else if(tc.getXPos()-tc.getWidth()>=1680)
		{
			tc.setXPos(0);
		}
		
		if(tc.getYPos()+tc.getHeight()<=0)
		{
			tc.setYPos(1050);
		}
		else if(tc.getYPos()-tc.getHeight()>=1050)
		{
			tc.setYPos(0);
		}
	}
}
