package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.commands.Admin;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Forcefield implements Listener {

	public static HashMap<Player, BukkitTask> sch = new HashMap<Player, BukkitTask>();
	public static ArrayList<String> cooldown = new ArrayList<String>();

	public static void knockback(Player p) {
		Location loc = p.getLocation();
		List<Entity> nearby = loc.getWorld().getEntities();
		for (Entity e : nearby) {
			if ((e.getLocation().distance(loc) < 6.0D) && ((e instanceof Player)) && (e instanceof Player)
					&& (!Admin.emadmin.contains(e))) {
				Player d = (Player) e;
				if (d != p) {
					d.damage(0.0001);
					d.damage(0.0002);
					d.damage(0.0003);
					d.damage(0.0004);
					d.damage(0.0005);
					d.damage(0.0006);
					d.damage(0.0007);
					d.damage(0.0008);
					d.damage(1.6D);
					d.setVelocity(d.getLocation().getDirection().multiply(-0.3));
					d.sendMessage(StringUtils.avisovermelho + "§cFuja do campo do forcefield!");
				}
			}
		}
	}

	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if ((KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Forcefield"))
				&& (p.getItemInHand().getType() == Material.IRON_FENCE)) {
			e.setCancelled(true);
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
			} else {
				Uteis.addCooldown(p, 40);
				p.sendMessage(StringUtils.avisoverde + "§cVocê ativou o forcefield!");
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {

					@Override
					public void run() {
						if (sch.containsKey(p)) {
							sch.get(p).cancel();
							sch.remove(p);
						}
					}
				}, 100L);
				sch.put(p, Bukkit.getScheduler().runTaskTimer(Main.getMe(), new Runnable() {

					@Override
					public void run() {
						for (Entity e : p.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
							if (((e instanceof Player)) && (!Admin.emadmin.contains(e))) {
								knockback(p);
								Location center = p.getLocation();
								int radius = 5;
								Location min = new Location(center.getWorld(), center.getX() - radius,
										center.getY() - 1, center.getZ() - radius);
								Location max = new Location(center.getWorld(), center.getX() + radius,
										center.getY() - 1, center.getZ() + radius);
								for (int x = (int) min.getX(); x < (int) max.getX(); x++) {
									for (int z = (int) min.getZ(); z < (int) max.getZ(); z++) {
										Location cord = new Location(center.getWorld(), x, center.getY(), z);
										ParticleEffect.CRIT.send(Bukkit.getOnlinePlayers(), cord, 2, 5, 2, 0, 1);
									}
								}
							}
						}
					}
				}, 0L, 1L));
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (sch.containsKey(e.getEntity())) {
			sch.get(e.getEntity()).cancel();
			sch.remove(e.getEntity());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (sch.containsKey(e.getPlayer())) {
			sch.get(e.getPlayer()).cancel();
			sch.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void onTp(PlayerTeleportEvent e) {
		if (sch.containsKey(e.getPlayer())) {
			sch.get(e.getPlayer()).cancel();
			sch.remove(e.getPlayer());
		}
	}
}
