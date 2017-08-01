package fr.pelt10.lobbymanager.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pelt10.lobbymanager.inventory.item.CommandAction;
import fr.pelt10.lobbymanager.inventory.item.ItemAction;

public class InventoryManager {
    private CustomItem[] hotbar = new CustomItem[9];
    private static final String PREFIX_GIVE = "inventory.give.";
    private static Map<String, Class<? extends ItemAction>> itemActions = new HashMap<>();
    
    public InventoryManager(JavaPlugin javaPlugin) {
	FileConfiguration config = javaPlugin.getConfig();
	registerItemAction(CommandAction.class, "command");
	// Load hotbar
	for (int i = 0; i < 9; i++) {
	    if (config.contains(PREFIX_GIVE + i)) {
		hotbar[i] = new CustomItem(Material.valueOf(config.getString(PREFIX_GIVE + i + ".item.ID")),
			config.getString(PREFIX_GIVE + i + ".action"),
			config.getString(PREFIX_GIVE + i + ".item.name", ""),
			config.getString(PREFIX_GIVE + i + ".item.enchant.name", ""),
			config.getInt(PREFIX_GIVE + i + ".item.enchant.level", -1));
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
    
    public void registerItemAction(Class<? extends ItemAction> clazz, String name) {
	if(itemActions.containsKey(name)) {
	    throw new IllegalArgumentException("This name is already use !");
	}
	itemActions.put(name, clazz);
    }
    
    static ItemAction getAction(String name) {
	if(itemActions.containsKey(name)) {
	    try {
		return itemActions.get(name).newInstance();
	    } catch (InstantiationException | IllegalAccessException e) {
		Bukkit.getLogger().log(Level.SEVERE, e.getMessage(), e);
	    }
	}
	throw new IllegalArgumentException(name + " is not a valid ItemAction name !");
    }
}
