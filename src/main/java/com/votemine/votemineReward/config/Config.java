package com.votemine.votemineReward.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

    private Plugin plugin;
    private FileConfiguration config;
    private StorageConfig storageConfig;
    private RewardsConfig rewardsConfig;

    private final String STORAGE_SECTION_PATH = "storage";
    private final String REWARD_SECTION_PATH = "rewards";

    public Config(Plugin plugin){
        this.plugin = plugin;
        this.config = plugin.getConfig();
        setDefaults();
        read();
    }

    private void setDefaults(){
        Bukkit.getLogger().info("Adding defaults");
        config.addDefault(STORAGE_SECTION_PATH +".type", "sqlite");
        config.addDefault(REWARD_SECTION_PATH+".congrats.price", 1);
        config.addDefault(REWARD_SECTION_PATH+".congrats.command", "say Merci %playername%. Ceci vaut %price% pv");
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public void reload(){
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        read();
    }

    private void read(){
        storageConfig = StorageConfig.fromConfig(config.getConfigurationSection(STORAGE_SECTION_PATH));
        rewardsConfig = RewardsConfig.fromConfig(config.getConfigurationSection(REWARD_SECTION_PATH));
    }

    public StorageConfig getStorageConfig(){
        return storageConfig;
    }

}
