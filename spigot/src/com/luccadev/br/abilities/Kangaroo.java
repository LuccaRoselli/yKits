package com.luccadev.br.abilities;

import java.util.HashMap;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.luccadev.br.manager.KitManager;

public class Kangaroo implements Listener {
	KitManager kit = KitManager.getInstance();
	private HashMap<Player, Integer> Pulo = new HashMap<Player, Integer>();

	@EventHandler
	public void Morte(final PlayerDeathEvent e) {
		final Player p = e.getEntity();
		if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("kangaroo")) {
			for (ListIterator<ItemStack> item = e.getDrops().listIterator(); item.hasNext();) {
				ItemStack i = item.next();
				if ((i.getItemMeta().hasDisplayName())) {
					item.remove();
				}
			}
		}
	}

	@SuppressWarnings("unused")
	@EventHandler
	public void interact(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("kangaroo")) {
			if (p.getItemInHand().getType() == Material.FIREWORK) {
				event.setCancelled(true);
				boolean combate = false;
				for (Entity entity : p.getNearbyEntities(3, 3, 3)) {
					if (entity instanceof Player) {
						Player t = (Player) entity;

						if (t != null) {
							combate = true;
						} else {
							combate = false;
						}

					}
				}

				if (combate == false) {
					if (!(Pulo.containsKey(p))) {
						if (!(p.isSneaking())) {
							if (!((CraftPlayer) p).getHandle().onGround) {
								Pulo.put(p, 1);
								p.setVelocity(new Vector(p.getVelocity().getX(), 1, p.getVelocity().getZ()));
							} else {
								p.setVelocity(new Vector(p.getVelocity().getX(), 1, p.getVelocity().getZ()));
							}
						} else {
							if (!((CraftPlayer) p).getHandle().onGround) {
								p.setVelocity(p.getLocation().getDirection().multiply(1.2));
								p.setVelocity(new Vector(p.getVelocity().getX(), 0.5, p.getVelocity().getZ()));
								Pulo.put(p, 1);
							} else {
								p.setVelocity(p.getLocation().getDirection().multiply(1.2));
								p.setVelocity(new Vector(p.getVelocity().getX(), 0.5, p.getVelocity().getZ()));
							}
						}
					}
				} else {
					if (!(Pulo.containsKey(p))) {
						if (!((CraftPlayer) p).getHandle().onGround) {
							p.setVelocity(new Vector(p.getVelocity().getX(), 0.5, p.getVelocity().getZ()));
							Pulo.put(p, 1);
						} else {
							p.setVelocity(new Vector(p.getVelocity().getX(), 0.5, p.getVelocity().getZ()));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void landed(PlayerMoveEvent e) {
		if (!(e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR)) {
			if (Pulo.containsKey(e.getPlayer())) {
				Pulo.remove(e.getPlayer());
			}
		}
	}

	@EventHandler
	public void gotdamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("kangaroo")) {
				if (e.getDamage() > 7) {
					e.setDamage(7);
				} else {
					e.setDamage(e.getDamage());
				}
			}
		}
	}

	// public ArrayList<String> Combate = new ArrayList<String>();

	@SuppressWarnings("deprecation")
	public boolean isOnGround(Player p) {
		Location l = p.getLocation();
		l = l.add(0, -1, 0);
		return l.getBlock().getState().getTypeId() != 0;
	}
}
