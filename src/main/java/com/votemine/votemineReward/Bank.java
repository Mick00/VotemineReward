package com.votemine.votemineReward;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import com.votemine.votemineReward.exceptions.PlayerNotFoundException;
import com.votemine.votemineReward.models.PointsBalance;
import com.votemine.votemineReward.storage.Storage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Bank {

    private TaskChainFactory taskFactory;
    private final String CHAIN_PREFIX = "BANK_";

    public Bank(Plugin plugin){
        taskFactory = BukkitTaskChainFactory.create(plugin);
    }

    public void getBalance(CommandSender sender, String playername){
        getBalanceChain(playername).syncLast(points -> {
            if (points >= 0) {
                sendBalance(sender, points);
            } else {
                sendPlayerNotFound(sender, playername);
            }
        }).execute();
    }

    public TaskChain<Integer> getBalanceChain(String playername){
        return taskFactory.newSharedChain(getPlayerChainName(playername))
                .asyncFirst(()->{
                    try{
                        return Storage.getBalanceOf(playername).get();
                    } catch (PlayerNotFoundException e) {
                        return -1;
                    }
                });
    }

    private String getPlayerChainName(String playername){
        return CHAIN_PREFIX+playername;
    }

    public void addFund(String playername, int points){
        addFundChain(playername,points).execute();
    }

    public void addFund(CommandSender sender, String playername, int points){
        addFundChain(playername, points).syncLast(result->{
            if (result == BankResult.SUCCESS){
                sendFundAdded(sender.getName(), points);
                sendFundAdded(playername, points);
            } else {
                sendPlayerNotFound(sender, playername);
            }
        }).execute();
    }

    public void sendFundAdded(String playername, int points){
        sendMessage(playername, "votemine.bank.fund_added", String.valueOf(points));
    }

    public TaskChain<BankResult> addFundChain(String playername, int points){
        return taskFactory.newSharedChain(getPlayerChainName(playername))
                .asyncFirst(()->{
                    try {
                        PointsBalance balance = Storage.getBalanceOf(playername);
                        balance.add(points);
                        return BankResult.SUCCESS;
                    } catch (PlayerNotFoundException e) {
                        return BankResult.PLAYER_NOT_FOUND;
                    }
                });
    }

    public void removeFund(String playername, int points){
        removeFundChain(playername, points).execute();
    }

    public void removeFund(CommandSender sender, String playername, int points){
        removeFundChain(playername, points).syncLast(result -> {
            if (result == BankResult.SUCCESS){
                sendRemovedFund(sender.getName(), points);
                sendRemovedFund(playername, points);
            } else {
                sendNotEnoughFund(sender);
            }
        }).execute();
    }

    private void sendRemovedFund(String playername, int points){
        sendMessage(playername,"votemine.bank.remove_fund", String.valueOf(points), playername);
    }

    private void sendNotEnoughFund(CommandSender sender){
        sendMessage(sender,"votemine.bank.not_enough_fund");
    }

    public TaskChain<BankResult> removeFundChain(String playername, int points){
        return taskFactory.newSharedChain(getPlayerChainName(playername))
                .asyncFirst(()->{
                    try {
                        PointsBalance balance = Storage.getBalanceOf(playername);
                        if (balance.get() >= points){
                            balance.remove(points);
                            return BankResult.SUCCESS;
                        } else {
                            return BankResult.NOT_ENOUGH_FUND;
                        }
                    } catch (PlayerNotFoundException e) {
                        e.printStackTrace();
                        return BankResult.PLAYER_NOT_FOUND;
                    }
                });
    }

    private void sendBalance(CommandSender sender, int points){
        sendMessage(sender,"votemine.bank.balance", String.valueOf(points));
    }

    private void sendPlayerNotFound(CommandSender sender, String playername){
        sendMessage(sender,"votemine.bank.player_not_found", playername);
    }

    private void sendMessage(String playername, String path, String... values){
        Player player = Bukkit.getPlayer(playername);
        sendMessage(player, path, values);
    }

    private void sendMessage(CommandSender sender, String path, String... values){
        if (sender != null){
            Message.sendWithHeader(path, sender, values);
        }
    }
}
