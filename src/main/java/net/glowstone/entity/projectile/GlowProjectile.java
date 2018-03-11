package net.glowstone.entity.projectile;

import com.flowpowered.network.Message;

import net.glowstone.entity.GlowEntity;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import net.glowstone.net.message.play.entity.EntityVelocityMessage;
import net.glowstone.net.message.play.entity.SpawnObjectMessage;
import net.glowstone.util.Position;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

/**
 * A projectile. Subclasses must call {@link #setBoundingBox(double, double)} if they are to collide
 * with other entities.
 */
public abstract class GlowProjectile extends GlowEntity implements Projectile
{
	private boolean bounce;
	private boolean glowing;
	private boolean invulnerable;
	private ProjectileSource shooter;

	/**
	 * Creates a projectile.
	 *
	 * @param location the initial location
	 */
	public GlowProjectile( Location location )
	{
		super( location );
	}

	@Override
	public abstract void collide( Block block );

	public abstract void collide( LivingEntity entity );

	@Override
	public List<Message> createSpawnMessage()
	{
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();

		int yaw = Position.getIntYaw( location );
		int pitch = Position.getIntPitch( location );

		return Arrays.asList( new SpawnObjectMessage( entityId, getUniqueId(), getObjectId(), x, y, z, pitch, yaw ), new EntityMetadataMessage( entityId, metadata.getEntryList() ), new EntityVelocityMessage( entityId, getVelocity() ) );
	}

	@Override
	public boolean doesBounce()
	{
		return bounce;
	}

	protected abstract int getObjectId();

	@Override
	public ProjectileSource getShooter()
	{
		return shooter;
	}

	@Override
	public void setShooter( ProjectileSource shooter )
	{
		this.shooter = shooter;
	}

	@Override
	public boolean isGlowing()
	{
		return glowing;
	}

	@Override
	public void setGlowing( boolean glowing )
	{
		this.glowing = glowing;
	}

	@Override
	public boolean isInvulnerable()
	{
		return invulnerable;
	}

	@Override
	public void setInvulnerable( boolean invulnerable )
	{
		this.invulnerable = invulnerable;
	}

	@Override
	protected void pulsePhysics()
	{
		if ( boundingBox != null )
		{
			Vector size = boundingBox.getSize();
			for ( Entity entity : world.getNearbyEntities( location, size.getX(), size.getY(), size.getZ() ) )
			{
				if ( entity instanceof LivingEntity && entity != this && !( entity.equals( shooter ) ) )
				{
					collide( ( LivingEntity ) entity );
					break;
				}
			}
		}
		super.pulsePhysics();
	}

	@Override
	public void setBounce( boolean bounce )
	{
		this.bounce = bounce;
	}
}
