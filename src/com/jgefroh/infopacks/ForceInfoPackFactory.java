package com.jgefroh.infopacks;

import com.jgefroh.components.ForceGeneratorComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class ForceInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(VelocityComponent.class)!=null
				&&entity.getComponent(ForceGeneratorComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new ForceInfoPack(entity);
		}
		return null;
	}

}
