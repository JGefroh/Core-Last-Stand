package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
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
		//Bullet damage is handled by the DamageSystem.
		if(source.getName().equals("BULLET"))
		{
			core.send("DAMAGE", source.getID(), target.getID());
			core.removeEntity(source);
		}
		else
		{
			core.send("DAMAGE", target.getID(), source.getID());
			core.removeEntity(target);
		}
	}
}
