package net.glowstone.entity.ai;

public class MobState
{
	public static final MobState NO_AI = new MobState( "no_ai" );
	public static final MobState IDLE = new MobState( "idle" );
	public static final MobState WANDER = new MobState( "wander" );
	public static final MobState ATTACKED = new MobState( "attacked" );

	private String name;

	MobState( String name )
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
