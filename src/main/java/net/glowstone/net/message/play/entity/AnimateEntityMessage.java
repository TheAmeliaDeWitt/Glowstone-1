package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

public final class AnimateEntityMessage implements Message
{
	private final int animation;
	private final int id;

	public AnimateEntityMessage( int id, int animation )
	{
		this.id = id;
		this.animation = animation;
	}

	public int getAnimation()
	{
		return animation;
	}

	public int getId()
	{
		return id;
	}
}
