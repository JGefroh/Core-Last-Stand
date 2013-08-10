package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.messages.Message;


/**
 * @author Joseph Gefroh
 */
public class ExplosionHitEffect implements IEffect
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public ExplosionHitEffect(final Core core)
	{
		this.core = core;
	}
	public boolean check(final String event, final IEntity source, final IEntity target)
	{
		if(event.equals(EVENT)
				&&(source.getName().equals("EXPLOSION")
				||target.getName().equals("EXPLOSION")))
		{
			return true;
		}
		return false;
	}
	public void execute(final IEntity source, final IEntity target)
	{
		//Explosion contacts are handled by the ExplosionSystem.
		if(source.getName().equals("EXPLOSION"))
		{
			core.send(Message.EXPLOSION_CONTACT, source.getID(), target.getID());
		}
		else
		{
			core.send(Message.EXPLOSION_CONTACT, target.getID(), source.getID());
		}
	}
}
