package net.glowstone.net.message.login;

import com.flowpowered.network.AsyncableMessage;

public final class LoginStartMessage implements AsyncableMessage
{
	private final String username;

	public LoginStartMessage( String username )
	{
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	@Override
	public boolean isAsync()
	{
		return true;
	}
}
