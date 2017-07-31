package fr.pelt10.lobbymanager.listener;

import java.util.Objects;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.pelt10.lobbymanager.LobbyManager;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_12_R1.PlayerConnection;

public class PlayerConnect implements Listener {
    private LobbyManager lobbyManager;
    
    //Title
    private boolean sendTitle;
    private String[] title = new String[2];
    
    //GameMode
    private boolean setGameMode;
    private GameMode gameMode;
    
    public PlayerConnect(LobbyManager lobbyManager) {
	this.lobbyManager = lobbyManager;
	FileConfiguration configuration = lobbyManager.getConfig();

	sendTitle = configuration.getBoolean("join.title.enable");
	title[0] = configuration.getString("join.title.title");
	title[1] = configuration.getString("join.title.subtitle");

	setGameMode = configuration.getBoolean("join.gamemode.enable");
	gameMode = GameMode.valueOf(configuration.getString("join.gamemode.gamemode"));
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent e) {
	Player player = e.getPlayer();
	
	player.teleport(lobbyManager.getSpawnConfig().getSpawnLocation());

	if (sendTitle) {
	    sendTitle(player, title[0], title[1]);
	}

	if (setGameMode && !player.hasPermission("lobbymanager.gamemode.bypass")) {
	    player.setGameMode(gameMode);
	}

	if (!Objects.isNull(lobbyManager.getInventoryManager())) {
	    lobbyManager.getInventoryManager().sendInventory(player);
	}
    }

    //TODO Move and create Reflexion
    private void sendTitle(Player player, String title, String subtitle) {
	title = title.replace("'", "\\'");
	subtitle = subtitle.replace("'", "\\'");

	CraftPlayer craftplayer = (CraftPlayer) player;
	PlayerConnection connection = craftplayer.getHandle().playerConnection;
	IChatBaseComponent titleJSON = ChatSerializer.a("{'text': '" + title + "'}");
	IChatBaseComponent subtitleJSON = ChatSerializer.a("{'text': '" + subtitle + "'}");

	PacketPlayOutTitle timesPacket = new PacketPlayOutTitle(EnumTitleAction.TIMES, titleJSON, 10, 20, 10);
	PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON);
	PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);

	connection.sendPacket(timesPacket);
	connection.sendPacket(titlePacket);
	connection.sendPacket(subtitlePacket);
    }

}
