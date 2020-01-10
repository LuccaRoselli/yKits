package com.luccadev.br.abilities;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class FireShield implements Listener {

	public static HashMap<Player, Integer> as = new HashMap<Player, Integer>();

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (as.containsKey(e.getEntity())) {
			Bukkit.getScheduler().cancelTask(((Integer) as.get(e.getEntity())).intValue());
			as.remove(e.getEntity());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (as.containsKey(e.getPlayer())) {
			Bukkit.getScheduler().cancelTask(((Integer) as.get(e.getPlayer())).intValue());
			as.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void onTp(PlayerTeleportEvent e) {
		if (as.containsKey(e.getPlayer())) {
			Bukkit.getScheduler().cancelTask(((Integer) as.get(e.getPlayer())).intValue());
			as.remove(e.getPlayer());
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void click(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (p instanceof Player) {
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Fireshield")) {
				if (p.getItemInHand().getType() == Material.MAGMA_CREAM) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
						return;
					}
					Uteis.addCooldown(p, 30);
					int a = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMe(), new Runnable() {

						@Override
						public void run() {

							Location loc = p.getLocation();
							final int cx = loc.getBlockX();
							final int cy = loc.getBlockY();
							final int cz = loc.getBlockZ();
							final World w = loc.getWorld();
							final int rSquared = 5 * 5;

							for (int x = cx - 5; x <= cx + 5; x++) {
								for (int z = cz - 5; z <= cz + 5; z++) {
									if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared
											&& (cx - x) * (cx - x) + (cz - z) * (cz - z) > 4 /* 2 squared */) {
										final Location l = new Location(w, x, cy, z);
										ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), l, 0.2F, 1.5F, 0.2F, 0.0F, 4);
									}
								}
							}
							List<Entity> nearby = p.getNearbyEntities(3, 3, 3);
							for (Entity ent : nearby) {
								if ((ent instanceof Player)) {
									((Player) ent).playSound(((Player) ent).getLocation(), Sound.ENDERMAN_HIT, 5f, 5f);
									((Player) ent).setFireTicks(20);
									((Player) ent).damage(2);
									((Player) ent)
											.setVelocity(((Player) ent).getLocation().getDirection().multiply(-1));
								}
							}
						}
					}, 0L, 3L);
					as.put(p, Integer.valueOf(a));
					Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getMe(), new Runnable() {
						public void run() {
							if (as.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(((Integer) as.get(p)).intValue());
								as.remove(p);
							}
						}
					}, 100L);
				}
			}
		}
	}
}
