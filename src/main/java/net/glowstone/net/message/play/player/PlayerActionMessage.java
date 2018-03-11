package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public final class PlayerActionMessage implements Message
{
	private final int action;
	private final int id;
	private final int jumpBoost;

	public PlayerActionMessage( int id, int action, int jumpBoost )
	{
		this.id = id;
		this.action = action;
		this.jumpBoost = jumpBoost;
	}

	public int getAction()
	{
		return action;
	}

	public int getId()
	{
		return id;
	}

	public int getJumpBoost()
	{
		return jumpBoost;
	}
}
