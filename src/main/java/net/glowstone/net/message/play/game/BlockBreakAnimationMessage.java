package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public class BlockBreakAnimationMessage implements Message
{
	private final int destroyStage;
	private final int id;
	private final int x;
	private final int y;
	private final int z;

	public BlockBreakAnimationMessage( int id, int x, int y, int z, int destroyStage )
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.destroyStage = destroyStage;
	}

	public int getDestroyStage()
	{
		return destroyStage;
	}

	public int getId()
	{
		return id;
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
