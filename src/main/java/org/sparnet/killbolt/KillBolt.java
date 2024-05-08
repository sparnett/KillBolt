package org.sparnet.killbolt;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class KillBolt extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("KillBolt plugin has been enabled!");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("KillBolt plugin has been disabled!");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer != null) {
            Location deathLocation = victim.getLocation();
            World world = deathLocation.getWorld();

            // Ottieni la superficie del terreno intorno al punto di morte
            Location[] surfaceLocations = getSurfaceLocations(deathLocation);

            // Genera 20 particelle rosse in ciascuna posizione sulla superficie del terreno
            for (Location particleLocation : surfaceLocations) {
                for (int i = 0; i < 20; i++) {
                    world.spigot().playEffect(particleLocation, org.bukkit.Effect.FLYING_GLYPH, 0, 0, 1, 1, 1, 0, 20, 16);
                }
            }
        }
    }

    private Location[] getSurfaceLocations(Location center) {
        Location[] surfaceLocations = new Location[5];
        World world = center.getWorld();

        int x = center.getBlockX();
        int z = center.getBlockZ();

        // Centro
        surfaceLocations[0] = center;

        // Lato destro
        surfaceLocations[1] = world.getHighestBlockAt(x + 1, z).getLocation();

        // Lato sinistro
        surfaceLocations[2] = world.getHighestBlockAt(x - 1, z).getLocation();

        // Lato sopra
        surfaceLocations[3] = world.getHighestBlockAt(x, z + 1).getLocation();

        // Lato sotto
        surfaceLocations[4] = world.getHighestBlockAt(x, z - 1).getLocation();

        return surfaceLocations;
    }
}
