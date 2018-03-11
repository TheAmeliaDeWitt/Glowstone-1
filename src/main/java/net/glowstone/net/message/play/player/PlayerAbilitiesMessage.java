package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public final class PlayerAbilitiesMessage implements Message
{
	private final int flags;
	private final float flySpeed;
	private final float walkSpeed;

	public PlayerAbilitiesMessage( int flags, float flySpeed, float walkSpeed )
	{
		this.flags = flags;
		this.flySpeed = flySpeed;
		this.walkSpeed = walkSpeed;
	}

	public int getFlags()
	{
		return flags;
	}

	public float getFlySpeed()
	{
		return flySpeed;
	}

	public float getWalkSpeed()
	{
		return walkSpeed;
	}
}

