package com.luccadev.br.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class eWok implements Listener {
	KitManager kit = KitManager.getInstance();

	@EventHandler
	public void aoAtirar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.GOLD_HOE) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				if (kit.getUsingKitName(p).equalsIgnoreCase("eWok")) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
						return;
					}
					e.setCancelled(true);
					p.launchProjectile(Snowball.class);
					Uteis.addCooldown(p, 6);
				}
			}
		}
	}

	@EventHandler
	public void aoSerAtingido(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			final Player p = (Player) e.getEntity();
			if (e.getDamager() instanceof Snowball) {
				Snowball snow = (Snowball) e.getDamager();
				if (snow.getShooter() instanceof Player) {
					Player shooter = (Player) snow.getShooter();
					if (kit.getUsingKitName(shooter).equalsIgnoreCase("eWok")) {
						e.setDamage(4.0);
						shooter.sendMessage(StringUtils.avisoverde + "Voce acertou " + ChatColor.GOLD + p.getName()
								+ ChatColor.GRAY + "!");
						p.sendMessage(StringUtils.avisovermelho + "Voce foi atingido pelo eWok chamado "
								+ ChatColor.GOLD + shooter.getName() + ChatColor.GRAY + "!");
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
							public void run() {
								p.setVelocity(p.getLocation().getDirection().multiply(3));
								p.setVelocity(new Vector(0, 1, 0));
							}
						}, 5);
					}
				}
			}
		}
	}
}