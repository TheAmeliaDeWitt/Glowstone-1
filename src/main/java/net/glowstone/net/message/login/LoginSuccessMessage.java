package net.glowstone.net.message.login;

import com.flowpowered.network.Message;

import io.amelia.support.Strs;

public final class LoginSuccessMessage implements Message
{
	private final String username;
	private final String uuid;

	public LoginSuccessMessage( String uuid, String username )
	{
		// Strs.lengthMustEqual( uuid, 36 );
		// Strs.lengthMustEqual( username, 16 );

		this.uuid = uuid;
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public String getUuid()
	{
		return uuid;
	}
}
