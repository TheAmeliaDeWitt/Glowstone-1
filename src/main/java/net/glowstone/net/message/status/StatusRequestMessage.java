package net.glowstone.net.message.status;

import com.flowpowered.network.AsyncableMessage;

public final class StatusRequestMessage implements AsyncableMessage
{
	@Override
	public boolean isAsync()
	{
		return true;
	}
}
