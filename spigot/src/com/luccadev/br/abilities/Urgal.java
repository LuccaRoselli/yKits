package com.luccadev.br.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Urgal implements Listener {

	private void urgalEffect(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1220, 0));
		p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0F, 1.0F);
		int points = 6;
		double size = 1;
		for (int i = 0; i < 360; i += 360 / points) {
			double angle = (i * Math.PI / 180);
			double x = size * Math.cos(angle);
			double z = size * Math.sin(angle);
			Location loc = p.getLocation().add(x, 1, z);
			ParticleEffect.SPELL_MOB.send(Bukkit.getOnlinePlayers(), loc, 0, 0, 0, 1, 20);
			ParticleEffect.SPELL_WITCH.send(Bukkit.getOnlinePlayers(), loc, 0, 0, 0, 1, 20);
			ParticleEffect.TOWN_AURA.send(Bukkit.getOnlinePlayers(), loc, 0, 0, 0, 1, 20);
		}
	}

	@EventHandler
	public void Om(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (p.getItemInHand().getType() == Material.BLAZE_POWDER)
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Urgal"))) {
			if (!Uteis.hasCooldown(p)) {
				urgalEffect(p);
				if (item.getAmount() > 1) {
					item.setAmount(item.getAmount() - 1);
				} else {
					item = null;
				}
				p.setItemInHand(item);
				Uteis.addCooldown(p, 30);
				p.updateInventory();

			} else {
				Uteis.sendCooldownMessage(p);
			}
		}
	}
}