package net.glowstone.net.message.play.inv;

import com.flowpowered.network.Message;

public final class TransactionMessage implements Message
{
	private final boolean accepted;
	private final int id;
	private final int transaction;

	public TransactionMessage( int id, int transaction, boolean accepted )
	{
		this.id = id;
		this.transaction = transaction;
		this.accepted = accepted;
	}

	public int getId()
	{
		return id;
	}

	public int getTransaction()
	{
		return transaction;
	}

	public boolean isAccepted()
	{
		return accepted;
	}
}
