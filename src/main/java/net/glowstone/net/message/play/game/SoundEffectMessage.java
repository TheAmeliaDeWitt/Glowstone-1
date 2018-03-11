package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import org.bukkit.SoundCategory;

public class SoundEffectMessage implements Message
{
	private final SoundCategory category;
	private final float pitch;
	private final int sound;
	private final float volume;
	private final double x;
	private final double y;
	private final double z;

	public SoundEffectMessage( int sound, SoundCategory category, double x, double y, double z, float volume, float pitch )
	{
		this.sound = sound;
		this.category = category;
		this.x = x;
		this.y = y;
		this.z = z;
		this.volume = volume;
		this.pitch = pitch;
	}

	public SoundCategory getCategory()
	{
		return category;
	}

	public float getPitch()
	{
		return pitch;
	}

	public int getSound()
	{
		return sound;
	}

	public float getVolume()
	{
		return volume;
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
