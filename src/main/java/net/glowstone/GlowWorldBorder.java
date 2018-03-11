package net.glowstone;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.game.WorldBorderMessage;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class GlowWorldBorder implements WorldBorder
{
	private final World world;
	private Location center;
	private double damageAmount;
	private double damageBuffer;
	private long lastWorldTick;
	private double size;
	/**
	 * The target side length the world border is being resized to, in blocks.
	 *
	 * @return the target side length the world border is being resized to.
	 */
	private double sizeLerpTarget;
	/**
	 * The delay in ticks until the world border's sides should reach the target length.
	 *
	 * @return the delay until the world border's sides should reach the target length.
	 */
	private long sizeLerpTime;
	private double step;
	private int warningDistance;
	private int warningTime;

	/**
	 * Initializes a new {@link WorldBorder} for the given world.
	 *
	 * @param world the world to initialize a new {@link WorldBorder} for.
	 */
	public GlowWorldBorder( World world )
	{
		this.world = world;
		lastWorldTick = world.getFullTime();
		size = 60000000;
		sizeLerpTime = 0;
		sizeLerpTarget = size;
		step = 0;
		center = new Location( world, 0, 0, 0 );
		damageBuffer = 5;
		damageAmount = 0.2;
		warningTime = 15;
		warningDistance = 5;
	}

	private void broadcast( WorldBorderMessage message )
	{
		world.getPlayers().forEach( player -> ( ( GlowPlayer ) player ).getSession().send( message ) );
	}

	/**
	 * Creates a {@link WorldBorderMessage} containing information to initialize the world border on
	 * the client-side.
	 *
	 * @return a new {@link WorldBorderMessage} for this world border.
	 */
	public WorldBorderMessage createMessage()
	{
		return new WorldBorderMessage( WorldBorderMessage.Action.INITIALIZE, center.getX(), center.getZ(), size, sizeLerpTarget, sizeLerpTime * 1000, 29999984, warningTime, warningDistance );
	}

	@Override
	public Location getCenter()
	{
		return center;
	}

	@Override
	public void setCenter( Location location )
	{
		center = location.clone();
		broadcast( new WorldBorderMessage( WorldBorderMessage.Action.SET_CENTER, center.getX(), center.getZ() ) );
	}

	@Override
	public double getDamageAmount()
	{
		return damageAmount;
	}

	@Override
	public void setDamageAmount( double damageAmount )
	{
		this.damageAmount = damageAmount;
	}

	@Override
	public double getDamageBuffer()
	{
		return damageBuffer;
	}

	@Override
	public void setDamageBuffer( double damageBuffer )
	{
		this.damageBuffer = damageBuffer;
	}

	@Override
	public double getSize()
	{
		return size;
	}

	@Override
	public void setSize( double size )
	{
		this.size = size;
		this.sizeLerpTarget = size;
		broadcast( new WorldBorderMessage( WorldBorderMessage.Action.SET_SIZE, size ) );
	}

	public double getSizeLerpTarget()
	{
		return sizeLerpTarget;
	}

	public long getSizeLerpTime()
	{
		return sizeLerpTime;
	}

	@Override
	public int getWarningDistance()
	{
		return warningDistance;
	}

	@Override
	public void setWarningDistance( int distance )
	{
		this.warningDistance = distance;
		broadcast( new WorldBorderMessage( WorldBorderMessage.Action.SET_WARNING_BLOCKS, distance ) );
	}

	@Override
	public int getWarningTime()
	{
		return warningTime;
	}

	@Override
	public void setWarningTime( int seconds )
	{
		this.warningTime = seconds;
		broadcast( new WorldBorderMessage( WorldBorderMessage.Action.SET_WARNING_TIME, seconds ) );
	}

	@Override
	public boolean isInside( Location location )
	{
		Location max = center.clone().add( size / 2, 0, size / 2 );
		Location min = center.clone().subtract( size / 2, 0, size / 2 );
		return location.getX() <= max.getX() && location.getZ() <= max.getZ() && location.getX() >= min.getX() && location.getZ() >= min.getZ();
	}

	/**
	 * Pulses the world border for each tick.
	 *
	 * <p>Attempts to call this method more than once per tick will be ignored.
	 */
	public void pulse()
	{
		if ( lastWorldTick >= world.getFullTime() )
		{
			// The pulse method is being called more than once per tick; abort.
			return;
		}
		lastWorldTick = world.getFullTime();
		if ( step != 0 )
		{
			size += step;
			if ( Math.abs( size - sizeLerpTarget ) < 1 )
			{
				// completed
				size = sizeLerpTarget;
				sizeLerpTime = 0;
				step = 0;
			}
		}
	}

	@Override
	public void reset()
	{
		setSize( 60000000 );
		sizeLerpTime = 0;
		sizeLerpTarget = size;
		step = 0;
		setCenter( new Location( world, 0, 0, 0 ) );
		setDamageBuffer( 5 );
		setDamageAmount( 0.2 );
		setWarningTime( 15 );
		setWarningDistance( 5 );
	}

	@Override
	public void setCenter( double x, double z )
	{
		setCenter( new Location( world, x, 0, z ) );
	}

	@Override
	public void setSize( double size, long seconds )
	{
		if ( seconds <= 0 )
		{
			setSize( size );
			return;
		}
		long ticks = seconds * 20;
		step = ( size - this.size ) / ( double ) ticks;
		sizeLerpTarget = size;
		sizeLerpTime = seconds;
		broadcast( new WorldBorderMessage( WorldBorderMessage.Action.LERP_SIZE, this.size, sizeLerpTarget, sizeLerpTime * 1000 ) );
	}
}
