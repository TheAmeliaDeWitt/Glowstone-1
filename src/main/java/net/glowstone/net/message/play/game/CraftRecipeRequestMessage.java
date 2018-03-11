package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class CraftRecipeRequestMessage implements Message
{
	private final boolean makeAll;
	private final int recipeId;
	private final int windowId;

	public CraftRecipeRequestMessage( int windowId, int recipeId, boolean makeAll )
	{
		this.windowId = windowId;
		this.recipeId = recipeId;
		this.makeAll = makeAll;
	}

	public int getRecipeId()
	{
		return recipeId;
	}

	public int getWindowId()
	{
		return windowId;
	}

	public boolean isMakeAll()
	{
		return makeAll;
	}
}
