package com.luccadev.br.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;
import com.nametagedit.plugin.NametagEdit;

public class Tag implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("tag")) {
			if (args.length == 0) {
				p.sendMessage(StringUtils.avisovermelho + "Uso correto: §7/tag <tag>");
			}
			if ((sender instanceof ConsoleCommandSender)) {
				System.out.println("Console nao pode usar esse comando");
				return true;
			}
			if (args.length == 1){
				switch (args[0]) {
				case "dev":
					if (!p.getName().equalsIgnoreCase("LuccaDEV")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add -kitpvp.tag.diretor");
					NametagEdit.getApi().setPrefix(p.getName(), "§e§lDEV §e");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + p.getName());
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " group set diretor");
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §eDEVELOPER§7!");
					break;
				case "diretor":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.diretor")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§4§lDIRETOR §4");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §4DIRETOR§7!");
					break;
				case "coordenador":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.coordenador")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§9§lCOORD §9");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §9COORDENADOR§7!");
					break;
				case "admin":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.admin")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§c§lADM §c");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §eADMIN§7!");
					break;
				case "mod":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.mod")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§5§lMOD §5");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §eMOD§7!");
					break;
				case "trial":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.trial")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§5§lTRIAL §d");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §5TRIAL§7!");
					break;
				case "youtuber":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.youtuber")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§b§lYT §b");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §eYOUTUBER§7!");
					break;
				case "ultimate":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.ultimate")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§d§lULTIMATE §d");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §dULTIMATE§7!");
					break;
				case "ultra":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.ultra")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§a§lULTRA §a");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §aULTRA§7!");
					break;
				case "pro":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.pro")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§6§lPRO §6");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §ePRO§7!");
					break;
				case "maker":
					if (!p.hasPermission(StringUtils.permissaoprefix + "tag.maker")){
						p.sendMessage(StringUtils.avisovermelho + "Você não tem permissão para usar esta tag!");
						return true;
					}
					NametagEdit.getApi().setPrefix(p.getName(), "§e§lMAKER §e");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §eMAKER§7!");
					break;
				case "normal":
					NametagEdit.getApi().setPrefix(p.getName(), "§9§lBETA §7");
					
					p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + Uteis.getShortStr(p.getName()));
					p.sendMessage(StringUtils.avisoverde + "Você está utilizando a tag §9BETA§7!");
					break;
				default:
					p.sendMessage(StringUtils.avisovermelho + "Esta tag não existe!");
					break;
				}
			}
		}
		return false;
	}
}