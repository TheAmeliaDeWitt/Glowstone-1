package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import java.util.UUID;

public final class SpawnPaintingMessage implements Message
{
	private final int facing;
	private final int id;
	private final String title;
	private final UUID uniqueId;
	private final int x;
	private final int y;
	private final int z;

	public SpawnPaintingMessage( int id, UUID uniqueId, String title, int x, int y, int z, int facing )
	{
		this.id = id;
		this.uniqueId = uniqueId;
		this.title = title;
		this.x = x;
		this.y = y;
		this.z = z;
		this.facing = facing;
	}

	public int getFacing()
	{
		return facing;
	}

	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public UUID getUniqueId()
	{
		return uniqueId;
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
