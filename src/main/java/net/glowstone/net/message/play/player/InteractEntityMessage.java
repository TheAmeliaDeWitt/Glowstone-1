package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import org.bukkit.inventory.EquipmentSlot;

public final class InteractEntityMessage implements Message
{
	private final int action;
	private final int hand; // 0 = main hand, 1 = off hand
	private final int id;
	private final float targetX;
	private final float targetY;
	private final float targetZ;

	public InteractEntityMessage( int id, int action )
	{
		this( id, action, 0, 0, 0, 0 );
	}

	public InteractEntityMessage( int id, int action, int hand )
	{
		this( id, action, 0, 0, 0, hand );
	}

	public InteractEntityMessage( int id, int action, float targetX, float targetY, float targetZ, int hand )
	{
		this.id = id;
		this.action = action;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetZ = targetZ;
		this.hand = hand;
	}

	public int getAction()
	{
		return action;
	}

	public int getHand()
	{
		return hand;
	}

	public EquipmentSlot getHandSlot()
	{
		return hand == 1 ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;
	}

	public int getId()
	{
		return id;
	}

	public float getTargetX()
	{
		return targetX;
	}

	public float getTargetY()
	{
		return targetY;
	}

	public float getTargetZ()
	{
		return targetZ;
	}

	public enum Action
	{
		INTERACT,
		ATTACK,
		INTERACT_AT
	}
}

