package net.glowstone.entity.monster;

import net.glowstone.entity.meta.MetadataIndex;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Witch;

public class GlowWitch extends GlowMonster implements Witch
{
	public GlowWitch( Location loc )
	{
		super( loc, EntityType.WITCH, 26 );
		setBoundingBox( 0.6, 1.8 );
	}

	@Override
	protected Sound getAmbientSound()
	{
		return Sound.ENTITY_WITCH_AMBIENT;
	}

	@Override
	protected Sound getDeathSound()
	{
		return Sound.ENTITY_WITCH_DEATH;
	}

	@Override
	protected Sound getHurtSound()
	{
		return Sound.ENTITY_WITCH_HURT;
	}

	public boolean isAggressive()
	{
		return metadata.getBoolean( MetadataIndex.WITCH_AGGRESSIVE );
	}

	public void setAggressive( boolean aggressive )
	{
		metadata.set( MetadataIndex.WITCH_AGGRESSIVE, aggressive );
	}
}
