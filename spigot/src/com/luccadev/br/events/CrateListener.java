package com.luccadev.br.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.luccadev.br.Main;
import com.luccadev.br.manager.CrateManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.storage.PlayerMySQL;
import com.luccadev.br.utils.StringUtils;

public class CrateListener implements Listener{
	
	@EventHandler
	public void onClose(final InventoryCloseEvent e){
		if (e.getInventory().getName().contains("Abrindo caixa")){
			if (CrateManager.runnable.containsKey(e.getPlayer())){
				new BukkitRunnable() {
					
					@Override
					public void run() {
						e.getPlayer().openInventory(e.getInventory());
					}
				}.runTaskLater(Main.getMe(), 1L);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
			if (e.getClickedBlock().getType() == Material.ENDER_CHEST){
				e.setCancelled(true);
				CrateManager.principalGui(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onQUit(PlayerQuitEvent e){
		if (CrateManager.runnable.containsKey(e.getPlayer())){
			CrateManager.runnable.get(e.getPlayer()).cancel();
			CrateManager.runnable.remove(e.getPlayer());
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(final InventoryClickEvent e) {
		if (e.getInventory().getName().contains("Abrindo caixa")
				&& (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
		}
		if (e.getInventory().getName().equalsIgnoreCase("§6Caixas ⇢ Loja")
				&& (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)){
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.CHEST){
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§a1 Caixa")){
					if(StatsManager.getBalance(p) >= 1500){
						if (StatsManager.money.get(p) >= 1500) {
							StatsManager.money.put(p, Integer
									.valueOf(((Integer) StatsManager.money
											.get(p)).intValue() - 1500));
						} else {
							StatsManager.money.put(p, Integer.valueOf(0));
						}
						p.sendMessage(StringUtils.avisoverde + "Você comprou 1 caixa!");
						p.sendMessage(StringUtils.avisovermelho + "§7-§c" + "1.500" + " §7Coin's.");
						PlayerMySQL.addCrates(p.getUniqueId(), 1);
						StatsManager.caixas.put(p, PlayerMySQL.getCrates(p.getUniqueId()));
						p.closeInventory();
						SCManager.send(p);
    					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
					}else{
						p.sendMessage(StringUtils.coinprefix + "Você não tem dinheiro suficiente!");
					}
				}
				
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§a5 Caixas")){
					if(StatsManager.getBalance(p) >= 7500){
						if (StatsManager.money.get(p) >= 7500) {
							StatsManager.money.put(p, Integer
									.valueOf(((Integer) StatsManager.money
											.get(p)).intValue() - 7500));
						} else {
							StatsManager.money.put(p, Integer.valueOf(0));
						}
						p.sendMessage(StringUtils.avisoverde + "Você comprou 5 caixas!");
						p.sendMessage(StringUtils.avisovermelho + "§7-§c" + "7.500" + " §7Coin's.");
						PlayerMySQL.addCrates(p.getUniqueId(), 5);
						StatsManager.caixas.put(p, PlayerMySQL.getCrates(p.getUniqueId()));
						p.closeInventory();
						SCManager.send(p);
    					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
					}else{
						p.sendMessage(StringUtils.coinprefix + "Você não tem dinheiro suficiente!");
					}
				}
				
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§a10 Caixas")){
					if(StatsManager.getBalance(p) >= 10000){
						if (StatsManager.money.get(p) >= 10000) {
							StatsManager.money.put(p, Integer
									.valueOf(((Integer) StatsManager.money
											.get(p)).intValue() - 10000));
						} else {
							StatsManager.money.put(p, Integer.valueOf(0));
						}
						p.sendMessage(StringUtils.avisoverde + "Você comprou 10 caixas!");
						p.sendMessage(StringUtils.avisovermelho + "§7-§c" + "10.000" + " §7Coin's.");
						PlayerMySQL.addCrates(p.getUniqueId(), 10);
						StatsManager.caixas.put(p, PlayerMySQL.getCrates(p.getUniqueId()));
						p.closeInventory();
						SCManager.send(p);
    					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
					}else{
						p.sendMessage(StringUtils.coinprefix + "Você não tem dinheiro suficiente!");
					}
				}
			}
		}
		if (e.getInventory().getName().equalsIgnoreCase("§6Caixas")
				&& (e.getCurrentItem() != null)
				&& (e.getCurrentItem().getTypeId() != 0)){
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.CHEST){
				CrateManager.openInv(p);
			}
			if (e.getCurrentItem().getType() == Material.GOLD_NUGGET){
				CrateManager.comprarCaixas(p);
			}
		}
	}

}
