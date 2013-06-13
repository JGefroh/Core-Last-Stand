package com.jgefroh.input;



import java.util.HashMap;

/**
 * An implementation of the IBindSystem interface that handles input binds.
 * @author Joseph Gefroh
 * @since 23FEB13
 */
public class BindMap implements IBindMap
{
	HashMap<Integer, String> bindsOnHold;
	HashMap<Integer, String> bindsOnPress;
	HashMap<Integer, String> bindsOnRelease;
	
	/**
	 * Create a default bind system with no binds.
	 */
	public BindMap()
	{
		bindsOnHold = new HashMap<Integer, String>();
		bindsOnPress = new HashMap<Integer, String>();
		bindsOnRelease = new HashMap<Integer, String>();
	}
	
	@Override	
	public String getCommandOnHold(final int keyCode)
	{
		return bindsOnHold.get(keyCode);
	}
	
	@Override	
	public String getCommandOnPress(final int keyCode)
	{
		return bindsOnPress.get(keyCode);
	}
	
	@Override
	public String getCommandOnRelease(final int keyCode)
	{
		return bindsOnRelease.get(keyCode);
	}

	@Override
	public void bind(final int keyCode, final String command, final int type)
	{
		switch(type)
		{
			case IBindMap.PRESS:
				bindsOnPress.put(keyCode, command);
				break;
			case IBindMap.HOLD:
				bindsOnHold.put(keyCode, command);
				break;
			case IBindMap.RELEASE:
				bindsOnRelease.put(keyCode, command);
				break;
			case IBindMap.ALL:
				bindsOnPress.put(keyCode,  command);
				bindsOnRelease.put(keyCode,  command);
				bindsOnHold.put(keyCode,  command);
				break;
		}
	}

	@Override
	public void unbind(final int keyCode, final int type)
	{
		switch(type)
		{
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
