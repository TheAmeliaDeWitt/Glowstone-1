package net.glowstone;

import net.glowstone.entity.meta.profile.GlowPlayerProfile;
import net.glowstone.entity.meta.profile.ProfileCache;
import net.glowstone.io.PlayerDataService.PlayerReader;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a player which is not connected to the server.
 */
@SerializableAs( "Player" )
public final class GlowOfflinePlayer implements OfflinePlayer
{
	/**
	 * Required method for configuration serialization.
	 *
	 * @param val map to deserialize
	 *
	 * @return deserialized player record
	 *
	 * @see org.bukkit.configuration.serialization.ConfigurationSerializable
	 */
	@SuppressWarnings( "UnusedDeclaration" )
	public static OfflinePlayer deserialize( Map<String, Object> val )
	{
		if ( val.get( "name" ) != null )
		{
			// use name
			return Bukkit.getServer().getOfflinePlayer( val.get( "name" ).toString() );
		}
		else
		{
			// use UUID
			return Bukkit.getServer().getOfflinePlayer( UUID.fromString( val.get( "UUID" ).toString() ) );
		}
	}

	/**
	 * Returns a Future for a GlowOfflinePlayer by UUID. If possible, the player's data (including
	 * name) will be loaded based on the UUID.
	 *
	 * @param server The server of the offline player. Must not be null.
	 * @param uuid   The UUID of the player. Must not be null.
	 *
	 * @return A {@link GlowOfflinePlayer} future.
	 */
	public static CompletableFuture<GlowOfflinePlayer> getOfflinePlayer( GlowServer server, UUID uuid )
	{
		checkNotNull( server, "server must not be null" );
		checkNotNull( uuid, "UUID must not be null" );
		return ProfileCache.getProfile( uuid ).thenApplyAsync( ( profile ) -> new GlowOfflinePlayer( server, profile ) );
	}

	private final GlowPlayerProfile profile;
	private final GlowServer server;
	private Location bedSpawnLocation;
	private long firstPlayed;
	private boolean hasPlayed;
	private String lastName;
	private long lastPlayed;

	/**
	 * Create a new offline player for the given name. If possible, the player's data will be
	 * loaded.
	 *
	 * @param server  The server of the offline player. Must not be null.
	 * @param profile The profile associated with the player. Must not be null.
	 */
	public GlowOfflinePlayer( GlowServer server, GlowPlayerProfile profile )
	{
		checkNotNull( server, "server must not be null" );
		checkNotNull( profile, "profile must not be null" );
		this.server = server;
		this.profile = profile;
		loadData();
	}

	@Override
	public boolean equals( Object o )
	{
		if ( this == o )
		{
			return true;
		}
		if ( o == null || getClass() != o.getClass() )
		{
			return false;
		}

		GlowOfflinePlayer that = ( GlowOfflinePlayer ) o;

		return profile.equals( that.profile );
	}

	@Override
	public Location getBedSpawnLocation()
	{
		return bedSpawnLocation;
	}

	@Override
	public long getFirstPlayed()
	{
		return firstPlayed;
	}

	@Override
	public long getLastPlayed()
	{
		return lastPlayed;
	}

	////////////////////////////////////////////////////////////////////////////
	// Core properties

	@Override
	public String getName()
	{
		Player player = getPlayer();
		if ( player != null )
		{
			return player.getName();
		}
		if ( profile.getName() != null )
		{
			return profile.getName();
		}
		if ( lastName != null )
		{
			return lastName;
		}
		return null;
	}

	@Override
	public Player getPlayer()
	{
		return server.getPlayer( getUniqueId() );
	}

	public GlowPlayerProfile getProfile()
	{
		return profile;
	}

	@Override
	public UUID getUniqueId()
	{
		return profile.getUniqueId();
	}

	////////////////////////////////////////////////////////////////////////////
	// Player properties

	@Override
	public boolean hasPlayedBefore()
	{
		return hasPlayed;
	}

	public int hashCode()
	{
		return getUniqueId() != null ? getUniqueId().hashCode() : 0;
	}

	////////////////////////////////////////////////////////////////////////////
	// Ban, op, whitelist

	@Override
	public boolean isBanned()
	{
		return server.getBanList( Type.NAME ).isBanned( getName() );
	}

	@Override
	public boolean isOnline()
	{
		return getPlayer() != null;
	}

	@Override
	public boolean isOp()
	{
		return server.getOpsList().containsUuid( getUniqueId() );
	}

	@Override
	public void setOp( boolean value )
	{
		if ( value )
		{
			server.getOpsList().add( this );
		}
		else
		{
			server.getOpsList().remove( profile );
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// Serialization

	@Override
	public boolean isWhitelisted()
	{
		return server.getWhitelist().containsProfile( profile );
	}

	@Override
	public void setWhitelisted( boolean value )
	{
		if ( value )
		{
			server.getWhitelist().add( this );
		}
		else
		{
			server.getWhitelist().remove( profile );
		}
	}

	private void loadData()
	{
		try ( PlayerReader reader = server.getPlayerDataService().beginReadingData( getUniqueId() ) )
		{
			hasPlayed = reader.hasPlayedBefore();
			if ( hasPlayed )
			{
				firstPlayed = reader.getFirstPlayed();
				lastPlayed = reader.getLastPlayed();
				bedSpawnLocation = reader.getBedSpawnLocation();

				String lastName = reader.getLastKnownName();
				if ( lastName != null )
				{
					this.lastName = lastName;
				}
			}
		}
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> ret = new HashMap<>();
		ret.put( "UUID", getUniqueId().toString() );
		return ret;
	}

	@Override
	public String toString()
	{
		return "GlowOfflinePlayer{" + "name='" + getName() + '\'' + ", uuid=" + getUniqueId() + '}';
	}
}
