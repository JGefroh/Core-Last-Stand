package com.jgefroh.infopacks;

import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class WeaponInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(WeaponComponent.class)!=null)
		{
			return new WeaponInfoPack(entity);
		}
		return null;
	}

}
