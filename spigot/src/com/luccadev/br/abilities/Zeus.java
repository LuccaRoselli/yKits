package com.luccadev.br.abilities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Zeus implements Listener {

	int cool = 30;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<String> zeuskit = new ArrayList();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<String> kitzeus = new ArrayList();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ArrayList<Block> blockList = new ArrayList();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerzeus(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("zeus"))
				&& (p.getItemInHand().getType() == Material.NETHER_STAR)) {
			if (Uteis.hasCooldown(p)) {

				e.setCancelled(true);
				p.updateInventory();
				Uteis.sendCooldownMessage(p);
				return;
			}
			Uteis.addCooldown(p, 15);
			e.setCancelled(true);
			p.updateInventory();
			Vector v = p.getLocation().getDirection().multiply(0).setY(2.0D);
			p.setVelocity(v);
			zeuskit.add(p.getName());
			kitzeus.add(p.getName());
			return;
		}
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerzeusLeftClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("zeus")) ||

				(p.getItemInHand().getType() == Material.PUMPKIN)) {
			if (e.getAction() == Action.LEFT_CLICK_AIR) {
				if ((zeuskit.contains(p.getName())) && (kitzeus.contains(p.getName()))) {
					e.setCancelled(true);
					kitzeus.remove(p.getName());
					Vector v = p.getLocation().getDirection().multiply(2.0D);
					p.setVelocity(v);
					return;
				}
				return;
			}
			return;
		}
	}

	@EventHandler
	public void onPlayerzeusFall(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
			if (zeuskit.contains(p.getName())) {
				for (Entity plr : p.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
					if ((plr instanceof Player)) {
						Player pla = (Player) plr;
						Vector v = p.getLocation().getDirection().multiply(0).setX(0.7D);
						pla.setVelocity(v);
						pla.playSound(pla.getLocation(), Sound.EXPLODE, 4.0F, 4.0F);
						Bukkit.getWorld(pla.getWorld().getName()).strikeLightning(pla.getLocation());
						pla.damage(9);
						pla.setFireTicks(300);
						p.playSound(p.getLocation(), Sound.EXPLODE, 4.0F, 4.0F);
					}
				}
				zeuskit.remove(p.getName());
				e.setDamage(9.0D);
				return;
			}
			return;
		}
	}
}
