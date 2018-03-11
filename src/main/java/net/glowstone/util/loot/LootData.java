package net.glowstone.util.loot;

import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class LootData
{
	private final int experience;
	private final Collection<ItemStack> items;

	public LootData( Collection<ItemStack> items, int experience )
	{
		this.items = items;
		this.experience = experience;
	}

	public int getExperience()
	{
		return experience;
	}

	public Collection<ItemStack> getItems()
	{
		return items;
	}
}
