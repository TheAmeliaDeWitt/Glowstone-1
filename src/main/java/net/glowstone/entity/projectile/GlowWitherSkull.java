package net.glowstone.entity.projectile;

import net.glowstone.net.message.play.entity.SpawnObjectMessage;

import org.bukkit.Location;
import org.bukkit.entity.WitherSkull;

public class GlowWitherSkull extends GlowFireball implements WitherSkull
{
	private boolean charged;

	public GlowWitherSkull( Location location )
	{
		super( location );
	}

	@Override
	protected int getObjectId()
	{
		return SpawnObjectMessage.WITHER_SKULL;
	}

	@Override
	public boolean isCharged()
	{
		return charged;
	}

	@Override
	public void setCharged( boolean charged )
	{
		this.charged = charged;
	}
}
