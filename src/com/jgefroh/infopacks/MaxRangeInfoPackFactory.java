package com.jgefroh.infopacks;

import com.jgefroh.components.MaxRangeComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class MaxRangeInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(TransformComponent.class)!=null
				&&entity.getComponent(MaxRangeComponent.class)!=null)
		{
			return new MaxRangeInfoPack(entity);
		}
		return null;
	}

}
