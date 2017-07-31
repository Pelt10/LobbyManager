package fr.pelt10.lobbymanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnConfig {
    private FileConfiguration config;
    private JavaPlugin javaPlugin;
    private File fileConfig;
    private static final String FILE_NAME = "spawn.yml";
    private static final String CONFIG_PREFIX = "spawn.";
    private Location spawnLocation;

    public SpawnConfig(JavaPlugin javaPlugin) {
	this.javaPlugin = javaPlugin;

	// Load config
	fileConfig = new File(javaPlugin.getDataFolder(), FILE_NAME);
	config = YamlConfiguration.loadConfiguration(fileConfig);

	// Load default config
	try (InputStream defaultConfigStream = javaPlugin.getResource(FILE_NAME)) {
	    YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));

	    config.setDefaults(defaultConfig);
	    javaPlugin.saveResource(FILE_NAME, false);
	} catch (IOException e) {
	    throw new IllegalStateException("spawn.yml not find in jar !", e);
	}
	
	spawnLocation = new Location(javaPlugin.getServer().getWorld(config.getString(CONFIG_PREFIX + "world")),
                		    config.getDouble(CONFIG_PREFIX + "x"),
                		    config.getDouble(CONFIG_PREFIX + "y"),
                		    config.getDouble(CONFIG_PREFIX + "z"),
                	     (float)config.getDouble(CONFIG_PREFIX + "yaw"),
                	     (float)config.getDouble(CONFIG_PREFIX + "picth"));
    }

    public Location getSpawnLocation() {
	return spawnLocation;
    }
    
    public void setSpawnLocation(Location location) {
	try {
	    spawnLocation = location;
	    config.set(CONFIG_PREFIX + "world", location.getWorld().getName());
	    config.set(CONFIG_PREFIX + "x", location.getX());
	    config.set(CONFIG_PREFIX + "y", location.getY());
	    config.set(CONFIG_PREFIX + "z", location.getZ());
	    config.set(CONFIG_PREFIX + "picth", location.getPitch());
	    config.set(CONFIG_PREFIX + "yaw", location.getYaw());
	    config.save(fileConfig);
	} catch (IOException e) {
	    javaPlugin.getLogger().log(Level.CONFIG, e.getMessage(), e);
	}
    }
}