package net.glowstone.net.message.play.player;

import org.bukkit.Location;

public final class PlayerPositionLookMessage extends PlayerUpdateMessage
{
	private final float pitch;
	private final double x;
	private final double y;
	private final float yaw;
	private final double z;

	/**
	 * Creates a message to update a player's location and facing direction.
	 *
	 * @param x        the player's X coordinate
	 * @param y        the player's Y coordinate
	 * @param z        the player's Z coordinate
	 * @param yaw      the yaw angle
	 * @param pitch    the pitch angle
	 * @param onGround whether the player is on the ground
	 */
	public PlayerPositionLookMessage( boolean onGround, double x, double y, double z, float yaw, float pitch )
	{
		super( onGround );
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = ( yaw % 360 + 360 ) % 360;
		this.pitch = pitch;
	}

	public float getPitch()
	{
		return pitch;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public float getYaw()
	{
		return yaw;
	}

	public double getZ()
	{
		return z;
	}

	@Override
	public boolean moved()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return "PlayerPositionLookMessage(" + "onGround=" + isOnGround() + ", x=" + x + ", y=" + y + ", z=" + z + ", yaw=" + yaw + ", pitch=" + pitch + ')';
	}

	@Override
	public void update( Location location )
	{
		location.setX( x );
		location.setY( y );
		location.setZ( z );
		location.setYaw( yaw );
		location.setPitch( pitch );
	}

}
