package com.luccadev.br.abilities;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.luccadev.br.manager.KitManager;

public class Magma implements Listener {

	@EventHandler
	public void onDamage(final EntityDamageEvent e) {
		if (e.getEntity() instanceof Player
				&& KitManager.getInstance().getUsingKitName((Player) e.getEntity()).equalsIgnoreCase("Magma")
				&& (e.getCause() == EntityDamageEvent.DamageCause.FIRE
						|| e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
						|| e.getCause() == EntityDamageEvent.DamageCause.LAVA)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void Posei(final PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Magma")) {
			if (p.getLocation().getBlock().getType() == Material.WATER
					|| p.getLocation().getBlock().getType() == Material.STATIONARY_WATER) {
				p.damage(2.0);
			}
		}
	}

	@EventHandler
	public void Dano(final EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			final Player p = (Player) e.getEntity();
			final Player d = (Player) e.getDamager();
			if (KitManager.getInstance().getUsingKitName(d).equalsIgnoreCase("Magma")) {
				final Random rand = new Random();
				if (rand.nextInt(90) + 10 < 20) {
					p.setFireTicks(100);
				}
			}
		}
	}

}
