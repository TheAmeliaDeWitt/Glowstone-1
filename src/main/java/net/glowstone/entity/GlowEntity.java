package net.glowstone.entity;

import com.flowpowered.network.Message;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.glowstone.EventFactory;
import net.glowstone.GlowServer;
import net.glowstone.GlowWorld;
import net.glowstone.chunk.GlowChunk;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataIndex.StatusFlags;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.entity.meta.MetadataMap.Entry;
import net.glowstone.entity.objects.GlowItemFrame;
import net.glowstone.entity.objects.GlowLeashHitch;
import net.glowstone.entity.objects.GlowPainting;
import net.glowstone.entity.physics.BoundingBox;
import net.glowstone.entity.physics.EntityBoundingBox;
import net.glowstone.net.GlowSession;
import net.glowstone.net.message.play.entity.AttachEntityMessage;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;
import net.glowstone.net.message.play.entity.EntityRotationMessage;
import net.glowstone.net.message.play.entity.EntityStatusMessage;
import net.glowstone.net.message.play.entity.EntityTeleportMessage;
import net.glowstone.net.message.play.entity.EntityVelocityMessage;
import net.glowstone.net.message.play.entity.RelativeEntityPositionMessage;
import net.glowstone.net.message.play.entity.RelativeEntityPositionRotationMessage;
import net.glowstone.net.message.play.entity.SetPassengerMessage;
import net.glowstone.net.message.play.player.InteractEntityMessage;
import net.glowstone.util.Position;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.entity.EntityUnleashEvent;
import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.metadata.MetadataStoreBase;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents some entity in the world such as an item on the floor or a player.
 *
 * @author Graham Edgecombe
 */
public abstract class GlowEntity implements Entity
{
	/**
	 * An ID to use in network messages when the protocol calls for a 32-bit entity ID, but the
	 * relevant Object doesn't exist, isn't an Entity, or is in a different World (and thus a
	 * different space for 32-bit entity IDs).
	 */
	public static final int ENTITY_ID_NOBODY = -1;
	/**
	 * The metadata store for entities.
	 */
	private static final MetadataStore<Entity> bukkitMetadata = new EntityMetadataStore();
	private static final Vector zeroG = new Vector();
	/**
	 * The current position.
	 */
	protected final Location location;
	/**
	 * The entity's metadata.
	 */
	protected final MetadataMap metadata = new MetadataMap( getClass() );
	/**
	 * The position in the last cycle.
	 */
	protected final Location previousLocation;
	/**
	 * The server this entity belongs to.
	 */
	protected final GlowServer server;
	/**
	 * The entity's velocity, applied each tick.
	 */
	protected final Vector velocity = new Vector();
	/**
	 * Lock to prevent concurrent modifications affected by switching worlds.
	 */
	protected final ReadWriteLock worldLock = new ReentrantReadWriteLock();
	/**
	 * List of custom String data for the entity.
	 */
	private final List<String> customTags = Lists.newArrayList();
	/**
	 * All entities that currently have this entity as leash holder.
	 */
	private final List<GlowEntity> leashedEntities = Lists.newArrayList();
	/**
	 * The original location of this entity.
	 */
	private final Location origin;
	/**
	 * A list of entities currently riding this entity.
	 */
	private final List<Entity> passengers = new ArrayList<>();
	/**
	 * A flag indicating if this entity is currently active.
	 */
	protected boolean active = true;
	/**
	 * Velocity reduction applied each tick in air, y component.
	 */
	protected double airDrag = 0.98;
	/**
	 * The entity's bounding box, or null if it has no physical presence.
	 */
	protected EntityBoundingBox boundingBox;
	/**
	 * This entity's current identifier for its world.
	 */
	protected int entityId;
	/**
	 * Gravity acceleration applied each tick.
	 */
	protected Vector gravityAccel = new Vector( 0, -0.04, 0 );
	/**
	 * Velocity reduction applied each tick in liquids.
	 */
	protected double liquidDrag = 0.8;
	protected boolean passengerChanged;
	/**
	 * Whether this entity was forcibly removed from the world.
	 */
	protected boolean removed;
	/**
	 * The slipperiness multiplier applied according to the block this entity was on.
	 */
	protected double slipMultiplier = 0.6;
	/**
	 * Whether the entity should have its position resent as if teleported.
	 */
	protected boolean teleported;
	/**
	 * A counter of how long this entity has existed.
	 */
	protected int ticksLived;
	/**
	 * The entity this entity is currently riding.
	 */
	protected GlowEntity vehicle;
	/**
	 * Whether the entity should have its velocity resent.
	 */
	protected boolean velocityChanged;
	/**
	 * The world this entity belongs to. Guarded by {@link #worldLock}.
	 */
	protected GlowWorld world;
	/**
	 * The distance the entity is currently falling without touching the ground.
	 */
	private float fallDistance;
	/**
	 * How long the entity has been on fire, or 0 if it is not.
	 */
	private int fireTicks;
	/**
	 * Whether friction applies to the entity.
	 */
	private boolean friction = true;
	/**
	 * Whether gravity applies to the entity.
	 */
	private boolean gravity = true;
	/**
	 * Velocity reduction applied each tick in air, x and z components.
	 */
	private double horizontalAirDrag = 0.91;
	/**
	 * Whether this entity is invulnerable.
	 */
	private boolean invulnerable;
	/**
	 * An EntityDamageEvent representing the last damage cause on this entity.
	 */
	private EntityDamageEvent lastDamageCause;
	/**
	 * The leash holder of the entity.
	 */
	private GlowEntity leashHolder;
	/**
	 * Has the leash holder of the entity changed.
	 */
	private boolean leashHolderChanged;
	/**
	 * The leash holders uuid of the entity. Will be null after the entities first tick.
	 */
	private UUID leashHolderUniqueId;
	/**
	 * A flag indicting if the entity is on the ground.
	 */
	private boolean onGround = true;
	/**
	 * Whether this entity has operator permissions.
	 */
	private boolean op;
	/**
	 * The Nether portal cooldown for the entity.
	 */
	private int portalCooldown;
	private Spigot spigot = new Spigot()
	{
		@Override
		public boolean isInvulnerable()
		{
			return GlowEntity.this.isInvulnerable();
		}
	};
	/**
	 * This entity's unique id.
	 */
	private UUID uuid;
	/**
	 * Creates an entity and adds it to the specified world.
	 *
	 * @param location The location of the entity.
	 */
	public GlowEntity( Location location )
	{
		// this is so dirty I washed my hands after writing it.
		if ( this instanceof GlowPlayer )
		{
			// spawn location event
			location = EventFactory.callEvent( new PlayerSpawnLocationEvent( ( Player ) this, location ) ).getSpawnLocation();
		}
		this.origin = location.clone();
		this.location = location.clone();
		world = ( GlowWorld ) location.getWorld();
		server = world.getServer();
		server.getEntityIdManager().allocate( this );
		world.getEntityManager().register( this );
		previousLocation = location.clone();
	}

	@Override
	public PermissionAttachment addAttachment( Plugin plugin, String s, boolean b )
	{
		return null;
	}

	@Override
	public PermissionAttachment addAttachment( Plugin plugin )
	{
		return null;
	}

	@Override
	public PermissionAttachment addAttachment( Plugin plugin, String s, boolean b, int i )
	{
		return null;
	}

	@Override
	public PermissionAttachment addAttachment( Plugin plugin, int i )
	{
		return null;
	}

	@Override
	public boolean addPassenger( Entity passenger )
	{
		Preconditions.checkArgument( !this.equals( passenger ), "Entity cannot ride itself." );

		if ( passenger == null || passengers.contains( passenger ) )
		{
			return false; // nothing changed
		}

		if ( !( passenger instanceof GlowEntity ) )
		{
			return false;
		}

		GlowEntity glowPassenger = ( GlowEntity ) passenger;

		if ( glowPassenger.vehicle != null )
		{
			glowPassenger.vehicle.removePassenger( passenger );
		}

		EntityMountEvent event = new EntityMountEvent( passenger, this );
		EventFactory.callEvent( event );
		if ( event.isCancelled() )
		{
			return false;
		}

		passengerChanged = true;
		glowPassenger.vehicle = this;

		glowPassenger.teleport( getMountLocation() );

		return this.passengers.add( passenger );
	}

	@Override
	public boolean addScoreboardTag( String tag )
	{
		// todo: 1.11
		return false;
	}

	/**
	 * Collide with the target block.
	 *
	 * @param block a block whose type {@link Material#isOccluding()}
	 */
	public void collide( Block block )
	{
		// No-op by default.
	}

	/**
	 * Creates a List of {@link Message} which can be sent to a client directly after the entity is
	 * spawned.
	 *
	 * @param session Session to update this entity for
	 *
	 * @return A message which can spawn this entity.
	 */
	public List<Message> createAfterSpawnMessage( GlowSession session )
	{
		List<Message> result = Lists.newArrayList();

		GlowPlayer player = session.getPlayer();
		if ( player == null )
		{
			// Player disconnected while this task was pending
			return result;
		}
		boolean visible = player.canSeeEntity( this );
		for ( GlowEntity leashedEntity : leashedEntities )
		{
			if ( visible && player.canSeeEntity( leashedEntity ) )
			{
				int attached = player.getEntityId() == this.getEntityId() ? 0 : leashedEntity.getEntityId();
				int holder = this.getEntityId();

				result.add( new AttachEntityMessage( attached, holder ) );
			}
		}

		if ( isLeashed() && visible && player.canSeeEntity( leashHolder ) )
		{
			int attached = player.getEntityId() == this.getEntityId() ? 0 : this.getEntityId();
			int holder = leashHolder.getEntityId();

			result.add( new AttachEntityMessage( attached, holder ) );
		}

		return result;
	}

	/**
	 * Creates a list of {@link Message}s which can be sent to a client to spawn this entity.
	 * Implementations in concrete subclasses may return a shallowly immutable list.
	 *
	 * @return A list of messages which can spawn this entity.
	 */
	public abstract List<Message> createSpawnMessage();

	/**
	 * Creates a {@link Message} which can be sent to a client to update this entity.
	 *
	 * @param session Session to update this entity for
	 *
	 * @return A message which can update this entity.
	 */
	public List<Message> createUpdateMessage( GlowSession session )
	{
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();

		double dx = x * 32 - previousLocation.getX() * 32;
		double dy = y * 32 - previousLocation.getY() * 32;
		double dz = z * 32 - previousLocation.getZ() * 32;

		dx *= 128;
		dy *= 128;
		dz *= 128;

		boolean teleport = dx > Short.MAX_VALUE || dy > Short.MAX_VALUE || dz > Short.MAX_VALUE || dx < Short.MIN_VALUE || dy < Short.MIN_VALUE || dz < Short.MIN_VALUE;

		List<Message> result = new LinkedList<>();

		boolean moved = hasMoved();
		boolean rotated = hasRotated();

		if ( teleported || moved && teleport )
		{
			result.add( new EntityTeleportMessage( entityId, location ) );
		}
		else if ( rotated )
		{
			int yaw = Position.getIntYaw( location );
			int pitch = Position.getIntPitch( location );
			if ( moved )
			{
				result.add( new RelativeEntityPositionRotationMessage( entityId, ( short ) dx, ( short ) dy, ( short ) dz, yaw, pitch ) );
			}
			else
			{
				result.add( new EntityRotationMessage( entityId, yaw, pitch ) );
			}
		}
		else if ( moved )
		{
			result.add( new RelativeEntityPositionMessage( entityId, ( short ) dx, ( short ) dy, ( short ) dz ) );
		}

		// send changed metadata
		List<Entry> changes = metadata.getChanges();
		if ( !changes.isEmpty() )
		{
			result.add( new EntityMetadataMessage( entityId, changes ) );
		}

		// send velocity if needed
		if ( velocityChanged )
		{
			result.add( new EntityVelocityMessage( entityId, velocity ) );
		}

		if ( passengerChanged )
		{
			// A player can be a passenger of any arbitrary entity, e.g. a boat
			// In case the current session belongs to this player passenger
			// We need to send the self_id
			List<Integer> passengerIds = new ArrayList<>();
			getPassengers().forEach( e -> {
				if ( session.getPlayer().equals( e ) )
				{
					passengerIds.add( GlowPlayer.SELF_ID );
				}
				else
				{
					passengerIds.add( e.getEntityId() );
				}
			} );
			result.add( new SetPassengerMessage( getEntityId(), passengerIds.stream().mapToInt( Integer::intValue ).toArray() ) );
			passengerChanged = false;
		}

		if ( leashHolderChanged )
		{
			int attached = isLeashed() && session.getPlayer().getEntityId() == leashHolder.getEntityId() ? 0 : this.getEntityId();
			int holder = !isLeashed() ? -1 : leashHolder.getEntityId();

			// When the leashHolder is not visible, the AttachEntityMessage will be created in
			// createAfterSpawnMessage()
			if ( !isLeashed() || session.getPlayer().canSeeEntity( leashHolder ) )
			{
				result.add( new AttachEntityMessage( attached, holder ) );
			}
		}

		return result;
	}

	public void damage( double amount )
	{
		damage( amount, null, DamageCause.CUSTOM );
	}

	public void damage( double amount, Entity source )
	{
		damage( amount, source, DamageCause.CUSTOM );
	}

	public void damage( double amount, DamageCause cause )
	{
		damage( amount, null, cause );
	}

	public void damage( double amount, Entity source, DamageCause cause )
	{
	}

	@Override
	public boolean eject()
	{
		return !isEmpty() && setPassenger( null );
	}

	public boolean entityInteract( GlowPlayer player, InteractEntityMessage message )
	{
		// Override in subclasses to implement behavior
		return false;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
		{
			return true;
		}
		if ( obj == null )
		{
			return false;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		GlowEntity other = ( GlowEntity ) obj;
		return entityId == other.entityId;
	}

	private void followLead()
	{
		if ( !isLeashed() )
		{
			return;
		}

		double distanceSquared = location.distanceSquared( leashHolder.getLocation() );

		// No need to move when already close enough
		if ( distanceSquared < 2 * 2 )
		{
			return;
		}

		// TODO: Physics are not right
		// For example, gravity is not respected

		// Leashes break when the distance between leashholder and leashedentity is greater than
		// 10 blocks
		if ( distanceSquared > 10 * 10 )
		{
			// break leashitch, if the entity is the only one left attached
			// will also destroy all remaining leashes
			if ( EntityType.LEASH_HITCH.equals( leashHolder.getType() ) && leashHolder.leashedEntities.size() == 1 )
			{
				leashHolder.remove();
			}
			else
			{
				// break leash
				unleash( this, UnleashReason.DISTANCE );
			}
			return;
		}

		if ( !leashHolder.hasMoved() )
		{
			this.setVelocity( new Vector( 0, 0, 0 ) );
			return;
		}

		// Don't move when already close enough
		double speed = distanceSquared / ( 10.0 * 10.0 * 2 );

		Vector direction = leashHolder.getLocation().toVector().subtract( location.toVector() );
		direction.normalize();

		direction.multiply( speed );
		this.setVelocity( direction );
	}

	@Override
	public boolean fromMobSpawner()
	{
		// TODO: Implementation (1.12.1)
		return false;
	}

	/**
	 * Get the direction (SOUTH, WEST, NORTH, or EAST) this entity is facing.
	 *
	 * @return The cardinal BlockFace of this entity.
	 */
	public BlockFace getCardinalFacing()
	{
		double rot = getLocation().getYaw() % 360;
		if ( rot < 0 )
		{
			rot += 360.0;
		}
		if ( 0 <= rot && rot < 45 )
		{
			return BlockFace.SOUTH;
		}
		else if ( 45 <= rot && rot < 135 )
		{
			return BlockFace.WEST;
		}
		else if ( 135 <= rot && rot < 225 )
		{
			return BlockFace.NORTH;
		}
		else if ( 225 <= rot && rot < 315 )
		{
			return BlockFace.EAST;
		}
		else if ( 315 <= rot && rot < 360.0 )
		{
			return BlockFace.SOUTH;
		}
		else
		{
			return BlockFace.EAST;
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// Command sender

	@Override
	public String getCustomName()
	{
		String name = metadata.getString( MetadataIndex.NAME_TAG );
		if ( name == null || name.isEmpty() )
		{
			name = "";
		}
		return name;
	}

	@Override
	public void setCustomName( String name )
	{
		if ( name == null )
		{
			name = "";
		}

		if ( name.length() > 64 )
		{
			name = name.substring( 0, 64 );
		}

		metadata.set( MetadataIndex.NAME_TAG, name ); // remove ?
	}

	public List<String> getCustomTags()
	{
		return customTags;
	}

	public Location getDismountLocation()
	{
		return this.location;
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions()
	{
		return null;
	}

	@Override
	public int getEntityId()
	{
		return entityId;
	}

	/**
	 * Gets the full direction (including SOUTH_SOUTH_EAST etc) this entity is facing.
	 *
	 * @return The intercardinal BlockFace of this entity
	 */
	public BlockFace getFacing()
	{
		long facing = Math.round( getLocation().getYaw() / 22.5 ) + 8;
		return Position.getDirection( ( byte ) ( facing % 16 ) );
	}

	@Override
	public float getFallDistance()
	{
		return fallDistance;
	}

	@Override
	public void setFallDistance( float distance )
	{
		fallDistance = Math.max( distance, 0 );
	}

	////////////////////////////////////////////////////////////////////////////
	// Core properties

	@Override
	public int getFireTicks()
	{
		return fireTicks;
	}

	@Override
	public void setFireTicks( int fireTicks )
	{
		this.fireTicks = fireTicks;
	}

	/**
	 * Returns the change in velocity per physics tick due to gravity.
	 *
	 * @return the change in velocity per physics tick due to gravity
	 */
	public Vector getGravityAccel()
	{
		if ( this.gravity )
		{
			return this.gravityAccel;
		}
		else
		{
			return GlowEntity.zeroG;
		}
	}

	public void setGravityAccel( Vector gravityAccel )
	{
		this.gravityAccel = gravityAccel;
	}

	////////////////////////////////////////////////////////////////////////////
	// Location stuff

	@Override
	public double getHeight()
	{
		return boundingBox.getSize().getY();
	}

	public double getHorizontalAirDrag()
	{
		return horizontalAirDrag;
	}

	public void setHorizontalAirDrag( double horizontalAirDrag )
	{
		this.horizontalAirDrag = horizontalAirDrag;
	}

	@Override
	public EntityDamageEvent getLastDamageCause()
	{
		return lastDamageCause;
	}

	@Override
	public void setLastDamageCause( EntityDamageEvent lastDamageCause )
	{
		this.lastDamageCause = lastDamageCause;
	}

	/**
	 * Gets the entity that is currently leading this entity.
	 *
	 * @return the entity holding the leash
	 *
	 * @throws IllegalStateException if not currently leashed
	 * @see org.bukkit.entity.LivingEntity#getLeashHolder()
	 */
	public Entity getLeashHolder() throws IllegalStateException
	{
		if ( !isLeashed() )
		{
			throw new IllegalStateException( "Entity not leashed" );
		}

		return leashHolder;
	}

	public List<GlowEntity> getLeashedEntities()
	{
		return leashedEntities;
	}

	@Override
	public Location getLocation()
	{
		return location.clone();
	}

	@Override
	public Location getLocation( Location loc )
	{
		return Position.copyLocation( location, loc );
	}

	@Override
	public int getMaxFireTicks()
	{
		return 160;  // this appears to be Minecraft's default value
	}

	@Override
	public List<MetadataValue> getMetadata( String metadataKey )
	{
		return bukkitMetadata.getMetadata( this, metadataKey );
	}

	////////////////////////////////////////////////////////////////////////////
	// Internals

	public Location getMountLocation()
	{
		return this.location.clone().add( 0, this.getHeight(), 0 );
	}

	@Override
	public String getName()
	{
		return getType().getName();
	}

	@Override
	public List<Entity> getNearbyEntities( double x, double y, double z )
	{
		// This behavior is similar to CraftBukkit, where a call with args
		// (0, 0, 0) finds any entities whose bounding boxes intersect that of
		// this entity.

		BoundingBox searchBox;
		if ( boundingBox == null )
		{
			searchBox = BoundingBox.fromPositionAndSize( location.toVector(), new Vector( 0, 0, 0 ) );
		}
		else
		{
			searchBox = BoundingBox.copyOf( boundingBox );
		}
		Vector vec = new Vector( x, y, z );
		searchBox.minCorner.subtract( vec );
		searchBox.maxCorner.add( vec );

		return world.getEntityManager().getEntitiesInside( searchBox, this );
	}

	@Override
	public Location getOrigin()
	{
		return origin;
	}

	@Override
	public Entity getPassenger()
	{
		if ( passengers.size() > 0 )
		{
			return passengers.get( 0 );
		}
		return null;
	}

	@Override
	public List<Entity> getPassengers()
	{
		return Collections.unmodifiableList( passengers );
	}

	@Override
	public PistonMoveReaction getPistonMoveReaction()
	{
		// TODO: Implementation (1.12.1)
		return null;
	}

	@Override
	public int getPortalCooldown()
	{
		return portalCooldown;
	}

	@Override
	public void setPortalCooldown( int portalCooldown )
	{
		this.portalCooldown = portalCooldown;
	}

	@Override
	public Set<String> getScoreboardTags()
	{
		// todo: 1.11
		return null;
	}

	@Override
	public GlowServer getServer()
	{
		return server;
	}

	@Override
	public int getTicksLived()
	{
		return ticksLived;
	}

	@Override
	public void setTicksLived( int ticksLived )
	{
		this.ticksLived = ticksLived;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.UNKNOWN;
	}

	@Override
	public UUID getUniqueId()
	{
		if ( uuid == null )
		{
			uuid = UUID.randomUUID();
		}
		return uuid;
	}

	/**
	 * Sets this entity's unique identifier if possible.
	 *
	 * @param uuid The new UUID. Must not be null.
	 *
	 * @throws IllegalArgumentException if the passed UUID is null.
	 * @throws IllegalStateException    if a UUID has already been set.
	 */
	public void setUniqueId( UUID uuid )
	{
		checkNotNull( uuid, "uuid must not be null" );
		if ( this.uuid == null )
		{
			this.uuid = uuid;
		}
		else if ( !this.uuid.equals( uuid ) )
		{
			// silently allow setting the same UUID, since
			// it can't be checked with getUniqueId()
			throw new IllegalStateException( "UUID of " + this + " is already " + this.uuid );
		}
	}

	@Override
	public GlowEntity getVehicle()
	{
		return vehicle;
	}

	@Override
	public Vector getVelocity()
	{
		return velocity.clone();
	}

	@Override
	public void setVelocity( Vector velocity )
	{
		this.velocity.copy( velocity );
		velocityChanged = true;
	}

	////////////////////////////////////////////////////////////////////////////
	// Physics stuff

	@Override
	public double getWidth()
	{
		return boundingBox.getSize().getX();
	}

	@Override
	public GlowWorld getWorld()
	{
		return world;
	}

	public boolean hasFriction()
	{
		return friction;
	}

	@Override
	public boolean hasGravity()
	{
		return gravity;
	}

	@Override
	public boolean hasMetadata( String metadataKey )
	{
		return bukkitMetadata.hasMetadata( this, metadataKey );
	}

	/**
	 * Checks if this entity has moved this cycle.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasMoved()
	{
		return Position.hasMoved( location, previousLocation );
	}

	@Override
	public boolean hasPermission( String s )
	{
		return false;
	}

	@Override
	public boolean hasPermission( Permission permission )
	{
		return false;
	}

	/**
	 * Checks if this entity has rotated this cycle.
	 *
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean hasRotated()
	{
		return Position.hasRotated( location, previousLocation );
	}

	@Override
	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = prime * result + entityId;
		return result;
	}

	////////////////////////////////////////////////////////////////////////////
	// Various properties

	public boolean intersects( BoundingBox box )
	{
		return boundingBox != null && boundingBox.intersects( box );
	}

	@Override
	public boolean isCustomNameVisible()
	{
		return metadata.getBoolean( MetadataIndex.SHOW_NAME_TAG );
	}

	@Override
	public void setCustomNameVisible( boolean flag )
	{
		metadata.set( MetadataIndex.SHOW_NAME_TAG, flag );
	}

	@Override
	public boolean isDead()
	{
		return !active;
	}

	////////////////////////////////////////////////////////////////////////////
	// Miscellaneous actions

	@Override
	public boolean isEmpty()
	{
		return getPassengers().isEmpty();
	}

	public boolean isFriction()
	{
		return friction;
	}

	public void setFriction( boolean friction )
	{
		this.friction = friction;
	}

	@Override
	public boolean isGlowing()
	{
		return metadata.getBit( MetadataIndex.STATUS, StatusFlags.GLOWING );
	}

	////////////////////////////////////////////////////////////////////////////
	// Entity stacking

	@Override
	public void setGlowing( boolean glowing )
	{
		metadata.setBit( MetadataIndex.STATUS, StatusFlags.GLOWING, glowing );
	}

	@Override
	public boolean isInsideVehicle()
	{
		return getVehicle() != null;
	}

	////////////////////////////////////////////////////////////////////////////
	// Custom name

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

	public boolean isLeashed()
	{
		return leashHolder != null;
	}

	@Override
	public boolean isOnGround()
	{
		return onGround;
	}

	/**
	 * Sets the on-ground flag and clears fall distance.
	 *
	 * @param onGround true if this entity is now on the ground; false otherwise
	 */
	public void setOnGround( boolean onGround )
	{
		if ( this.onGround != onGround )
		{
			setFallDistance( 0 );
		}
		this.onGround = onGround;
	}

	@Override
	public boolean isOp()
	{
		return op;
	}

	@Override
	public void setOp( boolean op )
	{
		this.op = op;
	}

	@Override
	public boolean isPermissionSet( String s )
	{
		return false;
	}

	@Override
	public boolean isPermissionSet( Permission permission )
	{
		return false;
	}

	public boolean isRemoved()
	{
		return removed;
	}

	@Override
	public boolean isSilent()
	{
		return metadata.getBoolean( MetadataIndex.SILENT );
	}

	@Override
	public void setSilent( boolean silent )
	{
		metadata.set( MetadataIndex.SILENT, silent );
	}

	public boolean isTeleported()
	{
		return teleported;
	}

	/**
	 * Determine if this entity is intersecting a block of the specified type.
	 *
	 * <p>If the entity has a defined bounding box, that is used to check for intersection.
	 * Otherwise, a less accurate calculation using only the entity's location and its surrounding
	 * blocks are used.
	 *
	 * @param material The material to check for.
	 *
	 * @return True if the entity is intersecting
	 */
	public boolean isTouchingMaterial( Material material )
	{
		if ( boundingBox == null )
		{
			// less accurate calculation if no bounding box is present
			for ( BlockFace face : new BlockFace[] {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH, BlockFace.DOWN, BlockFace.SELF, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST} )
			{
				if ( getLocation().getBlock().getRelative( face ).getType() == material )
				{
					return true;
				}
			}
		}
		else
		{
			// bounding box-based calculation
			Vector min = boundingBox.minCorner;
			Vector max = boundingBox.maxCorner;
			for ( int x = min.getBlockX(); x <= max.getBlockX(); ++x )
			{
				for ( int y = min.getBlockY(); y <= max.getBlockY(); ++y )
				{
					for ( int z = min.getBlockZ(); z <= max.getBlockZ(); ++z )
					{
						if ( world.getBlockTypeIdAt( x, y, z ) == material.getId() )
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isValid()
	{
		return world.getEntityManager().getEntity( entityId ) == this;
	}

	/**
	 * Checks if this entity is within the visible radius of another.
	 *
	 * @param other The other entity.
	 *
	 * @return {@code true} if the entities can see each other, {@code false} if not.
	 */
	public boolean isWithinDistance( GlowEntity other )
	{
		if ( other instanceof GlowLivingEntity )
		{
			return ( ( GlowLivingEntity ) other ).getDeathTicks() <= 20 && isWithinDistance( other.location );
		}
		else
		{
			return !other.isDead() && ( isWithinDistance( other.location ) || other instanceof GlowLightningStrike );
		}
	}

	/**
	 * Checks if this entity is within the visible radius of a location.
	 *
	 * @param loc The location.
	 *
	 * @return {@code true} if the entities can see each other, {@code false} if not.
	 */
	public boolean isWithinDistance( Location loc )
	{
		double dx = Math.abs( location.getX() - loc.getX() );
		double dz = Math.abs( location.getZ() - loc.getZ() );
		return loc.getWorld() == getWorld() && dx <= server.getViewDistance() * GlowChunk.WIDTH && dz <= server.getViewDistance() * GlowChunk.HEIGHT;
	}

	@Override
	public boolean leaveVehicle()
	{
		return isInsideVehicle() && vehicle.removePassenger( this );
	}

	@Override
	public void playEffect( EntityEffect type )
	{
		EntityStatusMessage message = new EntityStatusMessage( entityId, type );
		world.getRawPlayers().stream().filter( player -> player.canSeeEntity( this ) ).forEach( player -> player.getSession().send( message ) );
	}

	/**
	 * Called every game cycle. Subclasses should implement this to implement periodic functionality
	 * e.g. mob AI.
	 */
	public void pulse()
	{
		ticksLived++;
		if ( !getLocation().getChunk().isLoaded() )
		{
			return;
		}

		if ( fireTicks > 0 )
		{
			--fireTicks;
		}
		metadata.setBit( MetadataIndex.STATUS, StatusFlags.ON_FIRE, fireTicks > 0 );

		// resend position if it's been a while, causes ItemFrames to disappear and GlowPaintings
		// to dislocate.
		if ( ticksLived % ( 30 * 20 ) == 0 )
		{
			if ( !( this instanceof GlowItemFrame || this instanceof GlowPainting ) )
			{
				teleported = true;
			}
		}

		if ( this instanceof GlowLivingEntity && !isDead() && ( ( GlowLivingEntity ) this ).hasAI() && this.getLocation().getChunk().isLoaded() )
		{
			GlowLivingEntity entity = ( GlowLivingEntity ) this;
			entity.getTaskManager().pulse();
		}

		followLead();

		pulsePhysics();

		if ( hasMoved() )
		{
			Block currentBlock = location.getBlock();
			if ( currentBlock.getType() == Material.ENDER_PORTAL )
			{
				EventFactory.callEvent( new EntityPortalEnterEvent( this, currentBlock.getLocation() ) );
				if ( server.getAllowEnd() )
				{
					Location previousLocation = location.clone();
					boolean success;
					if ( getWorld().getEnvironment() == Environment.THE_END )
					{
						success = teleportToSpawn();
					}
					else
					{
						success = teleportToEnd();
					}
					if ( success )
					{
						EntityPortalExitEvent e = EventFactory.callEvent( new EntityPortalExitEvent( this, previousLocation, location.clone(), velocity.clone(), new Vector() ) );
						if ( !e.getAfter().equals( velocity ) )
						{
							setVelocity( e.getAfter() );
						}
					}
				}
			}
		}

		if ( leashHolderUniqueId != null && ticksLived < 2 )
		{
			Optional<GlowEntity> any = world.getEntityManager().getAll().stream().filter( e -> leashHolderUniqueId.equals( e.getUniqueId() ) ).findAny();
			if ( !any.isPresent() )
			{
				world.dropItemNaturally( location, new ItemStack( Material.LEASH ) );
			}
			setLeashHolder( any.orElse( null ) );
			leashHolderUniqueId = null;
		}
	}

	protected void pulsePhysics()
	{
		Location velLoc = location.clone().add( velocity );
		final Block block = velLoc.getBlock();
		if ( block.getType().isSolid() )
		{
			Location velLocY = location.clone().add( 0, velocity.getY(), 0 );
			if ( velLocY.getBlock().getType().isSolid() )
			{
				velocity.setY( 0 );
			}
			Location velLocX = location.clone().add( velocity.getX(), 0, 0 );
			if ( velLocX.getBlock().getType().isSolid() )
			{
				velocity.setX( 0 );
			}
			Location velLocZ = location.clone().add( 0, 0, velocity.getZ() );
			if ( velLocZ.getBlock().getType().isSolid() )
			{
				velocity.setZ( 0 );
			}
			collide( block );
		}
		else
		{
			if ( hasFriction() )
			{
				// apply friction and gravity
				if ( location.getBlock().getType() == Material.WATER )
				{
					velocity.multiply( liquidDrag );
					velocity.setY( velocity.getY() + getGravityAccel().getY() / 4d );
				}
				else if ( location.getBlock().getType() == Material.LAVA )
				{
					velocity.multiply( liquidDrag - 0.3 );
					velocity.setY( velocity.getY() + getGravityAccel().getY() / 4d );
				}
				else
				{
					velocity.setY( airDrag * ( velocity.getY() + getGravityAccel().getY() ) );
					if ( isOnGround() )
					{
						velocity.setX( velocity.getX() * slipMultiplier );
						velocity.setZ( velocity.getZ() * slipMultiplier );
					}
					else
					{
						velocity.setX( velocity.getX() * horizontalAirDrag );
						velocity.setZ( velocity.getZ() * horizontalAirDrag );
					}
				}
			}
			else if ( hasGravity() && !isOnGround() )
			{
				switch ( location.getBlock().getType() )
				{
					case WATER:
					case LAVA:
						velocity.setY( velocity.getY() + getGravityAccel().getY() / 4d );
						break;
					default:
						velocity.setY( velocity.getY() + getGravityAccel().getY() / 4d );
				}
			}
			setRawLocation( velLoc );
		}
	}

	@Override
	public void recalculatePermissions()
	{

	}

	/**
	 * Destroys this entity by removing it from the world and marking it as not being active.
	 */
	@Override
	public void remove()
	{
		removed = true;
		active = false;
		boundingBox = null;
		world.getEntityManager().unregister( this );
		server.getEntityIdManager().deallocate( this );
		this.setPassenger( null );

		ImmutableList.copyOf( this.leashedEntities ).forEach( e -> unleash( e, UnleashReason.HOLDER_GONE ) );

		if ( isLeashed() )
		{
			unleash( this, UnleashReason.HOLDER_GONE );
		}
	}

	@Override
	public void removeAttachment( PermissionAttachment permissionAttachment )
	{

	}

	@Override
	public void removeMetadata( String metadataKey, Plugin owningPlugin )
	{
		bukkitMetadata.removeMetadata( this, metadataKey, owningPlugin );
	}

	@Override
	public boolean removePassenger( Entity passenger )
	{
		Preconditions.checkArgument( !this.equals( passenger ), "Entity cannot ride itself." );

		if ( passenger == null || !passengers.contains( passenger ) )
		{
			return false; // nothing changed
		}

		if ( EventFactory.callEvent( new EntityDismountEvent( passenger, this ) ).isCancelled() )
		{
			return false;
		}

		if ( !( passenger instanceof GlowEntity ) )
		{
			return false;
		}

		GlowEntity glowPassenger = ( GlowEntity ) passenger;

		passengerChanged = true;
		glowPassenger.vehicle = null;

		glowPassenger.teleport( getDismountLocation() );

		return passengers.remove( passenger );
	}

	@Override
	public boolean removeScoreboardTag( String tag )
	{
		// todo: 1.11
		return false;
	}

	/**
	 * Resets the previous location and other properties to their current value.
	 */
	public void reset()
	{
		Position.copyLocation( location, previousLocation );
		metadata.resetChanges();
		teleported = false;
		velocityChanged = false;
		leashHolderChanged = false;
	}

	////////////////////////////////////////////////////////////////////////////
	// Metadata

	@Override
	public void sendMessage( String s )
	{
		throw new UnsupportedOperationException( "Not implemented yet." );
	}

	@Override
	public void sendMessage( String[] strings )
	{
		throw new UnsupportedOperationException( "Not implemented yet." );
	}

	protected final void setBoundingBox( double xz, double y )
	{
		boundingBox = new EntityBoundingBox( xz, y );
		updateBoundingBox();
	}

	/**
	 * Sets the velocity multiplier due to drag. For example, if the multiplier is 0.98, the entity
	 * will lose 2% of its velocity each physics tick. Set to 1.0 to disable drag.
	 *
	 * @param drag   the new drag rate
	 * @param liquid true to set liquid drag; false to set air drag
	 */
	public void setDrag( double drag, boolean liquid )
	{
		if ( liquid )
		{
			liquidDrag = drag;
		}
		else
		{
			airDrag = drag;
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// Permissions

	@Override
	public void setGravity( boolean gravity )
	{
		this.gravity = gravity;
	}

	/**
	 * Sets the leash on this entity to be held by the supplied entity.
	 *
	 * <p>This method has no effect on EnderDragons, Withers, Players, or Bats. Non-living entities
	 * excluding leashes will not persist as leash holders.
	 *
	 * @param holder the entity to leash this entity to
	 *
	 * @return whether the operation was successful
	 *
	 * @see org.bukkit.entity.LivingEntity#setLeashHolder(Entity)
	 */
	public boolean setLeashHolder( Entity holder )
	{
		// "This method has no effect on EnderDragons, Withers, Players, or Bats"
		if ( !GlowLeashHitch.isAllowedLeashHolder( this.getType() ) )
		{
			return false;
		}

		if ( this.leashHolder != null )
		{
			this.leashHolder.leashedEntities.remove( this );
		}

		if ( holder == null )
		{
			// unleash
			this.leashHolder = null;
			leashHolderChanged = true;
			return true;
		}

		if ( holder.isDead() )
		{
			return false;
		}

		this.leashHolder = ( GlowEntity ) holder;
		this.leashHolder.leashedEntities.add( this );
		leashHolderChanged = true;
		return true;
	}

	/**
	 * Set the unique ID of this entities leash holder. Only useful during load of the entity.
	 */
	public void setLeashHolderUniqueId( UUID uniqueId )
	{
		if ( ticksLived > 1 || isLeashed() )
		{
			return;
		}
		this.leashHolderUniqueId = uniqueId;
	}

	@Override
	public void setMetadata( String metadataKey, MetadataValue newMetadataValue )
	{
		bukkitMetadata.setMetadata( this, metadataKey, newMetadataValue );
	}

	@Override
	public boolean setPassenger( Entity newPassenger )
	{
		Preconditions.checkArgument( !this.equals( newPassenger ), "Entity cannot ride itself." );

		boolean result = false;
		for ( Entity passenger : Lists.newArrayList( passengers ) )
		{
			if ( !Objects.equals( passenger, newPassenger ) )
			{
				result = !removePassenger( passenger );
			}
		}

		if ( newPassenger != null && passengers.size() == 0 )
		{
			result = !addPassenger( newPassenger );
		}

		return !result;
	}

	/**
	 * Sets this entity's location.
	 *
	 * @param location The new location.
	 * @param fall     Whether to calculate fall damage or not.
	 */
	public void setRawLocation( Location location, boolean fall )
	{
		if ( location.getWorld() != world )
		{
			throw new IllegalArgumentException( "Cannot setRawLocation to a different world (got " + location.getWorld() + ", expected " + world + ")" );
		}

		if ( Objects.equals( location, previousLocation ) )
		{
			return;
		}

		if ( teleported )
		{
			teleported = false;
		}

		world.getEntityManager().move( this, location );
		Position.copyLocation( location, this.location );

		updateBoundingBox();

		Material type = location.getBlock().getType();

		if ( hasMoved() )
		{
			if ( !fall || type == Material.LADDER // todo: horses are not affected
					|| type == Material.VINE // todo: horses are not affected
					|| type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.WEB || type == Material.TRAP_DOOR || type == Material.IRON_TRAPDOOR || onGround )
			{
				setFallDistance( 0 );
			}
			else if ( location.getY() < previousLocation.getY() && !isInsideVehicle() )
			{
				setFallDistance( ( float ) ( fallDistance + previousLocation.getY() - location.getY() ) );
			}
		}

		if ( fall && !( this instanceof GlowPlayer ) )
		{
			setOnGround( location.clone().add( new Vector( 0, -1, 0 ) ).getBlock().getType().isSolid() );
		}
	}

	/**
	 * Sets this entity's location and applies fall damage calculations.
	 *
	 * @param location The new location.
	 */
	public void setRawLocation( Location location )
	{
		setRawLocation( location, true );
	}

	protected void setSize( float xz, float y )
	{
		setBoundingBox( xz, y );
	}

	/**
	 * Checks whether this entity should be saved as part of the world.
	 *
	 * @return True if the entity should be saved.
	 */
	public boolean shouldSave()
	{
		return true;
	}

	public Spigot spigot()
	{
		return spigot;
	}

	@Override
	public boolean teleport( Location location )
	{
		checkNotNull( location, "location cannot be null" );
		checkNotNull( location.getWorld(), "location's world cannot be null" );
		worldLock.writeLock().lock();
		try
		{
			if ( location.getWorld() != world )
			{
				world.getEntityManager().unregister( this );
				world = ( GlowWorld ) location.getWorld();
				world.getEntityManager().register( this );
			}
		}
		finally
		{
			worldLock.writeLock().unlock();
		}
		setRawLocation( location, false );
		teleported = true;
		return true;
	}

	@Override
	public boolean teleport( Entity destination )
	{
		return teleport( destination.getLocation() );
	}

	@Override
	public boolean teleport( Location location, TeleportCause cause )
	{
		return teleport( location );
	}

	@Override
	public boolean teleport( Entity destination, TeleportCause cause )
	{
		return teleport( destination.getLocation(), cause );
	}

	/**
	 * Teleport this entity to the End. If no End world is loaded this does nothing.
	 *
	 * @return {@code true} if the teleport was successful.
	 */
	protected boolean teleportToEnd()
	{
		if ( !server.getAllowEnd() )
		{
			return false;
		}
		Location target = null;
		for ( World world : server.getWorlds() )
		{
			if ( world.getEnvironment() == Environment.THE_END )
			{
				target = world.getSpawnLocation();
				break;
			}
		}
		if ( target == null )
		{
			return false;
		}

		EntityPortalEvent event = EventFactory.callEvent( new EntityPortalEvent( this, location.clone(), target, null ) );
		if ( event.isCancelled() )
		{
			return false;
		}
		target = event.getTo();

		teleport( target );
		return true;
	}

	/**
	 * Teleport this entity to the spawn point of the main world. This is used to teleport out of
	 * the End.
	 *
	 * @return {@code true} if the teleport was successful.
	 */
	protected boolean teleportToSpawn()
	{
		Location target = server.getWorlds().get( 0 ).getSpawnLocation();

		EntityPortalEvent event = EventFactory.callEvent( new EntityPortalEvent( this, location.clone(), target, null ) );
		if ( event.isCancelled() )
		{
			return false;
		}
		target = event.getTo();

		teleport( target );
		return true;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName();
	}

	private void unleash( GlowEntity entity, UnleashReason reason )
	{
		EventFactory.callEvent( new EntityUnleashEvent( entity, reason ) );
		world.dropItemNaturally( entity.location, new ItemStack( Material.LEASH ) );
		entity.setLeashHolder( null );
	}

	protected void updateBoundingBox()
	{
		// make sure bounding box is up to date
		if ( boundingBox != null )
		{
			boundingBox.setCenter( location.getX(), location.getY(), location.getZ() );
		}
	}

	/**
	 * The metadata store class for entities.
	 */
	private static class EntityMetadataStore extends MetadataStoreBase<Entity> implements MetadataStore<Entity>
	{

		@Override
		protected String disambiguate( Entity subject, String metadataKey )
		{
			return subject.getUniqueId() + ":" + metadataKey;
		}
	}
}
