package fr.pelt10.lobbymanager.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnPlayerDropItemEvent implements Listener {
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}
}
