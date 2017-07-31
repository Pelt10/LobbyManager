package fr.pelt10.lobbymanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pelt10.lobbymanager.command.ReloadInventoryCmd;
import fr.pelt10.lobbymanager.command.SetSpawnCmd;
import fr.pelt10.lobbymanager.command.SpawnCmd;
import fr.pelt10.lobbymanager.inventory.InventoryManager;
import fr.pelt10.lobbymanager.listener.OnPlayerDamageEntityEvent;
import fr.pelt10.lobbymanager.listener.OnPlayerDropItemEvent;
import fr.pelt10.lobbymanager.listener.OnPlayerInteractEvent;
import fr.pelt10.lobbymanager.listener.OnPlayerInventoryEvent;
import fr.pelt10.lobbymanager.listener.OnPlayerMoveEvent;
import fr.pelt10.lobbymanager.listener.PlayerConnect;

public class LobbyManager extends JavaPlugin {
    private Language language;
    private SpawnConfig spawnConfig;
    private InventoryManager inventoryManager;

    private int height;

    @Override
    public void onEnable() {
	// Configuration
	saveConfig();
	FileConfiguration configuration = getConfig();
	language = new Language(this, configuration.getString("lang"));
	spawnConfig = new SpawnConfig(this);

	inventoryManager = new InventoryManager(this);

	// Event
	PluginManager pm = getServer().getPluginManager();
	pm.registerEvents(new PlayerConnect(this), this);
	
	
	
	if (config.getBoolean("Inventory.control.enable"))
	    pm.registerEvents(new OnPlayerInventoryEvent(), this);
	if (config.getBoolean("noDamage.enable"))
	    pm.registerEvents(new OnPlayerDamageEntityEvent(), this);
	
	if (configuration.getBoolean("antidrop.enable"))
	    pm.registerEvents(new Listener() {
		@EventHandler
		public void playerDropEvent(PlayerDropItemEvent event) {
		    event.setCancelled(true);
		}
	    }, this);
	
	if (config.getBoolean("noFall.enable"))
	    pm.registerEvents(new OnPlayerMoveEvent(), this);

	pm.registerEvents(new OnPlayerInteractEvent(), this);

	// commands
	this.getCommand("setspawn").setExecutor(new SetSpawnCmd());
	this.getCommand("spawn").setExecutor(new SpawnCmd());
	this.getCommand("reloadinventory").setExecutor(new ReloadInventoryCmd());
	super.onEnable();
    }

    public Language getLanguage() {
	return language;
    }

    public SpawnConfig getSpawnConfig() {
	return spawnConfig;
    }

    public InventoryManager getInventoryManager() {
	return inventoryManager;
    }
}
