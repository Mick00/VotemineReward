package com.votemine.votemineReward.config;

import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class ServerRewardConfig {

    private Reward reward;

    public static ServerRewardConfig fromConfig(ConfigurationSection section){
        return new ServerRewardConfig(
                Reward.fromConfig(section)
        );
    }

    public ServerRewardConfig(Reward reward){
        this.reward = reward;
    }

    public boolean enabled(){
        return reward.getPrice() > 0;
    }

    public List<String> getCommands(){
        return reward.getCommands();
    }

    public int getObjective(){
        return reward.getPrice();
    }

}
