package net.glowstone.util.bans;

import net.glowstone.util.bans.JsonListFile.BaseEntry;

import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of BanEntry.
 */
final class GlowBanEntry implements BaseEntry, BanEntry, Cloneable
{
	private final GlowBanList list;
	private final String target;
	private Date created;
	private Date expires;
	private String reason;
	private String source;

	GlowBanEntry( GlowBanList list, String target, String reason, Date created, Date expires, String source )
	{
		if ( reason == null )
		{
			reason = "Banned";
		}
		if ( source == null )
		{
			source = "(Unknown)";
		}

		this.list = list;
		this.target = target;
		this.reason = reason;
		this.source = source;
		this.created = created;
		this.expires = expires;
	}

	@Override
	protected GlowBanEntry clone()
	{
		try
		{
			GlowBanEntry result = ( GlowBanEntry ) super.clone();
			result.created = copy( created );
			result.expires = copy( expires );
			return result;
		}
		catch ( CloneNotSupportedException e )
		{
			throw new Error( "Failed to clone GlowBanEntry", e );
		}
	}

	private Date copy( Date d )
	{
		return d == null ? null : ( Date ) d.clone();
	}

	@Override
	public Date getCreated()
	{
		return copy( created );
	}

	@Override
	public void setCreated( Date created )
	{
		this.created = copy( created );
	}

	@Override
	public Date getExpiration()
	{
		return copy( expires );
	}

	@Override
	public void setExpiration( Date expiration )
	{
		expires = copy( expiration );
	}

	@Override
	public String getReason()
	{
		return reason;
	}

	@Override
	public void setReason( String reason )
	{
		this.reason = reason;
	}

	@Override
	public String getSource()
	{
		return source;
	}

	@Override
	public void setSource( String source )
	{
		this.source = source;
	}

	@Override
	public String getTarget()
	{
		return target;
	}

	boolean isExpired()
	{
		return expires != null && expires.before( new Date() );
	}

	@Override
	public void save()
	{
		list.putEntry( this );
	}

	@Override
	public String toString()
	{
		return "GlowBanEntry{" + "type=" + list.type + ", target='" + target + '\'' + ", created=" + created + ", expires=" + expires + ", source='" + source + '\'' + ", reason='" + reason + '\'' + '}';
	}

	@Override
	public Map<String, String> write()
	{
		Map<String, String> result = new LinkedHashMap<>();

		// target
		if ( list.type == Type.NAME )
		{
			OfflinePlayer player = Bukkit.getOfflinePlayer( target );
			result.put( "uuid", player.getUniqueId().toString() );
			result.put( "name", player.getName() );
		}
		else if ( list.type == Type.IP )
		{
			result.put( "ip", target );
		}

		// other data
		result.put( "created", GlowBanList.DATE_FORMAT.format( created ) );
		result.put( "source", source );
		result.put( "expires", expires == null ? GlowBanList.FOREVER : GlowBanList.DATE_FORMAT.format( expires ) );
		result.put( "reason", reason );
		return result;
	}
}
