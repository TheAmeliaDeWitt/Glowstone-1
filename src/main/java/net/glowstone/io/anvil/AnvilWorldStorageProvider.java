package net.glowstone.io.anvil;

import net.glowstone.GlowServer;
import net.glowstone.GlowWorld;
import net.glowstone.io.FunctionIoService;
import net.glowstone.io.PlayerDataService;
import net.glowstone.io.ScoreboardIoService;
import net.glowstone.io.StructureDataService;
import net.glowstone.io.WorldStorageProvider;
import net.glowstone.io.data.WorldFunctionIoService;
import net.glowstone.io.json.JsonPlayerStatisticIoService;
import net.glowstone.io.nbt.NbtPlayerDataService;
import net.glowstone.io.nbt.NbtScoreboardIoService;
import net.glowstone.io.nbt.NbtStructureDataService;
import net.glowstone.io.nbt.NbtWorldMetadataService;

import java.io.File;

/**
 * A {@link WorldStorageProvider} for the Anvil map format.
 */
public class AnvilWorldStorageProvider implements WorldStorageProvider
{
	private final File dataDir;
	private final File folder;
	private final PlayerDataService playerDataService;
	private final JsonPlayerStatisticIoService playerStatisticIoService;
	private final ScoreboardIoService scoreboardIoService;
	private AnvilChunkIoService chunkIoService;
	private FunctionIoService functionIoService;
	private NbtWorldMetadataService metadataService;
	private StructureDataService structureDataService;
	private GlowWorld world;

	/**
	 * Create an instance for the given root folder.
	 *
	 * @param glowServer
	 * @param folder     the root folder
	 */
	public AnvilWorldStorageProvider( GlowServer glowServer, File folder )
	{
		this.folder = folder;
		this.dataDir = new File( folder, "data" );
		this.dataDir.mkdirs();

		playerDataService = new NbtPlayerDataService( glowServer, new File( folder, "playerdata" ) );
		scoreboardIoService = new NbtScoreboardIoService( glowServer, new File( folder, "data" ) );
		playerStatisticIoService = new JsonPlayerStatisticIoService( glowServer, new File( folder, "stats" ) );
	}

	@Override
	public AnvilChunkIoService getChunkIoService()
	{
		return chunkIoService;
	}

	@Override
	public File getFolder()
	{
		return folder;
	}

	@Override
	public FunctionIoService getFunctionIoService()
	{
		return functionIoService;
	}

	@Override
	public NbtWorldMetadataService getMetadataService()
	{
		return metadataService;
	}

	@Override
	public PlayerDataService getPlayerDataService()
	{
		return playerDataService;
	}

	@Override
	public JsonPlayerStatisticIoService getPlayerStatisticIoService()
	{
		return playerStatisticIoService;
	}

	@Override
	public ScoreboardIoService getScoreboardIoService()
	{
		return scoreboardIoService;
	}

	@Override
	public StructureDataService getStructureDataService()
	{
		return structureDataService;
	}

	@Override
	public void setWorld( GlowWorld world )
	{
		if ( this.world != null )
			throw new IllegalArgumentException( "World is already set" );

		this.world = world;

		functionIoService = new WorldFunctionIoService( world, dataDir );

		chunkIoService = new AnvilChunkIoService( folder );
		metadataService = new NbtWorldMetadataService( world, folder );
		dataDir.mkdirs();
		structureDataService = new NbtStructureDataService( world, dataDir );
	}
}
