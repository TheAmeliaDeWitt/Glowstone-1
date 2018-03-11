package net.glowstone.util.loot;

import net.glowstone.util.ReflectionProcessor;

import java.util.Optional;

public class ReflectiveValue<T>
{
	private final Optional<String> line;
	private final Optional<T> value;

	public ReflectiveValue( T value )
	{
		this.value = Optional.of( value );
		this.line = Optional.empty();
	}

	public ReflectiveValue( String line )
	{
		this.value = Optional.empty();
		this.line = Optional.of( line );
	}

	public Optional<String> getLine()
	{
		return line;
	}

	public Optional<T> getValue()
	{
		return value;
	}

	public Object process( Object... context )
	{
		return line.map( s -> new ReflectionProcessor( s, context ).process() ).orElseGet( value::get );
	}
}
