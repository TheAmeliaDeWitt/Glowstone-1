package net.glowstone.net.message.play.player;

import org.bukkit.Location;

public final class PlayerLookMessage extends PlayerUpdateMessage
{
	private final float pitch;
	private final float yaw;

	/**
	 * Creates a message to update the direction a player is facing.
	 *
	 * @param yaw      the yaw angle
	 * @param pitch    the pitch angle
	 * @param onGround whether the player is on the ground
	 */
	public PlayerLookMessage( float yaw, float pitch, boolean onGround )
	{
		super( onGround );
		this.yaw = ( yaw % 360 + 360 ) % 360;
		this.pitch = pitch;
	}

	public float getPitch()
	{
		return pitch;
	}

	public float getYaw()
	{
		return yaw;
	}

	@Override
	public String toString()
	{
		return "PlayerLookMessage(" + "yaw=" + yaw + ", pitch=" + pitch + ", onGround=" + isOnGround() + ')';
	}

	@Override
	public void update( Location location )
	{
		location.setYaw( yaw );
		location.setPitch( pitch );
	}

}
