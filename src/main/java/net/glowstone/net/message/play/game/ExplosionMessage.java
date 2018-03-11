package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import java.util.Collection;

public class ExplosionMessage implements Message
{
	private final float playerMotionX;
	private final float playerMotionY;
	private final float playerMotionZ;
	private final float radius;
	private final Collection<Record> records;
	private final float x;
	private final float y;
	private final float z;

	public ExplosionMessage( float x, float y, float z, float radius, float playerMotionX, float playerMotionY, float playerMotionZ, Collection<Record> records )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.playerMotionX = playerMotionX;
		this.playerMotionY = playerMotionY;
		this.playerMotionZ = playerMotionZ;
		this.records = records;
	}

	public float getPlayerMotionX()
	{
		return playerMotionX;
	}

	public float getPlayerMotionY()
	{
		return playerMotionY;
	}

	public float getPlayerMotionZ()
	{
		return playerMotionZ;
	}

	public float getRadius()
	{
		return radius;
	}

	public Collection<Record> getRecords()
	{
		return records;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getZ()
	{
		return z;
	}

	@Override
	public String toString()
	{
		return "ExplosionMessage{x=" + x + ",y=" + y + ",z=" + z + ",radius=" + radius + ",motX=" + playerMotionX + ",motY=" + playerMotionY + ",motZ=" + playerMotionZ + ",recordCount=" + records.size() + "}";
	}

	public static class Record
	{
		private final byte x;
		private final byte y;
		private final byte z;

		public Record( byte x, byte y, byte z )
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public byte getX()
		{
			return x;
		}

		public byte getY()
		{
			return y;
		}

		public byte getZ()
		{
			return z;
		}
	}
}
