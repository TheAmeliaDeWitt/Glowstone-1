package net.glowstone.net.message.play.scoreboard;

import com.flowpowered.network.Message;

public final class ScoreboardScoreMessage implements Message
{
	public static ScoreboardScoreMessage remove( String target, String objective )
	{
		return new ScoreboardScoreMessage( target, true, objective, 0 );
	}

	private final String objective;
	private final boolean remove;
	private final String target;
	private final int value;

	public ScoreboardScoreMessage( String target, String objective, int value )
	{
		this( target, false, objective, value );
	}

	private ScoreboardScoreMessage( String target, boolean remove, String objective, int value )
	{
		this.target = target;
		this.remove = remove;
		this.objective = objective;
		this.value = value;
	}

	public String getObjective()
	{
		return objective;
	}

	public String getTarget()
	{
		return target;
	}

	public int getValue()
	{
		return value;
	}

	public boolean isRemove()
	{
		return remove;
	}
}
