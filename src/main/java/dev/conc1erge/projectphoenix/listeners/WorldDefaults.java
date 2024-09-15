package dev.conc1erge.projectphoenix.listeners;

import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldDefaults implements Listener {
    @EventHandler
    public void worldDefaultGameRules(WorldInitEvent e) {
        e.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
        e.getWorld().setGameRule(GameRule.MOB_GRIEFING, false);
        e.getWorld().setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        e.getWorld().setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        e.getWorld().setGameRule(GameRule.KEEP_INVENTORY, true);
        e.getWorld().setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
    }
}
