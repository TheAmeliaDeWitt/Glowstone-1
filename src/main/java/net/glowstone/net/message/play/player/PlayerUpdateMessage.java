package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import org.bukkit.Location;

/**
 * Base class for player update messages.
 */
public class PlayerUpdateMessage implements Message
{
	private final boolean onGround;

	public PlayerUpdateMessage( boolean onGround )
	{
		this.onGround = onGround;
	}

	public boolean isOnGround()
	{
		return onGround;
	}

	public boolean moved()
	{
		return false;
	}

	public void update( Location location )
	{
		// do nothing
	}
}
