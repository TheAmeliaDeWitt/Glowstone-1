package net.glowstone.net.message.login;

import com.flowpowered.network.AsyncableMessage;

public final class EncryptionKeyResponseMessage implements AsyncableMessage
{
	private final byte[] sharedSecret;
	private final byte[] verifyToken;

	public EncryptionKeyResponseMessage( byte[] sharedSecret, byte[] verifyToken )
	{
		this.sharedSecret = sharedSecret;
		this.verifyToken = verifyToken;
	}

	public byte[] getSharedSecret()
	{
		return sharedSecret;
	}

	public byte[] getVerifyToken()
	{
		return verifyToken;
	}

	@Override
	public boolean isAsync()
	{
		return true;
	}

}
