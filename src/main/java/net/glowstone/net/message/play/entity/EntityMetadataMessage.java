package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import net.glowstone.entity.meta.MetadataMap.Entry;

import java.util.List;

public final class EntityMetadataMessage implements Message
{
	private final List<Entry> entries;
	private final int id;

	public EntityMetadataMessage( int id, List<Entry> entries )
	{
		this.id = id;
		this.entries = entries;
	}

	public List<Entry> getEntries()
	{
		return entries;
	}

	public int getId()
	{
		return id;
	}
}
