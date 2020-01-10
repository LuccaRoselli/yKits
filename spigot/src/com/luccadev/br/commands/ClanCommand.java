package com.luccadev.br.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.luccadev.br.constructors.Clan;
import com.luccadev.br.utils.StringUtils;

public class ClanCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Voce nao e um jogador!");
			return false;
		}
		Player p = (Player) sender;
		if (commandLabel.equalsIgnoreCase("clan")) {
			if (args.length == 0){
				p.sendMessage("§7§m§o§l---------------------------------------------");
				p.sendMessage("              " + StringUtils.getPrefix('b') + "§8: §eClans");
				p.sendMessage("§7§m§o§l---------------------------------------------");
				p.sendMessage("/clan criar <nome> <tag>§8: Crie seu clan. §c(25,000 Coin's)");
				p.sendMessage("/clan convidar <jogador> §8: Convide alguém para seu clan.");
				p.sendMessage("§7§m§o§l---------------------------------------------");
				return true;
			}
			if (args[0].equalsIgnoreCase("criar")){
				if (args.length == 3){
					if (!Clan.exists(args[1])){
						if (args[2].length() <= 5){
							if (args[1].length() <= 12){
								/*
								new Clan(p.getName(), args[1], args[2]);
								p.sendMessage(StringUtils.avisoverde + "Parabéns! Você acaba de criar um novo clan.");
								p.sendMessage(StringUtils.avisoverde + "Nome: §a" + args[1]);
								p.sendMessage(StringUtils.avisoverde + "TAG: §a" + args[2]);
								p.sendMessage(StringUtils.coinprefix + "§7-§c25.000 §7Coin's.");
								*/
								p.sendMessage(StringUtils.avisovermelho + "§cSistema de clans não disponível (§4§lBETA§c)");
							} else {
								p.sendMessage(StringUtils.avisovermelho + "O nome de seu clan pode ter no máximo §c12 §7letras.");
							}
						} else {
							p.sendMessage(StringUtils.avisovermelho + "O tag do seu clan pode ter no máximo §c5 §7letras.");
						}
					} else {
						p.sendMessage(StringUtils.avisovermelho + "O clan §c" + args[1] + " §7já existe.");
					}
				} else {
					p.sendMessage(StringUtils.avisovermelho + "Utilize: §a/clan criar <nome> <tag>");
				}
			}
		}
		return false;
	}

}