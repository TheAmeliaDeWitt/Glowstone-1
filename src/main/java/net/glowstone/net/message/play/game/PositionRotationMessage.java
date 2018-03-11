package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import org.bukkit.Location;

public final class PositionRotationMessage implements Message
{
	private final int flags;
	private final float pitch;
	private final float rotation;
	private final int teleportId;
	private final double x;
	private final double y;
	private final double z;

	public PositionRotationMessage( double x, double y, double z, float rotation, float pitch )
	{
		this( x, y, z, rotation, pitch, 0, 0 );
	}

	public PositionRotationMessage( Location location )
	{
		this( location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch() );
	}

	public PositionRotationMessage( double x, double y, double z, float rotation, float pitch, int flags, int teleportId )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.rotation = rotation;
		this.pitch = pitch;
		this.flags = flags;
		this.teleportId = teleportId;
	}

	public int getFlags()
	{
		return flags;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getRotation()
	{
		return rotation;
	}

	public int getTeleportId()
	{
		return teleportId;
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
