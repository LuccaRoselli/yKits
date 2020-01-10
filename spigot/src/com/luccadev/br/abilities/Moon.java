package com.luccadev.br.abilities;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Moon implements Listener {
	KitManager kit = KitManager.getInstance();

	@EventHandler
	public void click(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (!kit.getUsingKitName(p).equalsIgnoreCase("Moon")) {
			return;
		}
		if (p.getItemInHand().getType() != Material.COAL) {
			return;
		}
		if (Uteis.hasCooldown(p)) {
			Uteis.sendCooldownMessage(p);
			return;
		}
		Uteis.addCooldown(p, 30);
		final Location bat2l = new Location(p.getWorld(), p.getLocation().getX() - 1, p.getLocation().getY(),
				p.getLocation().getZ());
		final Location bat3l = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(),
				p.getLocation().getZ());
		final Bat bat1 = (Bat) Bukkit.getWorld(p.getWorld().getName()).spawnEntity(p.getLocation(), EntityType.BAT);
		final Bat bat2 = (Bat) Bukkit.getWorld(p.getWorld().getName()).spawnEntity(bat2l, EntityType.BAT);
		final Bat bat3 = (Bat) Bukkit.getWorld(p.getWorld().getName()).spawnEntity(bat3l, EntityType.BAT);
		bat1.setVelocity(p.getLocation().getDirection().multiply(5.0));
		bat2.setVelocity(p.getLocation().getDirection().multiply(5.0));
		bat3.setVelocity(p.getLocation().getDirection().multiply(5.0));
		bat1.setCustomName("Batman");
		bat2.setCustomName("Batman");
		bat3.setCustomName("Batman");
		bat1.setCustomNameVisible(true);
		bat2.setCustomNameVisible(true);
		bat3.setCustomNameVisible(true);

		p.getWorld().playSound(p.getLocation(), Sound.BAT_HURT, 5, 5);

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
			public void run() {
				p.getWorld().createExplosion(bat1.getLocation(), 2f);
				p.getWorld().createExplosion(bat2.getLocation(), 2f);
				p.getWorld().createExplosion(bat3.getLocation(), 2f);
				bat1.remove();
				bat2.remove();
				bat3.remove();
			}
		}, 40);
		while (!bat1.isDead()) {
			List<Entity> prox = bat1.getNearbyEntities(5, 5, 5);
			for (Entity nearb : prox) {
				if (nearb.equals(p)) {
					return;
				}
				((Player) nearb).damage(5.0);
			}
		}
		while (!bat2.isDead()) {
			List<Entity> prox = bat1.getNearbyEntities(5, 5, 5);
			for (Entity nearb : prox) {
				if (nearb.equals(p)) {
					return;
				}
				((Player) nearb).damage(5.0);
			}
		}
		while (!bat3.isDead()) {
			List<Entity> prox = bat1.getNearbyEntities(5, 5, 5);
			for (Entity nearb : prox) {
				if (nearb.equals(p)) {
					return;
				}
				((Player) nearb).damage(5.0);
			}
		}
	}
}
