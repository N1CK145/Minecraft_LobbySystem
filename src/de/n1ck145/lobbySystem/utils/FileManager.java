package de.n1ck145.lobbySystem.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class FileManager {
    private File f;
    private YamlConfiguration conf;

    public FileManager(String path, String fileName) {
        this.f = new File(path, fileName);
        this.conf = YamlConfiguration.loadConfiguration(this.f);
    }

    public void save() {
        try {
            this.conf.save(this.f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object val) {
        this.conf.set(path, val);
    }

    public int getInt(String path) {
        return this.conf.getInt(path);
    }
    public List < Integer > getIntegerList(String path) {
        return this.conf.getIntegerList(path);
    }

    public double getDouble(String path) {
        return this.conf.getDouble(path);
    }

    public String getString(String path) {
        return this.conf.getString(path);
    }
    
    public List < String > getStringList(String path) {
        return this.conf.getStringList(path);
    }

    public boolean getBoolean(String path) {
        return this.conf.getBoolean(path);
    }

    public void setLocation(String path, Location loc, boolean direction) {
        this.conf.set(path + ".World", loc.getWorld().getName());
        this.conf.set(path + ".X", loc.getX());
        this.conf.set(path + ".Y", loc.getY());
        this.conf.set(path + ".Z", loc.getZ());
        if(direction) {
        	this.conf.set(path + ".Yaw", loc.getYaw());
        	this.conf.set(path + ".Pitch", loc.getPitch());
        }
    }
    
    public Location getLocation(String path) {
        World world = Bukkit.getWorld(this.conf.getString(String.valueOf(path) + ".World"));
        double x = this.conf.getDouble(path + ".X");
        double y = this.conf.getDouble(path + ".Y");
        double z = this.conf.getDouble(path + ".Z");
        
        try {
        	float yaw = (float) this.conf.getDouble(path + ".Yaw");
        	float pitch = (float) this.conf.getDouble(path + ".Pitch");
        	return new Location(world, x, y, z, yaw, pitch);
        } catch (Exception e) {}

        return new Location(world, x, y, z);
    }

    public YamlConfigurationOptions options() {
        return this.conf.options();
    }
    
    public void addDefault(String path, Object val) {
        this.conf.addDefault(path, val);
    }
    
    public boolean contains(String path) {
        return this.conf.contains(path);
    }
    
    public ConfigurationSection getConfigurationSection(String path) {
        return this.conf.getConfigurationSection(path);
    }
}