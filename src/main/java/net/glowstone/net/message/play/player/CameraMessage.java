package net.glowstone.net.message.play.player;

import com.flowpowered.network.Message;

public final class CameraMessage implements Message
{
	private final int cameraId;

	public CameraMessage( int cameraId )
	{
		this.cameraId = cameraId;
	}

	public int getCameraId()
	{
		return cameraId;
	}
}
