package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import net.glowstone.entity.AttributeManager.Property;

import java.util.Map;

public final class EntityPropertyMessage implements Message
{
	private final int id;
	private final Map<String, Property> properties;

	public EntityPropertyMessage( int id, Map<String, Property> properties )
	{
		this.id = id;
		this.properties = properties;
	}

	public int getId()
	{
		return id;
	}

	public Map<String, Property> getProperties()
	{
		return properties;
	}
}
