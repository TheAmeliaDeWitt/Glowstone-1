package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import net.glowstone.util.nbt.CompoundTag;

import java.util.Collection;
import java.util.Set;

import io.netty.buffer.ByteBuf;

public final class ChunkDataMessage implements Message
{
	private final Collection<CompoundTag> blockEntities;
	private final boolean continuous;
	private final ByteBuf data;
	private final int primaryMask;
	private final int x;
	private final int z;

	public ChunkDataMessage( int x, int z, boolean continuous, int primaryMask, ByteBuf data, Set<CompoundTag> blockEntities )
	{
		this.x = x;
		this.z = z;
		this.continuous = continuous;
		this.primaryMask = primaryMask;
		this.data = data;
		this.blockEntities = blockEntities;
	}

	public Collection<CompoundTag> getBlockEntities()
	{
		return blockEntities;
	}

	public ByteBuf getData()
	{
		return data;
	}

	public int getPrimaryMask()
	{
		return primaryMask;
	}

	public int getX()
	{
		return x;
	}

	public int getZ()
	{
		return z;
	}

	public boolean isContinuous()
	{
		return continuous;
	}
}
