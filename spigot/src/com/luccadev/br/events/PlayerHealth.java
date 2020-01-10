package com.luccadev.br.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luccadev.br.manager.KitManager;

public class PlayerHealth implements Listener {

	@EventHandler
	public void nohunger(FoodLevelChangeEvent event) {
		event.setCancelled(true);
		event.setFoodLevel(20);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSoup(PlayerInteractEvent e) {
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (e.getItem() != null) && (e.getItem().getType() == Material.MUSHROOM_SOUP)
				&& ((((Damageable) e.getPlayer()).getHealth() < 20.0D) || (e.getPlayer().getFoodLevel() < 20))) {
			e.getPlayer().setItemInHand(KitManager.getPote(1));
			Player p = e.getPlayer();
			Damageable hp = p;
			e.setCancelled(true);
			p.playSound(p.getLocation(), Sound.BURP, 1.0F, 1.0F);
			if (((Damageable) hp).getHealth() < 20.0D) {
				if (hp.getHealth() + 7.0D <= 20.0D) {
					hp.setHealth(hp.getHealth() + 7.0D);
				} else {
					hp.setHealth(20.0D);
				}
			} else if (p.getFoodLevel() < 20) {
				if (p.getFoodLevel() + 7 <= 20) {
					p.setFoodLevel(p.getFoodLevel() + 7);
					p.setSaturation(3.0F);
				} else {
					p.setFoodLevel(20);
					p.setSaturation(3.0F);
				}
			}
		}
	}

}
