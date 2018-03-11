package net.glowstone.net.message.play.inv;

import com.flowpowered.network.Message;

import org.bukkit.inventory.ItemStack;

public final class SetWindowSlotMessage implements Message
{
	private final int id;
	private final ItemStack item;
	private final int slot;

	public SetWindowSlotMessage( int id, int slot, ItemStack item )
	{
		this.id = id;
		this.slot = slot;
		this.item = item;
	}

	public int getId()
	{
		return id;
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
