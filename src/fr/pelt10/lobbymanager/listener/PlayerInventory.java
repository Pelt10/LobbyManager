package fr.pelt10.lobbymanager.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerInventory implements Listener {
    @EventHandler
    public void onInventory(InventoryClickEvent e) {
	if (!(e.getWhoClicked() instanceof Player)) {
	    return;
	}

	Player player = (Player) e.getWhoClicked();
	if (!player.hasPermission("lobbymanager.useinventory")) {
	    e.setCancelled(true);
	    return;
	}
    }
}
