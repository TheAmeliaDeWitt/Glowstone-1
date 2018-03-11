package net.glowstone.net.message.login;

import com.flowpowered.network.Message;

public final class EncryptionKeyRequestMessage implements Message
{
	private final byte[] publicKey;
	private final String sessionId;
	private final byte[] verifyToken;

	public EncryptionKeyRequestMessage( String sessionId, byte[] publicKey, byte[] verifyToken )
	{
		this.sessionId = sessionId;
		this.publicKey = publicKey;
		this.verifyToken = verifyToken;
	}

	public byte[] getPublicKey()
	{
		return publicKey;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public byte[] getVerifyToken()
	{
		return verifyToken;
	}
}
