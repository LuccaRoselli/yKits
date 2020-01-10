package com.luccadev.br.abilities;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFireball;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

import net.minecraft.server.v1_8_R3.EntityFireball;

public class Devastator implements Listener {
	public static HashMap<UUID, String> fireballs;
	public static HashMap<Location, BlockState> used;
	public static HashMap<UUID, Integer> timers;

	static {
		fireballs = new HashMap<UUID, String>();
		used = new HashMap<Location, BlockState>();
		timers = new HashMap<UUID, Integer>();
	}

	@EventHandler
	public void click(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (p instanceof Player) {
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Devastator")) {
				if (p.getItemInHand().getType() == Material.FIREBALL) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
					} else {
						Uteis.addCooldown(p, 30);
						doEffect(p, p.getLocation());
					}
				}
			}
		}
	}

	public static void doEffect(final Player p, final Location loc) {
		final int r = 13;
		final int cx = loc.getBlockX();
		final int cz = loc.getBlockZ();
		final int rSquared = r * r;
		for (int x2 = cx - r; x2 <= cx + r; ++x2) {
			for (int z2 = cz - r; z2 <= cz + r; ++z2) {
				if ((cx - x2) * (cx - x2) + (cz - z2) * (cz - z2) <= rSquared) {
					final Location i = new Location(loc.getWorld(), (double) x2, 113.0, (double) z2);
					long delay = 0L;
					final int wave = new Random().nextInt(2);
					switch (wave) {
					case 0: {
						delay = 0L;
						break;
					}
					case 1: {
						delay = 20L;
						break;
					}
					}
					if (new Random().nextInt(100) <= 20) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
							public void run() {
								try {
									final Fireball fb = (Fireball) loc.getWorld().spawnEntity(i, EntityType.FIREBALL);
									fireballs.put(fb.getUniqueId(), p.getName());
									timers.put(fb.getUniqueId(), Bukkit.getScheduler()
											.scheduleSyncRepeatingTask(Main.getMe(), new Runnable() {
												int x = 0;

												@Override
												public void run() {
													try {
														if (this.x == 600) {
															timers.remove(fb.getUniqueId());
															Bukkit.getScheduler()
																	.cancelTask((int) timers.get(fb.getUniqueId()));
															return;
														}
														final EntityFireball efb = ((CraftFireball) fb).getHandle();
														if (!efb.isAlive()) {
															Bukkit.getScheduler()
																	.cancelTask((int) timers.get(fb.getUniqueId()));
															timers.remove(fb.getUniqueId());
															return;
														}
														if (fireballs.containsKey(efb.getUniqueID())) {
															efb.motX = 1.0E-4;
															efb.motZ = 1.0E-4;
															efb.motY = -1.0;
														}
														++this.x;
													} catch (Exception e) {
														Bukkit.getScheduler()
																.cancelTask((int) timers.get(fb.getUniqueId()));
														timers.remove(fb.getUniqueId());
													}
												}
											}, 0L, 1L));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}, delay);
					}
				}
			}
		}
		loc.getWorld().playSound(loc, Sound.ENDERDRAGON_GROWL, 2.0f, 1.0f);
	}

	@EventHandler
	public void damage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Fireball) {
			if (e.getEntity() instanceof Player) {
				if (KitManager.getInstance().getUsingKitName((Player)e.getEntity()).equalsIgnoreCase("Devastator")){
					e.setDamage(0.0D);
					return;
				}
				e.setDamage(e.getDamage() + 10.0D);
				if (e.getDamage() > 19.9D){
					Player p = (Player) e.getEntity();
					p.damage(25.0D);
				}
			}
		}
	}

}
