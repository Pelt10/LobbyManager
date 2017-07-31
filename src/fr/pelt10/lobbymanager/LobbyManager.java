package fr.pelt10.lobbymanager;

import java.util.Arrays;

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
import fr.pelt10.lobbymanager.listener.PlayerInteract;
import fr.pelt10.lobbymanager.listener.PlayerInventory;
import fr.pelt10.lobbymanager.listener.PlayerConnect;
import fr.pelt10.lobbymanager.listener.PlayerMove;
import fr.pelt10.lobbymanager.utils.DCommand;

public class LobbyManager extends JavaPlugin {
    private Language language;
    private SpawnConfig spawnConfig;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
	// Configuration
	saveDefaultConfig();
	FileConfiguration configuration = getConfig();
	language = new Language(this, configuration.getString("lang"));
	spawnConfig = new SpawnConfig(this);

	inventoryManager = new InventoryManager(this);

	// Event
	PluginManager pluginManager = getServer().getPluginManager();
	pluginManager.registerEvents(new PlayerConnect(this), this);
	pluginManager.registerEvents(new PlayerMove(this), this);
	pluginManager.registerEvents(new PlayerInteract(this), this);
	
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
	new DCommand("setspawn", "/setspawn", "This command allow you to set Spawn Location", "lobbymanager.setspawn", Arrays.asList(), new SetSpawnCommand(this), this);
	new DCommand("spawn", "/spawn", "Swap to Spawn !", "lobbymanager.spawn", Arrays.asList(), new SpawnCommand(this), this);
	new DCommand("reloadinventory", "/reloadinventory", "This command resend inventory", "lobbymanager.reloadinventory", Arrays.asList(), new ReloadInventoryCommand(this), this);
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
