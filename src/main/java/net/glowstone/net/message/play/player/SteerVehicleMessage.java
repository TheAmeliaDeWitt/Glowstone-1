package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public final class SteerVehicleMessage implements Message
{
	private final float forward;
	private final boolean jump;
	private final float sideways;
	private final boolean unmount;

	public SteerVehicleMessage( float sideways, float forward, boolean jump, boolean unmount )
	{
		this.sideways = sideways;
		this.forward = forward;
		this.jump = jump;
		this.unmount = unmount;
	}

	public float getForward()
	{
		return forward;
	}

	public float getSideways()
	{
		return sideways;
	}

	public boolean isJump()
	{
		return jump;
	}

	public boolean isUnmount()
	{
		return unmount;
	}
}

