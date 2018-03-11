package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import org.bukkit.inventory.EquipmentSlot;

public final class BlockPlacementMessage implements Message
{
	private final float cursorX;
	private final float cursorY;
	private final float cursorZ;
	private final int direction;
	private final int hand;
	private final int x;
	private final int y;
	private final int z;

	public BlockPlacementMessage( int x, int y, int z, int direction, int hand, float cursorX, float cursorY, float cursorZ )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction;
		this.hand = hand;
		this.cursorX = cursorX;
		this.cursorY = cursorY;
		this.cursorZ = cursorZ;
	}

	public float getCursorX()
	{
		return cursorX;
	}

	public float getCursorY()
	{
		return cursorY;
	}

	public float getCursorZ()
	{
		return cursorZ;
	}

	public int getDirection()
	{
		return direction;
	}

	public int getHand()
	{
		return hand;
	}

	public EquipmentSlot getHandSlot()
	{
		return hand == 1 ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;
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
