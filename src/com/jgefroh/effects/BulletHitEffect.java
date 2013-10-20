package com.jgefroh.effects;

import java.util.HashMap;
import java.util.Map;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IPayload;
import com.jgefroh.messages.DefaultMessage;
import com.jgefroh.messages.DefaultMessage.COMMAND_DAMAGE;


/**
 * @author Joseph Gefroh
 */
public class BulletHitEffect implements IEffect
{
	private final String EVENT = "COLLISION";
	private Core core;
	
	public BulletHitEffect(final Core core) {
		this.core = core;
	}

	public boolean check(final String event, final IEntity source,
			final IEntity target) {
		if (event.equals(EVENT)
				&& (source.getName().equals("BULLET") 
						|| target.getName().equals("BULLET"))) {
			return true;
		}
		return false;
	}

	public void execute(final IEntity source, final IEntity target) {
		Map<IPayload, String> parameters = new HashMap<IPayload, String>();

		// Bullet damage is handled by the DamageSystem.
		if (source.getName().equals("BULLET")) {
			parameters.put(COMMAND_DAMAGE.SOURCE_ENTITY_ID, source.getID());
			parameters.put(COMMAND_DAMAGE.VICTIM_ENTITY_ID, target.getID());
			core.send(DefaultMessage.COMMAND_DAMAGE, parameters);
			core.removeEntity(source);
		} else {
			parameters.put(COMMAND_DAMAGE.VICTIM_ENTITY_ID, source.getID());
			parameters.put(COMMAND_DAMAGE.SOURCE_ENTITY_ID, target.getID());
			core.send(DefaultMessage.COMMAND_DAMAGE, parameters);
			core.removeEntity(target);
		}
	}
}
