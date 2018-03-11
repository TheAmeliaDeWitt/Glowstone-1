package net.glowstone.advancement;

import java.util.Date;

public class CriterionProgress
{
	private final boolean achieved;
	private final Date time;

	public CriterionProgress( boolean achieved, Date time )
	{
		this.achieved = achieved;
		this.time = time;
	}

	public Date getTime()
	{
		return time;
	}

	public boolean isAchieved()
	{
		return achieved;
	}
}
