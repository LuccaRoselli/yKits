package com.luccadev.br.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Lightning implements Listener {

	@EventHandler
	public void disparar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Lightning"))
				&& (p.getItemInHand().getType() == Material.GHAST_TEAR)) {
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
			} else {
				e.setCancelled(true);
				p.updateInventory();

				Uteis.addCooldown(p, 25);
				p.sendMessage(StringUtils.avisoverde + "Você usou sua habilidade!");
				p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 1.0F);
				for (Entity pertos : p.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
					if ((pertos instanceof Player)) {
						Player x = (Player) pertos;
						Bukkit.getWorld(x.getWorld().getName()).strikeLightning(x.getLocation());
						x.damage(8.0D);
						x.sendMessage(StringUtils.avisovermelho + "§cVocê foi atingido por um lightning!");
					}
				}
			}
		}
	}

}
