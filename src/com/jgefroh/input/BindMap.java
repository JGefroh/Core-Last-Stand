package com.jgefroh.input;



import java.util.HashMap;

import com.jgefroh.effects.IAction;

/**
 * An implementation of the IBindSystem interface that handles input binds.
 * 
 * @author Joseph Gefroh
 */
public class BindMap implements IBindMap {
	HashMap<Integer, IAction> bindsOnHold;
	HashMap<Integer, IAction> bindsOnPress;
	HashMap<Integer, IAction> bindsOnRelease;

	/**
	 * Create a default bind system with no binds.
	 */
	public BindMap() {
		bindsOnHold = new HashMap<Integer, IAction>();
		bindsOnPress = new HashMap<Integer, IAction>();
		bindsOnRelease = new HashMap<Integer, IAction>();
	}

	@Override
	public IAction getCommandOnHold(final int keyCode) {
		return bindsOnHold.get(keyCode);
	}

	@Override
	public IAction getCommandOnPress(final int keyCode) {
		return bindsOnPress.get(keyCode);
	}

	@Override
	public IAction getCommandOnRelease(final int keyCode) {
		return bindsOnRelease.get(keyCode);
	}

	@Override
	public void bind(final int keyCode, final IAction action, final int type) {
		switch (type) {
			case IBindMap.PRESS:
				bindsOnPress.put(keyCode, action);
				break;
			case IBindMap.HOLD:
				bindsOnHold.put(keyCode, action);
				break;
			case IBindMap.RELEASE:
				bindsOnRelease.put(keyCode, action);
				break;
			case IBindMap.ALL:
				bindsOnPress.put(keyCode, action);
				bindsOnRelease.put(keyCode, action);
				bindsOnHold.put(keyCode, action);
				break;
		}
	}

	@Override
	public void unbind(final int keyCode, final int type) {
		switch (type) {
			case IBindMap.PRESS:
				bindsOnPress.remove(keyCode);
				break;
			case IBindMap.HOLD:
				bindsOnHold.remove(keyCode);
				break;
			case IBindMap.RELEASE:
				bindsOnRelease.remove(keyCode);
				break;
		}
	}

}
