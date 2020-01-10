package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Molotov implements Listener {

	public static List<Location> circle(Location loc, int radius, int height, boolean hollow, boolean sphere) {
		List<Location> circleBlocks = new ArrayList<Location>();
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		for (int x = cx - radius; x <= cx + radius; x++) {
			for (int z = cz - radius; z <= cz + radius; z++) {
				for (int y = sphere ? cy - radius : cy; y < (sphere ? cy + radius : cy + height); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if ((dist < radius * radius) && ((!hollow) || (dist >= (radius - 1) * (radius - 1)))) {
						Location l = new Location(loc.getWorld(), x, y, z);

						circleBlocks.add(l);
					}
				}
			}
		}
		return circleBlocks;
	}

	@EventHandler
	public void lancar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Molotov"))
				&& (p.getItemInHand().getType() == Material.SNOW_BALL)) {
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
				e.setCancelled(true);
				p.updateInventory();
			} else {
				e.setCancelled(true);
				p.updateInventory();
				Uteis.addCooldown(p, 20);
				p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.SNOW_BALL, 1) });
				Snowball granada = (Snowball) p.launchProjectile(Snowball.class);
				granada.setMetadata("molotov", new FixedMetadataValue(Main.getMe(), Boolean.valueOf(true)));
				p.playSound(p.getLocation(), Sound.FUSE, 1.0F, 1.0F);
				p.sendMessage(StringUtils.avisoverde + ChatColor.GOLD + "Molotov lançado!!");
			}
			return;
		}
	}

	@EventHandler
	public void explosao(ProjectileHitEvent e) {
		if ((e.getEntity() instanceof Snowball)) {
			Snowball b = (Snowball) e.getEntity();
			if (b.hasMetadata("molotov")) {
				final List<Block> broquis = new ArrayList<Block>();
				for (Location l : circle(b.getLocation(), 2, 1, false, false)) {
					try {
						if (l.getWorld().getBlockAt(l.getBlockX(), l.getBlockY(), l.getBlockZ())
								.getType() != Material.AIR)
							continue;
						l.getWorld().getBlockAt(l.getBlockX(), l.getBlockY(), l.getBlockZ()).setType(Material.FIRE);
						broquis.add(l.getWorld().getBlockAt(l.getBlockX(), l.getBlockY(), l.getBlockZ()));
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {

							@Override
							public void run() {
								for (Block block : broquis) {
									if (block.getType() == Material.FIRE) {
										block.setType(Material.AIR);
									}
								}
							}
						}, 200);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			return;
		}
	}
}
