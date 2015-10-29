package fr.pelt10.lobbymanager.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pelt10.lobbymanager.LobbyManager;

public class ReloadInventoryCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("lobbymanager.reloadinventory")){
			sender.sendMessage(LobbyManager.lang.getSentence("NoPermission"));
			return true;
		}
		
		Bukkit.broadcastMessage(LobbyManager.lang.getSentence("ReloadInventory"));
		
		for(Player player : Bukkit.getOnlinePlayers()){
			LobbyManager.setInventory(player);
		}
		sender.sendMessage(LobbyManager.lang.getSentence("ReloadInventorySuccessful"));
		return true;
	}

}
