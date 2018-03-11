package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class TimeMessage implements Message
{
	private final long time;
	private final long worldAge;

	public TimeMessage( long worldAge, long time )
	{
		this.worldAge = worldAge;
		this.time = time;
	}

	public long getTime()
	{
		return time;
	}

	public long getWorldAge()
	{
		return worldAge;
	}
}
