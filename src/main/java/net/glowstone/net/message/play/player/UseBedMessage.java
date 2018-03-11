package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public final class UseBedMessage implements Message
{
	private final int id;
	private final int x;
	private final int y;
	private final int z;

	public UseBedMessage( int id, int x, int y, int z )
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getId()
	{
		return id;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getZ()
	{
		return z;
	}
}
