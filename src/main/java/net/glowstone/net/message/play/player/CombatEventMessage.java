package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

import net.glowstone.util.TextMessage;

public final class CombatEventMessage implements Message
{
	private final int duration;
	private final int entityId;
	private final Event event;
	private final TextMessage message;
	private final int playerId;

	// BEGIN_COMBAT
	public CombatEventMessage( Event event )
	{
		this( event, 0, 0, 0, null );
	}

	// END_COMBAT
	public CombatEventMessage( Event event, int duration, int entityId )
	{
		this( event, duration, entityId, 0, null );
	}

	// ENTITY_DEAD
	public CombatEventMessage( Event event, int entityId, int playerId, TextMessage message )
	{
		this( event, 0, entityId, playerId, message );
	}

	public CombatEventMessage( Event event, int duration, int entityId, int playerId, TextMessage message )
	{
		this.event = event;
		this.duration = duration;
		this.entityId = entityId;
		this.playerId = playerId;
		this.message = message;
	}

	public int getDuration()
	{
		return duration;
	}

	public int getEntityId()
	{
		return entityId;
	}

	public Event getEvent()
	{
		return event;
	}

	public TextMessage getMessage()
	{
		return message;
	}

	public int getPlayerId()
	{
		return playerId;
	}

	public enum Event
	{
		ENTER_COMBAT,
		END_COMBAT,
		ENTITY_DEAD;

		public static Event getAction( int id )
		{
			Event[] values = values();
			return id < 0 || id >= values.length ? null : values[id];
		}
	}
}
