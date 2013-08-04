package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class ExplosionCreateEffect implements IEffect
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public ExplosionCreateEffect(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&("EXPLOSIVE".equals(source.getName())
				||"EXPLOSIVE".equals(target.getName())))
		{
			return true;
		}
		return false;
	}
	public void execute(final IEntity source, final IEntity target)
	{
		//Detonate is handled by the ExplosionSystem.
		if(source.getName().equals("EXPLOSIVE"))
		{
			core.send("DETONATE", source.getID(), target.getID());
		}
		else
		{
			core.send("DETONATE", target.getID(), source.getID());
		}
	}
}
