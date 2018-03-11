package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class BlockActionMessage implements Message
{
	private final int blockType;
	private final int data1;
	private final int data2;
	private final int x;
	private final int y;
	private final int z;

	public BlockActionMessage( int x, int y, int z, int data1, int data2, int blockType )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.data1 = data1;
		this.data2 = data2;
		this.blockType = blockType;
	}

	public int getBlockType()
	{
		return blockType;
	}

	public int getData1()
	{
		return data1;
	}

	public int getData2()
	{
		return data2;
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
