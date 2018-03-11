package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import net.glowstone.entity.meta.MetadataMap.Entry;
import net.glowstone.util.Position;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public final class SpawnMobMessage implements Message
{
	private final int headPitch;
	private final int id;
	private final List<Entry> metadata;
	private final int pitch;
	private final int rotation;
	private final int type;
	private final UUID uuid; //TODO: Handle UUID
	private final int velX;
	private final int velY;
	private final int velZ;
	private final double x;
	private final double y;
	private final double z;

	/**
	 * Creates an instance based on a {@link Location}, with headPitch equal to pitch and with zero
	 * velocity.
	 *
	 * @param id       the mob's ID within the world
	 * @param uuid     the mob's UUID
	 * @param type     the mob's network type ID
	 * @param location the mob's position, pitch and yaw
	 * @param metadata the mob's metadata
	 */
	public SpawnMobMessage( int id, UUID uuid, int type, Location location, List<Entry> metadata )
	{
		this( id, uuid, type, location.getX(), location.getY(), location.getZ(), Position.getIntYaw( location ), Position.getIntPitch( location ), Position.getIntPitch( location ), 0, 0, 0, metadata );
	}

	public SpawnMobMessage( int id, UUID uuid, int type, double x, double y, double z, int rotation, int pitch, int headPitch, int velX, int velY, int velZ, List<Entry> metadata )
	{
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotation = rotation;
		this.pitch = pitch;
		this.headPitch = headPitch;
		this.velX = velX;
		this.velY = velY;
		this.velZ = velZ;
		this.metadata = metadata;
	}

	public int getHeadPitch()
	{
		return headPitch;
	}

	public int getId()
	{
		return id;
	}

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

	public int getType()
	{
		return type;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public int getVelX()
	{
		return velX;
	}

	public int getVelY()
	{
		return velY;
	}

	public int getVelZ()
	{
		return velZ;
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
