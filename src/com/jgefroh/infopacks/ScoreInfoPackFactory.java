package com.jgefroh.infopacks;

import com.jgefroh.components.ScoreComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class ScoreInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(ScoreComponent.class)!=null)
		{
			return new ScoreInfoPack(entity);
		}
		return null;
	}

}
