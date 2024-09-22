package dev.conc1erge.projectphoenix;

import dev.conc1erge.projectphoenix.listeners.PlayerDefaults;
import dev.conc1erge.projectphoenix.listeners.WorldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

public final class IgnitionCore extends JavaPlugin {

    public static IgnitionCore plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        IgnitionCore.plugin = this; // here for when listeners need to recall itself for bukkitrunnables

        getLogger().info("Ignition Core by conc1erge - Project Phoenix, codename FlagClash: Ignition");

        // Sets Default Player Stuff on join
        getServer().getPluginManager().registerEvents(new PlayerDefaults(), this);

        // Persistent World Stuff
        getServer().getPluginManager().registerEvents(new WorldDefaults(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Shutting down IgnitionCore...");
        getLogger().info("see you next time!");
        plugin = null; // prevent memory leaks apparently?
    }

}
