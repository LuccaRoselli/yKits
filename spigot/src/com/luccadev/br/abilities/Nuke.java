package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Nuke implements Listener {
	ArrayList<String> jump = new ArrayList<>();
	KitManager kit = KitManager.getInstance();

	@EventHandler
	public void onmove(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (p instanceof Player) {
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nuke")) {
				if (p.getItemInHand().getType() == Material.TNT) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
						return;
					}
					p.playSound(p.getLocation(), Sound.BLAZE_HIT, 5f, 5f);
					Uteis.addCooldown(p, 10);
					Vector vec = new Vector(0, 3, 0);
					p.setVelocity(vec);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
						public void run() {
							Vector vec2 = new Vector(0, -2, 0);
							p.setVelocity(vec2);
						}
					}, 1 * 20);
					jump.add(p.getName());
				}
			}
		}
	}

	@EventHandler
	public void damage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nuke")) {
				if (jump.contains(p.getName())) {
					event.setDamage(2.0);
					p.playSound(p.getLocation(), Sound.BLAZE_BREATH, 5f, 5f);
					ParticleEffect.EXPLOSION_LARGE.send(Bukkit.getOnlinePlayers(), p.getLocation(), 1, 2, 1, 1, 1);
					List<Entity> nearby = p.getNearbyEntities(4, 4, 4);
					jump.remove(p.getName());
					for (Entity ent : nearby) {
						Vector vec = new Vector(0, 2, 0);
						p.getWorld().playSound(p.getLocation(), Sound.EXPLODE, 5f, 5f);
						ParticleEffect.EXPLOSION_LARGE.send(Bukkit.getOnlinePlayers(), p.getLocation(), 1, 2, 1, 1, 1);
						ent.setVelocity(vec);
					}
				}
			}
		}
	}
}
