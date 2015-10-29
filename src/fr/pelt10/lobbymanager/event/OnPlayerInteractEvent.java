package fr.pelt10.lobbymanager.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.pelt10.lobbymanager.ItemConfig;
import fr.pelt10.lobbymanager.LobbyManager;

public class OnPlayerInteractEvent implements Listener {
	@EventHandler
	public void onInventory(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemConfig item = LobbyManager.inventory[e.getPlayer().getInventory().getHeldItemSlot()];
			if(item != null && item.getItem().equals(e.getPlayer().getItemInHand()))		
				e.getPlayer().performCommand(LobbyManager.inventory[e.getPlayer().getInventory().getHeldItemSlot()].getCommand());
		}
	}
}
