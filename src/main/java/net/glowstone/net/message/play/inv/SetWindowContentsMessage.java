package net.glowstone.net.message.play.inv;

import com.flowpowered.network.Message;

import org.bukkit.inventory.ItemStack;

public final class SetWindowContentsMessage implements Message
{
	private final int id;
	private final ItemStack[] items;

	public SetWindowContentsMessage( int id, ItemStack[] items )
	{
		this.id = id;
		this.items = items;
	}

	public int getId()
	{
		return id;
	}

	public ItemStack[] getItems()
	{
		return items;
	}
}
