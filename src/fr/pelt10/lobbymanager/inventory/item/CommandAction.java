package fr.pelt10.lobbymanager.inventory.item;

import org.bukkit.entity.Player;

public class CommandAction implements ItemAction {
    private String command;
    
    public CommandAction(String command) {
	this.command = command;
    }

    @Override
    public void onAction(Player player) {
	player.performCommand(command);
    }

    @Override
    public String getName() {
	return "command";
    }

}
