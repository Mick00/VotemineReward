package com.votemine.votemineReward.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class Reward {
    private int price;
    private String command;

    public static Reward fromConfig(ConfigurationSection section){
        return new Reward(
                section.getInt("price", 0),
                section.getString("command", "say add a command to this item")
        );
    }

    public Reward(int price, String command) {
        this.price = price;
        this.command = command;
    }

    public int getPrice() {
        return price;
    }

    public String getCommand() {
        return command;
    }
}
