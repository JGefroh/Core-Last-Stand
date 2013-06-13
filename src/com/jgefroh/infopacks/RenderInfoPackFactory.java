package com.jgefroh.infopacks;

import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class RenderInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(RenderComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new RenderInfoPack(entity);
		}
		return null;
	}

}
