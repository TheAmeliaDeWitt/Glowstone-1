package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public class UnloadChunkMessage implements Message
{
	private final int chunkX;
	private final int chunkZ;

	public UnloadChunkMessage( int chunkX, int chunkZ )
	{
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
	}

	public int getChunkZ()
	{
		return chunkZ;
	}

	public int getChunkX()
	{
		return chunkX;
	}
}
