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
    private static final String FILE_NAME = "spawn.yml";
    private static final String CONFIG_PREFIX = "spawn.";

    public SpawnConfig(JavaPlugin javaPlugin) {
	this.javaPlugin = javaPlugin;

	// Load config
	config = YamlConfiguration.loadConfiguration(new File(javaPlugin.getDataFolder(), FILE_NAME));

	// Load default config
	try (InputStream defaultConfigStream = javaPlugin.getResource(FILE_NAME)) {
	    YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));

	    config.setDefaults(defaultConfig);
	    javaPlugin.saveResource(FILE_NAME, false);
	} catch (IOException e) {
	    throw new IllegalStateException("spawn.yml not find in jar !", e);
	}
    }

    public Location getSpawnLocation() {
	return new Location(javaPlugin.getServer().getWorld(config.getString(CONFIG_PREFIX + "world")),
			    config.getDouble(CONFIG_PREFIX + "x"),
			    config.getDouble(CONFIG_PREFIX + "y"),
			    config.getDouble(CONFIG_PREFIX + "z"),
		     (float)config.getDouble(CONFIG_PREFIX + "pitch"),
		     (float)config.getDouble(CONFIG_PREFIX + "yaw"));
    }

    public void setSpawnLocation(Location location) {
	try {
	    config.set(CONFIG_PREFIX + "world", location.getWorld().getName());
	    config.set(CONFIG_PREFIX + "x", location.getX());
	    config.set(CONFIG_PREFIX + "y", location.getY());
	    config.set(CONFIG_PREFIX + "z", location.getZ());
	    config.set(CONFIG_PREFIX + "picth", location.getPitch());
	    config.set(CONFIG_PREFIX + "yaw", location.getYaw());
	    config.save(FILE_NAME);
	} catch (IOException e) {
	    javaPlugin.getLogger().log(Level.CONFIG, e.getMessage(), e);
	}
    }
}