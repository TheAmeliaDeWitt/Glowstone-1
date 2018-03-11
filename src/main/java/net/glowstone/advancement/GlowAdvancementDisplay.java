package net.glowstone.advancement;

import com.flowpowered.network.util.ByteBufUtils;

import net.glowstone.net.GlowBufUtils;
import net.glowstone.util.TextMessage;

import org.bukkit.inventory.ItemStack;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class GlowAdvancementDisplay
{
	private final TextMessage description;
	private final ItemStack icon;
	private final TextMessage title;
	private final FrameType type;
	private final float x;
	private final float y;

	public GlowAdvancementDisplay( TextMessage title, TextMessage description, ItemStack icon, FrameType type, float x, int y )
	{
		this.title = title;
		this.description = description;
		this.icon = icon;
		this.type = type;
		this.x = x;
		this.y = y;
	}

	/**
	 * Writes this notification to the given {@link ByteBuf}.
	 *
	 * @param buf the buffer to write to
	 *
	 * @return {@code buf}, with this notification written to it
	 *
	 * @throws IOException if a string is too long
	 */
	public ByteBuf encode( ByteBuf buf ) throws IOException
	{
		GlowBufUtils.writeChat( buf, title );
		GlowBufUtils.writeChat( buf, description );
		GlowBufUtils.writeSlot( buf, icon );
		ByteBufUtils.writeVarInt( buf, type.ordinal() );
		buf.writeInt( ( 1 << 0x4 ) ); // todo: flags
		buf.writeFloat( x );
		buf.writeFloat( y );
		return buf;
	}

	public TextMessage getDescription()
	{
		return description;
	}

	public ItemStack getIcon()
	{
		return icon;
	}

	public TextMessage getTitle()
	{
		return title;
	}

	public FrameType getType()
	{
		return type;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public enum FrameType
	{
		TASK,
		CHALLENGE,
		GOAL
	}
}
