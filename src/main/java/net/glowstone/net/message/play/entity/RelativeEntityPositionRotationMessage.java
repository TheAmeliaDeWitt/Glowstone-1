package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

public final class RelativeEntityPositionRotationMessage implements Message
{
	private final short deltaX;
	private final short deltaY;
	private final short deltaZ;
	private final int id;
	private final boolean onGround;
	private final int pitch;
	private final int rotation;

	public RelativeEntityPositionRotationMessage( int id, short deltaX, short deltaY, short deltaZ, int rotation, int pitch )
	{
		this( id, deltaX, deltaY, deltaZ, rotation, pitch, true );
	}

	public RelativeEntityPositionRotationMessage( int id, short deltaX, short deltaY, short deltaZ, int rotation, int pitch, boolean onGround )
	{
		this.id = id;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
		this.rotation = rotation;
		this.pitch = pitch;
		this.onGround = onGround;
	}

	public short getDeltaX()
	{
		return deltaX;
	}

	public short getDeltaY()
	{
		return deltaY;
	}

	public short getDeltaZ()
	{
		return deltaZ;
	}

	public int getId()
	{
		return id;
	}

	public int getPitch()
	{
		return pitch;
	}

	public int getRotation()
	{
		return rotation;
	}

	public boolean isOnGround()
	{
		return onGround;
	}
}
