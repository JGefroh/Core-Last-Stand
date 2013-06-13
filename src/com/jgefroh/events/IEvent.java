package com.jgefroh.events;

import com.jgefroh.core.IEntity;

/**
 * Interface that describes an event. This does way too much.
 * @author Joseph Gefroh
 *
 */
public interface IEvent
{
	public boolean check(final String string, final IEntity source, final IEntity target);
	public void execute(final IEntity entity, final IEntity target);
}
