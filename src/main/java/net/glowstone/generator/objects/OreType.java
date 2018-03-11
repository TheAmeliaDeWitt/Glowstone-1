package net.glowstone.generator.objects;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.Random;

public class OreType
{
	private final int amount;
	private final MaterialData data;
	private final int maxY;
	private final int minY;
	private final Material targetType;
	private final Material type;

	public OreType( Material type, int minY, int maxY, int amount )
	{
		this( type, new MaterialData( type ), minY, maxY, amount );
	}

	public OreType( Material type, int minY, int maxY, int amount, Material targetType )
	{
		this( type, new MaterialData( type ), minY, maxY, amount, targetType );
	}

	public OreType( Material type, MaterialData data, int minY, int maxY, int amount )
	{
		this( type, data, minY, maxY, amount, Material.STONE );
	}

	/**
	 * Creates an ore type. If {@code minY} and {@code maxY} are equal, then the height range is
	 * 0 to {@code minY}*2, with greatest density around {@code minY}. Otherwise, density is uniform
	 * over the height range.
	 *
	 * @param type       the block type
	 * @param data       the block data value
	 * @param minY       the minimum height
	 * @param maxY       the maximum height
	 * @param amount     the size of a vein
	 * @param targetType the block this can replace
	 */
	public OreType( Material type, MaterialData data, int minY, int maxY, int amount, Material targetType )
	{
		this.type = type;
		this.data = data;
		this.minY = minY;
		this.maxY = maxY;
		this.amount = ++amount;
		this.targetType = targetType;
	}

	public int getAmount()
	{
		return amount;
	}

	public MaterialData getData()
	{
		return data;
	}

	public int getMaxY()
	{
		return maxY;
	}

	public int getMinY()
	{
		return minY;
	}

	/**
	 * Generates a random height at which a vein of this ore can spawn.
	 *
	 * @param random the PRNG to use
	 *
	 * @return a random height for this ore
	 */
	public int getRandomHeight( Random random )
	{
		return getMinY() == getMaxY() ? random.nextInt( getMinY() ) + random.nextInt( getMinY() ) : random.nextInt( getMaxY() - getMinY() ) + getMinY();
	}

	public Material getTargetType()
	{
		return targetType;
	}

	public Material getType()
	{
		return type;
	}
}
