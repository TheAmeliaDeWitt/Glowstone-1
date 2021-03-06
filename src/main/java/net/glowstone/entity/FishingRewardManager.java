package net.glowstone.entity;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import net.glowstone.GlowServer;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Generates fishing loot.
 */
public class FishingRewardManager
{
	private final Multimap<RewardCategory, RewardItem> values = MultimapBuilder.enumKeys( RewardCategory.class ).arrayListValues().build();

	/**
	 * Creates the instance.
	 */
	public FishingRewardManager()
	{
		YamlConfiguration builtinValues = YamlConfiguration.loadConfiguration( new InputStreamReader( getClass().getClassLoader().getResourceAsStream( "builtin/fishingRewards.yml" ) ) );

		registerBuiltins( builtinValues );
	}

	/**
	 * Returns all the {@link RewardItem} instances for the items in the given category.
	 *
	 * @param category the category to look up
	 *
	 * @return a collection of the reward items in that category
	 */
	public Collection<RewardItem> getCategoryItems( RewardCategory category )
	{
		return values.get( category );
	}

	@SuppressWarnings( "unchecked" )
	private void registerBuiltins( ConfigurationSection mainSection )
	{
		ConfigurationSection valuesSection = mainSection.getConfigurationSection( "rewards" );
		if ( valuesSection == null )
		{
			GlowServer.logger.warning( "Invalid fishingRewards.yml: no 'rewards' section" );
			return;
		}
		Set<String> categories = valuesSection.getKeys( false );
		for ( String strCategory : categories )
		{
			RewardCategory category = RewardCategory.valueOf( strCategory );
			List<Map<?, ?>> items = valuesSection.getMapList( strCategory );
			for ( Map<?, ?> item : items )
			{
				values.put( category, RewardItem.deserialize( ( Map<String, Object> ) item ) );
			}
		}
	}

	/**
	 * Category names must match one-to-one with the subtags of "rewards" in
	 * builtin/fishingRewards.yml.
	 */
	public enum RewardCategory
	{
		FISH( 85.0, -1.15 ),
		TREASURE( 5.0, 2.1 ),
		JUNK( 10.0, -1.95 );

		/**
		 * Percent chance to get an item in this category, with an unenchanted fishing pole.
		 */
		private final double chance;

		/**
		 * Additional chance in % points per level of "Luck of the Sea" enchantment.
		 */
		private final double modifier;



		RewardCategory( double chance, double modifier )
		{
			this.chance = chance;
			this.modifier = modifier;
		}

		public double getChance()
		{
			return chance;
		}

		public double getModifier()
		{
			return modifier;
		}
	}

	public static class RewardItem implements ConfigurationSerializable
	{
		/**
		 * Reads a RewardItem from a YAML tag.
		 *
		 * @param itemYaml a YAML tag deserialized as a map
		 *
		 * @return {@code itemYaml} as a RewardItem, or null if {@code itemYaml} is null
		 */
		public static RewardItem deserialize( Map<String, Object> itemYaml )
		{
			if ( itemYaml == null )
			{
				return null;
			}

			RewardItem result = new RewardItem();
			if ( itemYaml.containsKey( "item" ) )
			{
				result.item = ItemStack.deserialize( ( Map<String, Object> ) itemYaml.get( "item" ) );
			}

			if ( itemYaml.containsKey( "chance" ) )
			{
				result.chance = Double.parseDouble( Objects.toString( itemYaml.get( "chance" ), "0" ) );
			}

			if ( itemYaml.containsKey( "enchantment_level_min" ) )
			{
				result.minEnchantmentLevel = getAsIntOrDefault( itemYaml, "enchantment_level_min", 0 );
				result.maxEnchantmentLevel = getAsIntOrDefault( itemYaml, "enchantment_level_max", result.minEnchantmentLevel );
			}

			return result;
		}

		private static int getAsIntOrDefault( Map<String, ?> args, String key, int defaultValue )
		{
			Object value = args.get( key );
			if ( value == null )
			{
				return defaultValue;
			}
			try
			{
				return Integer.decode( value.toString() );
			}
			catch ( NumberFormatException e )
			{
				return defaultValue;
			}
		}
		/**
		 * Chance to get this item in this category.
		 */
		private double chance;
		/**
		 * The item to drop, without any random enchantments.
		 */
		private ItemStack item;
		/**
		 * Maximum enchantment level, or zero for unenchanted item.
		 */
		private int maxEnchantmentLevel;
		/**
		 * Minimum enchantment level, or zero for unenchanted item.
		 */
		private int minEnchantmentLevel;

		public RewardItem( ItemStack item, double chance, int minEnchantmentLevel, int maxEnchantmentLevel )
		{
			this.item = item;
			this.chance = chance;
			this.minEnchantmentLevel = minEnchantmentLevel;
			this.maxEnchantmentLevel = maxEnchantmentLevel;
		}

		private RewardItem()
		{
		}

		public double getChance()
		{
			return chance;
		}

		public ItemStack getItem()
		{
			return item;
		}

		public int getMaxEnchantmentLevel()
		{
			return maxEnchantmentLevel;
		}

		public int getMinEnchantmentLevel()
		{
			return minEnchantmentLevel;
		}

		@Override
		public Map<String, Object> serialize()
		{
			Map<String, Object> args = new HashMap<>();
			args.put( "item", item.serialize() );
			args.put( "chance", chance );
			return args;
		}

		@Override
		public String toString()
		{
			return "RewardItem{item=" + item + ", chance=" + chance + ", minEnchantmentLevel=" + minEnchantmentLevel + ", maxEnchantmentLevel=" + maxEnchantmentLevel + "}";
		}
	}
}
