package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import org.bukkit.util.BlockVector;

public final class TabCompleteMessage implements Message
{
	private final boolean assumeCommand;
	private final BlockVector location;
	private final String text;

	public TabCompleteMessage( String text, boolean assumeCommand, BlockVector location )
	{
		this.text = text;
		this.assumeCommand = assumeCommand;
		this.location = location;
	}

	public BlockVector getLocation()
	{
		return location;
	}

	public String getText()
	{
		return text;
	}

	public boolean isAssumeCommand()
	{
		return assumeCommand;
	}
}

