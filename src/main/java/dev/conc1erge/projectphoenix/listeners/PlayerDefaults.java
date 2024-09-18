package dev.conc1erge.projectphoenix.listeners;

import org.bukkit.Bukkit;
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
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Date;

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
        if (p.getName().equals("Kup1995"))
            p.ban("Convicted of cheating", (Date) null, "FC:PP Dev Team");
        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, SoundCategory.NEUTRAL, 1, 1); // Plays the classic ping sound on join
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0); // Ensures that the player on join DOES get 40 HP/20 hearts
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16.01);
        p.getAttribute(Attribute.PLAYER_SWEEPING_DAMAGE_RATIO).setBaseValue(0.2);

        if (p.hasPlayedBefore()) {
            p.setGameMode(GameMode.SPECTATOR);
        }

        // Set player's nametag hidden on join
        score.getTeam("namehide").addEntry(p.getName());
    }

    @EventHandler // Player Defaults on Leave
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        p.setHealth(0.00); // pseudo-combat log? hopefully that works
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0);
        p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
        p.getAttribute(Attribute.PLAYER_SWEEPING_DAMAGE_RATIO).setBaseValue(0.1);
        p.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
        p.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1);

        // Only here if the player is not in the team, coming from respawn
        score.getTeam("namehide").addEntry(p.getName());
    }

    @EventHandler // Removes player from the nametag hide team in spec, future proof for staff, might be removed later when new nametag system is implemented -c1
    public void onPlayerSwitchGamemode(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.SPECTATOR) {
            score.getTeam("namehide").removeEntry(p.getName());
        } else {
            score.getTeam("namehide").addEntry(p.getName());
        }
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
