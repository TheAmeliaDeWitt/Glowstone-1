package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import org.bukkit.inventory.EquipmentSlot;

public class UseItemMessage implements Message
{
	private final int hand;

	public UseItemMessage( int hand )
	{
		this.hand = hand;
	}

	public EquipmentSlot getEquipmentSlot()
	{
		return hand == 1 ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;
	}

	public int getHand()
	{
		return hand;
	}
}
