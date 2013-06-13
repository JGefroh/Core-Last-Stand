package com.jgefroh.infopacks;

import com.jgefroh.components.HealthBarComponent;
import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class HealthInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(HealthComponent.class)!=null)
		{
			return new HealthInfoPack(entity);
		}
		return null;
	}

}
