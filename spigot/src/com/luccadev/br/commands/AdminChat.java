package com.luccadev.br.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.luccadev.br.utils.StringUtils;

public class AdminChat implements CommandExecutor {
	public static List<Player> adminchat = new ArrayList<Player>();

	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
		Player p = (Player) sender;
		if ((label.equalsIgnoreCase("s")) && (p.hasPermission(StringUtils.permissaoprefix + "chat.admin"))) {
			if (args.length == 0) {
				p.sendMessage(StringUtils.avisoverde + "Use /s <on/off>");
			} else if (args[0].equalsIgnoreCase("on")) {
				if (adminchat.contains(p)) {
					p.sendMessage(StringUtils.avisoverde + "Voce ja esta no ChatStaff");
				} else {
					adminchat.add(p);
					p.sendMessage(StringUtils.avisoverde
							+ "Você está no chat admin, tudo o que você falar ira aparecer apenas para players com a permissão");
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (adminchat.contains(all)) {
							all.sendMessage("§a§lStaffChat §a» " + p.getDisplayName() + " §7entrou no ChatStaff!");
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("off")) {
				if (!adminchat.contains(p)) {
					p.sendMessage(StringUtils.avisoverde + "Voce nao esta no adminchat, para entrar digite /s on");
				} else {
					adminchat.remove(p);
					p.sendMessage(StringUtils.avisoverde
							+ "Voce saiu do chat dos admins! Suas mensagens serão enviadas normalmente!");
					for (Player all : Bukkit.getOnlinePlayers()) {
						if (adminchat.contains(all)) {
							all.sendMessage("§a§lStaffChat §a» " + p.getDisplayName() + " §7saiu do ChatStaff!");
						}
					}
				}
			}
		}
		return false;
	}
}
