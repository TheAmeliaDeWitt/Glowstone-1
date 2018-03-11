package net.glowstone.entity;

import net.glowstone.entity.monster.GlowBlaze;
import net.glowstone.entity.monster.GlowCaveSpider;
import net.glowstone.entity.monster.GlowCreeper;
import net.glowstone.entity.monster.GlowElderGuardian;
import net.glowstone.entity.monster.GlowEnderman;
import net.glowstone.entity.monster.GlowEndermite;
import net.glowstone.entity.monster.GlowEvoker;
import net.glowstone.entity.monster.GlowGhast;
import net.glowstone.entity.monster.GlowGiant;
import net.glowstone.entity.monster.GlowGuardian;
import net.glowstone.entity.monster.GlowHusk;
import net.glowstone.entity.monster.GlowIronGolem;
import net.glowstone.entity.monster.GlowMagmaCube;
import net.glowstone.entity.monster.GlowPigZombie;
import net.glowstone.entity.monster.GlowShulker;
import net.glowstone.entity.monster.GlowSilverfish;
import net.glowstone.entity.monster.GlowSkeleton;
import net.glowstone.entity.monster.GlowSlime;
import net.glowstone.entity.monster.GlowSnowman;
import net.glowstone.entity.monster.GlowSpider;
import net.glowstone.entity.monster.GlowStray;
import net.glowstone.entity.monster.GlowVex;
import net.glowstone.entity.monster.GlowVindicator;
import net.glowstone.entity.monster.GlowWitch;
import net.glowstone.entity.monster.GlowWither;
import net.glowstone.entity.monster.GlowWitherSkeleton;
import net.glowstone.entity.monster.GlowZombie;
import net.glowstone.entity.monster.GlowZombieVillager;
import net.glowstone.entity.monster.complex.GlowEnderDragon;
import net.glowstone.entity.objects.GlowArmorStand;
import net.glowstone.entity.objects.GlowBoat;
import net.glowstone.entity.objects.GlowEnderCrystal;
import net.glowstone.entity.objects.GlowEvokerFangs;
import net.glowstone.entity.objects.GlowExperienceOrb;
import net.glowstone.entity.objects.GlowFallingBlock;
import net.glowstone.entity.objects.GlowItem;
import net.glowstone.entity.objects.GlowItemFrame;
import net.glowstone.entity.objects.GlowLeashHitch;
import net.glowstone.entity.objects.GlowMinecart;
import net.glowstone.entity.objects.GlowPainting;
import net.glowstone.entity.passive.GlowAbstractHorse;
import net.glowstone.entity.passive.GlowBat;
import net.glowstone.entity.passive.GlowChestedHorse;
import net.glowstone.entity.passive.GlowChicken;
import net.glowstone.entity.passive.GlowCow;
import net.glowstone.entity.passive.GlowDonkey;
import net.glowstone.entity.passive.GlowFirework;
import net.glowstone.entity.passive.GlowHorse;
import net.glowstone.entity.passive.GlowLlama;
import net.glowstone.entity.passive.GlowMooshroom;
import net.glowstone.entity.passive.GlowMule;
import net.glowstone.entity.passive.GlowOcelot;
import net.glowstone.entity.passive.GlowParrot;
import net.glowstone.entity.passive.GlowPig;
import net.glowstone.entity.passive.GlowPolarBear;
import net.glowstone.entity.passive.GlowRabbit;
import net.glowstone.entity.passive.GlowSheep;
import net.glowstone.entity.passive.GlowSkeletonHorse;
import net.glowstone.entity.passive.GlowSquid;
import net.glowstone.entity.passive.GlowVillager;
import net.glowstone.entity.passive.GlowWolf;
import net.glowstone.entity.passive.GlowZombieHorse;
import net.glowstone.entity.projectile.GlowArrow;
import net.glowstone.entity.projectile.GlowEgg;
import net.glowstone.entity.projectile.GlowEnderPearl;
import net.glowstone.entity.projectile.GlowFireball;
import net.glowstone.entity.projectile.GlowLingeringPotion;
import net.glowstone.entity.projectile.GlowSnowball;
import net.glowstone.entity.projectile.GlowSpectralArrow;
import net.glowstone.entity.projectile.GlowSplashPotion;
import net.glowstone.entity.projectile.GlowThrownExpBottle;
import net.glowstone.entity.projectile.GlowTippedArrow;
import net.glowstone.io.entity.EntityStorage;

import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Boat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.ChestedHorse;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Egg;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Husk;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Mule;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Spider;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Stray;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Weather;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.entity.ZombieVillager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRegistry
{

	private static final Map<String, CustomEntityDescriptor> CUSTOM_ENTITIES = new HashMap<>();

	private static final Map<Class<? extends Entity>, Class<? extends GlowEntity>> ENTITIES;

	static
	{
		Map<Class<? extends Entity>, Class<? extends GlowEntity>> entities0 = new HashMap<>();
		entities0.put( AbstractHorse.class, GlowAbstractHorse.class );
		entities0.put( AreaEffectCloud.class, GlowAreaEffectCloud.class );
		entities0.put( ArmorStand.class, GlowArmorStand.class );
		entities0.put( Arrow.class, GlowArrow.class );
		entities0.put( Bat.class, GlowBat.class );
		entities0.put( Blaze.class, GlowBlaze.class );
		entities0.put( Boat.class, GlowBoat.class );
		entities0.put( CaveSpider.class, GlowCaveSpider.class );
		entities0.put( ChestedHorse.class, GlowChestedHorse.class );
		entities0.put( Chicken.class, GlowChicken.class );
		entities0.put( Cow.class, GlowCow.class );
		entities0.put( Creeper.class, GlowCreeper.class );
		entities0.put( Donkey.class, GlowDonkey.class );
		entities0.put( Egg.class, GlowEgg.class );
		entities0.put( ElderGuardian.class, GlowElderGuardian.class );
		entities0.put( EnderCrystal.class, GlowEnderCrystal.class );
		entities0.put( EnderDragon.class, GlowEnderDragon.class );
		entities0.put( EnderPearl.class, GlowEnderPearl.class );
		//TODO: Ender Signal
		entities0.put( Enderman.class, GlowEnderman.class );
		entities0.put( Endermite.class, GlowEndermite.class );
		entities0.put( ExperienceOrb.class, GlowExperienceOrb.class );
		entities0.put( Evoker.class, GlowEvoker.class );
		entities0.put( EvokerFangs.class, GlowEvokerFangs.class );
		entities0.put( FallingBlock.class, GlowFallingBlock.class );
		entities0.put( Fireball.class, GlowFireball.class );
		entities0.put( Firework.class, GlowFirework.class );
		//TODO: Fishing hook
		entities0.put( Ghast.class, GlowGhast.class );
		entities0.put( Giant.class, GlowGiant.class );
		entities0.put( Guardian.class, GlowGuardian.class );
		entities0.put( Horse.class, GlowHorse.class );
		entities0.put( Husk.class, GlowHusk.class );
		entities0.put( IronGolem.class, GlowIronGolem.class );
		entities0.put( Item.class, GlowItem.class );
		entities0.put( ItemFrame.class, GlowItemFrame.class );
		entities0.put( LeashHitch.class, GlowLeashHitch.class );
		entities0.put( LightningStrike.class, GlowLightningStrike.class );
		entities0.put( LingeringPotion.class, GlowLingeringPotion.class );
		entities0.put( Llama.class, GlowLlama.class );
		entities0.put( MagmaCube.class, GlowMagmaCube.class );
		entities0.put( GlowMinecart.MinecartType.RIDEABLE.getEntityClass(), GlowMinecart.MinecartType.RIDEABLE.getMinecartClass() );
		entities0.put( GlowMinecart.MinecartType.CHEST.getEntityClass(), GlowMinecart.MinecartType.CHEST.getMinecartClass() );
		entities0.put( GlowMinecart.MinecartType.FURNACE.getEntityClass(), GlowMinecart.MinecartType.FURNACE.getMinecartClass() );
		entities0.put( GlowMinecart.MinecartType.TNT.getEntityClass(), GlowMinecart.MinecartType.TNT.getMinecartClass() );
		entities0.put( GlowMinecart.MinecartType.HOPPER.getEntityClass(), GlowMinecart.MinecartType.HOPPER.getMinecartClass() );
		entities0.put( GlowMinecart.MinecartType.SPAWNER.getEntityClass(), GlowMinecart.MinecartType.SPAWNER.getMinecartClass() );
		//TODO: Command Block minecart
		entities0.put( Mule.class, GlowMule.class );
		entities0.put( MushroomCow.class, GlowMooshroom.class );
		entities0.put( Ocelot.class, GlowOcelot.class );
		entities0.put( Painting.class, GlowPainting.class );
		entities0.put( Parrot.class, GlowParrot.class );
		entities0.put( Pig.class, GlowPig.class );
		entities0.put( PigZombie.class, GlowPigZombie.class );
		entities0.put( Player.class, GlowPlayer.class );
		entities0.put( PolarBear.class, GlowPolarBear.class );
		entities0.put( TNTPrimed.class, GlowTntPrimed.class );
		entities0.put( Rabbit.class, GlowRabbit.class );
		entities0.put( Sheep.class, GlowSheep.class );
		entities0.put( Shulker.class, GlowShulker.class );
		entities0.put( Silverfish.class, GlowSilverfish.class );
		entities0.put( Skeleton.class, GlowSkeleton.class );
		entities0.put( SkeletonHorse.class, GlowSkeletonHorse.class );
		entities0.put( Slime.class, GlowSlime.class );
		entities0.put( Snowball.class, GlowSnowball.class );
		entities0.put( Snowman.class, GlowSnowman.class );
		entities0.put( SpectralArrow.class, GlowSpectralArrow.class );
		entities0.put( Spider.class, GlowSpider.class );
		entities0.put( SplashPotion.class, GlowSplashPotion.class );
		entities0.put( Squid.class, GlowSquid.class );
		entities0.put( Stray.class, GlowStray.class );
		entities0.put( ThrownExpBottle.class, GlowThrownExpBottle.class );
		entities0.put( TippedArrow.class, GlowTippedArrow.class );
		entities0.put( Vex.class, GlowVex.class );
		entities0.put( Villager.class, GlowVillager.class );
		entities0.put( Vindicator.class, GlowVindicator.class );
		entities0.put( Weather.class, GlowWeather.class );
		entities0.put( Witch.class, GlowWitch.class );
		entities0.put( Wither.class, GlowWither.class );
		entities0.put( WitherSkeleton.class, GlowWitherSkeleton.class );
		//TODO: Wither Skull
		entities0.put( Wolf.class, GlowWolf.class );
		entities0.put( Zombie.class, GlowZombie.class );
		entities0.put( ZombieHorse.class, GlowZombieHorse.class );
		entities0.put( ZombieVillager.class, GlowZombieVillager.class );

		ENTITIES = Collections.unmodifiableMap( entities0 );
	}

	public static CustomEntityDescriptor getCustomEntityDescriptor( String id )
	{
		return CUSTOM_ENTITIES.get( id.toLowerCase() );
	}

	public static Class<? extends GlowEntity> getEntity( Class<? extends Entity> clazz )
	{
		return ENTITIES.get( clazz );
	}

	public static Class<? extends GlowEntity> getEntity( EntityType type )
	{
		return ENTITIES.get( type.getEntityClass() );
	}

	public static List<CustomEntityDescriptor> getRegisteredCustomEntities()
	{
		List<CustomEntityDescriptor> entities = new ArrayList<>( CUSTOM_ENTITIES.values() );
		return Collections.unmodifiableList( entities );
	}

	public static boolean isCustomEntityRegistered( String id )
	{
		return CUSTOM_ENTITIES.containsKey( id.toLowerCase() );
	}

	/**
	 * Registers a custom entity type.
	 *
	 * @param descriptor the entity type to register; all fields except
	 *                   {@link CustomEntityDescriptor#getStorage()} must be non-null
	 */
	public static void registerCustomEntity( CustomEntityDescriptor<? extends GlowEntity> descriptor )
	{
		if ( descriptor == null || descriptor.getEntityClass() == null || descriptor.getId() == null || descriptor.getPlugin() == null )
		{
			return;
		}
		if ( descriptor.getPlugin().isEnabled() )
		{
			descriptor.getPlugin().getServer().getLogger().warning( "Cannot register custom entity '" + descriptor.getId() + "' for plugin '" + descriptor.getPlugin() + "', worlds are already loaded." );
			return;
		}
		if ( CUSTOM_ENTITIES.containsKey( descriptor.getId().toLowerCase() ) )
		{
			return;
		}
		CUSTOM_ENTITIES.put( descriptor.getId(), descriptor );
		if ( descriptor.getStorage() != null )
		{
			EntityStorage.bind( descriptor.getStorage() );
		}
	}
}
