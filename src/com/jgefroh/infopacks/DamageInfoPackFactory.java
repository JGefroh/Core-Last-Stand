package com.jgefroh.infopacks;

import com.jgefroh.components.DamageComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class DamageInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(DamageComponent.class)!=null)
		{
			return new DamageInfoPack(entity);
		}
		return null;
	}

}
