package com.deserialize.transpirecommands.events;

import com.deserialize.transpirecommands.TranspireCommands;
import com.deserialize.transpirecommands.objects.RandomCollection;
import com.deserialize.transpirecommands.objects.Reward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class Fish implements Listener {
    private final TranspireCommands main;

    public Fish(TranspireCommands main) {
        this.main = main;
    }

    @EventHandler(
            ignoreCancelled = true
    )
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Item fish = (Item)event.getCaught();
            if (!this.main.getRewards().containsKey(fish.getItemStack().getType().toString())) {
                return;
            }

            double chance = this.main.random();
            if (chance < (double)this.main.getFishChance()) {
                Reward reward = (Reward)((RandomCollection)this.main.getRewards().get(fish.getItemStack().getType().toString())).next();
                String command = reward.getCommand();
                if (reward.isSendMessage()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', reward.getMessage()));
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
            }
        }

    }
}