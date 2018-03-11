package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class PingMessage implements Message
{
	private final long pingId;

	public PingMessage( long pingId )
	{
		this.pingId = pingId;
	}

	public long getPingId()
	{
		return pingId;
	}
}
