package com.votemine.votemineReward;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.vexsoftware.votifier.model.VotifierEvent;
import com.votemine.votemineReward.events.NewVotesEvent;
import com.votemine.votemineReward.models.CachedPlayer;
import com.votemine.votemineReward.storage.Storage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.ConcurrentHashMap;

public class VotifierListener implements Listener {
    private static final String CHAIN_PREFIX = "VOTIFIER_LISTENER_";
    private TaskChainFactory taskFactory;
    private Bank bank;
    private ConcurrentHashMap<String, Integer> votes;

    public VotifierListener(VotemineReward plugin, Bank bank){
        taskFactory = BukkitTaskChainFactory.create(plugin);
        this.bank = bank;
        votes = new ConcurrentHashMap<>();
    }

    @EventHandler
    public void onNewVote(VotifierEvent voteEvent){
        String playername = voteEvent.getVote().getUsername();
        votes.put(playername,votes.getOrDefault(playername,0)+1);
        TaskChain<?> task = taskFactory.newChain();
        task.asyncFirst(()-> votes.get(playername))
                .delay(100)
                .sync(oldVoteCount -> {
                    if (!votes.containsKey(playername) || !oldVoteCount.equals(votes.get(playername))){
                       TaskChain.abort();
                    }
                    votes.remove(playername);
                    return oldVoteCount;
                }).async(voteCount -> {
                    CachedPlayer player = Storage.getCachedPlayer(playername);
                    if (player.exists()){
                        Message.broadcast("votemine.broadcast.vote", playername, String.valueOf(voteCount));
                        bank.addFund(playername,voteCount);
                    }
                    task.setTaskData("player", player);
                    return voteCount;
                }).syncLast(voteCount -> {
                    CachedPlayer player = task.getTaskData("player");
                    Bukkit.getPluginManager().callEvent(new NewVotesEvent(player, voteCount));
                }).execute();

    }

    private String getChainName(String playername){
        return CHAIN_PREFIX+playername;
    }
}
