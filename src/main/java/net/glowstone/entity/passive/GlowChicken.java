package net.glowstone.entity.passive;

import net.glowstone.entity.GlowAnimal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class GlowChicken extends GlowAnimal implements Chicken
{
	private boolean chickenJockey;
	private int eggLayTime;

	/**
	 * Creates a chicken.
	 *
	 * @param location the chicken's location
	 */
	public GlowChicken( Location location )
	{
		super( location, EntityType.CHICKEN, 4 );
		setSize( 0.4F, 0.7F );
		generateEggLayDelay();
	}

	private void generateEggLayDelay()
	{
		setEggLayTime( ThreadLocalRandom.current().nextInt( 20 * 60 * 5 ) + 20 * 60 * 5 );
	}

	@Override
	protected Sound getAmbientSound()
	{
		return Sound.ENTITY_CHICKEN_AMBIENT;
	}

	@Override
	protected Sound getDeathSound()
	{
		return Sound.ENTITY_CHICKEN_DEATH;
	}

	public int getEggLayTime()
	{
		return eggLayTime;
	}

	public void setEggLayTime( int eggLayTime )
	{
		this.eggLayTime = eggLayTime;
	}

	@Override
	protected Sound getHurtSound()
	{
		return Sound.ENTITY_CHICKEN_HURT;
	}

	public boolean isChickenJockey()
	{
		return chickenJockey;
	}

	public void setChickenJockey( boolean chickenJockey )
	{
		this.chickenJockey = chickenJockey;
	}

	@Override
	public void pulse()
	{
		super.pulse();
		eggLayTime--;
		if ( eggLayTime <= 0 )
		{
			getWorld().dropItemNaturally( getLocation(), new ItemStack( Material.EGG, 1 ) );
			getWorld().playSound( getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1 );
			generateEggLayDelay();
		}
	}
}
