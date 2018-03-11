package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import net.glowstone.entity.meta.MetadataMap.Entry;

import java.util.List;
import java.util.UUID;

public final class SpawnPlayerMessage implements Message
{
	private final int id;
	private final List<Entry> metadata;
	private final int pitch;
	private final int rotation;
	private final UUID uuid;
	private final double x;
	private final double y;
	private final double z;

	public List<Entry> getMetadata()
	{
		return metadata;
	}

	public int getPitch()
	{
		return pitch;
	}

	public int getRotation()
	{
		return rotation;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public SpawnPlayerMessage( int id, UUID uuid, double x, double y, double z, int rotation, int pitch, List<Entry> metadata )
	{
		this.id = id;
		this.uuid = uuid;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotation = rotation;
		this.pitch = pitch;
		this.metadata = metadata;
	}

	public int getId()
	{
		return id;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}
}
