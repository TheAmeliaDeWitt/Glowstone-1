package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class UnlockRecipesMessage implements Message
{
	public static final int ACTION_INIT = 0;
	public static final int ACTION_ADD = 1;
	public static final int ACTION_REMOVE = 2;

	private final int action;
	private final int[] allRecipes; // allRecipes is only set when action = ACTION_INIT (0)
	private final boolean bookOpen;
	private final boolean filterOpen;
	private final int[] recipes;

	public UnlockRecipesMessage( int action, boolean bookOpen, boolean filterOpen, int[] recipes )
	{
		this( action, bookOpen, filterOpen, recipes, null );
	}

	public UnlockRecipesMessage( int action, boolean bookOpen, boolean filterOpen, int[] recipes, int[] allRecipes )
	{
		this.action = action;
		this.bookOpen = bookOpen;
		this.filterOpen = filterOpen;
		this.recipes = recipes;
		this.allRecipes = allRecipes;
	}

	public int getAction()
	{
		return action;
	}

	public int[] getAllRecipes()
	{
		return allRecipes;
	}

	public int[] getRecipes()
	{
		return recipes;
	}

	public boolean isBookOpen()
	{
		return bookOpen;
	}

	public boolean isFilterOpen()
	{
		return filterOpen;
	}
}
