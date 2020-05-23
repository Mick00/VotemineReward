package com.votemine.votemineReward.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerVoteObjectiveUpdate extends Event {

    private static HandlerList HANDLERLIST = new HandlerList();

    private int objective;
    private int count;

    public ServerVoteObjectiveUpdate(int count, int objective){
        this.objective = objective;
        this.count = count;
    }

    public int getObjective() {
        return objective;
    }

    public int getCount() {
        return count;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERLIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERLIST;
    }
}
