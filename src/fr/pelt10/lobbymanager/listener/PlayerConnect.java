package fr.pelt10.lobbymanager.listener;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.pelt10.lobbymanager.LobbyManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class PlayerConnect implements Listener {
	@EventHandler
	public void onPlayerConnect(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		p.teleport(LobbyManager.spawn);

		if (LobbyManager.config.getBoolean("join.title.enable"))
			sendTitle(p, LobbyManager.title[0], LobbyManager.title[1]);

		if (LobbyManager.config.getBoolean("join.gamemode.enable")) {
			if (!p.getPlayer().hasPermission("lobbymanager.gmBypass"))
				p.setGameMode(LobbyManager.gm);
		}
		
		if(LobbyManager.config.getBoolean("Inventory.give.enable"))
			LobbyManager.setInventory(p);
	}
	
	private void sendTitle(Player player, String title, String subtitle){
			title = title.replace("'", "\\'");
			subtitle = subtitle.replace("'", "\\'");
			
            CraftPlayer craftplayer = (CraftPlayer)player;
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
