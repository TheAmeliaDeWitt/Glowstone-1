package net.glowstone.generator.objects.trees;

import net.glowstone.util.BlockStateDelegate;

import org.bukkit.Location;

import java.util.Random;

public class MegaSpruceTree extends MegaPineTree
{
	/**
	 * Initializes this tree, preparing it to attempt to generate.
	 *
	 * @param random   the PRNG
	 * @param location the base of the trunk
	 * @param delegate the BlockStateDelegate used to check for space and to fill wood and leaf
	 *                 blocks
	 */
	public MegaSpruceTree( Random random, Location location, BlockStateDelegate delegate )
	{
		super( random, location, delegate );
		setLeavesHeight( leavesHeight + 10 );
	}
}
