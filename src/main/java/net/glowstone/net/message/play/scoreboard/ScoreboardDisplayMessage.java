package net.glowstone.net.message.play.scoreboard;

import com.flowpowered.network.Message;

public final class ScoreboardDisplayMessage implements Message
{
	private final String objective;
	private final int position;

	public ScoreboardDisplayMessage( int position, String objective )
	{
		this.position = position;
		this.objective = objective;
	}

	public String getObjective()
	{
		return objective;
	}

	public int getPosition()
	{
		return position;
	}
}
