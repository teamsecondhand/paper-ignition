package dev.conc1erge.projectphoenix.listeners;

import dev.conc1erge.projectphoenix.IgnitionCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class GameplayListeners implements Listener {
    // Combat Logging Checker

    static int combatLogTime = 15;
    // set this value to how long the combat log is in seconds

    public static class combatCheck {
        private static HashMap<UUID, combatTimer> Timer = new HashMap<UUID, combatTimer>();

        public static void add(UUID uuid) {
            if (hasTimer(uuid))
                remove(uuid);
            Timer.put(uuid, new combatTimer(uuid));
        }

        public static boolean hasTimer(UUID uuid) {
            return Timer.containsKey(uuid);
        }

        public static boolean remove(UUID uuid) {
            if (hasTimer(uuid)) {
                Timer.get(uuid).cancel();
                Timer.remove(uuid);
            }
            return false;
        }

    }

    // Combat Logging Timer
    public static class combatTimer extends BukkitRunnable {
        private int time;
        private UUID uuid;
        private Player player;

        public combatTimer(UUID uuid) {
            this.time = combatLogTime; // change this for combat time check thing
            this.uuid = uuid;
            player = Bukkit.getPlayer(uuid);
            this.runTaskTimer(IgnitionCore.plugin, 0, 20L);
        }

        @Override
        public void run() {
            if (!player.isOnline()) {
                if (combatCheck.hasTimer(uuid))
                    combatCheck.remove(uuid);
                cancel();
                return;
            }

            if (time <= 0) {
                if (combatCheck.hasTimer(uuid))
                    combatCheck.remove(uuid);
                cancel();
                return;
            }
            time--;
        }
    }
}
