package net.glowstone.net.protocol;

/**
 * Enumeration of the different Minecraft protocol states.
 */
public enum ProtocolType
{
	HANDSHAKE( new HandshakeProtocol() ),
	STATUS( new StatusProtocol() ),
	LOGIN( new LoginProtocol() ),
	PLAY( new PlayProtocol() );

	/**
	 * Get a GlowProtocol corresponding to this protocol type.
	 *
	 * @return A matching GlowProtocol.
	 */
	private final GlowProtocol protocol;

	ProtocolType( GlowProtocol protocol )
	{
		this.protocol = protocol;
	}

	public GlowProtocol getProtocol()
	{
		return protocol;
	}
}
