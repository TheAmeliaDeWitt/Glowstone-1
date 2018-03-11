package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public final class ResourcePackStatusMessage implements Message
{
	private final int result;

	public ResourcePackStatusMessage( int result )
	{
		this.result = result;
	}

	public int getResult()
	{
		return result;
	}
}

