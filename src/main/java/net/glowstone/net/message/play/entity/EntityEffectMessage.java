package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

public final class EntityEffectMessage implements Message
{
	private final int amplifier;
	private final int duration;
	private final int effect;
	private final boolean hideParticles;
	private final int id;

	public EntityEffectMessage( int id, int effect, int amplifier, int duration, boolean hideParticles )
	{
		this.id = id;
		this.effect = effect;
		this.amplifier = amplifier;
		this.duration = duration;
		this.hideParticles = hideParticles;
	}

	public int getAmplifier()
	{
		return amplifier;
	}

	public int getDuration()
	{
		return duration;
	}

	public int getEffect()
	{
		return effect;
	}

	public int getId()
	{
		return id;
	}

	public boolean isHideParticles()
	{
		return hideParticles;
	}
}
