package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;

import java.util.List;
import java.util.Map;

public class AdvancementsMessage implements Message
{
	private final Map<NamespacedKey, Advancement> advancements;
	private final boolean clear;
	private final List<NamespacedKey> removeAdvancements;

	public AdvancementsMessage( boolean clear, Map<NamespacedKey, Advancement> advancements, List<NamespacedKey> removeAdvancements )
	{
		this.clear = clear;
		this.advancements = advancements;
		this.removeAdvancements = removeAdvancements;
	}

	public Map<NamespacedKey, Advancement> getAdvancements()
	{
		return advancements;
	}

	public List<NamespacedKey> getRemoveAdvancements()
	{
		return removeAdvancements;
	}

	public boolean isClear()
	{
		return clear;
	}
	// todo: progress
}
