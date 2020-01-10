package com.luccadev.br.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.luccadev.br.Main;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Reportar implements CommandExecutor, Listener {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof Player)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission(StringUtils.permissaoprefix + "comando.report")) {
					Main.getPlugin(Main.class).getReportManager().open(p);
				} else {
					p.sendMessage("§9§LREPORT §FEspecifique o §3§ljogador §fque deseja denunciar.");
				}
			} else if (args.length == 2) {
				Player t = Bukkit.getPlayer(args[0]);
				if (t != null) {
					if (t.getUniqueId() != p.getUniqueId()) {
						if (Uteis.hasCooldown(p)){
							p.sendMessage("§9§lREPORT §fAguarde §3" + Uteis.getCooldown(p) + " §fsegundos para reportar alguém!");
						} else {
							Main.getPlugin(Main.class).getReportManager().report(p, t, args[1]);
							p.sendMessage("§9§lREPORT §fO report do jogador §3§l" + t.getName() + "§f foi enviado!");
							Uteis.addCooldown(p, 2 * 60);
						}
					} else {
						p.sendMessage("§9§lREPORT §fVoc§ não pode §3§lREPORTAR§f você mesmo!");
					}
				} else {
					p.sendMessage("§9§lREPORT §fJogador não §3§lencontrado§f!");
				}
			} else {
				p.sendMessage("§9§LREPORT §FEspecifique o §3§ljogador e motivo §fque deseja denunciar.");
			}
		} else {
			sender.sendMessage("§4§lREPORT §fComando §c§lapenas§f para jogadores.");
		}
		return false;
	} 
}
