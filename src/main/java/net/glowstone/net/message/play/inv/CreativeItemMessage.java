package net.glowstone.net.message.play.inv;

import com.flowpowered.network.Message;

import org.bukkit.inventory.ItemStack;

public final class CreativeItemMessage implements Message
{
	private final ItemStack item;
	private final int slot;

	public CreativeItemMessage( int slot, ItemStack item )
	{
		this.slot = slot;
		this.item = item;
	}

	public ItemStack getItem()
	{
		return item;
	}

	public int getSlot()
	{
		return slot;
	}
}
