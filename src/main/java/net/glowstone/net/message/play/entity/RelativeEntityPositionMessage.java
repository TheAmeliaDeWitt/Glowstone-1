package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

public final class RelativeEntityPositionMessage implements Message
{
	private final short deltaX;
	private final short deltaY;
	private final short deltaZ;
	private final int id;
	private final boolean onGround;

	public RelativeEntityPositionMessage( int id, short deltaX, short deltaY, short deltaZ )
	{
		this( id, deltaX, deltaY, deltaZ, true );
	}

	public RelativeEntityPositionMessage( int id, short deltaX, short deltaY, short deltaZ, boolean onGround )
	{
		this.id = id;
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.deltaZ = deltaZ;
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

	public boolean isOnGround()
	{
		return onGround;
	}
}
