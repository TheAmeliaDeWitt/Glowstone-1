package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public final class ResourcePackSendMessage implements Message
{
	private final String hash;
	private final String url;

	public ResourcePackSendMessage( String url, String hash )
	{
		this.url = url;
		this.hash = hash;
	}

	public String getHash()
	{
		return hash;
	}

	public String getUrl()
	{
		return url;
	}
}
