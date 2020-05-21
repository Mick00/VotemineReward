package com.votemine.votemineReward;

import com.votemine.votemineReward.config.Reward;
import com.votemine.votemineReward.config.RewardsConfig;
import com.votemine.votemineReward.config.StoreConfig;
import com.votemine.votemineReward.config.StoreItem;
import com.votemine.votemineReward.exceptions.RewardNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class RewardStore implements Listener {

    private RewardsConfig rewards;
    private StoreConfig store;
    private Bank bank;
    private Inventory storeGui;

    public RewardStore(RewardsConfig rewards, StoreConfig storeConfig, Bank bank) {
        this.rewards = rewards;
        this.bank = bank;
        this.store = storeConfig;
        buildGui();
    }

    private void buildGui(){
        storeGui = Bukkit.createInventory(null, store.getInventorySize(), store.getName());
        storeGui.setContents(store.getStoreContents());
    }

    public void open(Player player){
        player.openInventory(storeGui);
    }

    @EventHandler
        public void onShopClick(InventoryClickEvent event){
        if (event.getView().getTitle().equals(store.getName())){
            event.setCancelled(true);
            StoreItem item = store.getItem(event.getSlot());
            if (item.hasConfiguredReward()){
                try {
                    buy(event.getWhoClicked().getName(), item.getRewardName());
                } catch (RewardNotFoundException e) {
                    Message.send("votemine.store.configuration_issue", event.getWhoClicked());
                    Bukkit.getLogger().severe("Reward name "+item.getRewardName()+" was not found. Used by slot #"+event.getSlot());
                }
            }
        }
    }

    public void buy(String playername, String rewardName) throws RewardNotFoundException {
        Reward reward = rewards.getReward(rewardName);
        bank.removeFundChain(playername, reward.getPrice())
                .syncLast(result->{
                    Player player = Bukkit.getPlayer(playername);
                    if (result == BankResult.SUCCESS){
                        completeTransaction(playername, reward);
                        Message.send("votemine.store.success", player);
                    } else if (result == BankResult.NOT_ENOUGH_FUND){
                        Message.send("votemine.store.not_enough_fund", player);
                    }
                }).execute();
    }

    public void completeTransaction(String playername, Reward reward){
        reward.getFilledCommands(playername).forEach(
                command -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command)
        );
    }
}
