package fr.pelt10.lobbymanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pelt10.lobbymanager.LobbyManager;

public class SpawnCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player) || !sender.hasPermission("lobbymanager.spawn")) {
			sender.sendMessage(LobbyManager.lang.getSentence("NoPermission"));
			return true;
		}

		((Player)sender).teleport(LobbyManager.spawn);
		return true;
	}

}
