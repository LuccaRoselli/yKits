package com.luccadev.br.abilities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Velotrol implements Listener {

	public static ArrayList<Player> velotrol = new ArrayList<Player>();

	@EventHandler
	public void VelotrolClick(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (event.getPlayer().getItemInHand().getType() == Material.MINECART) {
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("velotrol")) {
				if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)
						|| (event.getAction() == Action.RIGHT_CLICK_BLOCK)
						|| (event.getAction() == Action.RIGHT_CLICK_AIR)) {
					event.setCancelled(true);
				}
				if (Uteis.hasCooldown(p)) {
					Uteis.sendCooldownMessage(p);
					return;
				}
				event.setCancelled(true);
				Uteis.addCooldown(p, 35);
				p.setVelocity(p.getEyeLocation().getDirection().multiply(2).add(new Vector(0, 0, 0)));
				p.getPlayer().getWorld().playEffect(p.getPlayer().getLocation(), Effect.SMOKE, 10, 0);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {

					@Override
					public void run() {
						velotrol.add(p);
					}
				}, 40L);
			}
		}
	}

	@EventHandler
	public void aeeeh(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
				return;
			}
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("velotrol")) {
				e.setDamage(1.0D);
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (velotrol.contains(p)) {
			Block b = p.getLocation().getBlock();
			if ((b.getType() != Material.AIR) || (b.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
				if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("velotrol")) {
					velotrol.remove(p);
					Entity tnt = Bukkit.getServer().getWorld(p.getLocation().getWorld().getName())
							.spawnEntity(p.getLocation(), EntityType.PRIMED_TNT);
					((TNTPrimed) tnt).setFuseTicks(2);
					if (p.isInsideVehicle()) {
						p.getVehicle().remove();

					}
				}
			}

		}
	}

}
