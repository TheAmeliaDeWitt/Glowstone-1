package net.glowstone.entity.objects;

import com.flowpowered.network.Message;

import net.glowstone.entity.GlowEntity;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.inventory.GlowInventory;
import net.glowstone.net.message.play.entity.SpawnObjectMessage;
import net.glowstone.net.message.play.player.InteractEntityMessage;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

// TODO: Implement movement and collision detection.
public abstract class GlowMinecart extends GlowEntity implements Minecart
{
	/**
	 * Factory method that creates a minecart.
	 *
	 * @param location     the location
	 * @param minecartType the minecart type (i.e. the type of block carried, if any)
	 */
	public static GlowMinecart create( Location location, MinecartType minecartType )
	{
		return minecartType.getCreator().apply( location );
	}

	private final MinecartType minecartType;
	private volatile double damage;
	private volatile Vector derailedVelocityMod;
	private volatile MaterialData displayBlock;
	private volatile int displayBlockOffset;
	private volatile Vector flyingVelocityMod;
	private volatile double maxSpeed;
	private volatile boolean slowWhenEmpty;

	/**
	 * Creates a minecart.
	 *
	 * @param location     the location
	 * @param minecartType the minecart type (i.e. the type of block carried, if any)
	 */
	public GlowMinecart( Location location, MinecartType minecartType )
	{
		super( location );
		setSize( 0.98f, 0.7f );
		this.minecartType = minecartType;
	}

	@Override
	public List<Message> createSpawnMessage()
	{
		return Collections.singletonList( new SpawnObjectMessage( entityId, getUniqueId(), 10, location, minecartType.ordinal() ) );
	}

	@Override
	public boolean entityInteract( GlowPlayer player, InteractEntityMessage message )
	{
		if ( message.getAction() == InteractEntityMessage.Action.ATTACK.ordinal() )
		{
			// todo: damage points
			if ( this instanceof InventoryHolder )
			{
				InventoryHolder inv = ( InventoryHolder ) this;
				if ( inv.getInventory() != null )
				{
					for ( ItemStack drop : inv.getInventory().getContents() )
					{
						if ( drop == null || drop.getType() == Material.AIR || drop.getAmount() < 1 )
						{
							continue;
						}
						GlowItem item = world.dropItemNaturally( getLocation(), drop );
						item.setPickupDelay( 30 );
						item.setBias( player );
					}
				}
			}
			remove();
		}
		return true;
	}

	@Override
	public double getDamage()
	{
		return damage;
	}

	@Override
	public void setDamage( double damage )
	{
		this.damage = damage;
	}

	@Override
	public Vector getDerailedVelocityMod()
	{
		return derailedVelocityMod;
	}

	@Override
	public void setDerailedVelocityMod( Vector derailedVelocityMod )
	{
		this.derailedVelocityMod = derailedVelocityMod;
	}

	@Override
	public MaterialData getDisplayBlock()
	{
		return displayBlock;
	}

	@Override
	public void setDisplayBlock( MaterialData displayBlock )
	{
		this.displayBlock = displayBlock;
	}

	@Override
	public int getDisplayBlockOffset()
	{
		return displayBlockOffset;
	}

	@Override
	public void setDisplayBlockOffset( int displayBlockOffset )
	{
		this.displayBlockOffset = displayBlockOffset;
	}

	@Override
	public Vector getFlyingVelocityMod()
	{
		return flyingVelocityMod;
	}

	@Override
	public void setFlyingVelocityMod( Vector flyingVelocityMod )
	{
		this.flyingVelocityMod = flyingVelocityMod;
	}

	@Override
	public double getMaxSpeed()
	{
		return maxSpeed;
	}

	@Override
	public void setMaxSpeed( double maxSpeed )
	{
		this.maxSpeed = maxSpeed;
	}

	public MinecartType getMinecartType()
	{
		return minecartType;
	}

	@Override
	public boolean isSlowWhenEmpty()
	{
		return slowWhenEmpty;
	}

	@Override
	public void setSlowWhenEmpty( boolean slowWhenEmpty )
	{
		this.slowWhenEmpty = slowWhenEmpty;
	}

	public enum MinecartType
	{
		RIDEABLE( Rideable.class, EntityType.MINECART, RideableMinecart.class, Rideable::new ),
		CHEST( Storage.class, EntityType.MINECART_CHEST, StorageMinecart.class, Storage::new ),
		FURNACE( Powered.class, EntityType.MINECART_FURNACE, PoweredMinecart.class, Powered::new ),
		TNT( Explosive.class, EntityType.MINECART_TNT, ExplosiveMinecart.class, Explosive::new ),
		SPAWNER( Spawner.class, EntityType.MINECART_MOB_SPAWNER, SpawnerMinecart.class, Spawner::new ),
		HOPPER( Hopper.class, EntityType.MINECART_HOPPER, HopperMinecart.class, Hopper::new ),
		COMMAND( Command.class, EntityType.MINECART_COMMAND, CommandMinecart.class, Command::new );

		private final Function<? super Location, ? extends GlowMinecart> creator;
		private final Class<? extends Minecart> entityClass;
		private final EntityType entityType;
		private final Class<? extends GlowMinecart> minecartClass;

		MinecartType( Class<? extends GlowMinecart> minecartClass, EntityType entityType, Class<? extends Minecart> entityClass, Function<? super Location, ? extends GlowMinecart> creator )
		{
			this.minecartClass = minecartClass;
			this.entityType = entityType;
			this.entityClass = entityClass;
			this.creator = creator;
		}

		public Function<? super Location, ? extends GlowMinecart> getCreator()
		{
			return creator;
		}

		public Class<? extends Minecart> getEntityClass()
		{
			return entityClass;
		}

		public EntityType getEntityType()
		{
			return entityType;
		}

		public Class<? extends GlowMinecart> getMinecartClass()
		{
			return minecartClass;
		}
	}

	public static class Command extends GlowMinecart implements CommandMinecart
	{
		// TODO: Behavior not implemented

		private String command;
		private String name;

		public Command( Location location )
		{
			super( location, MinecartType.COMMAND );
		}

		@Override
		public String getCommand()
		{
			return command;
		}

		@Override
		public void setCommand( String command )
		{
			this.command = command;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public void setName( String name )
		{
			this.name = name;
		}
	}

	public static class Explosive extends GlowMinecart implements ExplosiveMinecart
	{

		public Explosive( Location location )
		{
			super( location, MinecartType.TNT );
		}
	}

	public static class Hopper extends GlowMinecart implements HopperMinecart
	{
		private final Inventory inventory;
		private boolean enabled = true;

		/**
		 * Creates a minecart with a hopper.
		 *
		 * @param location the location
		 */
		public Hopper( Location location )
		{
			super( location, MinecartType.HOPPER );
			inventory = new GlowInventory( this, InventoryType.HOPPER, InventoryType.HOPPER.getDefaultSize(), "Minecart with Hopper" );
		}

		@Override
		public boolean entityInteract( GlowPlayer player, InteractEntityMessage message )
		{
			super.entityInteract( player, message );
			if ( message.getAction() != InteractEntityMessage.Action.INTERACT.ordinal() )
			{
				return false;
			}
			if ( player.isSneaking() )
			{
				return false;
			}
			player.openInventory( inventory );
			return true;
		}

		@Override
		public Inventory getInventory()
		{
			return inventory;
		}

		@Override
		public boolean isEnabled()
		{
			return enabled;
		}

		@Override
		public void setEnabled( boolean enabled )
		{
			this.enabled = enabled;
		}
	}

	public static class Powered extends GlowMinecart implements PoweredMinecart
	{

		public Powered( Location location )
		{
			super( location, MinecartType.FURNACE );
		}
	}

	public static class Rideable extends GlowMinecart implements RideableMinecart
	{
		public Rideable( Location location )
		{
			super( location, MinecartType.RIDEABLE );
		}

		@Override
		public boolean entityInteract( GlowPlayer player, InteractEntityMessage message )
		{
			super.entityInteract( player, message );
			if ( message.getAction() != InteractEntityMessage.Action.INTERACT.ordinal() )
			{
				return false;
			}
			if ( player.isSneaking() )
			{
				return false;
			}
			if ( isEmpty() )
			{
				// todo: fix passengers
				// setPassenger(player);
				return true;
			}
			return false;
		}
	}

	public static class Spawner extends GlowMinecart implements SpawnerMinecart
	{

		public Spawner( Location location )
		{
			super( location, MinecartType.SPAWNER );
		}
	}

	public static class Storage extends GlowMinecart implements StorageMinecart
	{
		private final Inventory inventory;

		/**
		 * Creates a minecart with a chest.
		 *
		 * @param location the location.
		 */
		public Storage( Location location )
		{
			super( location, MinecartType.CHEST );
			inventory = new GlowInventory( this, InventoryType.CHEST, InventoryType.CHEST.getDefaultSize(), "Minecart with Chest" );
		}

		@Override
		public boolean entityInteract( GlowPlayer player, InteractEntityMessage message )
		{
			super.entityInteract( player, message );
			if ( message.getAction() != InteractEntityMessage.Action.INTERACT.ordinal() )
			{
				return false;
			}
			if ( player.isSneaking() )
			{
				return false;
			}
			player.openInventory( inventory );
			return true;
		}

		@Override
		public Inventory getInventory()
		{
			return inventory;
		}
	}
}
