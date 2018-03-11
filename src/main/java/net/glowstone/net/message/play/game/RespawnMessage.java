package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class RespawnMessage implements Message
{
	private final int difficulty;
	private final int dimension;
	private final String levelType;
	private final int mode;

	public RespawnMessage( int dimension, int difficulty, int mode, String levelType )
	{
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.mode = mode;
		this.levelType = levelType;
	}

	public int getDifficulty()
	{
		return difficulty;
	}

	public int getDimension()
	{
		return dimension;
	}

	public String getLevelType()
	{
		return levelType;
	}

	public int getMode()
	{
		return mode;
	}
}
