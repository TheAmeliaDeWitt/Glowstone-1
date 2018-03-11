package net.glowstone.inventory;

import net.glowstone.util.nbt.CompoundTag;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.Map;

public class GlowMetaSpawn extends GlowMetaItem implements SpawnEggMeta
{
	private CompoundTag entityTag;
	private EntityType spawnedType;

	/**
	 * Creates an instance by copying from the given {@link ItemMeta}. If that item is another
	 * {@link SpawnEggMeta}, the creature type is copied; if it's a {@link GlowMetaSpawn}, any
	 * custom NBT for the spawned entity is also copied.
	 *
	 * @param meta the {@link ItemMeta} to copy
	 */
	public GlowMetaSpawn( ItemMeta meta )
	{
		super( meta );

		if ( meta instanceof SpawnEggMeta )
		{
			this.spawnedType = ( ( SpawnEggMeta ) meta ).getSpawnedType();
			if ( meta instanceof GlowMetaSpawn )
			{
				entityTag = ( ( GlowMetaSpawn ) meta ).entityTag;
			}
		}
	}

	@Override
	public GlowMetaSpawn clone()
	{
		return new GlowMetaSpawn( this );
	}

	public CompoundTag getEntityTag()
	{
		return entityTag;
	}

	public void setEntityTag( CompoundTag entityTag )
	{
		this.entityTag = entityTag;
	}

	@Override
	public EntityType getSpawnedType()
	{
		return spawnedType;
	}

	@Override
	public void setSpawnedType( EntityType spawnedType )
	{
		this.spawnedType = spawnedType;
	}

	public boolean hasSpawnedType()
	{
		return this.spawnedType != null;
	}

	@Override
	public boolean isApplicable( Material material )
	{
		return material == Material.MONSTER_EGG;
	}

	@Override
	void readNbt( CompoundTag tag )
	{
		super.readNbt( tag );
		if ( tag.isCompound( "EntityTag" ) )
		{
			CompoundTag entity = tag.getCompound( "EntityTag" );
			if ( entity.isString( "id" ) )
			{
				String id = entity.getString( "id" );
				if ( id.startsWith( "minecraft:" ) )
				{
					id = id.substring( "minecraft:".length() );
				}
				spawnedType = EntityType.fromName( id );
			}
			this.entityTag = entity;
		}
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> result = super.serialize();
		result.put( "meta-type", "MONSTER_EGG" );
		if ( hasSpawnedType() )
		{
			result.put( "entity-id", "minecraft:" + getSpawnedType().getName() );
		}
		return result;
	}

	@Override
	void writeNbt( CompoundTag tag )
	{
		super.writeNbt( tag );
		CompoundTag entityTag = getEntityTag() != null ? getEntityTag() : new CompoundTag();
		if ( hasSpawnedType() )
		{
			entityTag.putString( "id", "minecraft:" + getSpawnedType().getName() );
		}
		tag.putCompound( "EntityTag", entityTag );
	}
}
