package net.glowstone.net.message;

import com.flowpowered.network.Message;

import net.glowstone.util.TextMessage;

public final class KickMessage implements Message
{
	private final TextMessage text;

	public KickMessage( TextMessage text )
	{
		this.text = text;
	}

	public KickMessage( String text )
	{
		this( new TextMessage( text ) );
	}

	public TextMessage getText()
	{
		return text;
	}
}
