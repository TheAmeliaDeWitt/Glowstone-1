package net.glowstone.net.message.handshake;

import com.flowpowered.network.AsyncableMessage;

public final class HandshakeMessage implements AsyncableMessage
{
	private final String address;
	private final int port;
	private final int state;
	private final int version;

	public HandshakeMessage( int version, String address, int port, int state )
	{
		this.version = version;
		this.address = address;
		this.port = port;
		this.state = state;
	}

	public String getAddress()
	{
		return address;
	}

	public int getPort()
	{
		return port;
	}

	public int getState()
	{
		return state;
	}

	public int getVersion()
	{
		return version;
	}

	@Override
	public boolean isAsync()
	{
		return true;
	}

}
