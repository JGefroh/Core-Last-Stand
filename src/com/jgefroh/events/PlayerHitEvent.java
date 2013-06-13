package com.jgefroh.events;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class PlayerHitEvent implements IEvent
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public PlayerHitEvent(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&source.getName().equals("player")
				&&target.getName().equals("asteroid"))
		{
					return true;
		}
		return false;
	}
	public void execute(final IEntity entity, final IEntity target)
	{
		System.out.println("~GAME OVER~ You're dead.");
		System.exit(0);
		core.removeEntity(entity);
	}
}
