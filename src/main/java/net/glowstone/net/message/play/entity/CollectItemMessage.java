package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

public final class CollectItemMessage implements Message
{
	private final int collector;
	private final int count;
	private final int id;

	public CollectItemMessage( int id, int collector, int count )
	{
		this.id = id;
		this.collector = collector;
		this.count = count;
	}

	public int getCollector()
	{
		return collector;
	}

	public int getCount()
	{
		return count;
	}

	public int getId()
	{
		return id;
	}
}
