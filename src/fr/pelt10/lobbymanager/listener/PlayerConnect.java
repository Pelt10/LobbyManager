package fr.pelt10.lobbymanager.listener;

import java.util.Objects;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.pelt10.lobbymanager.LobbyManager;
import fr.pelt10.lobbymanager.utils.Title;

public class PlayerConnect implements Listener {
    private LobbyManager lobbyManager;
    
    //Title
    private boolean sendTitle;
    private String[] title = new String[2];
    
    //GameMode
    private boolean setGameMode;
    private GameMode gameMode;
    
    public PlayerConnect(LobbyManager lobbyManager) {
	this.lobbyManager = lobbyManager;
	FileConfiguration configuration = lobbyManager.getConfig();

	sendTitle = configuration.getBoolean("join.title.enable");
	title[0] = configuration.getString("join.title.title");
	title[1] = configuration.getString("join.title.subtitle");

	setGameMode = configuration.getBoolean("join.gamemode.enable");
	gameMode = GameMode.valueOf(configuration.getString("join.gamemode.gamemode"));
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent e) {
	Player player = e.getPlayer();
	
	player.teleport(lobbyManager.getSpawnConfig().getSpawnLocation());

	if (sendTitle) {
	    new Title(title[0], title[1]).setTimes(10, 20, 10).sendTitle(player);
	}

	if (setGameMode && !player.hasPermission("lobbymanager.gamemode.bypass")) {
	    player.setGameMode(gameMode);
	}

	if (!Objects.isNull(lobbyManager.getInventoryManager())) {
	    lobbyManager.getInventoryManager().sendInventory(player);
	}
    }
}
