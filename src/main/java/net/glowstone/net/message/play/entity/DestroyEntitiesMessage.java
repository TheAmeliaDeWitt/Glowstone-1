package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import java.util.List;

public final class DestroyEntitiesMessage implements Message
{
	private final List<Integer> ids;

	public DestroyEntitiesMessage( List<Integer> ids )
	{
		this.ids = ids;
	}

	public List<Integer> getIds()
	{
		return ids;
	}
}
