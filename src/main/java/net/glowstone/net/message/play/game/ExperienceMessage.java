package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class ExperienceMessage implements Message
{
	private final float barValue;
	private final int level;
	private final int totalExp;

	public ExperienceMessage( float barValue, int level, int totalExp )
	{
		this.barValue = barValue;
		this.level = level;
		this.totalExp = totalExp;
	}

	public float getBarValue()
	{
		return barValue;
	}

	public int getLevel()
	{
		return level;
	}

	public int getTotalExp()
	{
		return totalExp;
	}
}
