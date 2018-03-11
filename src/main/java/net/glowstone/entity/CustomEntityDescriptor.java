package net.glowstone.entity;

import net.glowstone.io.entity.EntityStore;

import org.bukkit.plugin.Plugin;

public class CustomEntityDescriptor<T extends GlowEntity>
{
	private final Class<T> entityClass;
	private final String id;
	private final Plugin plugin;
	private final EntityStore<T> storage;
	private boolean summonable;

	public CustomEntityDescriptor( Class<T> entityClass, Plugin plugin, String id, EntityStore<T> storage )
	{
		this.entityClass = entityClass;
		this.plugin = plugin;
		this.id = id;
		this.storage = storage;
		summonable = true;
	}

	public Class<T> getEntityClass()
	{
		return entityClass;
	}

	public String getId()
	{
		return id;
	}

	public Plugin getPlugin()
	{
		return plugin;
	}

	public EntityStore<T> getStorage()
	{
		return storage;
	}

	public boolean isSummonable()
	{
		return summonable;
	}
}
