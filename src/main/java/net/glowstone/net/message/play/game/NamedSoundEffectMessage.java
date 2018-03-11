package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import org.bukkit.SoundCategory;

public final class NamedSoundEffectMessage implements Message
{
	private final float pitch;
	private final String sound;
	private final SoundCategory soundCategory;
	private final float volume;
	private final double x;
	private final double y;
	private final double z;

	public NamedSoundEffectMessage( String sound, SoundCategory soundCategory, double x, double y, double z, float volume, float pitch )
	{
		this.sound = sound;
		this.soundCategory = soundCategory;
		this.x = x;
		this.y = y;
		this.z = z;
		this.volume = volume;
		this.pitch = pitch;
	}

	public float getPitch()
	{
		return pitch;
	}

	public SoundCategory getSoundCategory()
	{
		return soundCategory;
	}

	public String getSound()
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
