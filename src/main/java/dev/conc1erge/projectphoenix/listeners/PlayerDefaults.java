package dev.conc1erge.projectphoenix.listeners;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.time.Instant;
import java.util.Date;

public class PlayerDefaults implements Listener {

    @EventHandler // Player Defaults on Join
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (p.getName().equals("Kup1995"))
            p.ban("Convicted of cheating", (Date) null, "FC:PP Dev Team");
        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, SoundCategory.NEUTRAL, 1, 1); // Plays the classic ping sound on join
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0); // Ensures that the player on join DOES get 40 HP/20 hearts
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16.01);
        p.getAttribute(Attribute.PLAYER_SWEEPING_DAMAGE_RATIO).setBaseValue(0.2);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0);
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
        p.getAttribute(Attribute.PLAYER_SWEEPING_DAMAGE_RATIO).setBaseValue(0.1);
    }

    @EventHandler
    public void itemDurabilityChange(PlayerItemDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer != null) killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.VOICE, 1, 1);
    }

    @EventHandler
    public void onPlayerHealthRegen(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player p) p.playSound(p.getLocation(), Sound.ITEM_CROP_PLANT, SoundCategory.PLAYERS, 1, 1);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f, 1.0f);
    }

    @EventHandler // Remove Hunger/Saturation Exhaustion
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler // Prevent Dropping Items TODO: except for select items
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        e.setCancelled(true);
    }
}
