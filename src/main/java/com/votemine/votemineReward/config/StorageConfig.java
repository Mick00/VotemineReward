package com.votemine.votemineReward.config;

import com.votemine.votemineReward.storage.StorageType;
import org.bukkit.configuration.ConfigurationSection;

public class StorageConfig {
    private StorageType type;
    private String host;
    private int port;
    private String user;
    private String password;

    public static StorageConfig fromConfig(ConfigurationSection section){
        return new StorageConfig(
                StorageType.valueOf(section.getString("type","SQLite")),
                section.getString("host","localhost"),
                section.getInt("port",3306),
                section.getString("user","votemine"),
                section.getString("password","")
        );
    }

    public StorageConfig(StorageType type, String host, int port, String user, String password) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public StorageConfig(){
        this.type = StorageType.SQLite;
    }

    public StorageType getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
