package fr.pelt10.lobbymanager.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.pelt10.lobbymanager.LobbyManager;

public class PlayerMove implements Listener {
    private boolean teleportOnFall;
    private int height;
    private LobbyManager lobbyManager;
    
    public PlayerMove(LobbyManager lobbyManager) {
	this.lobbyManager = lobbyManager;
	FileConfiguration config = lobbyManager.getConfig();
	teleportOnFall = config.getBoolean("nofall.enable");
	height = config.getInt("noFall.level");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
	Player player = e.getPlayer();

	player.setFoodLevel(20);

	if (teleportOnFall && player.getLocation().getY() <= height) {
	    player.teleport(lobbyManager.getSpawnConfig().getSpawnLocation());
	    player.sendMessage(lobbyManager.getLanguage().getSentence("FallMessage"));
	}
    }
}
