package com.votemine.votemineReward;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import com.votemine.votemineReward.storage.Storage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class UUIDUpdater implements Listener {

    private TaskChainFactory taskFactory;

    public UUIDUpdater(VotemineReward plugin){
        taskFactory = BukkitTaskChainFactory.create(plugin);
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        String playername = event.getPlayer().getName();
        taskFactory.newChain()
                .asyncFirst(() -> Storage.getCachedUUID(uuid))
                .asyncLast(cachedUUID -> cachedUUID.updatePlayername(playername))
                .execute();
    }
}
