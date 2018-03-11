package net.glowstone.util.nbt;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

/**
 * The types of NBT tags that exist.
 */
public enum TagType
{
	END( "End", null, Void.class ),
	BYTE( "Byte", ByteTag.class, byte.class ),
	SHORT( "Short", ShortTag.class, short.class ),
	INT( "Int", IntTag.class, int.class ),
	LONG( "Long", LongTag.class, long.class ),
	FLOAT( "Float", FloatTag.class, float.class ),
	DOUBLE( "Double", DoubleTag.class, double.class ),
	BYTE_ARRAY( "Byte_Array", ByteArrayTag.class, byte[].class ),
	STRING( "String", StringTag.class, String.class ),
	LIST( "List", ListTag.class, List.class ),
	COMPOUND( "Compound", CompoundTag.class, Map.class ),
	INT_ARRAY( "Int_Array", IntArrayTag.class, int[].class );

	/**
	 * Returns the tag type with a given ID.
	 *
	 * @param id the ID to look up
	 *
	 * @return the tag type with ID {@code id}, or null if none exists
	 */
	public static TagType byId( int id )
	{
		if ( id < 0 || id >= values().length )
		{
			return null;
		}
		return values()[id];
	}

	/**
	 * Returns the tag type with a given ID.
	 *
	 * @param id the ID to look up
	 *
	 * @return the tag type with ID {@code id}
	 *
	 * @throws IOException if {@code id} doesn't match any tag type
	 */
	static TagType byIdOrError( int id ) throws IOException
	{
		if ( id < 0 || id >= values().length )
		{
			throw new IOException( "Invalid tag type: " + id );
		}
		return values()[id];
	}

	private final String name;
	private final Class<? extends Tag> tagClass;
	private final Class<?> valueClass;

	TagType( String name, Class<? extends Tag> tagClass, Class<?> valueClass )
	{
		this.name = name;
		this.tagClass = tagClass;
		this.valueClass = valueClass;
	}

	public Constructor<? extends Tag> getConstructor() throws NoSuchMethodException
	{
		return tagClass.getConstructor( valueClass );
	}

	public byte getId()
	{
		return ( byte ) ordinal();
	}

	public String getName()
	{
		return name;
	}
}
