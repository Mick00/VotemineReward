package com.votemine.votemineReward.config;

import com.votemine.votemineReward.exceptions.RewardNotFoundException;
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

    public Reward getReward(String name) throws RewardNotFoundException {
        Reward reward = rewards.getOrDefault(name, null);
        if (reward == null){
            throw new RewardNotFoundException(name);
        }
        return reward;
    }

    public boolean rewardExists(String name){
        return rewards.getOrDefault(name, null) != null;
    }
}
