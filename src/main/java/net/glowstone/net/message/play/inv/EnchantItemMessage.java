package net.glowstone.net.message.play.inv;

import com.flowpowered.network.Message;

public final class EnchantItemMessage implements Message
{
	private final int enchantment;
	private final int window;

	public EnchantItemMessage( int window, int enchantment )
	{
		this.window = window;
		this.enchantment = enchantment;
	}

	public int getEnchantment()
	{
		return enchantment;
	}

	public int getWindow()
	{
		return window;
	}
}
