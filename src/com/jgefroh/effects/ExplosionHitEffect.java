package com.jgefroh.effects;

import java.util.HashMap;
import java.util.Map;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IPayload;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.EVENT_EXPLOSION_CONTACT;


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

	public void execute(final IEntity source, final IEntity target) {
		Map<IPayload, String> parameters = new HashMap<IPayload, String>();

		//Explosion contacts are handled by the ExplosionSystem.
		if (source.getName().equals("EXPLOSION")) {
			parameters.put(EVENT_EXPLOSION_CONTACT.SOURCE_ID, source.getID());
			parameters.put(EVENT_EXPLOSION_CONTACT.VICTIM_ID, target.getID());
			core.send(DefaultMessage.EVENT_EXPLOSION_CONTACT, parameters);
		} 
		else {

			parameters.put(EVENT_EXPLOSION_CONTACT.VICTIM_ID, source.getID());
			parameters.put(EVENT_EXPLOSION_CONTACT.SOURCE_ID, target.getID());
			core.send(DefaultMessage.EVENT_EXPLOSION_CONTACT, parameters);
		}
	}
}
