package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import net.glowstone.util.TextMessage;

public final class UserListHeaderFooterMessage implements Message
{
	private final TextMessage footer;
	private final TextMessage header;

	public UserListHeaderFooterMessage( TextMessage header, TextMessage footer )
	{
		this.header = header;
		this.footer = footer;
	}

	public TextMessage getFooter()
	{
		return footer;
	}

	public TextMessage getHeader()
	{
		return header;
	}
}
