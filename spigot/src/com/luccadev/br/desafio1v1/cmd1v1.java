package com.luccadev.br.desafio1v1;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.luccadev.br.Main;
import com.luccadev.br.events.UmVsUm;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class cmd1v1 implements CommandExecutor {

	public static void v1(Player p) {
		UmVsUm.naArena.add(p);

		ItemStack blaze = UmVsUm.item(Material.BLAZE_ROD, 1, "§e§l1v1 §r§7- §a1v1 Stick", true, new String[] { "" });

		ItemStack PartidaRapida = new ItemStack(Material.INK_SACK, 1, (short) 8);
		ItemMeta PartidaRapidaMeta = PartidaRapida.getItemMeta();
		PartidaRapidaMeta.setDisplayName("§e§l1v1 §r§7- §aPartida Rapida");
		PartidaRapida.setItemMeta(PartidaRapidaMeta);

		UmVsUm.clear(p);
		p.getInventory().setItem(0, blaze);
		p.getInventory().setItem(8, PartidaRapida);

		p.teleport(Uteis.getLocation(Main.getMe().getConfig().getString("1V1.Spawn1v1")));

		p.setGameMode(GameMode.SURVIVAL);

		p.sendMessage(StringUtils.avisoverde + "§aVoce entrou na arena 1v1!");

		p.updateInventory();

		KitManager.getInstance().zerarKit(p);
		System.out.println(UmVsUm.naArena.contains(p));
	}

	public static HashMap<Player, Integer> task = new HashMap<Player, Integer>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		final Player p = (Player) sender;
		if (label.equalsIgnoreCase("1v1")) {
			if (!UmVsUm.naArena.contains(p)) {
				if (KitManager.usingKit(p)) {
					if (p.hasPermission(StringUtils.permissaoprefix + ".kitpvp.cooldown")) {
						v1(p);
					} else if (task.containsKey(p)) {
						p.sendMessage(StringUtils.avisoverde + "§cVoce ja esta sendo teleportado!");
					} else {
						p.sendMessage(StringUtils.avisoverde + "§3Teleportando em 5 segundos! §cNao se mova!");

						task.put(p, Integer.valueOf(
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
									public void run() {
										task.remove(p);

										v1(p);
									}
								}, 100L)));
					}
				} else {
					p.sendMessage(StringUtils.avisovermelho + "Você não pode ir à 1v1 utilizando um kit!");
				}
			} else {
				UmVsUm.naLuta.remove(p);
				UmVsUm.naArena.remove(p);
				KitManager.getInstance().zerarKit(p);
				Uteis.tpSpawn(p);
				Uteis.setSpawnItems(p);
				// Warps.on1v1.remove(p);
				if (UmVsUm.Random.contains(p)) {
					UmVsUm.Random.remove(p);
				}
				p.sendMessage(StringUtils.avisovermelho + "Você saiu da arena 1v1");
			}
		}
		if (label.equalsIgnoreCase("pos1")) {
			if (p.hasPermission("kitpvp.arenas")) {
				Uteis.setLocation("1V1.Pos1", p.getLocation());
				p.sendMessage(StringUtils.avisoverde + "§9Posicao 1 Marcada!");
			}
		}
		if (label.equalsIgnoreCase("pos2")) {
			if (p.hasPermission("kitpvp.arenas")) {
				Uteis.setLocation("1V1.Pos2", p.getLocation());
				p.sendMessage(StringUtils.avisoverde + "§9Posicao 2 Marcada!");
			}
		}
		if (label.equalsIgnoreCase("set1v1")) {
			if (p.hasPermission("kitpvp.arenas")) {
				Uteis.setLocation("1V1.Spawn1v1", p.getLocation());
				p.sendMessage(StringUtils.avisoverde + "§91v1 Marcado!");
			}
		}
		return true;
	}

}
