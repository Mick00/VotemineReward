package com.votemine.votemineReward.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Reward {
    private int price;
    private List<String> commands;

    public static Reward fromConfig(ConfigurationSection section){
        if (section.isSet("command")) {
            return new Reward(
                    section.getInt("price", 0),
                    section.getString("command", "say add a command to this item")
            );
        } else {
            return new Reward(section.getInt("price",0),
                    section.getStringList("commands")
                    );
        }
    }

    public Reward(int price, String command) {
        this.price = price;
        this.commands = Collections.singletonList(command);
    }

    public Reward(int price, List<String> commands){
       this.price = price;
       this.commands = commands;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getCommands() {
        return commands;
    }

    public List<String> getFilledCommands(String playername){
        return commands.stream().map(
                command -> command.replace("%playername%", playername).replace("%price%",String.valueOf(price)))
                .collect(Collectors.toList());
    }
}
