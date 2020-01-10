package com.luccadev.br.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.luccadev.br.constructors.PlayerProfile;
import com.luccadev.br.constructors.Skill;
import com.luccadev.br.constructors.warp.Warp;
import com.luccadev.br.gui.NovaeraGui;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.StringUtils;

public class InventoryInteractsGeral implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if ((e.getInventory().getTitle().contains(StringUtils.getPrefix('b'))) && (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
				p.sendMessage(StringUtils.getPrefix('a') + "Faça seu formulário em: www.google.com");
				p.closeInventory();
			}
			if (e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getDurability() == 3) {
				p.closeInventory();
				NovaeraGui.openPerfil(p);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick2(InventoryClickEvent e) {
		if ((e.getInventory().getTitle().contains(StringUtils.getPrefix('b') + " §7- Perfil")) && (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickSkills(InventoryClickEvent e) {
		if ((e.getInventory().getTitle().equals("§eSkills")) && (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			String kitname = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
			Player p = (Player) e.getWhoClicked();
			p.openInventory(Skill.getInventory('2', p, kitname));
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickSkills2(InventoryClickEvent e) {
		if ((e.getInventory().getTitle().contains("§eSkills - 2")) && (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			String title = e.getCurrentItem().getItemMeta().getDisplayName();
			Player p = (Player) e.getWhoClicked();
			if (title.contains("1"))
				p.openInventory(Skill.getInventory('3', p, e.getInventory().getTitle().split("§6")[1]));
			if (title.contains("2"))
				p.sendMessage(StringUtils.avisovermelho + "Em construção.");
			if (title.contains("3"))
				p.sendMessage(StringUtils.avisovermelho + "Em construção.");
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickSkills3(InventoryClickEvent e) {
		if ((e.getInventory().getTitle().contains("§eSkills §7- 3")) && (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem().getType() == Material.WOOL && e.getCurrentItem().getDurability() == 14)
				p.closeInventory();
			if (e.getCurrentItem().getType() == Material.WOOL && e.getCurrentItem().getDurability() == 5) {
				Skill.buySkill(p, KitManager.getKitByName(e.getInventory().getTitle().split("§6")[1]));
				p.closeInventory();
				new PlayerProfile(p).createInventorySkill();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickWarps(InventoryClickEvent e) {
		if ((e.getInventory().getTitle().contains("§6Warps - Página §b")) && (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem().getType() != Material.getMaterial(351)){
				Warp clicked = Warp.getWarpByName(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
				clicked.teleportPlayer(p, clicked.getType(), false);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCLick5(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if ((e.getInventory().getTitle().contains("§aLoja de kits")) && (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7« §ePágina anterior")){
				int pageatual = Integer.valueOf(e.getInventory().getTitle().split("§b")[1]);
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui3(pageatual - 1);
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eProxima página §7»")){
				PlayerProfile pp = new PlayerProfile(p);
				int pageatual = Integer.valueOf(e.getInventory().getTitle().split("§b")[1]);
				pp.openKitGui3(pageatual + 1);
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Kits que você possui")){
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui(1);
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Kits que você §cnão §7possui")){
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui2(1);
				return;
			}
			if (e.getCurrentItem().getType() != Material.getMaterial(351) && e.getCurrentItem().getType() != Material.getMaterial(160)){
				String s = e.getCurrentItem().getItemMeta().getDisplayName();
				int startIndex = s.indexOf('[');
				int endIndex = s.indexOf(']');

				int valor = Integer.valueOf(s.substring(startIndex + 1, endIndex));
				if (p.hasPermission(StringUtils.permissaoprefix + "kit." + ChatColor.stripColor(s.split(" ")[0]))){
					p.sendMessage(StringUtils.avisovermelho + "Você já possui esse kit!");
					p.closeInventory();
					return;
				}
					
				if (StatsManager.getBalance(p) >= valor) {
					if (StatsManager.money.get(p) >= valor) {
						StatsManager.money.put(p, Integer.valueOf(((Integer) StatsManager.money.get(p)).intValue() - valor));
					} else {
						StatsManager.money.put(p, Integer.valueOf(0));
					}
					p.sendMessage(StringUtils.avisoverde + "Você comprou o kit §a" + ChatColor.stripColor(s.split(" ")[0]) + "§7!");
					p.sendMessage(StringUtils.coinprefix + "§4§l-§c" + KitManager.getKitByName(ChatColor.stripColor(s.split(" ")[0])).getPrice());
					SCManager.send(p);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 10.0F, 10.0F);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add " + StringUtils.permissaoprefix + "kit." + ChatColor.stripColor(s.split(" ")[0]));
				} else {
					p.sendMessage(StringUtils.coinprefix + "Você não tem dinheiro suficiente!");
					p.closeInventory();
				}
			}
		}
	}

}
