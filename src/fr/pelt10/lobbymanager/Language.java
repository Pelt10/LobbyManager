package fr.pelt10.lobbymanager;


import java.io.File;
import java.io.InputStream;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Language {
	private Plugin plugin = null;

	private File configFile = null;
	private FileConfiguration config = null;
	private String language = null;

	public Language(Plugin pl, String lang) {
		plugin = pl;
		language = lang;
		configFile = new File(this.plugin.getDataFolder(), language + ".yml");
		
		this.load();
		this.saveDefault();
	}

	public void load() {
		config = YamlConfiguration.loadConfiguration(configFile);

		InputStream defaultConfigStream = this.plugin.getResource(language + ".yml");
		if (defaultConfigStream != null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
			config.setDefaults(defaultConfig);
		}
	}

	public void saveDefault() {
		if (!configFile.exists()) {
			plugin.saveResource(language + ".yml", false);
		}
	}

	public String getSentence(String name) {
		String sentence = this.config.getString(name);
		if (sentence == null) {
			this.plugin.getLogger().warning("Missing sentence: " + name);
			sentence = ChatColor.RED + "[missing sentence]";
		}
		
		return sentence;
	}
	
}