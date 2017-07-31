package fr.pelt10.lobbymanager.inventory;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryManager {
    private CustomItem[] hotbar = new CustomItem[9];
    private static final String PREFIX_GIVE = "inventory.give.";

    public InventoryManager(JavaPlugin javaPlugin) {
	FileConfiguration config = javaPlugin.getConfig();

	// Load hotbar
	for (int i = 0; i < 9; i++) {
	    if (config.contains(PREFIX_GIVE + i)) {
		hotbar[i] = new CustomItem(Material.valueOf(config.getString(PREFIX_GIVE + i + "item.ID")),
			config.getString(PREFIX_GIVE + i + "action"),
			config.getString(PREFIX_GIVE + i + "item.name", ""),
			config.getString(PREFIX_GIVE + i + "item.enchant.name", ""),
			config.getInt(PREFIX_GIVE + i + "item.enchant.level", -1));
	    }
	}
    }

    public void sendInventory(Player player) {
	PlayerInventory inventory = player.getInventory();

	inventory.clear();
	for (int i = 0; i < hotbar.length; i++) {
	    if (hotbar[i] != null)
		inventory.setItem(i, hotbar[i].getItem());
	}
    }
    
    public CustomItem[] getHotbar() {
	return hotbar;
    }
}
