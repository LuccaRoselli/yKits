package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Fish implements Listener {
	KitManager kit = KitManager.getInstance();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void click(PlayerInteractEntityEvent event) {
		final Player p = event.getPlayer();
		if (event.getRightClicked() instanceof Player) {
			final Player p2 = (Player) event.getRightClicked();
			if (p.getItemInHand().getType() == Material.getMaterial(349)) {
				if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Fish")) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
						return;
					}
					p2.playSound(p2.getLocation(), Sound.ENDERDRAGON_DEATH, 10f, 10f);
					Location loc3 = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 15,
							p.getLocation().getZ());
					final List<Location> cuboid = new ArrayList<Location>();
					cuboid.clear();
					int bY;
					for (int bX = -2; bX <= 2; bX++) {
						for (int bZ = -2; bZ <= 2; bZ++) {
							for (bY = -1; bY <= 2; bY++) {
								Block b = loc3.clone().add(bX, bY, bZ).getBlock();
								if (!b.isEmpty() || b.getLocation().getBlock().getType() != Material.AIR) {
									p.sendMessage(StringUtils.avisovermelho + "A blocos encima de você!");
									return;
								}
								Uteis.addCooldown(p, 20);
								if (bY == 2) {
									cuboid.add(loc3.clone().add(bX, bY, bZ));
								} else if (bY == -1) {
									cuboid.add(loc3.clone().add(bX, bY, bZ));
								} else if ((bX == -2) || (bZ == -2) || (bX == 2) || (bZ == 2)) {
									cuboid.add(loc3.clone().add(bX, bY, bZ));
								}
								for (final Location loc11 : cuboid) {
									loc11.getBlock().setType(Material.GLASS);
									p2.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 5 * 20, 1));
									Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
										public void run() {
											loc11.getBlock().setType(Material.AIR);
										}
									}, 5 * 20);
								}
							}

						}

					}
					p2.teleport(loc3);
				}
			}
		}
	}
}
