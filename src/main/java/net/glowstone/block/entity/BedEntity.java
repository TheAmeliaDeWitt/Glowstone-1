package net.glowstone.block.entity;

import net.glowstone.block.GlowBlock;
import net.glowstone.block.GlowBlockState;
import net.glowstone.block.entity.state.GlowBed;
import net.glowstone.constants.GlowBlockEntity;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.util.nbt.CompoundTag;

public class BedEntity extends BlockEntity
{
	private int color;

	public BedEntity( GlowBlock block )
	{
		super( block );
		setSaveId( "minecraft:bed" );
	}

	public int getColor()
	{
		return color;
	}

	public void setColor( int color )
	{
		this.color = color;
	}

	@Override
	public GlowBlockState getState()
	{
		return new GlowBed( block );
	}

	@Override
	public void loadNbt( CompoundTag tag )
	{
		super.loadNbt( tag );
		color = tag.getInt( "color" );
	}

	@Override
	public void saveNbt( CompoundTag tag )
	{
		super.saveNbt( tag );
		tag.putInt( "color", color );
	}

	@Override
	public void update( GlowPlayer player )
	{
		super.update( player );
		CompoundTag nbt = new CompoundTag();
		saveNbt( nbt );
		player.sendBlockEntityChange( block.getLocation(), GlowBlockEntity.BED, nbt );
	}
}
