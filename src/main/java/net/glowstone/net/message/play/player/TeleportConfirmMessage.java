package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public class TeleportConfirmMessage implements Message
{
	private final int teleportId;

	public TeleportConfirmMessage( int teleportId )
	{
		this.teleportId = teleportId;
	}

	public int getTeleportId()
	{
		return teleportId;
	}
}
