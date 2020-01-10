package com.luccadev.br.abilities;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Flare implements Listener {
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Flare"))
				&& (p.getItemInHand().getType() == Material.BLAZE_POWDER)) {
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
				return;
			}
			Uteis.addCooldown(p, 30);
			Location location = p.getEyeLocation();
			BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, 40);
			while (blocksToAdd.hasNext()) {
				Location blockToAdd = blocksToAdd.next().getLocation();
				p.getWorld().playEffect(blockToAdd, Effect.MOBSPAWNER_FLAMES, 20);
			}
			Arrow h = (Arrow) p.launchProjectile(Arrow.class);
			Vector velo1 = p.getLocation().getDirection().normalize().multiply(10);
			h.setVelocity(velo1);
			h.setMetadata("flare", new FixedMetadataValue(Main.getMe(), Boolean.valueOf(true)));
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void dano(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Arrow))) {
			Arrow s = (Arrow) e.getDamager();
			Player p = (Player) e.getEntity();
			if (s.hasMetadata("flare")) {
				e.setDamage(10.0D);
				p.setFireTicks(300);
				p.setVelocity(new Vector(0.0D, 1.8D, 0.0D));
				p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			}
		}
	}
}
