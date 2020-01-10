package com.luccadev.br.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.warp.Warp;
import com.luccadev.br.events.CLog;
import com.luccadev.br.events.KillStreak;
import com.luccadev.br.events.SpawnProtection;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Spawn implements CommandExecutor {

	public static ArrayList<Player> teleport = new ArrayList<Player>();

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Voce nao e um jogador!");
			return false;
		}
		final Player p = (Player) sender;
		if (teleport.contains(p)) {
			p.sendMessage(StringUtils.avisovermelho + "§cAguarde o teleporte atual!");
			return false;
		} else {
			if (p.hasPermission(StringUtils.permissaoprefix + "delay")) {
				Uteis.tpSpawn(p);
				teleport.remove(p);
				Uteis.setSpawnItems(p);
				KitManager.getInstance().zerarKit(p);
				SCManager.send(p);
				if (Warp.isInWarp(p))
					Warp.getPlayerWarp(p).removePlayer(p);
				p.closeInventory();
				KillStreak.resetKillStreak(p);
				SpawnProtection.addProtection(p);
				if (Uteis.hasCooldown(p)) {
					Uteis.removeCooldown(p);
				}
				for (PotionEffect effect : p.getActivePotionEffects()) {
					p.removePotionEffect(effect.getType());
				}
				p.sendMessage(StringUtils.PROTECAO + "Voce ganhou a proteção do Spawn!");
			} else {
				if (CLog.antilog.contains(p.getName())) {
					p.sendMessage(StringUtils.avisovermelho + "Você não pode ir ao spawn em combate!");
					return false;
				}
				teleport.add(p);
				p.sendMessage(StringUtils.avisoverde + "§2Teleportando em 5 segundos!");
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {

					@Override
					public void run() {
						if (p.isOnline()) {
							Uteis.tpSpawn(p);
							teleport.remove(p);
							Uteis.setSpawnItems(p);
							KitManager.getInstance().zerarKit(p);
							SCManager.send(p);
							if (Warp.isInWarp(p))
								Warp.getPlayerWarp(p).removePlayer(p);
							p.closeInventory();
							KillStreak.resetKillStreak(p);
							SpawnProtection.addProtection(p);
							if (Uteis.hasCooldown(p)) {
								Uteis.removeCooldown(p);
							}
							for (PotionEffect effect : p.getActivePotionEffects()) {
								p.removePotionEffect(effect.getType());
							}
							p.sendMessage(StringUtils.PROTECAO + "Voce ganhou a proteção do Spawn!");
						}
					}
				}, 20 * 5);
			}
		}
		return false;
	}

}