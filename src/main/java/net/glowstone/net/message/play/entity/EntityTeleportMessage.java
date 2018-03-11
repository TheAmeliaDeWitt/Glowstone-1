package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import net.glowstone.util.Position;

import org.bukkit.Location;

public final class EntityTeleportMessage implements Message
{
	private final int id;
	private final boolean onGround;
	private final int pitch;
	private final int rotation;
	private final double x;
	private final double y;
	private final double z;

	public EntityTeleportMessage( int id, double x, double y, double z, int rotation, int pitch )
	{
		this( id, x, y, z, rotation, pitch, true );
	}

	public EntityTeleportMessage( int id, Location location )
	{
		this( id, location.getX(), location.getY(), location.getZ(), Position.getIntYaw( location ), Position.getIntPitch( location ) );
	}

	public EntityTeleportMessage( int id, double x, double y, double z, int rotation, int pitch, boolean onGround )
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotation = rotation;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	public int getId()
	{
		return id;
	}

	public int getPitch()
	{
		return pitch;
	}

	public int getRotation()
	{
		return rotation;
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

	public boolean isOnGround()
	{
		return onGround;
	}
}
