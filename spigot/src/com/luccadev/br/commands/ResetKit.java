package com.luccadev.br.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.luccadev.br.constructors.warp.Warp;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class ResetKit implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Voce nao e um jogador!");
			return false;
		}
		Player p = (Player) sender;
		if (commandLabel.equalsIgnoreCase("resetkit")) {
			if (p.hasPermission(StringUtils.permissaoprefix + "comando.resetkit")
					|| p.hasPermission(StringUtils.permissaoprefix + "staff")) {
				if (args.length == 0) {
					if (!KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")){
						KitManager.getInstance().zerarKit(p);
						SCManager.send(p);
						Uteis.setSpawnItems(p);
						if (Warp.isInWarp(p))
							Warp.getPlayerWarp(p).removePlayer(p);
						if (Uteis.hasCooldown(p)) {
							Uteis.removeCooldown(p);
						}
						p.sendMessage(StringUtils.avisoverde + "Agora você pode escolher um novo kit.");
						p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
					} else {
						p.sendMessage(StringUtils.avisovermelho + "Você não está usando nenhum kit.");
					}
				} else {
					if (args.length > 0 && args.length < 2 && args.length == 1) {
						Player target = Bukkit.getPlayer(args[0]);
						if (target != null && target.isOnline()) {
							if (!KitManager.getInstance().getUsingKitName(target).equalsIgnoreCase("Nenhum")){
								KitManager.getInstance().zerarKit(target);
								SCManager.send(target);
								Uteis.setSpawnItems(target);
								if (Warp.isInWarp(p))
									Warp.getPlayerWarp(p).removePlayer(p);
								if (Uteis.hasCooldown(p)) {
									Uteis.removeCooldown(p);
								}
								target.sendMessage(StringUtils.avisoverde + "Agora você pode escolher um novo kit.");
								target.sendMessage(StringUtils.avisoverde + "O staffer " + p.getName() + " resetou seu kit!");
								target.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
							} else {
								p.sendMessage(StringUtils.avisovermelho + "O jogador " + target.getName() + " não está usando nenhum kit.");
							}
						} else {
							p.sendMessage(StringUtils.avisovermelho + "O jogador " + target.getName() + " não está online ou não existe.");
						}
					}
				}
			}
		}return false;
}

}
