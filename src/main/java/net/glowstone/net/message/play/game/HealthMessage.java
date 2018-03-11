package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class HealthMessage implements Message
{
	private final int food;
	private final float health;
	private final float saturation;

	public HealthMessage( float health, int food, float saturation )
	{
		this.health = health;
		this.food = food;
		this.saturation = saturation;
	}

	public int getFood()
	{
		return food;
	}

	public float getHealth()
	{
		return health;
	}

	public float getSaturation()
	{
		return saturation;
	}
}
