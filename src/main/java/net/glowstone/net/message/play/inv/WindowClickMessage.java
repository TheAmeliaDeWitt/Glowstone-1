package net.glowstone.net.message.play.inv;

import com.flowpowered.network.Message;

import org.bukkit.inventory.ItemStack;

public final class WindowClickMessage implements Message
{
	private final int button;
	private final int id;
	private final ItemStack item;
	private final int mode;
	private final int slot;
	private final int transaction;

	public WindowClickMessage( int id, int slot, int button, int transaction, int mode, ItemStack item )
	{
		this.id = id;
		this.slot = slot;
		this.button = button;
		this.transaction = transaction;
		this.mode = mode;
		this.item = item;
	}

	public int getButton()
	{
		return button;
	}

	public int getId()
	{
		return id;
	}

	public ItemStack getItem()
	{
		return item;
	}

	public int getMode()
	{
		return mode;
	}

	public int getSlot()
	{
		return slot;
	}

	public int getTransaction()
	{
		return transaction;
	}
}
