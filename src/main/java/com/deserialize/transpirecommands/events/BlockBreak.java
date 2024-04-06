package com.deserialize.transpirecommands.events;

import com.deserialize.transpirecommands.TranspireCommands;
import com.deserialize.transpirecommands.objects.RandomCollection;
import com.deserialize.transpirecommands.objects.Reward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    TranspireCommands main;

    public BlockBreak(TranspireCommands main) {
        this.main = main;
    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String type = event.getBlock().getType().toString();
        byte subID = event.getBlock().getState().getRawData();
        String mix = type + ":" + subID;
        if (this.main.getRewards().containsKey(mix)) {
            double chance = this.main.random();
            if (chance < (double)this.main.getBlockChance()) {
                Reward reward = (Reward)((RandomCollection)this.main.getRewards().get(mix)).next();
                String command = reward.getCommand();
                if (reward.isSendMessage()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', reward.getMessage()));
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", event.getPlayer().getName()));
            }

        }
    }
}
