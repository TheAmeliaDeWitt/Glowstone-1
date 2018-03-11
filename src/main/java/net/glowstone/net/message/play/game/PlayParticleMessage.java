package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class PlayParticleMessage implements Message
{
	private final int count;
	private final float data;
	private final int[] extData;
	private final boolean longDistance;
	private final float offsetX;
	private final float offsetY;
	private final float offsetZ;
	private final int particle;
	private final float x;
	private final float y;
	private final float z;

	public PlayParticleMessage( int particle, boolean longDistance, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float data, int count, int[] extData )
	{
		this.particle = particle;
		this.longDistance = longDistance;
		this.x = x;
		this.y = y;
		this.z = z;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.data = data;
		this.count = count;
		this.extData = extData;
	}

	public int getCount()
	{
		return count;
	}

	public float getData()
	{
		return data;
	}

	public int[] getExtData()
	{
		return extData;
	}

	public boolean isLongDistance()
	{
		return longDistance;
	}

	public int getParticle()
	{
		return particle;
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

	public float getOffsetX()
	{
		return offsetX;
	}

	public float getOffsetY()
	{
		return offsetY;
	}

	public float getOffsetZ()
	{
		return offsetZ;
	}
}
