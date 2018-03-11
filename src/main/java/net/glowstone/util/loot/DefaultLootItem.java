package net.glowstone.util.loot;

import org.json.simple.JSONObject;

import java.util.Optional;

public class DefaultLootItem
{
	private final LootRandomValues count;
	private final Optional<ProbableValue<Integer>> data;
	private final Optional<ReflectiveValue<Integer>> reflectiveData;
	private final ProbableValue<String> type;

	/**
	 * Parses a loot-table entry from JSON.
	 *
	 * @param object a loot-table entry in JSON format.
	 */
	public DefaultLootItem( JSONObject object )
	{
		this.type = new ProbableValue<>( object, "item" );
		this.count = new LootRandomValues( object );
		if ( object.containsKey( "data" ) )
		{
			if ( object.get( "data" ) instanceof String )
			{
				this.reflectiveData = Optional.of( new ReflectiveValue<Integer>( ( String ) object.get( "data" ) ) );
				this.data = Optional.empty();
			}
			else if ( object.get( "data" ) instanceof Long )
			{
				this.reflectiveData = Optional.of( new ReflectiveValue<>( ( ( Long ) object.get( "data" ) ).intValue() ) );
				this.data = Optional.empty();
			}
			else
			{
				this.reflectiveData = Optional.empty();
				this.data = Optional.of( new ProbableValue<>( object, "data" ) );
			}
		}
		else
		{
			this.reflectiveData = Optional.of( new ReflectiveValue<>( 0 ) );
			this.data = Optional.empty();
		}
	}

	public LootRandomValues getCount()
	{
		return count;
	}

	public Optional<ProbableValue<Integer>> getData()
	{
		return data;
	}

	public Optional<ReflectiveValue<Integer>> getReflectiveData()
	{
		return reflectiveData;
	}

	public ProbableValue<String> getType()
	{
		return type;
	}
}
