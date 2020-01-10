package com.luccadev.br.abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;

public class Anchor implements Listener {

	@EventHandler
	public void Damage(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player)) {
			final Player p = (Player) e.getEntity();
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Anchor")) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
					public void run() {
						p.setVelocity(new Vector());
					}
				});
			}
			if ((e.getDamager() instanceof Player)) {
				final Player d = (Player) e.getDamager();
				if (KitManager.getInstance().getUsingKitName(d).equalsIgnoreCase("Anchor")) {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
						public void run() {
							d.setVelocity(new Vector());
							p.setVelocity(new Vector());
						}
					});
				}
			}
		}
	}

}
