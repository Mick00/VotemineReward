package com.votemine.votemineReward.events;

import com.votemine.votemineReward.models.CachedPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NewVotesEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private CachedPlayer player;
    private int nVotes;

    public NewVotesEvent(CachedPlayer player, int nVotes){
        super(false);
        this.player = player;
        this.nVotes = nVotes;
    }

    public CachedPlayer getPlayer() {
        return player;
    }

    public int getVotes() {
        return nVotes;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
