package net.glowstone.net.codec.play.player;

import com.flowpowered.network.Codec;

import net.glowstone.net.message.play.player.PlayerUpdateMessage;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public final class PlayerUpdateCodec implements Codec<PlayerUpdateMessage>
{
	@Override
	public PlayerUpdateMessage decode( ByteBuf buffer ) throws IOException
	{
		boolean onGround = buffer.readBoolean();
		return new PlayerUpdateMessage( onGround );
	}

	@Override
	public ByteBuf encode( ByteBuf buf, PlayerUpdateMessage message ) throws IOException
	{
		buf.writeBoolean( message.isOnGround() );
		return buf;
	}
}
