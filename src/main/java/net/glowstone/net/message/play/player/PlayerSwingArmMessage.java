package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import org.bukkit.inventory.EquipmentSlot;

public final class PlayerSwingArmMessage implements Message
{
	private final int hand;

	public PlayerSwingArmMessage( int hand )
	{
		this.hand = hand;
	}

	public int getHand()
	{
		return hand;
	}

	public EquipmentSlot getHandSlot()
	{
		return hand == 1 ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND;
	}
}
