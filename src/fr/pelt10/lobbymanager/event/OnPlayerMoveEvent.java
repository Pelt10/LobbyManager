package fr.pelt10.lobbymanager.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.pelt10.lobbymanager.LobbyManager;

public class OnPlayerMoveEvent implements Listener {
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		p.setFoodLevel(20);

		if (p.getLocation().getY() <= LobbyManager.height) {
			p.teleport(LobbyManager.spawn);
			p.sendMessage(LobbyManager.lang.getSentence("FallMessage"));
		}
	}
}
