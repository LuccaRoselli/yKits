package com.luccadev.br.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.luccadev.br.Main;
import com.luccadev.br.experience.ExperienceRank;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.utils.StringUtils;

public class CLog implements Listener {
	public static ArrayList<String> antilog = new ArrayList<String>();

	@EventHandler
	public void onAntiLogQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if (antilog.contains(p.getName())) {
			Bukkit.getServer().broadcastMessage(
					StringUtils.avisovermelho + "§7O jogador §6" + p.getName() + " §7deslogou em combate.");
			p.damage(20.0);
		}
	}

	@EventHandler
	public void entrarCombate(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Player))) {
			final Player p = (Player) e.getEntity();
			final Player damager = (Player) e.getDamager();
			/*
			 * if
			 * (!KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase(
			 * "Nenhum")){ if (!BarAPI.hasBar(p)){ BarAPI.setMessage(damager,
			 * "§bJogador: §7" + p.getName() + "    §bKit: §7" +
			 * KitManager.getInstance().getUsingKitName(p), 5); } }
			 */
			if (KitManager.getInstance().getUsingKitName(damager).equalsIgnoreCase("Nenhum")
					|| KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")) {
				return;
			}

			if (!antilog.contains(p.getName())) {
				antilog.add(p.getName());
				p.sendMessage(StringUtils.avisovermelho + "Você está em combate com " + ChatColor.AQUA
						+ damager.getName() + ".");
				if (SCManager.scb.get(damager).booleanValue() == true) {
					if (!KitManager.getInstance().getUsingKitName(damager).equalsIgnoreCase("Nenhum")
							&& !KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")) {

						Scoreboard sb = damager.getScoreboard();
						Objective ob = sb.getObjective("kills");
						if (ob != null) {
							ob.unregister();

						}
						ob = sb.registerNewObjective("kills", "health");
						ob.setDisplaySlot(DisplaySlot.BELOW_NAME);
						ob.setDisplayName(
								"§4" + SCManager.heart + " §8❮§e" + KitManager.getInstance().getUsingKitName(p) + " | §" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§8❯");
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
							public void run() {

								SCManager.send(damager);
							}
						}, 90L);
					}
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
					public void run() {
						if (CLog.antilog.contains(p.getName())) {
							CLog.antilog.remove(p.getName());
							if (p != null) {
								p.sendMessage(StringUtils.avisoverde + "Você não está mais em combate.");
							}
						}
					}
				}, 200L);
			}
			if (!antilog.contains(damager.getName())) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
					public void run() {
						if (CLog.antilog.contains(damager.getName())) {
							CLog.antilog.remove(damager.getName());
							if (damager != null) {
								damager.sendMessage(StringUtils.avisoverde + "Você não está mais em combate.");
							}
						}
					}
				}, 200L);
			}
		}
	}
}