package com.jgefroh.effects;

import com.jgefroh.core.IEntity;

/**
 * Interface that describes an event. This does way too much.
 * @author Joseph Gefroh
 *
 */
public interface IEffect
{
	enum Event{WAVE_WIN};
	public boolean check(final String string, final IEntity source, final IEntity target);
	public void execute(final IEntity entity, final IEntity target);
}
