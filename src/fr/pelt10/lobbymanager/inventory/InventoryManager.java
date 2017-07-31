package fr.pelt10.lobbymanager.inventory;

import java.util.Optional;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Sets;

import fr.pelt10.lobbymanager.inventory.item.CommandAction;
import fr.pelt10.lobbymanager.inventory.item.ItemAction;

public class InventoryManager {
    private CustomItem[] hotbar = new CustomItem[9];
    private static final String PREFIX_GIVE = "inventory.give.";
    private static Set<ItemAction> itemActions = Sets.newHashSet(new CommandAction());
    
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
    
    static ItemAction getAction(String name) {
	Optional<ItemAction> optional = itemActions.stream().filter(action -> action.getName().equals(name)).findFirst();
	if(optional.isPresent()) {
	    return optional.get();
	}
	throw new IllegalArgumentException(name + " is not a valid ItemAction name !");
    }
}
