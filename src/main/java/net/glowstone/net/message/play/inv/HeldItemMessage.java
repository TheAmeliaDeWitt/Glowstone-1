package net.glowstone.net.message.play.inv;

import com.flowpowered.network.Message;

public final class HeldItemMessage implements Message
{
	private final int slot;

	public HeldItemMessage( int slot )
	{
		this.slot = slot;
	}

	public int getSlot()
	{
		return slot;
	}
}

