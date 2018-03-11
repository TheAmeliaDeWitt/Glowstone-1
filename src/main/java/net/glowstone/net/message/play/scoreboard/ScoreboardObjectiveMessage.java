package net.glowstone.net.message.play.scoreboard;

import com.flowpowered.network.Message;

import net.glowstone.scoreboard.RenderType;

public final class ScoreboardObjectiveMessage implements Message
{
	public static ScoreboardObjectiveMessage create( String name, String displayName )
	{
		return new ScoreboardObjectiveMessage( name, displayName, Action.CREATE.ordinal(), RenderType.INTEGER );
	}

	public static ScoreboardObjectiveMessage create( String name, String displayName, RenderType renderType )
	{
		return new ScoreboardObjectiveMessage( name, displayName, Action.CREATE.ordinal(), renderType );
	}

	public static ScoreboardObjectiveMessage remove( String name )
	{
		return new ScoreboardObjectiveMessage( name, null, Action.REMOVE.ordinal(), null );
	}

	public static ScoreboardObjectiveMessage update( String name, String displayName )
	{
		return new ScoreboardObjectiveMessage( name, displayName, Action.UPDATE.ordinal(), RenderType.INTEGER );
	}

	public static ScoreboardObjectiveMessage update( String name, String displayName, RenderType renderType )
	{
		return new ScoreboardObjectiveMessage( name, displayName, Action.UPDATE.ordinal(), renderType );
	}

	private final int action;
	private final String displayName;
	private final String name;
	private final RenderType renderType;

	private ScoreboardObjectiveMessage( String name, String displayName, int action, RenderType renderType )
	{
		this.name = name;
		this.displayName = displayName;
		this.action = action;
		this.renderType = renderType;
	}

	public int getAction()
	{
		return action;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getName()
	{
		return name;
	}

	public RenderType getRenderType()
	{
		return renderType;
	}

	private enum Action
	{
		CREATE,
		REMOVE,
		UPDATE
	}
}
