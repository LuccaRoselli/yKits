package com.luccadev.br.abilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Sonic implements Listener {

	public static HashMap<String, ItemStack[]> Armadura = new HashMap<String, ItemStack[]>();

	public int boost = Integer.valueOf(3).intValue();

	@SuppressWarnings("unused")
	@EventHandler
	public void VelotrolClick(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (event.getPlayer().getItemInHand().getType() == Material.LAPIS_BLOCK) {
			if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)
					|| (event.getAction() == Action.RIGHT_CLICK_BLOCK)
					|| (event.getAction() == Action.RIGHT_CLICK_AIR)) {
				event.setCancelled(true);
			}
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Sonic")) {
				if (Uteis.hasCooldown(p)) {
					Uteis.sendCooldownMessage(p);
					return;
				}
				Uteis.addCooldown(p, 35);
				p.setVelocity(p.getEyeLocation().getDirection().multiply(this.boost).add(new Vector(0, 0, 0)));
				p.getPlayer().getWorld().playEffect(p.getPlayer().getLocation(), Effect.SMOKE, 10, 0);
				Location loc = p.getLocation();
				for (Entity pertos : p.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
					if ((pertos instanceof Player)) {
						Player perto = (Player) pertos;
						((Player) pertos).damage(10.0D);
						pertos.setVelocity(new Vector(0.1D, 0.0D, 0.1D));
						((Player) pertos).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 105, 105));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 105, 105));
					}
				}
				ItemStack Capacete = new ItemStack(Material.LEATHER_HELMET);
				LeatherArmorMeta kCapacete = (LeatherArmorMeta) Capacete.getItemMeta();
				kCapacete.setColor(Color.BLUE);
				Capacete.setItemMeta(kCapacete);

				ItemStack Peitoral = new ItemStack(Material.LEATHER_CHESTPLATE);
				LeatherArmorMeta kPeitoral = (LeatherArmorMeta) Peitoral.getItemMeta();
				kPeitoral.setColor(Color.BLUE);
				Peitoral.setItemMeta(kPeitoral);

				ItemStack Calça = new ItemStack(Material.LEATHER_LEGGINGS);
				LeatherArmorMeta kCalça = (LeatherArmorMeta) Calça.getItemMeta();
				kCalça.setColor(Color.BLUE);
				Calça.setItemMeta(kCalça);

				ItemStack Bota = new ItemStack(Material.LEATHER_BOOTS);
				LeatherArmorMeta kBota = (LeatherArmorMeta) Capacete.getItemMeta();
				kBota.setColor(Color.BLUE);
				Bota.setItemMeta(kBota);

				Armadura.put(p.getName(), p.getInventory().getArmorContents());

				p.getInventory().setHelmet(Capacete);
				p.getInventory().setChestplate(Peitoral);
				p.getInventory().setLeggings(Calça);
				p.getInventory().setBoots(Bota);
				p.updateInventory();

				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
					public void run() {
						p.getInventory().setHelmet(null);
						p.getInventory().setChestplate(null);
						p.getInventory().setLeggings(null);
						p.getInventory().setBoots(null);
						p.updateInventory();
					}
				}, 50L);
			}
		}
	}
}
