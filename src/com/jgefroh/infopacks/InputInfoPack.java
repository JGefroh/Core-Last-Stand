package com.jgefroh.infopacks;

import com.jgefroh.components.InputComponent;
import com.jgefroh.core.AbstractInfoPack;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

/**
 * Intended to be used by the InputSystem.
 * 
 * Controls access to the following components:
 * InputComponent
 * 
 * @author Joseph Gefroh
 */
public class InputInfoPack extends AbstractInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private InputComponent ic;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public InputInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	@Override
	public boolean checkDirty()
	{
		if(owner.hasChanged())
		{
			ic = owner.getComponent(InputComponent.class);	
			if(ic==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}

	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(InputComponent.class)!=null)
		{
			return new InputInfoPack(entity);
		}
		return null;
	}

	/**
	 * @see InputComponent#checkInterested(String)
	 */
	public boolean isInterested(final String command)
	{
		return ic.checkInterested(command);
	}

	
	//////////
	// SETTERS
	//////////
}