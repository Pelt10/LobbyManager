package fr.pelt10.lobbymanager.listener;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import fr.pelt10.lobbymanager.LobbyManager;
import fr.pelt10.lobbymanager.inventory.CustomItem;

public class PlayerInteract implements Listener {
    private LobbyManager lobbyManager;
    
    public PlayerInteract(LobbyManager lobbyManager) {
	this.lobbyManager = lobbyManager;
    }
    
    @EventHandler
    public void onInventory(PlayerInteractEvent e) {
	Action action = e.getAction();
	Player player = e.getPlayer();
	PlayerInventory playerInventory = player.getInventory();
	
	if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
	    CustomItem item = lobbyManager.getInventoryManager().getHotbar()[playerInventory.getHeldItemSlot()];
	    if (!Objects.isNull(item) && item.getItem().equals(playerInventory.getItemInMainHand())){
		item.getItemAction().onAction(player);
	    }
	}
    }
}
