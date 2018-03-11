package net.glowstone.net.codec.play.entity;

import com.flowpowered.network.Codec;
import com.flowpowered.network.util.ByteBufUtils;

import net.glowstone.net.message.play.entity.SetPassengerMessage;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public class SetPassengerCodec implements Codec<SetPassengerMessage>
{
	@Override
	public SetPassengerMessage decode( ByteBuf buffer ) throws IOException
	{
		int entityId = ByteBufUtils.readVarInt( buffer );
		int length = ByteBufUtils.readVarInt( buffer );
		int[] passengers = new int[length];
		for ( int i = 0; i < length; i++ )
		{
			passengers[i] = ByteBufUtils.readVarInt( buffer );
		}
		return new SetPassengerMessage( entityId, passengers );
	}

	@Override
	public ByteBuf encode( ByteBuf buf, SetPassengerMessage message ) throws IOException
	{
		ByteBufUtils.writeVarInt( buf, message.getEntityId() );
		ByteBufUtils.writeVarInt( buf, message.getPassengers().length );
		for ( int passenger : message.getPassengers() )
		{
			ByteBufUtils.writeVarInt( buf, passenger );
		}
		return buf;
	}
}
