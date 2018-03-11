package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import net.glowstone.util.Position;

import org.bukkit.Location;

import java.util.UUID;

public final class SpawnObjectMessage implements Message
{
	public static final int BOAT = 1;
	public static final int ITEM = 2;
	public static final int ENDER_CRYSTAL = 51;
	public static final int THROWN_ENDERPEARL = 65;
	public static final int ITEM_FRAME = 71;
	public static final int FIREWORK = 76;
	public static final int LEASH_HITCH = 77;
	public static final int ARROW = 60;
	public static final int EGG = 62;
	public static final int GHAST_FIREBALL = 63;
	public static final int ENDER_DRAGON_FIREBALL = 93;
	/**
	 * Blaze fireball or Fire Charge.
	 */
	public static final int FIREBALL = 64;
	public static final int SNOWBALL = 61;
	public static final int SPECTRAL_ARROW = 91;
	public static final int SPLASH_POTION = 73;
	public static final int EXPERIENCE_BOTTLE = 75;
	public static final int WITHER_SKULL = 66;

	private final int data;
	private final int id;
	private final int pitch;
	private final int type;
	private final UUID uuid; // TODO: Handle UUID
	private final int velX;
	private final int velY;
	private final int velZ;
	private final double x;
	private final double y;
	private final int yaw;
	private final double z;

	public SpawnObjectMessage( int id, UUID uuid, int type, double x, double y, double z, int pitch, int yaw )
	{
		this( id, uuid, type, x, y, z, pitch, yaw, 0, 0, 0, 0 );
	}

	public SpawnObjectMessage( int id, UUID uuid, int type, double x, double y, double z, int pitch, int yaw, int data )
	{
		this( id, uuid, type, x, y, z, pitch, yaw, data, 0, 0, 0 );
	}

	/**
	 * Create an instance based on a location.
	 *
	 * @param id       the entity id
	 * @param uuid     the entity UUID
	 * @param type     the network ID of the entity type
	 * @param location The location whose x, y, z, pitch and yaw will be used
	 */
	public SpawnObjectMessage( int id, UUID uuid, int type, Location location )
	{
		this( id, uuid, type, location, 0 );
	}

	/**
	 * Create an instance based on a location.
	 *
	 * @param id       the entity id
	 * @param uuid     the entity UUID
	 * @param type     the network ID of the entity type
	 * @param location the location whose x, y, z, pitch and yaw will be used
	 * @param data     as defined by the entity type
	 */
	public SpawnObjectMessage( int id, UUID uuid, int type, Location location, int data )
	{
		this( id, uuid, type, location.getX(), location.getY(), location.getZ(), Position.getIntPitch( location ), Position.getIntYaw( location ), data );
	}

	public SpawnObjectMessage( int id, UUID uuid, int type, double x, double y, double z, int pitch, int yaw, int data, int velX, int velY, int velZ )
	{
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.data = data;
		this.velX = velX;
		this.velY = velY;
		this.velZ = velZ;
	}

	public int getData()
	{
		return data;
	}

	public int getId()
	{
		return id;
	}

	public int getPitch()
	{
		return pitch;
	}

	public int getType()
	{
		return type;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public int getVelX()
	{
		return velX;
	}

	public int getVelY()
	{
		return velY;
	}

	public int getVelZ()
	{
		return velZ;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public int getYaw()
	{
		return yaw;
	}

	public double getZ()
	{
		return z;
	}

	public boolean hasFireball()
	{
		return data != 0;
	}

}
