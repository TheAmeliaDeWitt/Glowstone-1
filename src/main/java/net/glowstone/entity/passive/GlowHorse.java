package net.glowstone.entity.passive;

import com.flowpowered.network.Message;

import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.entity.meta.MetadataMap;
import net.glowstone.inventory.GlowHorseInventory;
import net.glowstone.net.message.play.entity.EntityMetadataMessage;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;

import java.util.List;
import java.util.Random;

public class GlowHorse extends GlowAbstractHorse implements Horse
{
	private Color color = Color.values()[new Random().nextInt( 6 )];
	private boolean eatingHay;
	private boolean hasReproduced;
	private HorseInventory inventory = new GlowHorseInventory( this );
	private Style style = Style.values()[new Random().nextInt( 3 )];
	private int temper;
	private Variant variant = Variant.values()[new Random().nextInt( 2 )];

	public GlowHorse( Location location )
	{
		super( location, EntityType.HORSE, 15 );
		setSize( 1.4F, 1.6F );
	}

	@Override
	public Ageable createBaby()
	{
		GlowHorse baby = ( GlowHorse ) super.createBaby();
		baby.setColor( getColor() );
		baby.setStyle( getStyle() );
		return baby;
	}

	@Override
	public List<Message> createSpawnMessage()
	{
		List<Message> messages = super.createSpawnMessage();
		MetadataMap map = new MetadataMap( GlowHorse.class );
		map.set( MetadataIndex.HORSE_STYLE, getHorseStyleData() );
		map.set( MetadataIndex.HORSE_ARMOR, getHorseArmorData() );
		messages.add( new EntityMetadataMessage( entityId, map.getEntryList() ) );
		return messages;
	}

	@Override
	protected Sound getAmbientSound()
	{
		return Sound.ENTITY_HORSE_AMBIENT;
	}

	@Override
	public Color getColor()
	{
		return color;
	}

	@Override
	public void setColor( Color color )
	{
		this.color = color;
		metadata.set( MetadataIndex.HORSE_STYLE, getHorseStyleData() );
	}

	@Override
	protected Sound getDeathSound()
	{
		return Sound.ENTITY_HORSE_DEATH;
	}

	private int getHorseArmorData()
	{
		if ( getInventory().getArmor() != null )
		{
			if ( getInventory().getArmor().getType() == Material.DIAMOND_BARDING )
			{
				return 3;
			}
			else if ( getInventory().getArmor().getType() == Material.GOLD_BARDING )
			{
				return 2;
			}
			else if ( getInventory().getArmor().getType() == Material.IRON_BARDING )
			{
				return 1;
			}
		}
		return 0;
	}

	private int getHorseStyleData()
	{
		return color.ordinal() & 0xFF | style.ordinal() << 8;
	}

	@Override
	protected Sound getHurtSound()
	{
		return Sound.ENTITY_HORSE_HURT;
	}

	@Override
	public HorseInventory getInventory()
	{
		return inventory;
	}

	public void setInventory( HorseInventory inventory )
	{
		this.inventory = inventory;
	}

	@Override
	public Style getStyle()
	{
		return style;
	}

	@Override
	public void setStyle( Style style )
	{
		this.style = style;
		metadata.set( MetadataIndex.HORSE_STYLE, getHorseStyleData() );
	}

	public int getTemper()
	{
		return temper;
	}

	public void setTemper( int temper )
	{
		this.temper = temper;
	}

	@Override
	public Variant getVariant()
	{
		return variant;
	}

	@Override
	public void setVariant( Variant variant )
	{
		this.variant = variant;
	}

	public boolean hasReproduced()
	{
		return hasReproduced;
	}

	@Override
	public boolean isCarryingChest()
	{
		// Field has been removed in 1.11
		return false;
	}

	@Override
	public void setCarryingChest( boolean b )
	{
		// Field has been removed in 1.11
	}

	public boolean isEatingHay()
	{
		return eatingHay;
	}

	public void setEatingHay( boolean eatingHay )
	{
		this.eatingHay = eatingHay;
	}

	public boolean isHasReproduced()
	{
		return hasReproduced;
	}
}
