package net.glowstone.block.entity.state;

import net.glowstone.block.GlowBlock;
import net.glowstone.block.entity.FurnaceEntity;

import org.bukkit.block.Furnace;
import org.bukkit.inventory.FurnaceInventory;

public class GlowFurnace extends GlowContainer implements Furnace
{
	private short burnTime;
	private short cookTime;

	/**
	 * /**
	 * Creates an instance for the given block.
	 *
	 * @param block the furnace block
	 */
	public GlowFurnace( GlowBlock block )
	{
		super( block );
		burnTime = getBlockEntity().getBurnTime();
		cookTime = getBlockEntity().getCookTime();
	}

	/**
	 * Creates an instance for the given block.
	 *
	 * @param block    the furnace block
	 * @param burnTime the number of ticks before this furnace must consume more fuel, or 0 if not
	 *                 burning
	 * @param cookTime the number of ticks the current item has been cooking
	 */
	public GlowFurnace( GlowBlock block, short burnTime, short cookTime )
	{
		super( block );
		this.burnTime = burnTime;
		this.cookTime = cookTime;
	}

	private FurnaceEntity getBlockEntity()
	{
		return ( FurnaceEntity ) getBlock().getBlockEntity();
	}

	@Override
	public short getBurnTime()
	{
		return burnTime;
	}

	@Override
	public void setBurnTime( short burnTime )
	{
		this.burnTime = burnTime;
	}

	@Override
	public short getCookTime()
	{
		return cookTime;
	}

	@Override
	public void setCookTime( short cookTime )
	{
		this.cookTime = cookTime;
	}

	@Override
	public FurnaceInventory getInventory()
	{
		return ( FurnaceInventory ) getBlockEntity().getInventory();
	}

	@Override
	public FurnaceInventory getSnapshotInventory()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update( boolean force, boolean applyPhysics )
	{
		boolean result = super.update( force, applyPhysics );
		if ( result )
		{
			FurnaceEntity furnace = getBlockEntity();
			furnace.setBurnTime( burnTime );
			furnace.setCookTime( cookTime );
		}
		return result;
	}
}
