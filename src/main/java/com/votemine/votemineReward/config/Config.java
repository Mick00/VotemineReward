package com.votemine.votemineReward;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

    private Plugin plugin;
    private FileConfiguration config;

    public Config(Plugin plugin){
        this.plugin = plugin;
        setDefaults();
        read();
    }

    private void setDefaults(){
        Bukkit.getLogger().info("Adding defaults");
        config.addDefault(TOKEN_PATH, "YOURAPITOKENHERE");
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public void reload(){
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        read();
    }

}
