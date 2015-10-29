package fr.pelt10.lobbymanager;

import java.io.File;
import java.io.InputStream;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pelt10.lobbymanager.event.OnPlayerInventoryEvent;
import fr.pelt10.lobbymanager.event.OnPlayerMoveEvent;
import fr.pelt10.lobbymanager.command.ReloadInventoryCmd;
import fr.pelt10.lobbymanager.command.SetSpawnCmd;
import fr.pelt10.lobbymanager.command.SpawnCmd;
import fr.pelt10.lobbymanager.event.*;

public class LobbyManager extends JavaPlugin {
	static public Language lang;
	static public Location spawn;
	static public FileConfiguration config;
	static public ItemConfig inventory[];
	static public int height;
	static public GameMode gm;
	static public String[] title;
	static public SpawnConfig spawnConfig;
	
	
	@Override
	public void onEnable() {

		// Configuration
		this.saveDefaultConfig();
		config = getConfig();
		lang = new Language(this, config.getString("lang"));
		spawnConfig = new SpawnConfig(this);

		// Requirements for modules
		requirements();

		// Inventory
		loadInventory();

		// Event
		PluginManager pm = getServer().getPluginManager();
		if(config.getBoolean("Inventory.control.enable"))
			pm.registerEvents(new OnPlayerInventoryEvent(), this);
		if(config.getBoolean("join.gamemode.enable") || config.getBoolean("join.title.enable") || config.getBoolean("Inventory.give.enable"))
			pm.registerEvents(new OnPlayerConnectEvent(), this);
		if(config.getBoolean("noDamage.enable"))
			pm.registerEvents(new OnPlayerDamageEntityEvent(), this);
		if(config.getBoolean("antidrop.enable"))
			pm.registerEvents(new OnPlayerDropItemEvent(), this);
		if(config.getBoolean("noFall.enable"))
			pm.registerEvents(new OnPlayerMoveEvent(), this);

		pm.registerEvents(new OnPlayerInteractEvent(), this);
		
		
		//commands
		this.getCommand("setspawn").setExecutor(new SetSpawnCmd());
		this.getCommand("spawn").setExecutor(new SpawnCmd());
		this.getCommand("reloadinventory").setExecutor(new ReloadInventoryCmd());
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		save();
		super.onDisable();
	}

	static public void setInventory(Player p) {
		Inventory inv = p.getInventory();
		inv.clear();
		for (int i = 0; i < inventory.length; i++) {
			if(inventory[i] != null)
				inv.setItem(i, inventory[i].getItem());
		}

		p.updateInventory();
	}

	public void loadInventory() {
		inventory = new ItemConfig[9];
		for (int i = 0; i < inventory.length; i++) {
			System.out.println(config.getString("Inventory.give." + i + ".item.ID") + config.getString("Inventory.give." + i + ".command") +config.getString("Inventory.give." + i + ".item.name") +config.getString("Inventory.give." + i + ".item.enchant.name") + config.getInt("Inventory.give." + i + ".item.enchant.level"));
			if (config.get("Inventory.give." + i) != null) {
				if (config.get("Inventory.give." + i + ".item.name") != null) {
					if (config.get("Inventory.give." + i + ".item.enchant.name") != null && config.get("Inventory.give." + i + ".item.enchant.level") != null) {
						ItemConfig item = new ItemConfig(config.getString("Inventory.give." + i + ".item.ID"), config.getString("Inventory.give." + i + ".command"), config.getString("Inventory.give." + i + ".item.name"), config.getString("Inventory.give." + i + ".item.enchant.name"), config.getInt("Inventory.give." + i + ".item.enchant.level"));
						inventory[i] = item;
						continue;
					} else {
						ItemConfig item = new ItemConfig(config.getString("Inventory.give." + i + ".item.ID"), config.getString("Inventory.give." + i + ".command"), config.getString("Inventory.give." + i + ".item.name"));
						inventory[i] = item;
						continue;
					}
				} else {
					ItemConfig item = new ItemConfig(config.getString("Inventory.give." + i + ".item.ID"), config.getString("Inventory.give." + i + ".command"));
					inventory[i] = item;
					continue;
				}
			} else {
				inventory[i] = null;
			}
		}
	}

	public void requirements(){
		LobbyManager.spawn = new Location(this.getServer().getWorld(spawnConfig.getString("spawn.world")), spawnConfig.getDouble("spawn.x"),
				spawnConfig.getDouble("spawn.y"), spawnConfig.getDouble("spawn.z"), (float)spawnConfig.getDouble("spawn.yaw"),
				(float) spawnConfig.getDouble("spawn.pitch"));

		height = config.getInt("noFall.level");
		
		title = new String[2];
		title[0] = LobbyManager.config.getString("join.title.title");
		title[1] = LobbyManager.config.getString("join.title.subtitle");
		

		// Gamemode
		switch (config.getInt("gamemodeOnJoin")) {
		case 0:
			gm = GameMode.SURVIVAL;
			break;
		case 1:
			gm = GameMode.CREATIVE;
			break;
		case 2:
			gm = GameMode.ADVENTURE;
			break;
		case 3:
			gm = GameMode.SPECTATOR;
			break;
		}
	}
	
	public FileConfiguration spawnConfig(){
		File spawnFile = new File(getDataFolder(), "spawn.yml");
		FileConfiguration spawn = YamlConfiguration.loadConfiguration(spawnFile);
		InputStream defaultConfigStream = getResource("spawn.yml");
		if (defaultConfigStream != null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration defaultspawnConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
			spawn.setDefaults(defaultspawnConfig);
		}
		if (!spawnFile.exists()) {
			saveResource("spawn.yml", false);
		}
		return spawn;
	}
	
	public void save(){
		spawnConfig.set("spawn.world", spawn.getWorld().getName());
		spawnConfig.set("spawn.x", spawn.getX());
		spawnConfig.set("spawn.y", spawn.getY());
		spawnConfig.set("spawn.z", spawn.getZ());
		spawnConfig.set("spawn.yaw", spawn.getYaw());
		spawnConfig.set("spawn.pitch", spawn.getPitch());
		
		spawnConfig.save();
	}
}
