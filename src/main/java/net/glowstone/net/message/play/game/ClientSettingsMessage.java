package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class ClientSettingsMessage implements Message
{
	private final boolean chatColors;
	private final int chatFlags;
	private final int hand;
	private final String locale;
	private final int skinFlags;
	private final int viewDistance;

	public ClientSettingsMessage( String locale, int chatFlags, int hand, boolean chatColors, int skinFlags, int viewDistance )
	{
		this.chatColors = chatColors;
		this.chatFlags = chatFlags;
		this.hand = hand;
		this.locale = locale;
		this.skinFlags = skinFlags;
		this.viewDistance = viewDistance;
	}

	public int getChatFlags()
	{
		return chatFlags;
	}

	public int getHand()
	{
		return hand;
	}

	public String getLocale()
	{
		return locale;
	}

	public int getSkinFlags()
	{
		return skinFlags;
	}

	public int getViewDistance()
	{
		return viewDistance;
	}

	public boolean isChatColors()
	{
		return chatColors;
	}
}
