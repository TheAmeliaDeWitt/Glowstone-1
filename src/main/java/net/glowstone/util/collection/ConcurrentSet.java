package net.glowstone.util.collection;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentSet<E> extends AbstractSet<E>
{
	private final ConcurrentMap<E, Boolean> map = new ConcurrentHashMap<>();

	@Override
	public boolean add( E e )
	{
		return map.putIfAbsent( e, Boolean.TRUE ) == null;
	}

	@Override
	public void clear()
	{
		map.clear();
	}

	@Override
	public boolean contains( Object o )
	{
		return map.containsKey( o );
	}

	@Override
	public Iterator<E> iterator()
	{
		return map.keySet().iterator();
	}

	@Override
	public boolean remove( Object o )
	{
		return map.remove( o ) != null;
	}

	@Override
	public int size()
	{
		return map.size();
	}
}
