package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import org.bukkit.Difficulty;

public final class ServerDifficultyMessage implements Message
{
	private final Difficulty difficulty;

	public ServerDifficultyMessage( Difficulty difficulty )
	{
		this.difficulty = difficulty;
	}

	public Difficulty getDifficulty()
	{
		return difficulty;
	}
}
