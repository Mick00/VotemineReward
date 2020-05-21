package com.votemine.votemineReward;

import co.aikar.commands.PaperCommandManager;
import com.votemine.votemineReward.config.Config;
import com.votemine.votemineReward.storage.Storage;
import com.votemine.votemineReward.storage.StorageException;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Locale;

public final class VotemineReward extends JavaPlugin {

    private Config config;
    private Bank bank;
    private PaperCommandManager commandManager;
    private RewardStore store;
    private Commands commands;
    @Override
    public void onEnable() {
        config = new Config(this);
        bank = new Bank(this);
        commandManager = new PaperCommandManager(this);
        commandManager.addSupportedLanguage(Locale.FRENCH);
        commandManager.enableUnstableAPI("help");
        commandManager.getLocales().setDefaultLocale(Locale.FRENCH);
        Message.init(commandManager);
        getServer().getPluginManager().registerEvents(new UUIDUpdater(this), this);
        getServer().getPluginManager().registerEvents(new VotifierListener(this, bank), this);
        boot();
    }

    private void boot(){
        try {
            Storage.init(config.getStorageConfig());
            store = new RewardStore(config.getRewardsConfig(), config.getStoreConfig(), bank);
            getServer().getPluginManager().registerEvents(store, this);
            commands = new Commands(this, bank, store);
            commandManager.registerCommand(commands);
        } catch (StorageException e) {
            Bukkit.getLogger().severe("Storage failed to initialize: "+e.getMessage());
        }
    }

    public void reload(){
        HandlerList.unregisterAll(store);
        commandManager.unregisterCommand(commands);
        config.reload();
        boot();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
