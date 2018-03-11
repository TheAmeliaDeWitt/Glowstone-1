package net.glowstone.net.message.play.entity;

import com.flowpowered.network.Message;

import org.bukkit.util.Vector;

public final class EntityVelocityMessage implements Message
{
	private static int convert( double val )
	{
		return ( int ) ( val * 8000 );
	}

	private final int id;
	private final int velocityX;
	private final int velocityY;
	private final int velocityZ;

	public EntityVelocityMessage( int id, Vector velocity )
	{
		this( id, convert( velocity.getX() ), convert( velocity.getY() ), convert( velocity.getZ() ) );
	}

	public EntityVelocityMessage( int id, int velocityX, int velocityY, int velocityZ )
	{
		this.id = id;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}

	public int getId()
	{
		return id;
	}

	public int getVelocityX()
	{
		return velocityX;
	}

	public int getVelocityY()
	{
		return velocityY;
	}

	public int getVelocityZ()
	{
		return velocityZ;
	}
}
