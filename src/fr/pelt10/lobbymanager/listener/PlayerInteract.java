package fr.pelt10.lobbymanager.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Location;
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
    private Map<UUID, Location> lastPlayerLocInteract = new HashMap<>();
    
    public PlayerInteract(LobbyManager lobbyManager) {
	this.lobbyManager = lobbyManager;
	lobbyManager.getServer().getScheduler().runTaskTimerAsynchronously(lobbyManager, () -> lastPlayerLocInteract.clear(), 0L, 10L);
    }
    
    @EventHandler
    public void onInventory(PlayerInteractEvent e) {
	Action action = e.getAction();
	Player player = e.getPlayer();
	PlayerInventory playerInventory = player.getInventory();
	if (!lastPlayerLocInteract.containsKey(player.getUniqueId())) {
	    lastPlayerLocInteract.put(player.getUniqueId(), player.getLocation());
	    if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
		CustomItem item = lobbyManager.getInventoryManager().getHotbar()[playerInventory.getHeldItemSlot()];
		if (!Objects.isNull(item) && item.getItem().equals(playerInventory.getItemInMainHand())) {
		    item.getItemAction().onAction(player);
		}
	    }
	}
    }
}
