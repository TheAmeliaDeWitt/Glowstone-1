package net.glowstone.util.loot;

import org.json.simple.JSONObject;

import java.util.Optional;

public class ConditionalLootItem
{
	private final String condition;
	private final Optional<LootRandomValues> count;
	private final Optional<ProbableValue<Integer>> data;
	private final Optional<ReflectiveValue<Integer>> reflectiveData;
	private final Optional<ProbableValue<String>> type;

	/**
	 * Parses a conditional loot item from its JSONObject form.
	 *
	 * @param object a JSON object describing the loot item
	 */
	public ConditionalLootItem( JSONObject object )
	{
		if ( object.containsKey( "item" ) )
		{
			type = Optional.of( new ProbableValue<>( object, "item" ) );
		}
		else
		{
			type = Optional.empty();
		}
		if ( object.containsKey( "data" ) )
		{
			Object data = object.get( "data" );
			if ( data instanceof String )
			{
				this.reflectiveData = Optional.of( new ReflectiveValue<Integer>( ( String ) data ) );
				this.data = Optional.empty();
			}
			else
			{
				this.data = Optional.of( new ProbableValue<>( object, "data" ) );
				this.reflectiveData = Optional.empty();
			}
		}
		else
		{
			data = Optional.empty();
			reflectiveData = Optional.empty();
		}
		if ( object.containsKey( "count" ) )
		{
			count = Optional.of( new LootRandomValues( object ) );
		}
		else
		{
			count = Optional.empty();
		}
		condition = ( String ) object.get( "condition" );
	}

	public String getCondition()
	{
		return condition;
	}

	public Optional<LootRandomValues> getCount()
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

	public Optional<ProbableValue<String>> getType()
	{
		return type;
	}
}
