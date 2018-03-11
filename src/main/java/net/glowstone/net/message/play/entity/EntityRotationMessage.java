package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

public final class EntityRotationMessage implements Message
{
	private final int id;
	private final boolean onGround;
	private final int pitch;
	private final int rotation;

	public EntityRotationMessage( int id, int rotation, int pitch )
	{
		this( id, rotation, pitch, true );
	}

	public EntityRotationMessage( int id, int rotation, int pitch, boolean onGround )
	{
		this.id = id;
		this.rotation = rotation;
		this.pitch = pitch;
		this.onGround = onGround;
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
