package com.votemine.votemineReward;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener {
    private TaskChainFactory taskFactory;
    private Bank bank;

    public VotifierListener(VotemineReward plugin, Bank bank){
        taskFactory = BukkitTaskChainFactory.create(plugin);
        this.bank = bank;
    }

    @EventHandler
    public void onNewvote(VotifierEvent voteEvent){
        bank.addFund(voteEvent.getVote().getUsername(),1);
    }
}
