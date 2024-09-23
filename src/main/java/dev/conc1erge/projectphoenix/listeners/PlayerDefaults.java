package dev.conc1erge.projectphoenix.listeners;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import dev.conc1erge.projectphoenix.listeners.GameplayListeners.combatCheck;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public class PlayerDefaults implements Listener {

    Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

    @EventHandler // Team for hiding nametags
    private void hideNametags(ServerLoadEvent event) { // sets a team for hiding nametags, this may or may not work but im leaving it here anyway -c1
        Team team = score.getTeam("namehide");
        if(team == null) {
            team = score.registerNewTeam("namehide");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        }
    }

    @EventHandler // Player Defaults on Join
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (p.getUniqueId().equals("a1ce5c43-55ca-4ada-a605-cb6bc7928994"))
            p.ban("You have been banned by source code.", (Date) null, "FC:PP Dev Team");
        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, SoundCategory.NEUTRAL, 1, 1); // Plays the classic ping sound on join
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0); // Ensures that the player on join DOES get 40 HP/20 hearts
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
        p.getAttribute(Attribute.PLAYER_SWEEPING_DAMAGE_RATIO).setBaseValue(0.1);
        // Set player's nametag hidden on join
        score.getTeam("namehide").addEntry(p.getName());
    }

    @EventHandler // Player Defaults on Leave
    public void onPlayerLeave(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (!combatCheck.hasTimer(uuid)) {return;}
        e.getPlayer().setHealth(0.00);
        e.getPlayer().setGameMode(GameMode.SPECTATOR);
        combatCheck.remove(uuid);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0);
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
        p.getAttribute(Attribute.PLAYER_SWEEPING_DAMAGE_RATIO).setBaseValue(0.1);
        // Reset effects
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
        p.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1);

        // Only here if the player is not in the team, coming from respawn
        score.getTeam("namehide").addEntry(p.getName());
    }

    @EventHandler // Removes player from the nametag hide team in spec, future proof for staff, might be removed later when new nametag system is implemented -c1
    public void onPlayerSwitchGamemode(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() != GameMode.SURVIVAL) {
            score.getTeam("namehide").removeEntry(p.getName());
        } else {
            score.getTeam("namehide").addEntry(p.getName());
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {return;}
        combatCheck.add(e.getEntity().getUniqueId());
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
        UUID uuid = e.getEntity().getUniqueId();
        if (combatCheck.hasTimer(uuid)) {combatCheck.remove(uuid);}
    }

    @EventHandler // Remove Item Durability Changes
    public void itemDurabilityChange(PlayerItemDamageEvent e) {e.setCancelled(true);}

    @EventHandler // Remove Hunger/Saturation Exhaustion
    public void onHungerDeplete(FoodLevelChangeEvent e) {e.setCancelled(true);}

    @EventHandler // Prevent Dropping Items TODO: except for select items
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        e.setCancelled(true);
    }
}
