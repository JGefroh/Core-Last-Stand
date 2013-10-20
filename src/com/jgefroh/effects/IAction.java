package com.jgefroh.effects;

import com.jgefroh.core.Core;
import com.jgefroh.core.IEntity;

/**
 * Interface that describes an action. This does way too much.
 * @author Joseph Gefroh
 *
 */
public interface IAction {
	void execute(final Core core);
	void setTarget(final String entityID);
}
