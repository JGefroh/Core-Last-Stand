package com.jgefroh.infopacks;

import com.jgefroh.components.GUIComponent;
import com.jgefroh.components.GUITextComponent;
import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class GUITextInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(GUIComponent.class)!=null
				&&entity.getComponent(GUITextComponent.class)!=null
				&&entity.getComponent(RenderComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new GUITextInfoPack(entity);
		}
		return null;
	}

}
