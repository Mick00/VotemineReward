package com.votemine.votemineReward.placeholders;

import com.votemine.votemineReward.ServerVoteObjective;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class VoteminePapiExpansion extends PlaceholderExpansion {

    private ServerVoteObjective objective;

    public VoteminePapiExpansion(ServerVoteObjective voteObjective){
        this.objective = voteObjective;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "votemine";
    }

    @Override
    public @NotNull String getAuthor() {
        return "HOVOH";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.OfflinePlayer OfflinePlayer}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return Possibly-null String of the requested identifier.
     */
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        if(identifier.equals("counter")){
            return String.valueOf(objective.getVoteCounter());
        }
        if(identifier.equals("objective")){
            return String.valueOf(objective.getObjective());
        }
        return null;
    }
}
