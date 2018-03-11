package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public final class DiggingMessage implements Message
{
	public static final int START_DIGGING = 0;
	public static final int CANCEL_DIGGING = 1;
	public static final int FINISH_DIGGING = 2;
	public static final int STATE_DROP_ITEMSTACK = 3;
	public static final int STATE_DROP_ITEM = 4;
	public static final int STATE_SHOT_ARROW_FINISH_EATING = 5;
	public static final int SWAP_ITEM_IN_HAND = 6;

	private final int face;
	private final int state;
	private final int x;
	private final int y;
	private final int z;

	public DiggingMessage( int state, int x, int y, int z, int face )
	{
		this.state = state;
		this.x = x;
		this.y = y;
		this.z = z;
		this.face = face;
	}

	public int getFace()
	{
		return face;
	}

	public int getState()
	{
		return state;
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
