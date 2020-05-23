package com.votemine.votemineReward;

import com.votemine.votemineReward.config.ServerRewardConfig;
import com.votemine.votemineReward.events.NewVotesEvent;
import com.votemine.votemineReward.events.ServerVoteObjectiveUpdate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class ServerVoteObjective implements Listener {

    private static final String VOTE_COUNTER_PATH = "voteCounter";

    private ServerRewardConfig rewardConfig;
    private int voteCounter;
    private File dataFile;
    private YamlConfiguration data;

    public ServerVoteObjective(VotemineReward votemine){
        this.rewardConfig = votemine.getVotemineConfig().getServerRewardConfig();
        loadData(votemine.getDataFolder());
        voteCounter = data.getInt(VOTE_COUNTER_PATH);
        dispatchEvent();
    }

    private void loadData(File dataFolder){
        if (!dataFolder.exists()){
            dataFolder.mkdir();
        }
        dataFile = new File(dataFolder, "data.yml");
        data = new YamlConfiguration();
        try {
            if (!dataFile.exists()){
                dataFile.createNewFile();
                data.set(VOTE_COUNTER_PATH, 0);
                data.save(dataFile);
            } else {
                data.load(dataFile);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void dispatchEvent(){
        ServerVoteObjectiveUpdate voteObjectiveUpdate = new ServerVoteObjectiveUpdate(voteCounter, rewardConfig.getObjective());
        Bukkit.getPluginManager().callEvent(voteObjectiveUpdate);
    }

    @EventHandler
    public void onNewVotes(NewVotesEvent event){
        incrementVoteCounter(event.getVotes());
        while(voteCounter >= rewardConfig.getObjective()){
            voteCounter -= rewardConfig.getObjective();
            rewardConfig.getCommands().forEach(
                    command -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command)
            );
        }
        saveVoteCount();
        dispatchEvent();
    }

    public void incrementVoteCounter(int nVotes){
        voteCounter += nVotes;
    }

    private void saveVoteCount(){
        try {
            data.set(VOTE_COUNTER_PATH, voteCounter);
            data.save(dataFile);
        } catch (IOException e) {
            Bukkit.getLogger().warning("Could not save vote counter. Check if the file exists and the permission");
        }
    }

    public int getVoteCounter(){
        return this.voteCounter;
    }

    public int getObjective(){
        return this.rewardConfig.getObjective();
    }

}
