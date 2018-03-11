package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class SignEditorMessage implements Message
{
	private final int x;
	private final int y;
	private final int z;

	public SignEditorMessage( int x, int y, int z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
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

