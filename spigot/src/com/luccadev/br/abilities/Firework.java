package com.luccadev.br.abilities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Firework implements Listener {
	final ArrayList<String> dano = new ArrayList<String>();
	KitManager kit = KitManager.getInstance();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void aoInteragir(final PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			final Player t = (Player) e.getRightClicked();
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Firework")) {
				if (p.getItemInHand().getType() == Material.FIREWORK) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
						return;
					}
					Uteis.addCooldown(p, 10);
					e.setCancelled(true);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new BukkitRunnable() {
						public void run() {
							Entity fw = (Entity) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
							fw.setPassenger(t);
							fw.setVelocity(new Vector(0, 1.2, 0));
							t.sendMessage(StringUtils.avisovermelho + "§c§oO mestre dos fogos de artificios te pegou");
							dano.add(t.getName());
						}
					}, 5L);
				}
			}
		}
	}

	@EventHandler
	public void onCancel(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.FIREWORK) {
			if (kit.getUsingKitName(p).equalsIgnoreCase("Firework")) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void aoDano(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL) {
				if (dano.contains(p.getName())) {
					e.setDamage(e.getDamage() / 2.5);
					dano.remove(p.getName());
					p.sendMessage(StringUtils.avisoverde + "§7Por causa da firework, seu dano de queda foi diminuido, mas agora você esta vulneravel!");
				}
			}
		}
	}
}
