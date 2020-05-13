package com.votemine.votemineReward.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class StoreItem {

    private String rewardName = null;
    private ItemStack itemStack;

    public static StoreItem fromConfig(ConfigurationSection config){
        String rewardName = config.getString("buyReward", null);
        ItemStack item = new ItemStack(Material.valueOf(config.getString("material")), config.getInt("amount",1));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',config.getString("name","")));
        List<String> lore = config.getStringList("lore");
        lore = lore.stream()
                .map(line -> ChatColor.WHITE+ChatColor.translateAlternateColorCodes('&',line))
                .collect(Collectors.toList());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return new StoreItem(rewardName, item);
    }

    StoreItem(String rewardName, ItemStack itemStack){
        this.rewardName = rewardName;
        this.itemStack = itemStack;
    }

    public boolean hasConfiguredReward(){
        return rewardName != null;
    }

    public String getRewardName(){
        return rewardName;
    }

    public ItemStack getItemStack(){
        return itemStack;
    }

}
