package net.glowstone.entity.objects;

import com.flowpowered.network.Message;
import com.google.common.base.Preconditions;

import net.glowstone.entity.GlowEntity;
import net.glowstone.net.message.play.entity.DestroyEntitiesMessage;
import net.glowstone.net.message.play.entity.SpawnXpOrbMessage;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

public class GlowExperienceOrb extends GlowEntity implements ExperienceOrb
{
	private static final int LIFETIME = 5 * 60 * 20;
	private int experience;
	private boolean fromBottle;
	private UUID sourceEntityId;
	private SpawnReason spawnReason;
	private boolean tickSkipped = false;
	private UUID triggerEntityId;

	public GlowExperienceOrb( Location location )
	{
		this( location, 1 );
	}

	/**
	 * Creates an experience orb.
	 *
	 * @param location   the location
	 * @param experience the amount of experience contained
	 */
	public GlowExperienceOrb( Location location, int experience )
	{
		super( location );
		setBoundingBox( 0.5, 0.5 );
		this.experience = experience;
		this.fromBottle = false;
	}

	@Override
	public List<Message> createSpawnMessage()
	{
		return Collections.singletonList( new SpawnXpOrbMessage( getEntityId(), getLocation(), ( short ) getExperience() ) );
	}

	@Override
	public void damage( double amount, Entity source, EntityDamageEvent.DamageCause cause )
	{
		if ( !isInvulnerable() )
		{
			remove();
		}
	}

	@Override
	public int getExperience()
	{
		return experience;
	}

	@Override
	public void setExperience( int experience )
	{
		Preconditions.checkArgument( experience > 0, "Experience points cannot be negative." );
		this.experience = experience;
		refresh();
	}

	@Nullable
	@Override
	public UUID getSourceEntityId()
	{
		return sourceEntityId;
	}

	public void setSourceEntityId( UUID sourceEntityId )
	{
		this.sourceEntityId = sourceEntityId;
	}

	@Override
	public SpawnReason getSpawnReason()
	{
		return spawnReason;
	}

	public void setSpawnReason( SpawnReason spawnReason )
	{
		this.spawnReason = spawnReason;
	}

	@Nullable
	@Override
	public UUID getTriggerEntityId()
	{
		return triggerEntityId;
	}

	public void setTriggerEntityId( UUID triggerEntityId )
	{
		this.triggerEntityId = triggerEntityId;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.EXPERIENCE_ORB;
	}

	@Override
	public boolean isFromBottle()
	{
		return fromBottle;
	}

	public void setFromBottle( boolean fromBottle )
	{
		this.fromBottle = fromBottle;
	}

	@Override
	public void pulse()
	{
		super.pulse();
		if ( tickSkipped )
		{
			// find player to give experience
			// todo: drag self towards player
			Optional<Player> player = getWorld().getPlayers().stream().filter( p -> p.getLocation().distanceSquared( location ) <= 1 ).findAny();
			if ( player.isPresent() )
			{
				player.get().giveExp( experience );
				world.playSound( location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F );
				remove();
				return;
			}
		}
		if ( getTicksLived() > LIFETIME )
		{
			remove();
			return;
		}
		if ( !tickSkipped )
		{
			tickSkipped = true;
		}
	}

	private void refresh()
	{
		DestroyEntitiesMessage destroyMessage = new DestroyEntitiesMessage( Collections.singletonList( this.getEntityId() ) );
		List<Message> spawnMessages = this.createSpawnMessage();
		Message[] messages = new Message[] {destroyMessage, spawnMessages.get( 0 )};
		getWorld().getRawPlayers().stream().filter( p -> p.canSeeEntity( this ) ).forEach( p -> p.getSession().sendAll( messages ) );
	}
}
