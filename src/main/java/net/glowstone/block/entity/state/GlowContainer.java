package net.glowstone.block.entity.state;

import com.destroystokyo.paper.loottable.LootableBlockInventory;

import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;

import org.bukkit.Nameable;
import org.bukkit.block.Container;
import org.bukkit.block.Lockable;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public abstract class GlowContainer extends GlowBlockState implements LootableBlockInventory, Lockable, Nameable, Container
{
	private final AtomicLong lastFilled = new AtomicLong( -1 );
	private final AtomicReference<String> lootTable = new AtomicReference<>( null );
	private final AtomicLong lootTableSeed = new AtomicLong( 0 );
	private final AtomicLong nextRefill = new AtomicLong( -1 );
	private final Map<UUID, Long> playersWhoHaveLooted = new ConcurrentHashMap<>();
	private String customName;
	private String lock;

	public GlowContainer( GlowBlock block )
	{
		super( block );
	}

	@Override
	public void clearLootTable()
	{
		setLootTable( null );
	}

	@Override
	public String getCustomName()
	{
		return customName;
	}

	@Override
	public void setCustomName( String customName )
	{
		this.customName = customName;
	}

	@Override
	public long getLastFilled()
	{
		return lastFilled.get();
	}

	// TODO: Lootable implementation

	@Override
	public Long getLastLooted( UUID uuid )
	{
		return playersWhoHaveLooted.get( uuid );
	}

	@Override
	public String getLock()
	{
		return lock;
	}

	@Override
	public void setLock( String lock )
	{
		this.lock = lock;
	}

	@Override
	public String getLootTableName()
	{
		return lootTable.get();
	}

	@Override
	public long getLootTableSeed()
	{
		return lootTableSeed.get();
	}

	@Override
	public long getNextRefill()
	{
		return nextRefill.get();
	}

	@Override
	public Inventory getSnapshotInventory()
	{
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasBeenFilled()
	{
		return lastFilled.get() >= 0;
	}

	@Override
	public boolean hasLootTable()
	{
		return lootTable.get() != null;
	}

	@Override
	public boolean hasPendingRefill()
	{
		return getNextRefill() >= Math.max( 0, getLastFilled() );
	}

	@Override
	public boolean hasPlayerLooted( UUID uuid )
	{
		return playersWhoHaveLooted.containsKey( uuid );
	}

	@Override
	public boolean isLocked()
	{
		return getLock() != null;
	}

	@Override
	public boolean isRefillEnabled()
	{
		// TODO
		return false;
	}

	@Override
	public boolean setHasPlayerLooted( UUID uuid, boolean b )
	{
		return b ? playersWhoHaveLooted.put( uuid, getWorld().getFullTime() ) != null : playersWhoHaveLooted.remove( uuid ) != null;
	}

	@Override
	public String setLootTable( String name )
	{
		return setLootTable( name, 0 );
	}

	@Override
	public String setLootTable( String name, long seed )
	{
		setLootTableSeed( seed );
		return lootTable.getAndSet( name );
	}

	@Override
	public long setLootTableSeed( long seed )
	{
		return lootTableSeed.getAndSet( seed );
	}

	@Override
	public long setNextRefill( long l )
	{
		return nextRefill.getAndSet( l );
	}
}
