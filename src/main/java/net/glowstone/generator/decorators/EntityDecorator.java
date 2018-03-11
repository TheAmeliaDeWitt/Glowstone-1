package net.glowstone.generator.decorators;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class EntityDecorator extends BlockPopulator
{
	private EntityType[] entityTypes;
	private int maxGroup = 4;
	private int minGroup = 4;
	private float rarity = 0.1f;

	public EntityDecorator( EntityType... entityTypes )
	{
		this.entityTypes = entityTypes;
	}

	public EntityType[] getEntityTypes()
	{
		return entityTypes;
	}

	public void setEntityTypes( EntityType... entityTypes )
	{
		this.entityTypes = entityTypes;
	}

	public int getMaxGroup()
	{
		return maxGroup;
	}

	public int getMinGroup()
	{
		return minGroup;
	}

	public float getRarity()
	{
		return rarity;
	}

	public void setRarity( float rarity )
	{
		this.rarity = rarity;
	}

	@Override
	public void populate( World world, Random random, Chunk chunk )
	{
		if ( entityTypes.length == 0 )
		{
			return;
		}
		if ( random.nextFloat() >= rarity )
		{
			return;
		}
		int sourceX = chunk.getX() << 4;
		int sourceZ = chunk.getZ() << 4;
		EntityType type = entityTypes[random.nextInt( entityTypes.length )];
		int centerX = sourceX + random.nextInt( 16 );
		int centerZ = sourceZ + random.nextInt( 16 );
		int count = minGroup == maxGroup ? minGroup : random.nextInt( maxGroup - minGroup ) + minGroup;
		int range = 5;
		int attempts = 5;
		for ( int i = 0; i < count; i++ )
		{
			if ( attempts == 0 )
			{
				continue;
			}
			double radius = ( double ) range * random.nextDouble();
			double angle = random.nextDouble() * Math.PI;
			double x = radius * Math.sin( angle ) + centerX;
			double z = radius * Math.cos( angle ) + centerZ;
			Block block = world.getHighestBlockAt( new Location( world, x, 0, z ) );
			if ( block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER || block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA )
			{
				i--;
				attempts--;
				continue;
			}
			attempts = 5;
			Location location = block.getLocation().clone().add( 0, 1, 0 );
			location.setYaw( random.nextFloat() * 360 - 180 );
			if ( location.getBlock().getRelative( BlockFace.DOWN ).getType() == Material.AIR )
			{
				location.subtract( 0, 1, 0 );
			}
			world.spawnEntity( location, type );
		}
	}

	public void setGroupSize( int minGroup, int maxGroup )
	{
		this.minGroup = minGroup;
		this.maxGroup = maxGroup;
	}
}
