package net.glowstone.net.message.play.game;

import com.flowpowered.network.Message;

public final class CraftRecipeResponseMessage implements Message
{
	private final int recipeId;
	private final int windowId;

	public CraftRecipeResponseMessage( int windowsId, int recipeId )
	{
		this.windowId = windowsId;
		this.recipeId = recipeId;
	}

	public int getRecipeId()
	{
		return recipeId;
	}

	public int getWindowId()
	{
		return windowId;
	}
}
