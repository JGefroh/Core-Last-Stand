package com.jgefroh.infopacks;

import com.jgefroh.components.AnimationComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class AnimationInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(AnimationComponent.class)!=null
				&&entity.getComponent(RenderComponent.class)!=null)
		{
			return new AnimationInfoPack(entity);
		}
		return null;
	}

}
