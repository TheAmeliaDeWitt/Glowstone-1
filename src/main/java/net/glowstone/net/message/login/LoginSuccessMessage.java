package net.glowstone.net.message.login;

import com.flowpowered.network.Message;

public final class LoginSuccessMessage implements Message
{
	private final String username;
	private final String uuid;

	public LoginSuccessMessage( String uuid, String username )
	{
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
