package fr.pelt10.lobbymanager.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.pelt10.lobbymanager.Language;
import fr.pelt10.lobbymanager.LobbyManager;
import fr.pelt10.lobbymanager.inventory.InventoryManager;

public class ReloadInventoryCommand implements CommandExecutor {
    private LobbyManager lobbyManager;
    private Language language;
    private InventoryManager inventoryManager;
    
    public ReloadInventoryCommand(LobbyManager lobbyManager) {
	this.lobbyManager = lobbyManager;
	language = lobbyManager.getLanguage();
	inventoryManager = lobbyManager.getInventoryManager();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	Bukkit.broadcastMessage(language.getSentence("ReloadInventory"));

	lobbyManager.getServer().getOnlinePlayers().forEach(player -> inventoryManager.sendInventory(player));
	
	sender.sendMessage(language.getSentence("ReloadInventorySuccessful"));
	return true;
    }

}
