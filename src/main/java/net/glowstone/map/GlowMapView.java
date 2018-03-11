package net.glowstone.map;

import net.glowstone.entity.GlowPlayer;

import org.bukkit.World;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a map item.
 */
public final class GlowMapView implements MapView
{
	private final Map<MapRenderer, Map<GlowPlayer, GlowMapCanvas>> canvases = new HashMap<>();
	private final short id;
	//private final Map<GlowPlayer, RenderData> renderCache =
	//        new HashMap<GlowPlayer, RenderData<>();
	private final List<MapRenderer> renderers = new ArrayList<>();
	private int centerX;
	private int centerZ;
	private Scale scale;
	private boolean unlimitedTracking;
	private World world;

	protected GlowMapView( World world, short id )
	{
		this.world = world;
		this.id = id;
		centerX = world.getSpawnLocation().getBlockX();
		centerZ = world.getSpawnLocation().getBlockZ();
		scale = Scale.FAR;
		addRenderer( new GlowMapRenderer( this ) );
	}

	@Override
	public void addRenderer( MapRenderer renderer )
	{
		if ( !renderers.contains( renderer ) )
		{
			renderers.add( renderer );
			canvases.put( renderer, new HashMap<>() );
			renderer.initialize( this );
		}
	}

	@Override
	public int getCenterX()
	{
		return centerX;
	}

	@Override
	public void setCenterX( int centerX )
	{
		this.centerX = centerX;
	}

	@Override
	public int getCenterZ()
	{
		return centerZ;
	}

	@Override
	public void setCenterZ( int centerZ )
	{
		this.centerZ = centerZ;
	}

	@Override
	public short getId()
	{
		return id;
	}

	@Override
	public List<MapRenderer> getRenderers()
	{
		// TODO: Defensive copy
		return renderers;
	}

	@Override
	public Scale getScale()
	{
		return scale;
	}

	@Override
	public void setScale( Scale scale )
	{
		if ( scale == null )
		{
			throw new NullPointerException();
		}
		this.scale = scale;
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public void setWorld( World world )
	{
		this.world = world;
	}

	@Override
	public boolean isUnlimitedTracking()
	{
		return unlimitedTracking;
	}

	@Override
	public void setUnlimitedTracking( boolean unlimitedTracking )
	{
		this.unlimitedTracking = unlimitedTracking;
	}

	@Override
	public boolean isVirtual()
	{
		throw new UnsupportedOperationException( "Not supported yet." );
	}

	@Override
	public boolean removeRenderer( MapRenderer renderer )
	{
		if ( renderers.contains( renderer ) )
		{
			renderers.remove( renderer );
			canvases.remove( renderer );
			return true;
		}
		else
		{
			return false;
		}
	}
}
