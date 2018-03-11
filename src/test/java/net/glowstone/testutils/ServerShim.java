package net.glowstone.testutils;

import com.destroystokyo.paper.profile.PlayerProfile;

import net.glowstone.inventory.GlowItemFactory;
import net.glowstone.net.SessionRegistry;
import net.glowstone.scheduler.GlowScheduler;
import net.glowstone.scheduler.WorldScheduler;
import net.md_5.bungee.api.chat.BaseComponent;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.advancement.Advancement;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;
import org.mockito.Mockito;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Simple mocked Server implementation.
 */
public class ServerShim implements Server
{
	public static void install()
	{
		if ( Bukkit.getServer() == null )
		{
			Bukkit.setServer( new ServerShim() );
		}
	}

	// actual implementations
	private final PluginManager pluginManager = Mockito.mock( PluginManager.class, Mockito.RETURNS_SMART_NULLS );
	private final SessionRegistry sessionRegistry = new SessionRegistry();
	private final WorldScheduler worldScheduler = new WorldScheduler();
	private final GlowScheduler scheduler = new GlowScheduler( this, worldScheduler, sessionRegistry );

	@Override
	public boolean addRecipe( Recipe recipe )
	{
		return false;
	}

	@Override
	public Iterator<Advancement> advancementIterator()
	{
		return null;
	}

	@Override
	public void banIP( String address )
	{

	}

	@Override
	public void broadcast( BaseComponent baseComponent )
	{

	}

	@Override
	public void broadcast( BaseComponent... baseComponents )
	{

	}

	@Override
	public int broadcast( String message, String permission )
	{
		return 0;
	}

	@Override
	public int broadcastMessage( String message )
	{
		return 0;
	}

	// do nothing stubs

	@Override
	public void clearRecipes()
	{

	}

	@Override
	public BossBar createBossBar( String s, BarColor barColor, BarStyle barStyle, BarFlag... barFlags )
	{
		return null;
	}

	@Override
	public ChunkGenerator.ChunkData createChunkData( World world )
	{
		return null;
	}

	@Override
	public Inventory createInventory( InventoryHolder owner, InventoryType type )
	{
		return null;
	}

	@Override
	public Inventory createInventory( InventoryHolder owner, InventoryType type, String title )
	{
		return null;
	}

	@Override
	public Inventory createInventory( InventoryHolder owner, int size ) throws IllegalArgumentException
	{
		return null;
	}

	@Override
	public Inventory createInventory( InventoryHolder owner, int size, String title ) throws IllegalArgumentException
	{
		return null;
	}

	@Override
	public MapView createMap( World world )
	{
		return null;
	}

	@Override
	public Merchant createMerchant( String s )
	{
		return null;
	}

	@Override
	public PlayerProfile createProfile( UUID id )
	{
		return null;
	}

	@Override
	public PlayerProfile createProfile( String name )
	{
		return null;
	}

	@Override
	public PlayerProfile createProfile( UUID id, String name )
	{
		return null;
	}

	@Override
	public World createWorld( WorldCreator creator )
	{
		return null;
	}

	@Override
	public boolean dispatchCommand( CommandSender sender, String commandLine ) throws CommandException
	{
		return false;
	}

	@Override
	public Advancement getAdvancement( NamespacedKey key )
	{
		return null;
	}

	@Override
	public boolean getAllowEnd()
	{
		return false;
	}

	@Override
	public boolean getAllowFlight()
	{
		return false;
	}

	@Override
	public boolean getAllowNether()
	{
		return false;
	}

	@Override
	public int getAmbientSpawnLimit()
	{
		return 0;
	}

	@Override
	public int getAnimalSpawnLimit()
	{
		return 0;
	}

	@Override
	public BanList getBanList( BanList.Type type )
	{
		return null;
	}

	@Override
	public Set<OfflinePlayer> getBannedPlayers()
	{
		return null;
	}

	@Override
	public String getBukkitVersion()
	{
		return "Test-Shim";
	}

	@Override
	public Map<String, String[]> getCommandAliases()
	{
		return null;
	}

	@Override
	public CommandMap getCommandMap()
	{
		return null;
	}

	@Override
	public long getConnectionThrottle()
	{
		return 0;
	}

	@Override
	public ConsoleCommandSender getConsoleSender()
	{
		return null;
	}

	@Override
	public GameMode getDefaultGameMode()
	{
		return null;
	}

	@Override
	public void setDefaultGameMode( GameMode mode )
	{

	}

	@Override
	public Entity getEntity( UUID uuid )
	{
		return null;
	}

	@Override
	public boolean getGenerateStructures()
	{
		return false;
	}

	@Override
	public HelpMap getHelpMap()
	{
		return null;
	}

	@Override
	public Set<String> getIPBans()
	{
		return null;
	}

	@Override
	public int getIdleTimeout()
	{
		return 0;
	}

	@Override
	public void setIdleTimeout( int threshold )
	{

	}

	@Override
	public String getIp()
	{
		return null;
	}

	@Override
	public ItemFactory getItemFactory()
	{
		return GlowItemFactory.instance();
	}

	@Override
	public Set<String> getListeningPluginChannels()
	{
		return null;
	}

	@Override
	public Logger getLogger()
	{
		return Logger.getLogger( "Test-Shim" );
	}

	@Override
	public MapView getMap( short id )
	{
		return null;
	}

	@Override
	public int getMaxPlayers()
	{
		return 0;
	}

	@Override
	public Messenger getMessenger()
	{
		return null;
	}

	@Override
	public int getMonsterSpawnLimit()
	{
		return 0;
	}

	@Override
	public String getMotd()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return "Glowstone";
	}

	@Override
	public OfflinePlayer getOfflinePlayer( String name )
	{
		return null;
	}

	@Override
	public OfflinePlayer getOfflinePlayer( UUID id )
	{
		return null;
	}

	@Override
	public OfflinePlayer[] getOfflinePlayers()
	{
		return new OfflinePlayer[0];
	}

	@Override
	public boolean getOnlineMode()
	{
		return false;
	}

	@Override
	public Collection<? extends Player> getOnlinePlayers()
	{
		return Arrays.asList();
	}

	@Override
	public Set<OfflinePlayer> getOperators()
	{
		return null;
	}

	@Override
	public Player getPlayer( String name )
	{
		return null;
	}

	@Override
	public Player getPlayer( UUID id )
	{
		return null;
	}

	@Override
	public Player getPlayerExact( String name )
	{
		return null;
	}

	@Override
	public PluginCommand getPluginCommand( String name )
	{
		return null;
	}

	@Override
	public PluginManager getPluginManager()
	{
		return pluginManager;
	}

	@Override
	public int getPort()
	{
		return 0;
	}

	@Override
	public List<Recipe> getRecipesFor( ItemStack result )
	{
		return null;
	}

	@Override
	public GlowScheduler getScheduler()
	{
		return scheduler;
	}

	@Override
	public ScoreboardManager getScoreboardManager()
	{
		return null;
	}

	@Override
	public CachedServerIcon getServerIcon()
	{
		return null;
	}

	@Override
	public String getServerId()
	{
		return null;
	}

	@Override
	public String getServerName()
	{
		return null;
	}

	@Override
	public ServicesManager getServicesManager()
	{
		return null;
	}

	@Override
	public String getShutdownMessage()
	{
		return null;
	}

	@Override
	public int getSpawnRadius()
	{
		return 0;
	}

	@Override
	public void setSpawnRadius( int value )
	{

	}

	@Override
	public double[] getTPS()
	{
		return new double[0];
	}

	@Override
	public int getTicksPerAnimalSpawns()
	{
		return 0;
	}

	@Override
	public int getTicksPerMonsterSpawns()
	{
		return 0;
	}

	@Override
	public UnsafeValues getUnsafe()
	{
		return null;
	}

	@Override
	public String getUpdateFolder()
	{
		return null;
	}

	@Override
	public File getUpdateFolderFile()
	{
		return null;
	}

	@Override
	public String getVersion()
	{
		return "Test-Shim";
	}

	@Override
	public int getViewDistance()
	{
		return 0;
	}

	@Override
	public Warning.WarningState getWarningState()
	{
		return null;
	}

	@Override
	public int getWaterAnimalSpawnLimit()
	{
		return 0;
	}

	@Override
	public Set<OfflinePlayer> getWhitelistedPlayers()
	{
		return null;
	}

	@Override
	public World getWorld( String name )
	{
		return null;
	}

	@Override
	public World getWorld( UUID uid )
	{
		return null;
	}

	@Override
	public File getWorldContainer()
	{
		return null;
	}

	@Override
	public String getWorldType()
	{
		return null;
	}

	@Override
	public List<World> getWorlds()
	{
		return null;
	}

	@Override
	public boolean hasWhitelist()
	{
		return false;
	}

	@Override
	public boolean isHardcore()
	{
		return false;
	}

	@Override
	public boolean isPrimaryThread()
	{
		return false;
	}

	@Override
	public CachedServerIcon loadServerIcon( File file ) throws Exception
	{
		return null;
	}

	@Override
	public CachedServerIcon loadServerIcon( BufferedImage image ) throws Exception
	{
		return null;
	}

	@Override
	public List<Player> matchPlayer( String name )
	{
		return null;
	}

	@Override
	public Iterator<Recipe> recipeIterator()
	{
		return null;
	}

	@Override
	public void reload()
	{

	}

	@Override
	public boolean reloadCommandAliases()
	{
		return false;
	}

	@Override
	public void reloadData()
	{

	}

	@Override
	public void reloadPermissions()
	{

	}

	@Override
	public void reloadWhitelist()
	{

	}

	@Override
	public void resetRecipes()
	{

	}

	@Override
	public void savePlayers()
	{

	}

	@Override
	public void sendPluginMessage( Plugin source, String channel, byte[] message )
	{

	}

	@Override
	public void setWhitelist( boolean value )
	{

	}

	@Override
	public void shutdown()
	{

	}

	@Override
	public Spigot spigot()
	{
		return null;
	}

	@Override
	public boolean suggestPlayerNamesWhenNullTabCompletions()
	{
		return false;
	}

	@Override
	public void unbanIP( String address )
	{

	}

	@Override
	public boolean unloadWorld( String name, boolean save )
	{
		return false;
	}

	@Override
	public boolean unloadWorld( World world, boolean save )
	{
		return false;
	}
}
