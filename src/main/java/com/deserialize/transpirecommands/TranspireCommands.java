package com.deserialize.transpirecommands;

import com.deserialize.transpirecommands.events.BlockBreak;
import com.deserialize.transpirecommands.events.EntityKill;
import com.deserialize.transpirecommands.events.Fish;
import com.deserialize.transpirecommands.objects.RandomCollection;
import com.deserialize.transpirecommands.objects.Reward;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public final class TranspireCommands extends JavaPlugin {
    private HashMap<String, RandomCollection<Reward>> rewards;
    private int mobChance;
    private int blockChance;
    private int fishChance;

    public void onEnable() {
        this.saveDefaultConfig();
        this.loadChances();
        this.rewards = new HashMap();
        this.loadType("mobs");
        this.loadType("blocks");
        this.loadType("fish");
        this.registerListeners();
    }

    private void registerListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new BlockBreak(this), this);
        pluginManager.registerEvents(new EntityKill(this), this);
        pluginManager.registerEvents(new Fish(this), this);
    }

    private void loadBlocks() {
    }

    public Double random() {
        Random random = new Random();
        return random.nextDouble() * 100.0D;
    }

    private void loadChances() {
        this.mobChance = this.getConfig().getInt("settings.chance-for-mob-reward");
        this.blockChance = this.getConfig().getInt("settings.chance-for-block-reward");
        this.fishChance = this.getConfig().getInt("settings.chance-for-fish-reward");
    }

    public void onDisable() {
    }

    public HashMap<String, RandomCollection<Reward>> getRewards() {
        return this.rewards;
    }

    public int getMobChance() {
        return this.mobChance;
    }

    public int getBlockChance() {
        return this.blockChance;
    }

    public int getFishChance() {
        return this.fishChance;
    }

    private void loadType(String type) {
        String identity;
        RandomCollection collection;
        for(Iterator var2 = this.getConfig().getConfigurationSection(type).getKeys(false).iterator(); var2.hasNext(); this.getRewards().put(identity, collection)) {
            String string = (String)var2.next();
            identity = this.getConfig().getString(type + "." + string + ".type");
            collection = new RandomCollection();
            Iterator var6 = this.getConfig().getConfigurationSection(type + "." + string + ".commands").getKeys(false).iterator();

            while(var6.hasNext()) {
                String c = (String)var6.next();
                int chance = this.getConfig().getInt(type + "." + string + ".commands." + c + ".chance");
                boolean sendMessage = this.getConfig().getBoolean(type + "." + string + ".commands." + c + ".send-message");
                String message = this.getConfig().getString(type + "." + string + ".commands." + c + ".message");
                String command = this.getConfig().getString(type + "." + string + ".commands." + c + ".command");
                collection.add((double)chance, new Reward(sendMessage, message, command));
            }

            identity = identity.toUpperCase();
            if (type == "blocks") {
                identity = identity + ":" + this.getConfig().getInt(type + "." + string + ".damage");
            }
        }

    }
}