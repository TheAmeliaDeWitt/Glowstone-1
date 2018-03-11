package net.glowstone.net.codec.play.game;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;

import net.glowstone.net.message.play.game.PlayParticleMessage;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;

public final class PlayParticleCodec implements Codec<PlayParticleMessage>
{
	@Override
	public PlayParticleMessage decode( ByteBuf buf ) throws IOException
	{
		throw new DecoderException( "Cannot decode PlayParticleMessage" );
	}

	@Override
	public ByteBuf encode( ByteBuf buf, PlayParticleMessage message ) throws IOException
	{
		buf.writeInt( message.getParticle() );
		buf.writeBoolean( message.isLongDistance() );
		buf.writeFloat( message.getX() );
		buf.writeFloat( message.getY() );
		buf.writeFloat( message.getZ() );
		buf.writeFloat( message.getOffsetX() );
		buf.writeFloat( message.getOffsetY() );
		buf.writeFloat( message.getOffsetZ() );
		buf.writeFloat( message.getData() );
		buf.writeInt( message.getCount() );

		for ( int extData : message.getExtData() )
		{
			ByteBufUtils.writeVarInt( buf, extData );
		}

		return buf;
	}
}
