package fr.pelt10.lobbymanager;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.plugin.java.JavaPlugin;

public class Language {
	private FileConfiguration config;
	private String fileName;
	private JavaPlugin javaPlugin;

    public Language(JavaPlugin javaPlugin, String language) {
	fileName = language + ".yml";
	this.javaPlugin = javaPlugin;

	// Load config
	config = YamlConfiguration.loadConfiguration(new File(javaPlugin.getDataFolder(), fileName));

	// Load default config
	try (InputStream defaultConfigStream = javaPlugin.getResource(fileName)) {
	    YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));

	    config.setDefaults(defaultConfig);
	    javaPlugin.saveResource(fileName, false);
	} catch (IOException e) {
	    throw new IllegalStateException(fileName + " not find in jar !", e);
	}
    }

    public String getSentence(String name) {
	String sentence = config.getString(name);
	if (sentence == null) {
	    javaPlugin.getLogger().log(Level.WARNING, "Missing sentence: %s", name);
	    sentence = ChatColor.RED + "[missing sentence]";
	}

	return sentence;
    }	
}