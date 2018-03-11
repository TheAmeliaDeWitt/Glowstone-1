package net.glowstone.entity;

import com.flowpowered.network.Message;

import net.glowstone.EventFactory;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.profile.GlowPlayerProfile;
import net.glowstone.entity.objects.GlowItem;
import net.glowstone.inventory.ArmorConstants;
import net.glowstone.inventory.EquipmentMonitor;
import net.glowstone.inventory.GlowCraftingInventory;
import net.glowstone.inventory.GlowEnchantingInventory;
import net.glowstone.inventory.GlowInventory;
import net.glowstone.inventory.GlowInventoryView;
import net.glowstone.inventory.GlowPlayerInventory;
import net.glowstone.io.entity.EntityStorage;
import net.glowstone.net.message.play.entity.EntityEquipmentMessage;
import net.glowstone.net.message.play.entity.EntityHeadRotationMessage;
import net.glowstone.net.message.play.entity.SpawnPlayerMessage;
import net.glowstone.util.InventoryUtil;
import net.glowstone.util.Position;
import net.glowstone.util.nbt.CompoundTag;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a human entity, such as an NPC or a player.
 */
public abstract class GlowHumanEntity extends GlowLivingEntity implements HumanEntity
{
	/**
	 * The ender chest inventory of this human.
	 */
	private final GlowInventory enderChest = new GlowInventory( this, InventoryType.ENDER_CHEST );
	/**
	 * The inventory of this human.
	 */
	private final GlowPlayerInventory inventory = new GlowPlayerInventory( this );
	/**
	 * The player profile with name and UUID information.
	 */
	private final GlowPlayerProfile profile;
	/**
	 * This human's PermissibleBase for permissions.
	 */
	protected PermissibleBase permissions;
	/**
	 * Whether this human is sleeping or not.
	 */
	protected boolean sleeping;
	/**
	 * The player's active game mode.
	 */
	private GameMode gameMode;
	/**
	 * The item the player has on their cursor.
	 */
	private ItemStack itemOnCursor;
	/**
	 * Whether the client needs to be notified of armor changes (set to true after joining).
	 */
	private boolean needsArmorUpdate = false;
	/**
	 * Whether this human is considered an op.
	 */
	private boolean op;
	/**
	 * The player's currently open inventory.
	 */
	private InventoryView openInventory;

	/**
	 * How long this human has been sleeping.
	 */
	private int sleepTicks;

	/**
	 * The player's xpSeed. Used for calculation of enchantments.
	 */
	private int xpSeed;

	/**
	 * Creates a human within the specified world and with the specified name.
	 *
	 * @param location The location.
	 * @param profile  The human's profile with name and UUID information.
	 */
	public GlowHumanEntity( Location location, GlowPlayerProfile profile )
	{
		super( location );
		this.profile = profile;
		xpSeed = new Random().nextInt(); //TODO: use entity's random instance
		permissions = new PermissibleBase( this );
		gameMode = server.getDefaultGameMode();

		openInventory = new GlowInventoryView( this );
		addViewer( openInventory.getTopInventory() );
		addViewer( openInventory.getBottomInventory() );
	}

	@Override
	public PermissionAttachment addAttachment( Plugin plugin )
	{
		return permissions.addAttachment( plugin );
	}

	@Override
	public PermissionAttachment addAttachment( Plugin plugin, int ticks )
	{
		return permissions.addAttachment( plugin, ticks );
	}

	@Override
	public PermissionAttachment addAttachment( Plugin plugin, String name, boolean value )
	{
		return permissions.addAttachment( plugin, name, value );
	}

	@Override
	public PermissionAttachment addAttachment( Plugin plugin, String name, boolean value, int ticks )
	{
		return permissions.addAttachment( plugin, name, value, ticks );
	}

	private void addViewer( Inventory inventory )
	{
		if ( inventory instanceof GlowInventory )
		{
			( ( GlowInventory ) inventory ).addViewer( this );
		}
	}

	@Override
	public boolean canTakeDamage( DamageCause damageCause )
	{
		return ( damageCause == DamageCause.VOID || damageCause == DamageCause.SUICIDE || gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE ) && super.canTakeDamage( damageCause );
	}

	@Override
	public void closeInventory()
	{
		EventFactory.callEvent( new InventoryCloseEvent( openInventory ) );
		if ( getGameMode() != GameMode.CREATIVE )
		{
			if ( !InventoryUtil.isEmpty( getItemOnCursor() ) )
			{
				drop( getItemOnCursor() );
			}
			handleUnusedInputs();
		}
		setItemOnCursor( InventoryUtil.createEmptyStack() );
		resetInventoryView();
	}

	@Override
	public List<Message> createSpawnMessage()
	{
		List<Message> result = new LinkedList<>();

		// spawn player
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		int yaw = Position.getIntYaw( location );
		int pitch = Position.getIntPitch( location );
		result.add( new SpawnPlayerMessage( entityId, profile.getUniqueId(), x, y, z, yaw, pitch, metadata.getEntryList() ) );

		// head facing
		result.add( new EntityHeadRotationMessage( entityId, yaw ) );

		// equipment
		EntityEquipment equipment = getEquipment();
		result.add( new EntityEquipmentMessage( entityId, EntityEquipmentMessage.HELD_ITEM, equipment.getItemInMainHand() ) );
		result.add( new EntityEquipmentMessage( entityId, EntityEquipmentMessage.OFF_HAND, equipment.getItemInOffHand() ) );
		for ( int i = 0; i < 4; i++ )
		{
			result.add( new EntityEquipmentMessage( entityId, EntityEquipmentMessage.BOOTS_SLOT + i, equipment.getArmorContents()[i] ) );
		}
		return result;
	}

	/**
	 * Spawns a new {@link GlowItem} in the world, as if this HumanEntity had dropped it.
	 *
	 * <p>Note that this does NOT remove the item from the inventory.
	 *
	 * @param stack The item to drop
	 *
	 * @return the GlowItem that was generated, or null if the spawning was cancelled
	 *
	 * @throws IllegalArgumentException if the stack is empty
	 */
	public GlowItem drop( ItemStack stack )
	{
		checkArgument( !InventoryUtil.isEmpty( stack ), "stack must not be empty" );

		Location dropLocation = location.clone().add( 0, getEyeHeight( true ) - 0.3, 0 );
		GlowItem dropItem = world.dropItem( dropLocation, stack );
		Vector vel = location.getDirection().multiply( new Vector( 0.11F, 0.0F, 0.11F ) );
		vel.setY( 0.006F );
		dropItem.setVelocity( vel );
		return dropItem;
	}

	/**
	 * Drops the item this entity currently has in its hands and remove the item from the
	 * HumanEntity's inventory.
	 *
	 * @param wholeStack True if the whole stack should be dropped
	 */
	public void dropItemInHand( boolean wholeStack )
	{
		ItemStack stack = getItemInHand();
		if ( InventoryUtil.isEmpty( stack ) )
		{
			return;
		}

		ItemStack dropping = stack.clone();
		if ( !wholeStack )
		{
			dropping.setAmount( 1 );
		}

		GlowItem dropped = drop( dropping );
		if ( dropped == null )
		{
			return;
		}

		if ( stack.getAmount() == 1 || wholeStack )
		{
			setItemInHand( InventoryUtil.createEmptyStack() );
		}
		else
		{
			ItemStack now = stack.clone();
			now.setAmount( now.getAmount() - 1 );
			setItemInHand( now );
		}
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return permissions.getEffectivePermissions();
	}

	@Override
	public GlowInventory getEnderChest()
	{
		return enderChest;
	}

	@Override
	public EntityEquipment getEquipment()
	{
		return getInventory();
	}

	////////////////////////////////////////////////////////////////////////////
	// Internals

	@Override
	public int getExpToLevel()
	{
		throw new UnsupportedOperationException( "Non-player HumanEntity has no level" );
	}

	@Override
	public GameMode getGameMode()
	{
		return gameMode;
	}

	@Override
	public void setGameMode( GameMode gameMode )
	{
		this.gameMode = gameMode;
	}

	@Override
	public GlowPlayerInventory getInventory()
	{
		return inventory;
	}

	////////////////////////////////////////////////////////////////////////////
	// Properties

	@Override
	public ItemStack getItemInHand()
	{
		return getInventory().getItemInMainHand();
	}

	@Override
	public void setItemInHand( ItemStack item )
	{
		getInventory().setItemInMainHand( item );
	}

	@Override
	public ItemStack getItemOnCursor()
	{
		return itemOnCursor;
	}

	@Override
	public void setItemOnCursor( ItemStack itemOnCursor )
	{
		this.itemOnCursor = itemOnCursor;
	}

	public CompoundTag getLeftShoulderTag()
	{
		Object tag = metadata.get( MetadataIndex.PLAYER_LEFT_SHOULDER );
		return tag == null ? new CompoundTag() : ( CompoundTag ) tag;
	}

	public void setLeftShoulderTag( CompoundTag tag )
	{
		metadata.set( MetadataIndex.PLAYER_LEFT_SHOULDER, tag == null ? new CompoundTag() : tag );
	}

	////////////////////////////////////////////////////////////////////////////
	// Permissions

	@Override
	public String getName()
	{
		return profile.getName();
	}

	@Override
	public InventoryView getOpenInventory()
	{
		return openInventory;
	}

	public GlowPlayerProfile getProfile()
	{
		return profile;
	}

	public CompoundTag getRightShoulderTag()
	{
		Object tag = metadata.get( MetadataIndex.PLAYER_RIGHT_SHOULDER );
		return tag == null ? new CompoundTag() : ( CompoundTag ) tag;
	}

	public void setRightShoulderTag( CompoundTag tag )
	{
		metadata.set( MetadataIndex.PLAYER_RIGHT_SHOULDER, tag == null ? new CompoundTag() : tag );
	}

	@Override
	public Entity getShoulderEntityLeft()
	{
		CompoundTag tag = getLeftShoulderTag();
		if ( tag.isEmpty() )
		{
			return null;
		}
		UUID uuid = new UUID( tag.getLong( "UUIDMost" ), tag.getLong( "UUIDLeast" ) );
		return server.getEntity( uuid );
	}

	@Override
	public void setShoulderEntityLeft( Entity entity )
	{
		if ( entity == null )
		{
			releaseLeftShoulderEntity();
		}
		else
		{
			CompoundTag tag = new CompoundTag();
			EntityStorage.save( ( GlowEntity ) entity, tag );
			setLeftShoulderTag( tag );
		}
	}

	@Override
	public Entity getShoulderEntityRight()
	{
		CompoundTag tag = getRightShoulderTag();
		if ( tag.isEmpty() )
		{
			return null;
		}
		UUID uuid = new UUID( tag.getLong( "UUIDMost" ), tag.getLong( "UUIDLeast" ) );
		return server.getEntity( uuid );
	}

	@Override
	public void setShoulderEntityRight( Entity entity )
	{
		if ( entity == null )
		{
			releaseRightShoulderEntity();
		}
		else
		{
			CompoundTag tag = new CompoundTag();
			EntityStorage.save( ( GlowEntity ) entity, tag );
			setRightShoulderTag( tag );
		}
	}

	@Override
	public int getSleepTicks()
	{
		return sleepTicks;
	}

	private GlowInventory getTopInventory()
	{
		return ( GlowInventory ) getOpenInventory().getTopInventory();
	}

	@Override
	public UUID getUniqueId()
	{
		return profile.getUniqueId();
	}

	////////////////////////////////////////////////////////////////////////////
	// Health

	@Override
	public void setUniqueId( UUID uuid )
	{
		// silently allow setting the same UUID again
		if ( !profile.getUniqueId().equals( uuid ) )
		{
			throw new IllegalStateException( "UUID of " + this + " is already " + profile.getUniqueId() );
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// Inventory

	public int getXpSeed()
	{
		return xpSeed;
	}

	public void setXpSeed( int xpSeed )
	{
		this.xpSeed = xpSeed;
	}

	// Drop items left in crafting area.
	private void handleUnusedInputs()
	{
		for ( int i = 0; i < getTopInventory().getSlots().size(); i++ )
		{
			ItemStack itemStack = getOpenInventory().getItem( i );
			if ( InventoryUtil.isEmpty( itemStack ) )
			{
				continue;
			}

			if ( isDroppableCraftingSlot( i ) )
			{
				getOpenInventory().getBottomInventory().addItem( itemStack );
				getOpenInventory().getTopInventory().setItem( i, InventoryUtil.createEmptyStack() );
			}
		}
	}

	@Override
	public boolean hasPermission( String name )
	{
		return permissions.hasPermission( name );
	}

	@Override
	public boolean hasPermission( Permission perm )
	{
		return permissions.hasPermission( perm );
	}

	@Override
	public boolean isBlocking()
	{
		return false;
	}

	private boolean isDroppableCraftingSlot( int i )
	{
		if ( getTopInventory().getSlot( i ).getType() == SlotType.CRAFTING )
		{
			switch ( getTopInventory().getType() )
			{
				case BREWING:
				case FURNACE:
					return false;
				default:
					return true;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean isOp()
	{
		return op;
	}

	@Override
	public void setOp( boolean value )
	{
		op = value;
		recalculatePermissions();
	}

	@Override
	public boolean isPermissionSet( String name )
	{
		return permissions.isPermissionSet( name );
	}

	@Override
	public boolean isPermissionSet( Permission perm )
	{
		return permissions.isPermissionSet( perm );
	}

	@Override
	public boolean isSleeping()
	{
		return sleeping;
	}

	@Override
	public InventoryView openEnchanting( Location location, boolean force )
	{
		if ( location == null )
		{
			location = getLocation();
		}
		if ( !force && location.getBlock().getType() != Material.ENCHANTMENT_TABLE )
		{
			return null;
		}
		return openInventory( new GlowEnchantingInventory( location, ( GlowPlayer ) this ) );
	}

	@Override
	public InventoryView openInventory( Inventory inventory )
	{
		InventoryView view = new GlowInventoryView( this, inventory );
		openInventory( view );
		return view;
	}

	@Override
	public void openInventory( InventoryView inventory )
	{
		checkNotNull( inventory );
		this.inventory.getDragTracker().reset();

		// stop viewing the old inventory and start viewing the new one
		removeViewer( openInventory.getTopInventory() );
		removeViewer( openInventory.getBottomInventory() );
		openInventory = inventory;
		addViewer( openInventory.getTopInventory() );
		addViewer( openInventory.getBottomInventory() );
	}

	@Override
	public InventoryView openWorkbench( Location location, boolean force )
	{
		if ( location == null )
		{
			location = getLocation();
		}
		if ( !force && location.getBlock().getType() != Material.WORKBENCH )
		{
			return null;
		}
		return openInventory( new GlowCraftingInventory( this, InventoryType.WORKBENCH ) );
	}

	/**
	 * Process changes to the human enitity's armor, and update the entity's armor attributes
	 * accordingly.
	 */
	private void processArmorChanges()
	{
		GlowPlayer player = null;
		if ( this instanceof GlowPlayer )
		{
			player = ( ( GlowPlayer ) this );
		}
		boolean armorUpdate = false;
		List<EquipmentMonitor.Entry> armorChanges = getEquipmentMonitor().getArmorChanges();
		if ( armorChanges.size() > 0 )
		{
			for ( EquipmentMonitor.Entry entry : armorChanges )
			{
				if ( player != null && needsArmorUpdate )
				{
					player.getSession().send( new EntityEquipmentMessage( 0, entry.slot, entry.item ) );
				}
				armorUpdate = true;
			}
		}
		if ( armorUpdate )
		{
			getAttributeManager().setProperty( AttributeManager.Key.KEY_ARMOR, ArmorConstants.getDefense( getEquipment().getArmorContents() ) );
			getAttributeManager().setProperty( AttributeManager.Key.KEY_ARMOR_TOUGHNESS, ArmorConstants.getToughness( getEquipment().getArmorContents() ) );
		}
		needsArmorUpdate = true;
	}

	@Override
	public void pulse()
	{
		super.pulse();
		if ( sleeping )
		{
			++sleepTicks;
		}
		else
		{
			sleepTicks = 0;
		}
		processArmorChanges();
	}

	@Override
	public void recalculatePermissions()
	{
		permissions.recalculatePermissions();
	}

	@Override
	public Entity releaseLeftShoulderEntity()
	{
		CompoundTag tag = getLeftShoulderTag();
		GlowEntity shoulderEntity = null;
		if ( !tag.isEmpty() )
		{
			shoulderEntity = EntityStorage.loadEntity( world, tag );
			shoulderEntity.setRawLocation( getLocation() );
		}
		setLeftShoulderTag( null );
		return shoulderEntity;
	}

	@Override
	public Entity releaseRightShoulderEntity()
	{
		CompoundTag tag = getRightShoulderTag();
		GlowEntity shoulderEntity = null;
		if ( !tag.isEmpty() )
		{
			shoulderEntity = EntityStorage.loadEntity( world, tag );
			shoulderEntity.setRawLocation( getLocation() );
		}
		setRightShoulderTag( null );
		return shoulderEntity;
	}

	@Override
	public void removeAttachment( PermissionAttachment attachment )
	{
		permissions.removeAttachment( attachment );
	}

	private void removeViewer( Inventory inventory )
	{
		if ( inventory instanceof GlowInventory )
		{
			( ( GlowInventory ) inventory ).removeViewer( this );
		}
	}

	private void resetInventoryView()
	{
		openInventory( new GlowInventoryView( this ) );
	}

	@Override
	public void setFireTicks( int ticks )
	{
		if ( gameMode == GameMode.SURVIVAL || gameMode == GameMode.ADVENTURE )
		{
			super.setFireTicks( ticks );
		}
	}

	@Override
	public boolean setWindowProperty( Property prop, int value )
	{
		// nb: does not actually send anything
		return prop.getType() == openInventory.getType();
	}
}
