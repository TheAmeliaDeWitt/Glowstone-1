package net.glowstone.block.entity.state;

import net.glowstone.block.GlowBlock;
import net.glowstone.block.entity.BrewingStandEntity;

import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.BrewerInventory;

public class GlowBrewingStand extends GlowContainer implements BrewingStand
{
	private int brewingTime;
	private int fuelLevel;

	public GlowBrewingStand( GlowBlock block )
	{
		super( block );
		brewingTime = getBlockEntity().getBrewTime();
	}

	public GlowBrewingStand( GlowBlock block, int brewTime )
	{
		super( block );
		this.brewingTime = brewTime;
	}

	private BrewingStandEntity getBlockEntity()
	{
		return ( BrewingStandEntity ) getBlock().getBlockEntity();
	}

	@Override
	public int getBrewingTime()
	{
		return brewingTime;
	}

	@Override
	public void setBrewingTime( int brewingTime )
	{
		this.brewingTime = brewingTime;
	}

	@Override
	public int getFuelLevel()
	{
		return fuelLevel;
	}

	@Override
	public void setFuelLevel( int fuelLevel )
	{
		this.fuelLevel = fuelLevel;
	}

	@Override
	public BrewerInventory getInventory()
	{
		return ( BrewerInventory ) getBlockEntity().getInventory();
	}

	@Override
	public BrewerInventory getSnapshotInventory()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update( boolean force, boolean applyPhysics )
	{
		boolean result = super.update( force, applyPhysics );
		if ( result )
		{
			BrewingStandEntity stand = getBlockEntity();
			stand.setBrewTime( brewingTime );
			stand.updateInRange();
		}
		return result;
	}
}
