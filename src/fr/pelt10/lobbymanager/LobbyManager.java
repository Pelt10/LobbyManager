package fr.pelt10.lobbymanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pelt10.lobbymanager.command.ReloadInventoryCommand;
import fr.pelt10.lobbymanager.command.SetSpawnCommand;
import fr.pelt10.lobbymanager.command.SpawnCommand;
import fr.pelt10.lobbymanager.inventory.InventoryManager;
import fr.pelt10.lobbymanager.listener.OnPlayerInteractEvent;
import fr.pelt10.lobbymanager.listener.PlayerInventory;
import fr.pelt10.lobbymanager.listener.PlayerConnect;
import fr.pelt10.lobbymanager.listener.PlayerMove;

public class LobbyManager extends JavaPlugin {
    private Language language;
    private SpawnConfig spawnConfig;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
	// Configuration
	saveConfig();
	FileConfiguration configuration = getConfig();
	language = new Language(this, configuration.getString("lang"));
	spawnConfig = new SpawnConfig(this);

	inventoryManager = new InventoryManager(this);

	// Event
	PluginManager pluginManager = getServer().getPluginManager();
	pluginManager.registerEvents(new PlayerConnect(this), this);
	pluginManager.registerEvents(new PlayerMove(this), this);
	pluginManager.registerEvents(new OnPlayerInteractEvent(), this);
	
	//Inventory control module
	if (configuration.getBoolean("inventory.control.enable"))
	    pluginManager.registerEvents(new PlayerInventory(), this);
	
	//noDamage module
	if (configuration.getBoolean("noDamage.enable")) {
	    pluginManager.registerEvents(new Listener() {
		@EventHandler
		public void entityDamage(EntityDamageEvent event) {
		    event.setCancelled(true);
		}
	    }, this);
	}
	
	//antiDrop module
	if (configuration.getBoolean("antidrop.enable"))
	    pluginManager.registerEvents(new Listener() {
		@EventHandler
		public void playerDropEvent(PlayerDropItemEvent event) {
		    event.setCancelled(true);
		}
	    }, this);

	// commands
	this.getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
	this.getCommand("spawn").setExecutor(new SpawnCommand(this));
	this.getCommand("reloadinventory").setExecutor(new ReloadInventoryCommand(this));
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
