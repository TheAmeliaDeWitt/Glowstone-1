package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import net.glowstone.util.TextMessage;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

import java.util.UUID;

public class BossBarMessage implements Message
{
	private final Action action;
	private final Color color;
	private final Division division;
	private final byte flags;
	private final float health;
	private final TextMessage title;
	private final UUID uuid;

	//For action 1, remove
	public BossBarMessage( UUID uuid, Action action )
	{
		this( uuid, action, null, 0f, null, null, ( byte ) 0 );
	}

	//For action 2, update health
	public BossBarMessage( UUID uuid, Action action, float health )
	{
		this( uuid, action, null, health, null, null, ( byte ) 0 );
	}

	//For action 3, update title
	public BossBarMessage( UUID uuid, Action action, TextMessage title )
	{
		this( uuid, action, title, 0f, null, null, ( byte ) 0 );
	}

	//For action 4, update style
	public BossBarMessage( UUID uuid, Action action, Color color, Division division )
	{
		this( uuid, action, null, 0f, color, division, ( byte ) 0 );
	}

	//For action 5, update flags
	public BossBarMessage( UUID uuid, Action action, byte flags )
	{
		this( uuid, action, null, 0f, null, null, flags );
	}

	public BossBarMessage( UUID uuid, Action action, TextMessage title, float health, Color color, Division division, byte flags )
	{
		this.uuid = uuid;
		this.action = action;
		this.title = title;
		this.health = health;
		this.color = color;
		this.division = division;
		this.flags = flags;
	}

	public Action getAction()
	{
		return action;
	}

	public Color getColor()
	{
		return color;
	}

	public Division getDivision()
	{
		return division;
	}

	public byte getFlags()
	{
		return flags;
	}

	public float getHealth()
	{
		return health;
	}

	public TextMessage getTitle()
	{
		return title;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public enum Action
	{
		ADD,
		REMOVE,
		UPDATE_HEALTH,
		UPDATE_TITLE,
		UPDATE_STYLE,
		UPDATE_FLAGS;

		private static Action[] values;

		/**
		 * Since values() is expensive, we cache it.
		 *
		 * @param i the ordinal to look up
		 *
		 * @return the Action with ordinal {@code i}
		 *
		 * @throws ArrayIndexOutOfBoundsException if {@code values()[i]} doesn't exist
		 */
		public static Action fromInt( int i )
		{
			if ( values == null )
			{
				values = Action.values();
			}
			return values[i];
		}
	}

	public enum Color
	{
		PINK,
		BLUE,
		RED,
		GREEN,
		YELLOW,
		PURPLE,
		WHITE;

		private static Color[] values;

		/**
		 * Converts a {@link BarColor} to an instance of this enum.
		 *
		 * @param barColor the bar color to convert
		 *
		 * @return the bar color as a Color
		 */
		public static Color fromBarColor( BarColor barColor )
		{
			if ( values == null )
			{
				values = Color.values();
			}
			return values[barColor.ordinal()];
		}

		/**
		 * Since values() is expensive, we cache it.
		 *
		 * @param i the ordinal to look up
		 *
		 * @return the Color with ordinal {@code i}
		 *
		 * @throws ArrayIndexOutOfBoundsException if {@code values()[i]} doesn't exist
		 */
		public static Color fromInt( int i )
		{
			if ( values == null )
			{
				values = Color.values();
			}
			return values[i];
		}
	}

	public enum Division
	{
		NO_DIVISION,
		SIX_NOTCHES,
		TEN_NOTCHES,
		TWELVE_NOTCHES,
		TWENTY_NOTCHES;

		private static Division[] values;

		/**
		 * Converts a {@link BarStyle} to an instance of this enum.
		 *
		 * @param barStyle the bar style to convert
		 *
		 * @return the bar style as a Division
		 */
		public static Division fromBarStyle( BarStyle barStyle )
		{
			if ( values == null )
			{
				values = Division.values();
			}
			return values[barStyle.ordinal()];
		}

		/**
		 * Since values() is expensive, we cache it.
		 *
		 * @param i the ordinal to look up
		 *
		 * @return the Action with ordinal {@code i}
		 *
		 * @throws ArrayIndexOutOfBoundsException if {@code values()[i]} doesn't exist
		 */
		public static Division fromInt( int i )
		{
			if ( values == null )
			{
				values = Division.values();
			}
			return values[i];
		}
	}
}
