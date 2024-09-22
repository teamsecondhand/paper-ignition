package dev.conc1erge.projectphoenix.listeners;

import dev.conc1erge.projectphoenix.IgnitionCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class GameplayListeners implements Listener {
    @EventHandler
        public void combatCheck (){
            new BukkitRunnable() {
                @Override
                public void run() {
                    // TODO: Make a combat logging system here, nothing too harsh, simply has to be basic and kills player on disconnect.
                }
        }.runTaskTimer(IgnitionCore.plugin, 0, 20);
    }
}
