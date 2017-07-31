package fr.pelt10.lobbymanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pelt10.lobbymanager.LobbyManager;

public class SpawnCommand implements CommandExecutor {
    private LobbyManager lobbyManager;

    public SpawnCommand(LobbyManager lobbyManager) {
	this.lobbyManager = lobbyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (sender instanceof Player) {
	    ((Player) sender).teleport(lobbyManager.getSpawnConfig().getSpawnLocation());
	    return true;
	}
	return false;
    }
}
