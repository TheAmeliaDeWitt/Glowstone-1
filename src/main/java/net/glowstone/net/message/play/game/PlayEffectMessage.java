package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class PlayEffectMessage implements Message
{
	private final int data;
	private final int id;
	private final boolean ignoreDistance;
	private final int x;
	private final int y;
	private final int z;

	public PlayEffectMessage( int id, int x, int y, int z, int data, boolean ignoreDistance )
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.data = data;
		this.ignoreDistance = ignoreDistance;
	}

	public int getData()
	{
		return data;
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

	public boolean isIgnoreDistance()
	{
		return ignoreDistance;
	}
}
