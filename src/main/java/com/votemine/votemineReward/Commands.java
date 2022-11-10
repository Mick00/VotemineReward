package com.votemine.votemineReward;

import co.aikar.commands.*;
import co.aikar.commands.annotation.*;
import com.votemine.votemineReward.config.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

@CommandAlias("vote|vmr|voteshop")
public class Commands extends BaseCommand {

    private VotemineReward votemineReward;
    private Bank bank;
    private RewardStore store;
    private Config config;
    private ServerVoteObjective voteObjective;

    public Commands(VotemineReward votemineReward){
        this.votemineReward = votemineReward;
        this.bank = votemineReward.getBank();
        this.store = votemineReward.getStore();
        this.config = votemineReward.getVotemineConfig();
        this.voteObjective = votemineReward.getServerVoteObjective();
    }

    @HelpCommand
    public void helpMenu(CommandSender sender, CommandHelp help){
        help.showHelp();
    }

    @Default
    @Subcommand("link")
    @Description("{@@votemine.cmd-description.link}")
    public void link(CommandSender sender){
        if (config.getServerRewardConfig().enabled()) {
            Message.sendWithHeader("votemine.cmd.objective", sender, String.valueOf(voteObjective.getVoteCounter()), String.valueOf(voteObjective.getObjective()));
        }
        TextComponent clickableLink = new TextComponent(Message.trans("votemine.cmd.link", sender));
        clickableLink.setColor(ChatColor.LIGHT_PURPLE);
        clickableLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, config.getVoteLink()));
        TextComponent voteShopMessage = new TextComponent(Message.trans("votemine.cmd.voteshop", sender));
        voteShopMessage.setColor(ChatColor.GOLD);
        sender.spigot().sendMessage(clickableLink);
        sender.spigot().sendMessage(voteShopMessage);
    }

    @Subcommand("balance")
    @Syntax("[player]")
    @CommandCompletion("@players")
    @Description("{@@votemine.cmd-description.balance}")
    public void balance(CommandSender sender, String[] args){
        if (args.length >= 1){
            bank.getBalance(sender, args[0]);
        } else {
            bank.getBalance(sender, sender.getName());
        }
    }

    @Subcommand("shop")
    @Description("{@@votemine.cmd-description.shop}")
    public void openShop(CommandSender sender, String[] args){
        if (sender instanceof Player){
            store.open(((Player) sender));
        } else {
            Message.send("votemine.cmd.only_players", sender);
        }
    }


    @Subcommand("reload")
    @CommandPermission("votemine.admin")
    @Description("{@@votemine.cmd-description.reload}")
    public void reload(CommandSender sender){
        votemineReward.reload();
        Message.sendWithHeader("votemine.cmd.reloaded", sender);
    }


    @Subcommand("add")
    @CommandPermission("votemine.admin")
    @Syntax("<player> <points>")
    @Description("{@@votemine.cmd-description.add}")
    @CommandCompletion("@players @nothing")
    public void addBalance(CommandSender sender, String[] args){
        if (args.length == 2){
            if (sender.hasPermission("votemine.admin")){
                bank.addFund(sender,args[0], Integer.parseInt(args[1]));
            } else {
                Message.sendWithHeader("acf-core.permission_denied",sender);
            }
        } else {
            List<String> search = new ArrayList<>();
            search.add(getName());
            throw new ShowCommandHelp(search);
        }
    }

    @Subcommand("remove")
    @CommandPermission("votemine.admin")
    @Syntax("<player> <points>")
    @Description("{@@votemine.cmd-description.remove}")
    @CommandCompletion("@players @nothing")
    public void removeFund(CommandSender sender, String[] args){
        if (args.length == 2){
            bank.removeFund(sender, args[0], Integer.parseInt(args[1]));
        }
    }

}
