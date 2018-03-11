package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class StateChangeMessage implements Message
{
	private final int reason;
	private final float value;

	public StateChangeMessage( Reason reason, float value )
	{
		this.reason = reason.ordinal();
		this.value = value;
	}

	public StateChangeMessage( int reason, float value )
	{
		this.reason = reason;
		this.value = value;
	}

	public int getReason()
	{
		return reason;
	}

	public float getValue()
	{
		return value;
	}

	public enum Reason
	{
		INVALID_BED,
		STOP_RAIN,
		START_RAIN,
		GAMEMODE,
		CREDITS,
		DEMO_MESSAGE,
		ARROW,
		RAIN_DENSITY,
		SKY_DARKNESS
	}

}
