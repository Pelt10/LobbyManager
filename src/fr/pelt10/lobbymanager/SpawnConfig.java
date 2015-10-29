package fr.pelt10.lobbymanager;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SpawnConfig {
	private Plugin plugin = null;

	private File configFile = null;
	private FileConfiguration config = null;

	public SpawnConfig(Plugin pl) {
		plugin = pl;
		configFile = new File(this.plugin.getDataFolder(), "spawn.yml");
		
		this.load();
		this.saveDefault();
	}

	public void load() {
		config = YamlConfiguration.loadConfiguration(configFile);

		InputStream defaultConfigStream = this.plugin.getResource("spawn.yml");
		if (defaultConfigStream != null) {
			@SuppressWarnings("deprecation")
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
			config.setDefaults(defaultConfig);
		}
	}

	public void saveDefault() {
		if (!configFile.exists()) {
			plugin.saveResource("spawn.yml", false);
		}
	}

	public String getString(String path) {
		return config.getString(path);
	}
	
	public double getDouble(String path){
		return config.getDouble(path);
	}
	
	public void set(String path, Object value){
		config.set(path, value);
	}
	
	public void save(){
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}