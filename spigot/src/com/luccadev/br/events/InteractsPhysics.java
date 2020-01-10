package com.luccadev.br.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luccadev.br.commands.Admin;
import com.luccadev.br.constructors.PlayerProfile;
import com.luccadev.br.constructors.warp.WarpManager;
import com.luccadev.br.gui.NovaeraGui;
import com.luccadev.br.manager.CrateManager;
import com.luccadev.br.manager.KitManager;

public class InteractsPhysics implements Listener {

	@EventHandler
	public void onClicarNPC(PlayerInteractEntityEvent e){
		if (e.getRightClicked() instanceof Villager){
			System.out.println("trabson");
			Villager v = (Villager)e.getRightClicked();
			if (v.getProfession() == Villager.Profession.LIBRARIAN){
				e.setCancelled(true);
				CrateManager.principalGui(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onClicarChesst(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.BOOK_AND_QUILL) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")) {
					e.setCancelled(true);
					new PlayerProfile(p).openKitGui(1);
				}
			}
		}
	}
		
	@EventHandler
	public void onClicarBooq(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.GOLD_NUGGET) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")) {
					e.setCancelled(true);
					new PlayerProfile(p).openKitGui3(1);
				}
			}
		}
	}

	@EventHandler
	public void onClickarMagma(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.MAGMA_CREAM) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (!Admin.emadmin.contains(p)) {
					if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")) {
						e.setCancelled(true);
						NovaeraGui.openGeral(p);
					}
				}
			}
		}
	}

	@EventHandler
	public void onClickarCompasso(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.COMPASS) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")) {
					e.setCancelled(true);
					// TODO: warps
					WarpManager.openWarpInventory(p);
				}
			}
		}
	}

	@EventHandler
	public void onCLickarEnchanted(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.ENCHANTED_BOOK) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")) {
					e.setCancelled(true);
					new PlayerProfile(p).openSkillPrincipal();
				}
			}
		}
	}

	/*
	 * @EventHandler public void onClick(BlockPlaceEvent e){ if
	 * (e.getBlock().getType() == Material.DIAMOND_BLOCK){ Block b =
	 * e.getBlock(); final Location loc = new Location(b.getWorld(),
	 * b.getLocation().getX(), b.getLocation().getY() + 3.0,
	 * b.getLocation().getZ()); Effect.effect(e.getPlayer(), "WAKE", 0.0F, loc);
	 * } }
	 */

}
