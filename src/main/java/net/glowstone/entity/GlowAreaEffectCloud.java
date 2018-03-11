package net.glowstone.entity;

import com.flowpowered.network.Message;

import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import net.glowstone.net.message.play.entity.SpawnObjectMessage;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GlowAreaEffectCloud extends GlowEntity implements AreaEffectCloud
{
	private static final int NETWORK_TYPE_ID = 3;
	private final Map<PotionEffectType, PotionEffect> customEffects = new ConcurrentHashMap<>();
	/**
	 * Used to implement the reapplication delay. Note that this isn't serialized -- all
	 * reapplication delays will effectively end when the chunk unloads.
	 */
	private final Map<LivingEntity, Long> temporaryImmunities = new WeakHashMap<>();
	private PotionData basePotionData;
	private Color color;
	private int duration;
	private int durationOnUse;
	private Particle particle;
	private float radius;
	private float radiusOnUse;
	private float radiusPerTick;
	private int reapplicationDelay;
	private ProjectileSource source;
	private int waitTime;

	/**
	 * Creates an entity and adds it to the specified world.
	 *
	 * @param location The location of the entity.
	 */
	public GlowAreaEffectCloud( Location location )
	{
		super( location );
	}

	@Override
	public boolean addCustomEffect( PotionEffect potionEffect, boolean overwrite )
	{
		PotionEffectType type = potionEffect.getType();
		if ( overwrite )
		{
			customEffects.put( type, potionEffect );
			return true;
		}
		else
		{
			return customEffects.putIfAbsent( type, potionEffect ) == null;
		}
	}

	@Override
	public void clearCustomEffects()
	{
		customEffects.clear();
	}

	@Override
	public void clearCustomEffects0()
	{
		clearCustomEffects();
	}

	@Override
	public List<Message> createSpawnMessage()
	{
		MetadataMap metadataMap = new MetadataMap( GlowAreaEffectCloud.class );
		metadataMap.set( MetadataIndex.AREAEFFECTCLOUD_COLOR, color );
		metadataMap.set( MetadataIndex.AREAEFFECTCLOUD_RADIUS, radius );
		metadataMap.set( MetadataIndex.AREAEFFECTCLOUD_PARTICLEID, particle.ordinal() );
		return Arrays.asList( new SpawnObjectMessage( entityId, getUniqueId(), NETWORK_TYPE_ID, location ), new EntityMetadataMessage( entityId, metadataMap.getEntryList() ) );
	}

	@Override
	public PotionData getBasePotionData()
	{
		return basePotionData;
	}

	@Override
	public void setBasePotionData( PotionData basePotionData )
	{
		this.basePotionData = basePotionData;
	}

	@Override
	public Color getColor()
	{
		return color;
	}

	@Override
	public void setColor( Color color )
	{
		this.color = color;
	}

	@Override
	public List<PotionEffect> getCustomEffects()
	{
		return new ArrayList<>( customEffects.values() );
	}

	@Override
	public int getDuration()
	{
		return duration;
	}

	@Override
	public void setDuration( int duration )
	{
		this.duration = duration;
	}

	@Override
	public int getDurationOnUse()
	{
		return durationOnUse;
	}

	@Override
	public void setDurationOnUse( int durationOnUse )
	{
		this.durationOnUse = durationOnUse;
	}

	@Override
	public Particle getParticle()
	{
		return particle;
	}

	@Override
	public void setParticle( Particle particle )
	{
		this.particle = particle;
	}

	@Override
	public float getRadius()
	{
		return radius;
	}

	@Override
	public void setRadius( float radius )
	{
		this.radius = radius;
	}

	@Override
	public float getRadiusOnUse()
	{
		return radiusOnUse;
	}

	@Override
	public void setRadiusOnUse( float radiusOnUse )
	{
		this.radiusOnUse = radiusOnUse;
	}

	@Override
	public float getRadiusPerTick()
	{
		return radiusPerTick;
	}

	@Override
	public void setRadiusPerTick( float radiusPerTick )
	{
		this.radiusPerTick = radiusPerTick;
	}

	@Override
	public int getReapplicationDelay()
	{
		return reapplicationDelay;
	}

	@Override
	public void setReapplicationDelay( int reapplicationDelay )
	{
		this.reapplicationDelay = reapplicationDelay;
	}

	@Override
	public ProjectileSource getSource()
	{
		return source;
	}

	@Override
	public void setSource( ProjectileSource source )
	{
		this.source = source;
	}

	@Override
	public int getWaitTime()
	{
		return waitTime;
	}

	@Override
	public void setWaitTime( int waitTime )
	{
		this.waitTime = waitTime;
	}

	@Override
	public boolean hasCustomEffect( PotionEffectType potionEffectType )
	{
		return customEffects.containsKey( potionEffectType );
	}

	@Override
	public boolean hasCustomEffects()
	{
		return !customEffects.isEmpty();
	}

	@Override
	public void pulse()
	{
		super.pulse();
		radius += radiusPerTick;
		waitTime--;
		duration--;
		if ( duration <= 0 || radius <= 0 )
		{
			remove();
		}
		if ( waitTime <= 0 )
		{
			long currentTick = world.getFullTime();
			for ( Entity entity : world.getNearbyEntities( location, radius, radius, radius ) )
			{
				if ( entity instanceof LivingEntity && temporaryImmunities.getOrDefault( entity, Long.MIN_VALUE ) <= currentTick && location.distanceSquared( entity.getLocation() ) < radius * radius )
				{
					( ( LivingEntity ) entity ).addPotionEffects( customEffects.values() );
					temporaryImmunities.put( ( LivingEntity ) entity, currentTick + reapplicationDelay );
				}
			}
		}
	}

	@Override
	public boolean removeCustomEffect( PotionEffectType potionEffectType )
	{
		return customEffects.remove( potionEffectType ) != null;
	}
}
