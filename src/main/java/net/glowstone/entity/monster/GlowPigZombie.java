package net.glowstone.entity.monster;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.Random;
import java.util.UUID;

public class GlowPigZombie extends GlowZombie implements PigZombie
{
	private int anger;
	private UUID hurtBy;

	public GlowPigZombie( Location loc )
	{
		super( loc, EntityType.PIG_ZOMBIE );
	}

	@Override
	public boolean canTakeDamage( DamageCause damageCause )
	{
		if ( damageCause == DamageCause.FIRE || damageCause == DamageCause.LAVA )
		{
			return false;
		}
		return super.canTakeDamage( damageCause );
	}

	@Override
	protected Sound getAmbientSound()
	{
		return Sound.ENTITY_ZOMBIE_PIG_AMBIENT;
	}

	@Override
	public int getAnger()
	{
		return anger;
	}

	@Override
	public void setAnger( int anger )
	{
		this.anger = anger;
	}

	@Override
	protected Sound getDeathSound()
	{
		return Sound.ENTITY_ZOMBIE_PIG_DEATH;
	}

	public UUID getHurtBy()
	{
		return hurtBy;
	}

	public void setHurtBy( UUID hurtBy )
	{
		this.hurtBy = hurtBy;
	}

	@Override
	protected Sound getHurtSound()
	{
		return Sound.ENTITY_ZOMBIE_PIG_HURT;
	}

	@Override
	public boolean isAngry()
	{
		return anger > 0;
	}

	@Override
	public void setAngry( boolean angry )
	{
		if ( !angry )
		{
			anger = 0;
		}
		else if ( isAngry() )
		{
			anger = ( int ) ( new Random().nextGaussian() * 400 ) + 400;
		}
	}
}
