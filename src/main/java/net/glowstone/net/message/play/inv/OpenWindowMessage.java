package net.glowstone.net.message.play.inv;

import com.flowpowered.network.Message;

import net.glowstone.util.TextMessage;

public final class OpenWindowMessage implements Message
{
	private final int entityId;
	private final int id;
	private final int slots;
	private final TextMessage title;
	private final String type;

	public OpenWindowMessage( int id, String type, String title, int slots )
	{
		this( id, type, new TextMessage( title ), slots, 0 );
	}

	public OpenWindowMessage( int id, String type, TextMessage title, int slots, int entityId )
	{
		this.id = id;
		this.type = type;
		this.title = title;
		this.slots = slots;
		this.entityId = entityId;
	}

	public int getEntityId()
	{
		return entityId;
	}

	public int getId()
	{
		return id;
	}

	public int getSlots()
	{
		return slots;
	}

	public TextMessage getTitle()
	{
		return title;
	}

	public String getType()
	{
		return type;
	}
}
