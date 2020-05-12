package com.votemine.votemineReward;

import co.aikar.commands.BukkitCommandIssuer;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import co.aikar.locales.MessageKey;
import com.votemine.votemineReward.config.Config;
import com.votemine.votemineReward.storage.Storage;
import com.votemine.votemineReward.storage.StorageException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class VotemineReward extends JavaPlugin {

    private Config config;
    private Bank bank;
    private PaperCommandManager commandManager;
    @Override
    public void onEnable() {
        config = new Config(this);
        bank = new Bank(this);
        registerCommands();
        Message.init(commandManager);
        boot();
    }

    private void boot(){
        try {
            Storage.init(config.getStorageConfig());
        } catch (StorageException e) {
            Bukkit.getLogger().severe("Storage failed to initialize: "+e.getMessage());
        }
    }

    private void registerCommands(){
        commandManager = new PaperCommandManager(this);
        commandManager.addSupportedLanguage(Locale.FRENCH);
        commandManager.enableUnstableAPI("help");
        commandManager.getLocales().setDefaultLocale(Locale.FRENCH);
        commandManager.registerCommand(new Commands(this, bank));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reload(){
        config.reload();
        boot();
    }
}
