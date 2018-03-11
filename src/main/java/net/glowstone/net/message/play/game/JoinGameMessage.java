package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class JoinGameMessage implements Message
{
	private final int difficulty;
	private final int dimension;
	private final int id;
	private final String levelType;
	private final int maxPlayers;
	private final int mode;
	private final boolean reducedDebugInfo;

	public JoinGameMessage( int id, int mode, int dimension, int difficulty, int maxPlayers, String levelType, boolean reducedDebugInfo )
	{
		this.id = id;
		this.mode = mode;
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.maxPlayers = maxPlayers;
		this.levelType = levelType;
		this.reducedDebugInfo = reducedDebugInfo;
	}

	public int getDifficulty()
	{
		return difficulty;
	}

	public int getDimension()
	{
		return dimension;
	}

	public int getId()
	{
		return id;
	}

	public String getLevelType()
	{
		return levelType;
	}

	public int getMaxPlayers()
	{
		return maxPlayers;
	}

	public int getMode()
	{
		return mode;
	}

	public boolean isReducedDebugInfo()
	{
		return reducedDebugInfo;
	}
}
