package com.votemine.votemineReward.storage;

import com.votemine.votemineReward.config.StorageConfig;
import com.votemine.votemineReward.exceptions.PlayerNotFoundException;
import com.votemine.votemineReward.models.CachedPlayer;
import com.votemine.votemineReward.models.PointsBalance;
import com.votemine.votemineReward.models.Transaction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.List;
import java.util.UUID;

public class Storage {
    private static Store store = null;

    public static void init(StorageConfig config) throws StorageException {
        store = StorageFactory.getStorage(config);
    }

    public static PointsBalance getBalanceOf(UUID uuid){
        return store.getBalance(uuid.toString());
    }

    public static PointsBalance getBalanceOf(String name) throws PlayerNotFoundException {
        return getBalanceOf(playernameToUniqueId(name));
    }

    private static UUID playernameToUniqueId(String name) throws PlayerNotFoundException {
        Player player = Bukkit.getPlayer(name);
        if (player == null){
            CachedPlayer uuid = store.getCachedUUID(name);
            if (!uuid.exists()){
                throw new PlayerNotFoundException(name);
            } else {
                return uuid.getUUID();
            }
        }
        return player.getUniqueId();
    }

    private static List<Transaction> getTransactions(UUID uuid){
        return store.getTransactions(uuid.toString());
    }

    private static List<Transaction> getTransactions(String name) throws PlayerNotFoundException {
        return getTransactions(playernameToUniqueId(name));
    }

    public static CachedPlayer getCachedPlayer(UUID uuid){
        return store.getCachedUUID(uuid);
    }

    public static CachedPlayer getCachedPlayer(String playername){
        return store.getCachedUUID(playername);
    }
}
