package net.glowstone.net.codec.play.player;

import com.flowpowered.network.Codec;

import net.glowstone.net.message.play.player.PlayerAbilitiesMessage;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public final class PlayerAbilitiesCodec implements Codec<PlayerAbilitiesMessage>
{
	@Override
	public PlayerAbilitiesMessage decode( ByteBuf buf ) throws IOException
	{
		int flags = buf.readUnsignedByte();
		float flySpeed = buf.readFloat();
		float walkSpeed = buf.readFloat();
		return new PlayerAbilitiesMessage( flags, flySpeed, walkSpeed );
	}

	@Override
	public ByteBuf encode( ByteBuf buf, PlayerAbilitiesMessage message ) throws IOException
	{
		buf.writeByte( message.getFlags() );
		buf.writeFloat( message.getFlySpeed() );
		buf.writeFloat( message.getWalkSpeed() );
		return buf;
	}
}
