package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.luccadev.br.Main;
import com.luccadev.br.events.KillStreak;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class C4 implements Listener {

	HashMap<Player, Item> local = new HashMap<>();
	ArrayList<Player> usou = new ArrayList<>();
	public static HashMap<Player, Entity> loc = new HashMap<Player, Entity>();
	public static ArrayList<String> c4item = new ArrayList<String>();

	@EventHandler
	public void a(final PlayerInteractEvent e) {
		if (!usou.contains(e.getPlayer())) {
			if ((e.getMaterial().equals(Material.WOOD_BUTTON))
					&& (KitManager.getInstance().getUsingKitName(e.getPlayer()).equalsIgnoreCase("C4"))) {
				if (!Uteis.hasCooldown(e.getPlayer())) {
					if (e.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_AIR)
							|| e.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
						local.get(e.getPlayer()).remove();
						local.remove(e.getPlayer());
						e.getPlayer().getItemInHand().setType(Material.SLIME_BALL);
						e.getPlayer().sendMessage(StringUtils.avisovermelho + "§aVoce removeu sua bomba!");
						c4item.remove(e.getPlayer().getName());
						e.getPlayer().updateInventory();
					} else {
						Player ca;
						loc.put(e.getPlayer(), local.get(e.getPlayer()));
						local.get(e.getPlayer()).getLocation().getWorld()
								.createExplosion(local.get(e.getPlayer()).getLocation(), 7.5F, false);
						if (!e.getPlayer().isDead()) {
							local.get(e.getPlayer()).remove();
							local.remove(e.getPlayer());
						}
						for (Entity perto : loc.get(e.getPlayer()).getNearbyEntities(6, 6, 6)) {
							if (perto instanceof Player) {
								ca = (Player) perto;
								if (ca.isDead()) {
									if (ca != e.getPlayer()) {
										((Player) ca).sendMessage(StringUtils.avisovermelho
												+ "7Voce foi explodido pela §bC4 §7de §6" + e.getPlayer().getName());
										if (StatsManager.kills.get(e.getPlayer()) != 0) {
											StatsManager.kills.put(e.getPlayer(), Integer.valueOf(
													((Integer) StatsManager.kills.get(e.getPlayer())).intValue() + 1));
										} else {
											StatsManager.kills.put(e.getPlayer(), Integer.valueOf(1));
										}

										e.getPlayer().sendMessage(StringUtils.avisoverde + "§7Voce matou o jogador §b"
												+ ca.getName() + "§7 com sua C4");
										e.getPlayer().sendMessage(StringUtils.avisoverde + "§f+§a120 §fCoin's.");
										KillStreak.addKill(e.getPlayer());
										Player vitima = ca;
										Player matador = e.getPlayer();
										int xp = Uteis.getRand(0, 30);
										int coins = Uteis.getRand(0, 100);

										int xplose = Uteis.getRand(0, 20);
										int coinslose = Uteis.getRand(0, 100);
										vitima.sendMessage(StringUtils.avisovermelho + "§7Voce foi explodido pela c4 de §b" + matador.getName());
										vitima.sendMessage(StringUtils.avisovermelho + "§7-§c" + coinslose + " §7Coin's.");
										vitima.sendMessage(StringUtils.avisovermelho + "§7-§c" + xplose + " §7XP's.");
										vitima.playSound(matador.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);

										matador.playSound(matador.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
										matador.sendMessage(StringUtils.avisoverde + "§7Voce explodiu §b" + vitima.getName() + "§7 com sua c4");
										matador.sendMessage(StringUtils.avisoverde + "§7+§a" + coins + " §7Coin's.");
										matador.sendMessage(StringUtils.avisoverde + "§7+§a" + xp + " §7XP's.");
				

										if (StatsManager.money.get(matador) != 0) {
											StatsManager.money.put(matador,
													Integer.valueOf(((Integer) StatsManager.money.get(matador)).intValue() + coins));
										} else {
											StatsManager.money.put(matador, Integer.valueOf(coins));
										}

										if (StatsManager.money.get(vitima) >= coinslose) {
											StatsManager.money.put(vitima,
													Integer.valueOf(((Integer) StatsManager.money.get(vitima)).intValue() - coinslose));
										} else {
											StatsManager.money.put(vitima, Integer.valueOf(0));
										}

										if (StatsManager.xp.get(vitima) >= xplose) {
											StatsManager.xp.put(vitima, Integer.valueOf(((Integer) StatsManager.xp.get(vitima)).intValue() - xplose));
										} else {
											StatsManager.xp.put(vitima, Integer.valueOf(0));
										}

										if (StatsManager.xp.get(matador) != 0) {
											StatsManager.xp.put(matador, Integer.valueOf(((Integer) StatsManager.xp.get(matador)).intValue() + xp));
										} else {
											StatsManager.xp.put(matador, Integer.valueOf(xp));
										}
										SCManager.send(e.getPlayer());
									}
								}
							}
						}
						if (!e.getPlayer().isDead()) {
							e.getPlayer().getItemInHand().setType(Material.SLIME_BALL);
						}
						e.getPlayer().updateInventory();
						e.getPlayer().sendMessage(StringUtils.avisoverde + "§7Você explodiu sua c4!");
						c4item.remove(e.getPlayer().getName());
						Uteis.addCooldown(e.getPlayer(), 20);
					}
				} else {
					Uteis.sendCooldownMessage(e.getPlayer());
				}
			}
		} else {

		}
	}

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent e) {
		if (e.getMaterial().equals(Material.SLIME_BALL)) {
			if ((KitManager.getInstance().getUsingKitName(e.getPlayer()).equalsIgnoreCase("C4"))) {
				if (!Uteis.hasCooldown(e.getPlayer())) {
					if (e.getAction().equals(org.bukkit.event.block.Action.RIGHT_CLICK_AIR)
							|| e.getAction().equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
						Item bomba = e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(),
								new ItemStack(Material.TNT));
						bomba.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1.2));
						local.put(e.getPlayer(), bomba);
						e.getPlayer().sendMessage(StringUtils.avisoverde + "§6Voce lançou sua bomba!");
						e.getPlayer().getItemInHand().setType(Material.WOOD_BUTTON);
						e.getPlayer().updateInventory();
						e.getPlayer().updateInventory();
						c4item.add(e.getPlayer().getName());
						usou.add(e.getPlayer());
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
							@Override
							public void run() {
								usou.remove(e.getPlayer());

							}
						}, 10L);
					}
				} else {
					Uteis.sendCooldownMessage(e.getPlayer());
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void Morrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (c4item.contains(p.getName())) {
			if (local.containsKey(e.getEntity())) {
				local.get(e.getEntity()).remove();
				local.remove(e.getEntity());
			}
			c4item.remove(e.getEntity().getName());
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void Morrer(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (c4item.contains(p.getName())) {
			local.get(p).remove();
			local.remove(p);
			c4item.remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onplayerpick(PlayerPickupItemEvent e) {
		if (local.containsValue(e.getItem())) {
			e.setCancelled(true);
		}
	}
}