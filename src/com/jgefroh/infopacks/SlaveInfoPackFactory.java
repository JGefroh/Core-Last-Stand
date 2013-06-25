package com.jgefroh.infopacks;

import com.jgefroh.components.SlaveComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class SlaveInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(SlaveComponent.class)!=null)
		{
			return new SlaveInfoPack(entity);
		}
		return null;
	}

}
