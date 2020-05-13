package com.votemine.votemineReward.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StoreConfig {

    private String storeName;
    private HashMap<Integer, StoreItem> items;
    private int maxSlot = 0;

    public static StoreConfig fromConfig(ConfigurationSection config){
        StoreConfig storeConfig = new StoreConfig(config.getString("storeName", "Minevote store"));

        ConfigurationSection slots = config.getConfigurationSection("slots");
        Set<String> keys = slots.getKeys(false);
        for (String key : keys) {
            StoreItem storeItem = StoreItem.fromConfig(slots.getConfigurationSection(key));
            storeConfig.addItem(Integer.parseInt(key),storeItem);
        }
        return storeConfig;
    }

    public StoreConfig(String name){
        storeName = name;
        items = new HashMap<>();
    }

    public void addItem(int index, StoreItem storeItem){
        if (index > maxSlot){
            maxSlot = index;
        }
        items.put(index, storeItem);
    }

    public StoreItem getItem(int index){
        return items.getOrDefault(index,new StoreItem(null, new ItemStack(Material.AIR)));
    }

    public int getInventorySize(){
        return maxSlot + (9 - maxSlot % 9);
    }

    public String getName(){
        return storeName;
    }

    public ItemStack[] getStoreContents(){
        ItemStack[] items = new ItemStack[getInventorySize()];
        this.items.forEach((slot, storeItem)->{
            items[slot] = storeItem.getItemStack();
        });
        return items;
    }

}
