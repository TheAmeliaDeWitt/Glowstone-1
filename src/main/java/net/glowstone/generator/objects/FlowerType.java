package net.glowstone.generator.objects;

import org.bukkit.Material;

public enum FlowerType
{
	DANDELION( Material.YELLOW_FLOWER, 0 ),
	POPPY( Material.RED_ROSE, 0 ),
	BLUE_ORCHID( Material.RED_ROSE, 1 ),
	ALLIUM( Material.RED_ROSE, 2 ),
	HOUSTONIA( Material.RED_ROSE, 3 ),
	TULIP_RED( Material.RED_ROSE, 4 ),
	TULIP_ORANGE( Material.RED_ROSE, 5 ),
	TULIP_WHITE( Material.RED_ROSE, 6 ),
	TULIP_PINK( Material.RED_ROSE, 7 ),
	OXEYE_DAISY( Material.RED_ROSE, 8 );

	private final int data;
	private final Material type;

	FlowerType( Material type, int data )
	{
		this.type = type;
		this.data = data;
	}

	public int getData()
	{
		return data;
	}

	public Material getType()
	{
		return type;
	}
}

