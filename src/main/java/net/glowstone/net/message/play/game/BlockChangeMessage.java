package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class BlockChangeMessage implements Message
{
	private final int type;
	private final int x;
	private final int y;
	private final int z;

	/**
	 * Creates a message indicating that a block has changed.
	 *
	 * @param x        the x coordinate
	 * @param y        the y coordinate
	 * @param z        the z coordinate
	 * @param type     the new block ID
	 * @param metadata the new block data nibble
	 */
	public BlockChangeMessage( int x, int y, int z, int type, int metadata )
	{
		this( x, y, z, type << 4 | metadata & 0xf );
	}

	public BlockChangeMessage( int x, int y, int z, int type )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	public int getType()
	{
		return type;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getZ()
	{
		return z;
	}

}
