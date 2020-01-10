package com.luccadev.br.abilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Darius implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand().getType() == Material.GOLD_AXE) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				Player p = e.getPlayer();
				if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Darius")) {
					if (!Uteis.hasCooldown(p)) {
						throwHook(p);
						Uteis.addCooldown(p, 5);
					} else {
						Uteis.sendCooldownMessage(p);
					}
				}
			}
		}
	}

	public void throwHook(final Player p) {
		new BukkitRunnable() {
			Vector dir = p.getLocation().getDirection().normalize();
			Location loc = p.getLocation();
			double t = 0;

			public void run() {
				t += 0.5;
				double x = dir.getX() * t;
				double y = dir.getY() * t + 1.0;
				double z = dir.getZ() * t;
				loc.add(x, y, z);
				ParticleEffect.SMOKE_NORMAL.send(Bukkit.getOnlinePlayers(), loc, 0.0F, 0.0F, 0.0F, 0, 5);
				for (Entity e : loc.getChunk().getEntities()) {
					if (e.getLocation().distance(loc) < 1.0) {
						if (!e.equals(p)) {
							Location lc = ((Player) e).getLocation();
							Location to = p.getLocation();

							lc.setY(lc.getY() + 0.5D);
							double g = -0.08D;
							double d = to.distance(lc);
							double t = d;
							double v_x = (0.3D + 0.17D * t) * (to.getX() - lc.getX()) / t;
							double v_y = (0.3D + 0.03D * t) * (to.getY() - lc.getY()) / t - 0.2D * g * t;
							double v_z = (0.3D + 0.17D * t) * (to.getZ() - lc.getZ()) / t;
							Vector v = p.getVelocity();
							v.setX(v_x);
							v.setY(v_y);
							v.setZ(v_z);
							((Player) e).setVelocity(v);
							((Player) e).setFireTicks(100);
							;
							((Player) e).sendMessage(
									StringUtils.avisovermelho + "Você foi puxado pelo Darius " + p.getName() + "!");
						}
					}
				}
				loc.subtract(x, y, z);
				if (t > 40) {
					this.cancel();
				}
			}
		}.runTaskTimer(Main.getMe(), 0, 1);
	}

}
