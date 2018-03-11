package net.glowstone.entity;

import org.bukkit.Location;
import org.bukkit.entity.Explosive;

public abstract class GlowExplosive extends GlowEntity implements Explosive
{
	private boolean incendiary;
	private float yield;

	/**
	 * Creates a non-incendiary instance.
	 *
	 * @param location the location
	 * @param yield    the explosive strength
	 */
	public GlowExplosive( Location location, float yield )
	{
		super( location );
		this.yield = yield;
		incendiary = false;
	}

	@Override
	public float getYield()
	{
		return yield;
	}

	@Override
	public void setYield( float yield )
	{
		this.yield = yield;
	}

	@Override
	public boolean isIncendiary()
	{
		return incendiary;
	}

	@Override
	public void setIsIncendiary( boolean isIncendiary )
	{
		incendiary = isIncendiary;
	}
}
