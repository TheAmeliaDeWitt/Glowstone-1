package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import net.glowstone.util.nbt.CompoundTag;

public final class UpdateBlockEntityMessage implements Message
{
	private final int action;
	private final CompoundTag nbt;
	private final int x;
	private final int y;
	private final int z;

	public UpdateBlockEntityMessage( int x, int y, int z, int action, CompoundTag nbt )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.action = action;
		this.nbt = nbt;
	}

	public int getAction()
	{
		return action;
	}

	public CompoundTag getNbt()
	{
		return nbt;
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
