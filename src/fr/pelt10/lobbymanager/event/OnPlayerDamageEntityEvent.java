package fr.pelt10.lobbymanager.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnPlayerDamageEntityEvent implements Listener {
	@EventHandler
	public void onPlayerDamageEvent(EntityDamageEvent e) {
		e.setCancelled(true);
	}
}
