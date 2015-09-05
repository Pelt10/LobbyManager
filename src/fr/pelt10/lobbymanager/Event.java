package fr.pelt10.lobbymanager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Event implements Listener {
	@EventHandler
	public void onPlayerConnect(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		p.teleport(LobbyManager.spawn);

		if (!p.getPlayer().hasPermission("lobbymanager.gmBypass")) {
			p.setGameMode(LobbyManager.gm);
		}

		LobbyManager.setInventory(p);
	}

	@EventHandler
	public void onMoveEvent(PlayerMoveEvent e) {
		Location loc = e.getPlayer().getLocation();
		Player p = e.getPlayer();

		p.setFoodLevel(20);

		if (loc.getY() <= LobbyManager.height) {
			p.teleport(LobbyManager.spawn);
			p.sendMessage(LobbyManager.fallMessage);
		}
	}

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlayerMoveItemEvent(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (!p.hasPermission("lobbymanager.useInventory")) {
			LobbyManager.setInventory(p);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDamageEvent(EntityDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onInventoryEvent(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemConfig inventory[] = LobbyManager.inventory;
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] != null) {
					if (e.getPlayer().getItemInHand().isSimilar(inventory[i].getItem())) {
						e.getPlayer().performCommand(inventory[i].getCommand());
					}
				}
			}

		}
	}
}
