package fr.pelt10.lobbymanager.inventory.item;

import org.bukkit.entity.Player;

public class CommandAction implements ItemAction {
    private String command;
    
    @Override
    public void onAction(Player player) {
	player.performCommand(command);
    }

    @Override
    public String getName() {
	return "command";
    }

    @Override
    public ItemAction giveParameters(String arg) {
	command = arg;
	return this;
    }

}
