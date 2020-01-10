package com.luccadev.br.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class WarpSet implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Voce nao e um jogador!");
			return false;
		}
		Player p = (Player) sender;
		if (p.hasPermission(StringUtils.permissaoprefix + "comando.setwarp")) {
			if (args.length > 0 && args.length < 2 && args.length == 1) {
				String warpname = args[0].toLowerCase();
				Uteis.setLocation("Warps." + warpname, p.getLocation());
				p.sendMessage(StringUtils.avisoverde + "Warp §e" + warpname.toUpperCase() + " §7setada!");
			}
		}
		return false;
	}

}