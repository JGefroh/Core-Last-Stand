package com.jgefroh.infopacks;

import com.jgefroh.components.DecayComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class DecayInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(DecayComponent.class)!=null)
		{
			return new DecayInfoPack(entity);
		}
		return null;
	}

}
