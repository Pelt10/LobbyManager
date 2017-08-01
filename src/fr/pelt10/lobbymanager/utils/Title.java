package fr.pelt10.lobbymanager.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Title {
    private static final String NMS = "net.minecraft.server.";
    private Object titleJson;
    private Object subTitleJson;
    private int fadeIn = 10;
    private int stay = 70;
    private int fadeOut = 20;
    private static String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName();
    
    
    static {
	bukkitVersion = bukkitVersion.substring(bukkitVersion.lastIndexOf('.') + 1);
    }
    
    public Title(String title, String subtitle) {
	this.titleJson = title.replace("\"", "\\\"");
	this.subTitleJson = subtitle.replace("\"", "\\\"");
	try {
	    Class<?> chatSerializerClazz = Class.forName(NMS + bukkitVersion + ".IChatBaseComponent$ChatSerializer");
	    Arrays.stream(chatSerializerClazz.getClasses()).forEach(System.out::println);
	    Method a = chatSerializerClazz.getDeclaredMethod("a", String.class);
	    this.titleJson = a.invoke(null,"{\"text\": \"" + title.replace("\"", "\\\"") + "\"}");
	    this.subTitleJson = a.invoke(null,"{\"text\": \"" + subtitle.replace("\"", "\\\"") + "\"}");
	} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	    Bukkit.getLogger().log(Level.SEVERE, e.getMessage(), e);
	}
    }

    public Title setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
        return this;
    }

    public Title setStay(int stay) {
        this.stay = stay;
        return this;
    }

    public Title setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
        return this;
    }
    
    public Title setTimes(int fadeIn, int stay, int fadeOut) {
	setFadeIn(fadeIn);
	setStay(stay);
	setFadeOut(fadeOut);
	return this;
    }

    public void sendTitle(Player player) {
	Class<?> craftPlayerclazz;
	try {
	    craftPlayerclazz = Class.forName("org.bukkit.craftbukkit." + bukkitVersion + ".entity.CraftPlayer");
	    Object craftplayer = craftPlayerclazz.cast(player);
	    Object entityPlayer = craftplayer.getClass().getMethod("getHandle").invoke(craftplayer);
	    Object connection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

	    Class<?> packetPlayOutTitleClazz = Class.forName(NMS + bukkitVersion + ".PacketPlayOutTitle");
	    Class<?> enumTitleActionClazz = Class.forName(NMS + bukkitVersion + ".PacketPlayOutTitle$EnumTitleAction");
	    Class<?> iChatBaseComponentClazz = Class.forName(NMS + bukkitVersion + ".IChatBaseComponent");
	    Object timesPacket = packetPlayOutTitleClazz.getDeclaredConstructor(enumTitleActionClazz, iChatBaseComponentClazz, int.class, int.class, int.class).newInstance(Enum.valueOf((Class<Enum>) enumTitleActionClazz, "TIMES"), titleJson, fadeIn, stay, fadeOut);
	    Constructor<?> packetPlayOutTitleConstructor = packetPlayOutTitleClazz.getConstructor(enumTitleActionClazz, iChatBaseComponentClazz);
	    Object titlePacket = packetPlayOutTitleConstructor.newInstance(Enum.valueOf((Class<Enum>) enumTitleActionClazz, "TITLE"), titleJson);
	    Object subtitlePacket = packetPlayOutTitleConstructor.newInstance(Enum.valueOf((Class<Enum>) enumTitleActionClazz, "SUBTITLE"), subTitleJson);
	    
	    Method sendPacketConnection = connection.getClass().getDeclaredMethod("sendPacket", Class.forName(NMS + bukkitVersion + ".Packet"));
	    sendPacketConnection.invoke(connection, timesPacket);
	    sendPacketConnection.invoke(connection, titlePacket);
	    sendPacketConnection.invoke(connection, subtitlePacket);
	} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException | InstantiationException e) {
	    Bukkit.getLogger().log(Level.SEVERE, e.getMessage(), e);
	}
    }
}
