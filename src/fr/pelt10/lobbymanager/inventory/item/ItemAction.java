package fr.pelt10.lobbymanager.inventory.item;

import org.bukkit.entity.Player;

public interface ItemAction {
public void onAction(Player player);
public ItemAction giveParameters(String arg);
}
