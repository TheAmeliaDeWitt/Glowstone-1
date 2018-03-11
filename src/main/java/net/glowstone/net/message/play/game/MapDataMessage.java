package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public final class MapDataMessage implements Message
{
	private final List<Icon> icons;
	private final int id;
	private final int scale;
	private final Section section;

	public MapDataMessage( short id, int scale, List<Icon> icons, Section section )
	{
		this.id = id;
		this.scale = scale;
		this.icons = icons;
		this.section = section;
	}

	public List<Icon> getIcons()
	{
		return icons;
	}

	public int getId()
	{
		return id;
	}

	public int getScale()
	{
		return scale;
	}

	public Section getSection()
	{
		return section;
	}

	public static class Icon
	{
		public final int facing;
		public final int type;
		public final int x;
		public final int y;

		public Icon( int facing, int type, int x, int y )
		{
			this.facing = facing;
			this.type = type;
			this.x = x;
			this.y = y;
		}

		public int getFacing()
		{
			return facing;
		}

		public int getType()
		{
			return type;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}
	}

	public static class Section
	{
		public final byte[] data;
		public final int height;
		public final int width;
		public final int x;
		public final int y;

		/**
		 * Creates an instance.
		 *
		 * @param width  the section width
		 * @param height the section height
		 * @param x      the x offset
		 * @param y      the y offset
		 * @param data   the data
		 */
		public Section( int width, int height, int x, int y, byte... data )
		{
			checkArgument( width * height == data.length, "width * height == data.length" );
			this.width = width;
			this.height = height;
			this.x = x;
			this.y = y;
			this.data = data;
		}

		public byte[] getData()
		{
			return data;
		}

		public int getHeight()
		{
			return height;
		}

		public int getWidth()
		{
			return width;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}
	}
}
