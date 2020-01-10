package com.luccadev.br.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.luccadev.br.experience.ExperienceRank;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.InventoryUtils;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class NovaeraGui {
	
	public static void openGeral(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, StringUtils.getPrefix('b'));
		InventoryUtils.setHeader(inv, '2');
		inv.setItem(11, Uteis.setItem(Material.ENCHANTED_BOOK, 1, "§eFormulário", Arrays.asList("§7Clique para ter acesso", "§7ao link do formulário para staff!")));
		inv.setItem(13, Uteis.setItem(Material.MAGMA_CREAM, 1, StringUtils.getPrefix('b'), null));
		inv.setItem(15, Uteis.createHead(1, "§ePerfil", Arrays.asList("§7Clique para ver perfil (status)!"), p.getName()));
		p.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	public static void openPerfil(Player p){
		Inventory inv = Bukkit.createInventory(null, 27,  StringUtils.getPrefix('b') + " §7- Perfil");
		InventoryUtils.setHeader(inv, '3');
		inv.setItem(10, Uteis.setItemComData(Material.getMaterial(31), (byte)2, 1, "§eRank §7► " + StatsManager.getRank(p), null));
		String xpnext = null;
		if (ExperienceRank.getPlayerRank(p) != ExperienceRank.WARRRIOR)
			xpnext = ExperienceRank.getPercentageToLevelUp(p) + "%"; 
		else 
			xpnext = "-";
		inv.setItem(13, Uteis.setItem(Material.REDSTONE, 1, "§eStatus", Arrays.asList("§b► §7Kills: §b" + StatsManager.getKills(p), "§b► §7Deaths: §b" + StatsManager.getDeaths(p), "§b► §7XP's: §b" + StatsManager.getXp(p), "§b► §7Level UP's: §b" + xpnext, "§b► §7KDR: §b" + StatsManager.getKDR(p))));
		inv.setItem(16, Uteis.setItem(Material.GOLD_NUGGET, 1, "§eMoedas §7► " + StatsManager.getBalance(p), null));
		p.openInventory(inv);
	}
	

}
