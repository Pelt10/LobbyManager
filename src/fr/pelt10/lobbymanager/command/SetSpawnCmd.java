package fr.pelt10.lobbymanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pelt10.lobbymanager.LobbyManager;

public class SetSpawnCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player) || !sender.hasPermission("lobbymanager.setspawn")) {
			sender.sendMessage(LobbyManager.lang.getSentence("NoPermission"));
			return true;
		}

		Player p = (Player) sender;
		LobbyManager.spawn = p.getLocation();
		p.sendMessage(LobbyManager.lang.getSentence("SetSpawn"));
		return true;
	}

}
