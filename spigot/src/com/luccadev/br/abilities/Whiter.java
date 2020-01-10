package com.luccadev.br.abilities;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Whiter implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onmove(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (p instanceof Player) {
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Wither")) {
				if (p.getItemInHand().getType() == Material.getMaterial(399)) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
						return;
					}
					Uteis.addCooldown(p, 10);
					Vector velo1 = p.getLocation().getDirection().normalize().multiply(2);
					p.playSound(p.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
					p.getEyeLocation().getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
					p.getLocation().getWorld().playEffect(p.getLocation(), Effect.GHAST_SHOOT, 1);
					p.getLocation().getWorld().playEffect(p.getLocation(), Effect.BLAZE_SHOOT, 1);
					((WitherSkull) p.launchProjectile(WitherSkull.class)).setVelocity(velo1);
				}
			}
		}
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		Entity e1 = e.getEntity();
		Entity e2 = e.getDamager();
		if (((e1 instanceof Player)) && ((e2 instanceof WitherSkull))) {
			Player player = (Player) e1;
			player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 1));
			e2.getWorld().playSound(e2.getLocation(), Sound.WITHER_HURT, 1.0F, 0.0F);
		}
	}
}
