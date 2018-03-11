package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public class SteerBoatMessage implements Message
{
	private final boolean isLeftPaddleTurning;
	private final boolean isRightPaddleTurning;

	public SteerBoatMessage( boolean isRightPaddleTurning, boolean isLeftPaddleTurning )
	{
		this.isRightPaddleTurning = isRightPaddleTurning;
		this.isLeftPaddleTurning = isLeftPaddleTurning;
	}

	public boolean isLeftPaddleTurning()
	{
		return isLeftPaddleTurning;
	}

	public boolean isRightPaddleTurning()
	{
		return isRightPaddleTurning;
	}
}
