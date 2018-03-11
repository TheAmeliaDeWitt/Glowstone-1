package net.glowstone.entity.passive;

import com.flowpowered.network.Message;

import net.glowstone.entity.GlowAmbient;
import net.glowstone.entity.meta.MetadataIndex;
import net.glowstone.net.message.play.entity.EntityHeadRotationMessage;
import net.glowstone.net.message.play.entity.SpawnMobMessage;
import net.glowstone.util.Position;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;

import java.util.LinkedList;
import java.util.List;

public class GlowBat extends GlowAmbient implements Bat
{
	public GlowBat( Location location )
	{
		super( location, 6 );
		setSize( 0.5F, 0.9F );
	}

	@Override
	public List<Message> createSpawnMessage()
	{
		List<Message> result = new LinkedList<>();

		result.add( new SpawnMobMessage( entityId, getUniqueId(), getType().getTypeId(), location, metadata.getEntryList() ) );

		// head facing
		result.add( new EntityHeadRotationMessage( entityId, Position.getIntYaw( location ) ) );
		return result;
	}

	@Override
	protected Sound getAmbientSound()
	{
		return Sound.ENTITY_BAT_AMBIENT;
	}

	@Override
	protected Sound getDeathSound()
	{
		return Sound.ENTITY_BAT_DEATH;
	}

	@Override
	protected Sound getHurtSound()
	{
		return Sound.ENTITY_BAT_HURT;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.BAT;
	}

	@Override
	public boolean isAwake()
	{
		return !metadata.getBit( MetadataIndex.BAT_FLAGS, MetadataIndex.BatFlags.IS_HANGING );
	}

	@Override
	public void setAwake( boolean isAwake )
	{
		metadata.setBit( MetadataIndex.BAT_FLAGS, MetadataIndex.BatFlags.IS_HANGING, !isAwake );
	}
}
