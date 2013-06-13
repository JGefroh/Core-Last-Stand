package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * Interface that describes an event.
 * @author Joseph Gefroh
 *
 */
public class BulletHitEffect implements IEffect
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public BulletHitEffect(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&(source.getName().equals("BULLET")
				||target.getName().equals("BULLET")))
		{
			return true;
		}
		return false;
	}
	public void execute(final IEntity source, final IEntity target)
	{
		if(source.getName().equals("BULLET"))
		{
			core.send("BULLET_HIT", source.getID(), target.getID());
		}
		else
		{
			core.send("BULLET_HIT", target.getID(), source.getID());
		}
	}
}
