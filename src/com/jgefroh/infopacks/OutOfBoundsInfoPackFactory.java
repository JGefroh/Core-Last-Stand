package com.jgefroh.infopacks;

import com.jgefroh.components.OutOfBoundsComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class OutOfBoundsInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(OutOfBoundsComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new OutOfBoundsInfoPack(entity);
		}
		return null;
	}

}
