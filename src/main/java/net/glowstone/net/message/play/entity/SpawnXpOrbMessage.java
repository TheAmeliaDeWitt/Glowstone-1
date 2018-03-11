package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import org.bukkit.Location;

public final class SpawnXpOrbMessage implements Message
{
	private final short count;
	private final int id;
	private final double x;
	private final double y;
	private final double z;

	public SpawnXpOrbMessage( int id, Location location, short count )
	{
		this( id, location.getX(), location.getY(), location.getZ(), count );
	}

	public SpawnXpOrbMessage( int id, double x, double y, double z, short count )
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.count = count;
	}

	public short getCount()
	{
		return count;
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
