package net.glowstone.inventory;

import net.glowstone.constants.ItemIds;
import net.glowstone.util.InventoryUtil;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class GlowEntityEquipment implements EntityEquipment
{

	private Entity holder;
	private Entry[] slots = new Entry[6];

	public GlowEntityEquipment( Entity holder )
	{
		this.holder = holder;
	}

	@Override
	public void clear()
	{
		for ( EquipmentSlot slot : EquipmentSlot.values() )
		{
			setItem( slot, InventoryUtil.createEmptyStack() );
		}
	}

	@Override
	public ItemStack[] getArmorContents()
	{
		ItemStack[] armor = new ItemStack[4];
		int feet = EquipmentSlot.FEET.ordinal();
		for ( int i = feet; i < slots.length; i++ )
		{
			armor[i - feet] = getItem( EquipmentSlot.values()[i] );
		}
		return armor;
	}

	@Override
	public void setArmorContents( ItemStack[] itemStacks )
	{
		if ( itemStacks.length != slots.length )
		{
			throw new IllegalArgumentException( "Length of armor must be " + slots.length );
		}
		for ( int i = EquipmentSlot.FEET.ordinal(); i < slots.length; i++ )
		{
			setItem( EquipmentSlot.values()[i], itemStacks[i] );
		}
	}

	@Override
	public ItemStack getBoots()
	{
		return getItem( EquipmentSlot.FEET );
	}

	@Override
	public void setBoots( ItemStack itemStack )
	{
		setItem( EquipmentSlot.FEET, itemStack );
	}

	@Override
	public float getBootsDropChance()
	{
		return getDropChance( EquipmentSlot.FEET );
	}

	@Override
	public void setBootsDropChance( float chance )
	{
		setDropChance( EquipmentSlot.FEET, chance );
	}

	@Override
	public ItemStack getChestplate()
	{
		return getItem( EquipmentSlot.CHEST );
	}

	@Override
	public void setChestplate( ItemStack itemStack )
	{
		setItem( EquipmentSlot.CHEST, itemStack );
	}

	@Override
	public float getChestplateDropChance()
	{
		return getDropChance( EquipmentSlot.CHEST );
	}

	@Override
	public void setChestplateDropChance( float chance )
	{
		setDropChance( EquipmentSlot.CHEST, chance );
	}

	private float getDropChance( EquipmentSlot slot )
	{
		Entry slotEntry = getSlotEntry( slot );
		return slotEntry == null ? 1F : slotEntry.dropChance;
	}

	@Override
	public ItemStack getHelmet()
	{
		return getItem( EquipmentSlot.HEAD );
	}

	@Override
	public void setHelmet( ItemStack itemStack )
	{
		setItem( EquipmentSlot.HEAD, itemStack );
	}

	@Override
	public float getHelmetDropChance()
	{
		return getDropChance( EquipmentSlot.HEAD );
	}

	@Override
	public void setHelmetDropChance( float chance )
	{
		setDropChance( EquipmentSlot.HEAD, chance );
	}

	@Override
	public Entity getHolder()
	{
		return holder;
	}

	/**
	 * Returns the ItemStack found in the slot at the given EquipmentSlot.
	 *
	 * @param slot The EquipmentSlot of the Slot's ItemStack to return
	 *
	 * @return The ItemStack in the slot
	 */
	public ItemStack getItem( EquipmentSlot slot )
	{
		Entry slotEntry = getSlotEntry( slot );
		ItemStack stack = slotEntry != null ? slotEntry.itemStack : null;
		return InventoryUtil.itemOrEmpty( stack );
	}

	@Override
	public ItemStack getItemInHand()
	{
		return getItemInMainHand();
	}

	@Override
	public void setItemInHand( ItemStack itemStack )
	{
		setItemInMainHand( itemStack );
	}

	@Override
	public float getItemInHandDropChance()
	{
		return getDropChance( EquipmentSlot.HAND );
	}

	@Override
	public void setItemInHandDropChance( float chance )
	{
		setDropChance( EquipmentSlot.HAND, chance );
	}

	@Override
	public ItemStack getItemInMainHand()
	{
		return getItem( EquipmentSlot.HAND );
	}

	@Override
	public void setItemInMainHand( ItemStack itemStack )
	{
		setItem( EquipmentSlot.HAND, itemStack );
	}

	@Override
	public float getItemInMainHandDropChance()
	{
		return getItemInHandDropChance();
	}

	@Override
	public void setItemInMainHandDropChance( float chance )
	{
		setItemInHandDropChance( chance );
	}

	@Override
	public ItemStack getItemInOffHand()
	{
		return getItem( EquipmentSlot.OFF_HAND );
	}

	@Override
	public void setItemInOffHand( ItemStack itemStack )
	{
		setItem( EquipmentSlot.OFF_HAND, itemStack );
	}

	@Override
	public float getItemInOffHandDropChance()
	{
		return getDropChance( EquipmentSlot.OFF_HAND );
	}

	@Override
	public void setItemInOffHandDropChance( float chance )
	{
		setDropChance( EquipmentSlot.OFF_HAND, chance );
	}

	@Override
	public ItemStack getLeggings()
	{
		return getItem( EquipmentSlot.LEGS );
	}

	@Override
	public void setLeggings( ItemStack itemStack )
	{
		setItem( EquipmentSlot.LEGS, itemStack );
	}

	@Override
	public float getLeggingsDropChance()
	{
		return getDropChance( EquipmentSlot.LEGS );
	}

	@Override
	public void setLeggingsDropChance( float chance )
	{
		setDropChance( EquipmentSlot.LEGS, chance );
	}

	private Entry getSlotEntry( EquipmentSlot slot )
	{
		return slots[slot.ordinal()];
	}

	private void setDropChance( EquipmentSlot slot, float chance )
	{
		Entry slotEntry = getSlotEntry( slot );
		if ( slotEntry == null )
		{
			return;
		}

		slotEntry.dropChance = chance;
	}

	/**
	 * Stores the ItemStack at the given index of the inventory.
	 *
	 * @param slot The EquipmentSlot where to put the ItemStack
	 * @param item The ItemStack to set
	 */
	public void setItem( EquipmentSlot slot, ItemStack item )
	{
		Entry entry = new Entry( ItemIds.sanitize( item ), 1f );
		slots[slot.ordinal()] = entry;
	}

	private class Entry
	{
		private float dropChance;
		private ItemStack itemStack;

		public Entry( ItemStack itemStack, float dropChance )
		{
			this.itemStack = itemStack;
			this.dropChance = dropChance;
		}
	}
}
