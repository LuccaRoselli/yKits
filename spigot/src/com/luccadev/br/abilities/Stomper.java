package com.luccadev.br.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.Skill;
import com.luccadev.br.events.KillStreak;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.Uteis;

public class Stomper implements Listener {

	static KitManager kit = KitManager.getInstance();

	@EventHandler
	public void stomper(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			final Player player = (Player) event.getEntity();
			if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
				return;
			}
			if (!kit.getUsingKitName(player).equalsIgnoreCase("Stomper")) {
				return;
			}
			if (!(event.getEntity() instanceof Player)) {
				return;
			}
			if (event.getDamage() <= 3.0D) {
				return;
			}
			player.getWorld().playSound(player.getLocation(), Sound.ANVIL_LAND, 10f, 10f);
			event.setDamage(4.0D);
			new BukkitRunnable() {
				double t = Math.PI / 4;
				Location loc = player.getLocation();

				public void run() {
					t = t + 0.1 * Math.PI;
					for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 50) {
						double x = t / 1.4 * Math.cos(theta);
						double y = 0.3 * Math.exp(-0.1 * t) * Math.sin(t);
						double z = t / 1.4 * Math.sin(theta);
						loc.add(x, y, z);
						ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), loc, 0, 0, 0, 0, 1);
						loc.subtract(x, y, z);
					}
					if (t > 3) {
						this.cancel();
					}
				}

			}.runTaskTimer(Main.getMe(), 0, 1);
			if (Skill.hasSkill(player, KitManager.getKitByName("Stomper"))) {
				for (Entity stomped : player.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
					if (!(stomped instanceof Player)) {
						return;
					}
					if (!((Player) stomped).isSneaking()) {
						((Player) stomped).damage(player.getFallDistance() / 2.0F);
						if (((Player) stomped).isDead()) {
							KillStreak.addKill(player);
							if (StatsManager.kills.get(player) != 0) {
								StatsManager.kills.put(player,
										Integer.valueOf(((Integer) StatsManager.kills.get(player)).intValue() + 1));
							} else {
								StatsManager.kills.put(player, Integer.valueOf(1));
							}

							SCManager.send(player);
							SCManager.send((Player) stomped);
						} else if (((Player) stomped).isSneaking()) {
							((Player) stomped).damage(6.0D);
						}
					}
				}
			} else {
				for (Entity stomped : player.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
					if (!(stomped instanceof Player)) {
						return;
					}
					if (!((Player) stomped).isSneaking()) {
						((Player) stomped).damage(player.getFallDistance() / 2.0F);
						if (((Player) stomped).isDead()) {
							if (StatsManager.kills.get(player) != 0) {
								StatsManager.kills.put(player,
										Integer.valueOf(((Integer) StatsManager.kills.get(player)).intValue() + 1));
							} else {
								StatsManager.kills.put(player, Integer.valueOf(1));
							}

							SCManager.send(player);
							SCManager.send((Player) stomped);
						} else if (((Player) stomped).isSneaking()) {
							((Player) stomped).damage(6.0D);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void stomperApple(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if ((event.getPlayer().getItemInHand().getType() == Material.GOLDEN_APPLE)
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Stomper"))) {
			if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)
					|| (event.getAction() == Action.RIGHT_CLICK_BLOCK)
					|| (event.getAction() == Action.RIGHT_CLICK_AIR)) {
				event.setCancelled(true);
			}
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
				return;
			}
			Vector vector = p.getEyeLocation().getDirection();
			vector.multiply(0.0F);
			vector.setY(6.0F);
			p.setVelocity(vector);
			Location loc = p.getLocation();
			p.getWorld().playSound(loc, Sound.ZOMBIE_REMEDY, 5.0F, -5.0F);
			Uteis.addCooldown(p, 20);
		}
	}
}