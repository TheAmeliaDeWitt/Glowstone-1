package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

public final class EntityRemoveEffectMessage implements Message
{
	private final int effect;
	private final int id;

	public EntityRemoveEffectMessage( int id, int effect )
	{
		this.id = id;
		this.effect = effect;
	}

	public int getEffect()
	{
		return effect;
	}

	public int getId()
	{
		return id;
	}
}
