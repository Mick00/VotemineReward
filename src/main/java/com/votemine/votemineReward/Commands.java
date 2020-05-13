package com.votemine.votemineReward;

import co.aikar.commands.*;
import co.aikar.commands.annotation.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("voteminereward|vmr|voteshop")
@CommandPermission("votemine.admin")
public class Commands extends BaseCommand {

    private VotemineReward votemineReward;
    private Bank bank;
    private RewardStore store;

    public Commands(VotemineReward votemineReward, Bank bank, RewardStore store){
        this.votemineReward = votemineReward;
        this.bank = bank;
        this.store = store;
    }

    @Subcommand("reload")
    @Description("{@@votemine.cmd-description.reload}")
    public void reload(CommandSender sender, String[] args){
        votemineReward.reload();
        Message.sendWithHeader("votemine.cmd.reloaded", sender);
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

    @Subcommand("add")
    @Syntax("<player> <points>")
    @Description("{@@votemine.cmd-description.add}")
    @CommandCompletion("@players @nothing")
    public void addBalance(CommandSender sender, String[] args){
        if (args.length == 2){
            bank.addFund(sender,args[0], Integer.parseInt(args[1]));
        } else {
            List<String> search = new ArrayList<>();
            search.add(getName());
            throw new ShowCommandHelp(search);
        }
    }

    @Subcommand("remove")
    @Syntax("<player> <points>")
    @Description("{@@votemine.cmd-description.remove}")
    @CommandCompletion("@players @nothing")
    public void removeFund(CommandSender sender, String[] args){
        if (args.length == 2){
            bank.removeFund(sender, args[0], Integer.parseInt(args[1]));
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

    @Default
    @HelpCommand
    public void helpMenu(CommandSender sender, CommandHelp help){
        help.showHelp();
    }

}
