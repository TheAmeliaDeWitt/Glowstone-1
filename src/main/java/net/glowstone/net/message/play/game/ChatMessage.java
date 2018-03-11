package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import net.glowstone.util.TextMessage;

import org.json.simple.JSONObject;

public final class ChatMessage implements Message
{
	private final int mode;
	private final TextMessage text;

	public ChatMessage( JSONObject json )
	{
		this( new TextMessage( json ), 0 );
	}

	public ChatMessage( String text )
	{
		this( new TextMessage( text ), 0 );
	}

	public ChatMessage( TextMessage text, int mode )
	{
		this.text = text;
		this.mode = mode;
	}

	public int getMode()
	{
		return mode;
	}

	public TextMessage getText()
	{
		return text;
	}
}
