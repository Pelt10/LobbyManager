package fr.pelt10.lobbymanager.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnPlayerInventoryEvent implements Listener {
	@EventHandler
	public void onInventory(InventoryClickEvent e) {
		if(!(e.getWhoClicked() instanceof Player)){
			return;
		}
		
		Player p = (Player) e.getWhoClicked();
		if(!p.hasPermission("lobbymanager.useInventory")){
			e.setCancelled(true);
			return;
		}
	}
}
