package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

public class SetCooldownMessage implements Message
{
	private final int cooldownTicks;
	private final int itemId;

	public SetCooldownMessage( int itemId, int cooldownTicks )
	{
		this.itemId = itemId;
		this.cooldownTicks = cooldownTicks;
	}

	public int getCooldownTicks()
	{
		return cooldownTicks;
	}

	public int getItemId()
	{
		return itemId;
	}
}
