package com.votemine.votemineReward.config;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RewardsConfig {
    private HashMap<String, Reward> rewards;

    public static RewardsConfig fromConfig(ConfigurationSection section){
        RewardsConfig rewardsConfig = new RewardsConfig();
        Set<String> rewardsName = section.getKeys(false);
        for (String rewardName : rewardsName) {
            rewardsConfig.addRewardFromConfig(rewardName, section.getConfigurationSection(rewardName));
        }
        return rewardsConfig;
    }

    public void addRewardFromConfig(String rewardName, ConfigurationSection section){
        rewards.put(rewardName, Reward.fromConfig(section));
    }

    public RewardsConfig(){
        rewards = new HashMap<>();
    }

    public HashMap<String, Reward> getRewards(){
        return rewards;
    }

    public Reward getReward(String name){
        return rewards.get(name);
    }
}
