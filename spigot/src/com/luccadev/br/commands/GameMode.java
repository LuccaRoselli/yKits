package com.luccadev.br.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.luccadev.br.utils.StringUtils;

public class GameMode implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(StringUtils.avisovermelho + "§4Voce nao e um jogador!");
			return false;
		}
		Player p = (Player) sender;
		if (((commandLabel.equalsIgnoreCase("gamemode")) || (commandLabel.equalsIgnoreCase("gm")))
				&& (p.hasPermission(StringUtils.permissaoprefix + "comando.gm"))) {
			if (args.length == 0) {
				p.sendMessage(StringUtils.avisoverde + "§7Use: /" + commandLabel + " <jogador> <gamemode>");
			} else if (args.length == 1) {
				if ((args[0].equalsIgnoreCase("survival")) || (args[0].equalsIgnoreCase("0"))) {
					p.setGameMode(org.bukkit.GameMode.SURVIVAL);
					p.sendMessage(StringUtils.avisoverde + "§7Seu modo de jogo foi alterado para §6SURVIVAL");
				} else if ((args[0].equalsIgnoreCase("criativo")) || (args[0].equalsIgnoreCase("1"))) {
					p.setGameMode(org.bukkit.GameMode.CREATIVE);
					p.sendMessage(StringUtils.avisoverde + "§7Seu modo de jogo foi alterado para §6CRIATIVO");
				} else if ((args[0].equalsIgnoreCase("aventura")) || (args[0].equalsIgnoreCase("2"))) {
					p.setGameMode(org.bukkit.GameMode.ADVENTURE);
					p.sendMessage(StringUtils.avisoverde + "§7Seu modo de jogo foi alterado para §6AVENTURA");
				} else {
					p.sendMessage(StringUtils.avisoverde + "§7Use: /" + commandLabel + " <jogador> <gamemode>");
				}
			} else if (args.length == 2) {
				Player target = p.getServer().getPlayer(args[1]);
				if (target != null) {
					if ((args[0].equalsIgnoreCase("survival")) || (args[0].equalsIgnoreCase("0"))) {
						target.setGameMode(org.bukkit.GameMode.SURVIVAL);
						target.sendMessage("§7Seu modo de jogo foi alterado para §6SURVIVAL");
						p.sendMessage(StringUtils.avisoverde + "§7Modo de jogo do jogador §6" + target.getName()
								+ " §7alterado para §6SURVIVAL");
					} else if ((args[0].equalsIgnoreCase("criativo")) || (args[0].equalsIgnoreCase("1"))) {
						target.setGameMode(org.bukkit.GameMode.CREATIVE);
						target.sendMessage("§7Seu modo de jogo foi alterado para §6CRIATIVO");
						p.sendMessage(StringUtils.avisoverde + "§7Modo de jogo do jogador §6" + target.getName()
								+ " §7alterado para §6CRIATIVO");
					} else if ((args[0].equalsIgnoreCase("aventura")) || (args[0].equalsIgnoreCase("2"))) {
						target.setGameMode(org.bukkit.GameMode.ADVENTURE);
						target.sendMessage("§7Seu modo de jogo foi alterado para §6AVENTURA");
						p.sendMessage(StringUtils.avisoverde + "§7Modo de jogo do jogador §6" + target.getName()
								+ " §7alterado para §6AVENTURA");
					} else {
						p.sendMessage(StringUtils.avisoverde + "§7Use: /" + commandLabel + " <jogador> <gamemode>");
					}
				} else {
					p.sendMessage(StringUtils.avisoverde + "§7Jogador §6" + args[0] + " §7inexistente.");
				}
			}
		}
		return false;
	}
}
