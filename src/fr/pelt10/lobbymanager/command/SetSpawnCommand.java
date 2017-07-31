package fr.pelt10.lobbymanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pelt10.lobbymanager.LobbyManager;

public class SetSpawnCommand implements CommandExecutor {
    private LobbyManager lobbyManager;

    public SetSpawnCommand(LobbyManager lobbyManager) {
	this.lobbyManager = lobbyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (sender instanceof Player) {
	    Player player = (Player) sender;
	    lobbyManager.getSpawnConfig().setSpawnLocation(player.getLocation());
	    player.sendMessage(lobbyManager.getLanguage().getSentence("SetSpawn"));
	    return true;
	}
	return false;
    }
}
