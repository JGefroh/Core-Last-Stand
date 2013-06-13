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
public class HealthBarInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(HealthBarComponent.class)!=null
				&&entity.getComponent(HealthComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			IEntity bar 
				= entity.getComponent(HealthBarComponent.class).getHealthBar();
			if(bar!=null
					&&bar.getComponent(TransformComponent.class)!=null)
			{
				return new HealthBarInfoPack(entity);
			}
		}
		return null;
	}

}
