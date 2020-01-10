package com.luccadev.br.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.luccadev.br.manager.SCManager;
import com.luccadev.br.utils.StringUtils;

public class ScoreEnable implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Voce nao e um jogador!");
			return false;
		}
		Player p = (Player) sender;
		if (commandLabel.equalsIgnoreCase("score")) {
			if (SCManager.scb.get(p).booleanValue() == true) {
				SCManager.scb.put(p, false);
				SCManager.send(p);
				p.sendMessage(
						StringUtils.avisovermelho + "�cVoc� desativou o scoreboard! Agora ele voc� n�o o ver� mais.");
			} else {
				SCManager.scb.put(p, true);
				SCManager.send(p);
				p.sendMessage(StringUtils.avisoverde + "�aVoc� ativou o scoreboard! Agora ele aparecer� normalmente.");
			}
		}
		return false;
	}

}