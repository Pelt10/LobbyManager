package fr.pelt10.lobbymanager;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyManager extends JavaPlugin {
	static public Location spawn;
	static public int height;
	static public GameMode gm;
	static protected ItemStack pinventory[];
	static public String fallMessage;
	protected FileConfiguration config;
	static protected ItemConfig inventory[];

	@Override
	public void onEnable() {

		// Configuration
		this.saveDefaultConfig();
		config = getConfig();

		// Spawn and Minimum Y Level
		double x = config.getDouble("spawn.x");
		double y = config.getDouble("spawn.y");
		double z = config.getDouble("spawn.z");
		String world = config.getString("spawn.world");
		float yaw = (float) config.getDouble("spawn.yaw");
		float pitch = (float) config.getDouble("spawn.pitch");
		spawn = new Location(this.getServer().getWorld(world), x, y, z, yaw, pitch);
		
		height = config.getInt("minYLevel");
		fallMessage = config.getString("fallMessage");

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

		// Inventory
		inventory = new ItemConfig[9];
		pinventory = new ItemStack[9];
		for (int i = 0; i < 8; i++) {
			//System.out.println(config.getString("Inventory." + i + ".item.ID") + config.getString("Inventory." + i + ".command") + config.getString("Inventory." + i + ".item.name") + config.getString("Inventory." + i + ".item.enchant.name") + config.getInt("Inventory." + i + ".item.enchant.level"));
			if (config.get("Inventory." + i) != null) {
				if (config.get("Inventory." + i + ".item.name") != null) {
					if (config.get("Inventory." + i + ".item.enchant.name") != null && config.get("Inventory." + i + ".item.enchant.level") != null) {
						ItemConfig item = new ItemConfig(config.getString("Inventory." + i + ".item.ID"), config.getString("Inventory." + i + ".command"), config.getString("Inventory." + i + ".item.name"), config.getString("Inventory." + i + ".item.enchant.name"), config.getInt("Inventory." + i + ".item.enchant.level"));
						pinventory[i] = item.getItem();
						inventory[i] = item;
					} else {
						ItemConfig item = new ItemConfig(config.getString("Inventory." + i + ".item.ID"), 	config.getString("Inventory." + i + ".command"), config.getString("Inventory." + i + ".item.name"));
						pinventory[i] = item.getItem();
						inventory[i] = item;
					}
				} else {
					ItemConfig item = new ItemConfig(config.getString("Inventory." + i + ".item.ID"), config.getString("Inventory." + i + ".command"));
					pinventory[i] = item.getItem();
					inventory[i] = item;
				}
			}

		}

		// Event
		this.getServer().getPluginManager().registerEvents(new Event(), this);

		super.onEnable();
	}

	static public void setInventory(Player p) {
		Inventory inv = p.getInventory();
		
		for (int i = 0; i < pinventory.length; i++) {
			inv.setItem(i, pinventory[i]);
		}
		
		p.updateInventory();
	}

	public FileConfiguration getConfiguration() {
		return getConfig();
	}
}
