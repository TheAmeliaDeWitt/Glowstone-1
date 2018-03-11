package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import java.util.List;

public final class TabCompleteResponseMessage implements Message
{
	private final List<String> completions;

	public TabCompleteResponseMessage( List<String> completions )
	{
		this.completions = completions;
	}

	public List<String> getCompletions()
	{
		return completions;
	}
}

