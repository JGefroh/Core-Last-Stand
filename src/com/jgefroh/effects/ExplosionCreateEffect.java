package com.jgefroh.effects;

import java.util.HashMap;
import java.util.Map;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IPayload;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_DETONATE;


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
		Map<IPayload, String> parameters = new HashMap<IPayload, String>();
		//Detonate is handled by the ExplosionSystem.
		if(source.getName().equals("EXPLOSIVE"))
		{
			parameters.put(COMMAND_DETONATE.ENTITY_ID, source.getID());
			core.send(DefaultMessage.COMMAND_DETONATE, parameters);
		}
		else
		{
			parameters.put(COMMAND_DETONATE.ENTITY_ID, target.getID());
			core.send(DefaultMessage.COMMAND_DETONATE, parameters);
		}
	}
}
