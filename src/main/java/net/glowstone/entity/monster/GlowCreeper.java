package net.glowstone.entity.monster;

import com.flowpowered.network.Message;

import net.glowstone.entity.meta.MetadataIndex;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.List;

public class GlowCreeper extends GlowMonster implements Creeper
{
	private int explosionRadius;
	private boolean ignited;
	private int maxFuseTicks;

	public GlowCreeper( Location loc )
	{
		super( loc, EntityType.CREEPER, 20 );
		setBoundingBox( 0.6, 1.7 );
	}

	@Override
	public List<Message> createSpawnMessage()
	{
		// todo Implement the fuse & Ignition later.
		return super.createSpawnMessage();
	}

	@Override
	public void damage( double amount, Entity source, DamageCause cause )
	{
		super.damage( amount, source, cause );
		if ( DamageCause.LIGHTNING.equals( cause ) )
		{
			setPowered( true );
		}
	}

	@Override
	protected Sound getDeathSound()
	{
		return Sound.ENTITY_CREEPER_DEATH;
	}

	@Override
	public int getExplosionRadius()
	{
		return explosionRadius;
	}

	@Override
	public void setExplosionRadius( int explosionRadius )
	{
		this.explosionRadius = explosionRadius;
	}

	@Override
	protected Sound getHurtSound()
	{
		return Sound.ENTITY_CREEPER_HURT;
	}

	@Override
	public int getMaxFuseTicks()
	{
		return maxFuseTicks;
	}

	@Override
	public void setMaxFuseTicks( int maxFuseTicks )
	{
		this.maxFuseTicks = maxFuseTicks;
	}

	public boolean isIgnited()
	{
		return ignited;
	}

	public void setIgnited( boolean ignited )
	{
		this.ignited = ignited;
	}

	@Override
	public boolean isPowered()
	{
		return metadata.getBoolean( MetadataIndex.CREEPER_POWERED );
	}

	@Override
	public void setPowered( boolean value )
	{
		metadata.set( MetadataIndex.CREEPER_POWERED, value );
	}
}
