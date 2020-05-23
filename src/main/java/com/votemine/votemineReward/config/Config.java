package com.votemine.votemineReward.config;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

    private Plugin plugin;
    private FileConfiguration config;
    private StorageConfig storageConfig;
    private RewardsConfig rewardsConfig;
    private StoreConfig storeConfig;
    private String voteLink;
    private ServerRewardConfig serverRewardConfig;

    private final String STORAGE_SECTION_PATH = "storage";
    private final String REWARD_SECTION_PATH = "rewards";
    private final String STORE_SECTION_PATH = "store";
    private final String SERVER_REWARD_PATH = "serverReward";

    public Config(Plugin plugin){
        this.plugin = plugin;
        this.config = plugin.getConfig();
        setDefaults();
        read();
    }

    private void setDefaults(){
        if (!config.isSet("storage")){
            Bukkit.getLogger().info("Adding defaults");
            config.addDefault(STORAGE_SECTION_PATH +".type", "sqlite");
            config.options().copyDefaults(true);
            plugin.saveConfig();
        }
    }

    public void reload(){
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        read();
    }

    private void read(){
        storageConfig = StorageConfig.fromConfig(config.getConfigurationSection(STORAGE_SECTION_PATH));
        rewardsConfig = RewardsConfig.fromConfig(config.getConfigurationSection(REWARD_SECTION_PATH));
        storeConfig = StoreConfig.fromConfig(config.getConfigurationSection(STORE_SECTION_PATH));
        serverRewardConfig = ServerRewardConfig.fromConfig(config.getConfigurationSection(SERVER_REWARD_PATH));
        voteLink = config.getString("link", "https://votemine.com/fr/server/Station47");
    }

    public StorageConfig getStorageConfig(){
        return storageConfig;
    }
    public RewardsConfig getRewardsConfig() { return  rewardsConfig; }
    public ServerRewardConfig getServerRewardConfig(){ return serverRewardConfig; }
    public StoreConfig getStoreConfig(){ return storeConfig; }
    public String getVoteLink() {
        return voteLink;
    }
}
