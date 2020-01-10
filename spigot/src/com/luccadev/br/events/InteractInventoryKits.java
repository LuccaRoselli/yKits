package com.luccadev.br.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.luccadev.br.constructors.Kit;
import com.luccadev.br.constructors.PlayerProfile;
import com.luccadev.br.manager.KitManager;

public class InteractInventoryKits implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if ((e.getInventory().getTitle().contains("§9Kits §7- §9Pagina §b"))
				&& (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7« §ePágina anterior")){
				int pageatual = Integer.valueOf(e.getInventory().getTitle().split("§b")[1]);
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui(pageatual - 1);
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eProxima página §7»")){
				PlayerProfile pp = new PlayerProfile(p);
				int pageatual = Integer.valueOf(e.getInventory().getTitle().split("§b")[1]);
				pp.openKitGui(pageatual + 1);
				return;
			}
			if (e.getCurrentItem().getType() != Material.getMaterial(351) && e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE){
				Kit kitclicked = KitManager.getKitByName(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().split("§e")[1]));
				if (kitclicked != null){
					KitManager.selectKit(p, kitclicked.getName());
				}
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Kits que você §cnão §7possui")){
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui2(1);
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Loja de kits")){
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui3(1);
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick2(InventoryClickEvent e) {
		if ((e.getInventory().getTitle().contains("Kits que você não possui §b"))
				&& (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7« §ePágina anterior")){
				int pageatual = Integer.valueOf(e.getInventory().getTitle().split("§b")[1]);
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui2(pageatual - 1);
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eProxima página §7»")){
				PlayerProfile pp = new PlayerProfile(p);
				int pageatual = Integer.valueOf(e.getInventory().getTitle().split("§b")[1]);
				pp.openKitGui2(pageatual + 1);
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Kits que você possui")){
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui(1);
				return;
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Loja de kits")){
				PlayerProfile pp = new PlayerProfile(p);
				pp.openKitGui3(1);
				return;
			}
		}
	}

}
