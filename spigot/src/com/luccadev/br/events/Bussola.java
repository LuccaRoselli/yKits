package com.luccadev.br.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luccadev.br.commands.Admin;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;

public class Bussola implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerInteractEvent(PlayerInteractEvent event) {
		if (((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK))
				|| (event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
			if (event.getPlayer().getItemInHand().getType() == Material.COMPASS) {
				if (event.getPlayer().getGameMode() == GameMode.ADVENTURE) {
					Location pLoc = event.getPlayer().getLocation();
					double closestDist = 170.0D;
					Player closestPlayer = event.getPlayer();
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (p.getLocation().distance(pLoc) > 8) {
							if ((p.getLocation().distance(pLoc) < closestDist)
									&& (p.getName() != event.getPlayer().getName())) {
								closestDist = p.getLocation().distance(pLoc);
								closestPlayer = p;
							}
						}
					}
					if (KitManager.getInstance().getUsingKitName(closestPlayer).equalsIgnoreCase("nenhum")) {
						return;
					}
					if (Admin.emadmin.contains(closestPlayer.getPlayer())) {
						return;
					}
					if (event.getPlayer() == closestPlayer) {
						event.getPlayer().sendMessage(
								StringUtils.avisovermelho + "§cNao há nenhum player que a bússola possa apontar.");
						return;
					}
					event.getPlayer().setCompassTarget(closestPlayer.getLocation());
					event.getPlayer().sendMessage(
							StringUtils.avisoverde + "§eBússola apontando para §6" + closestPlayer.getName());
				}
			}
		}
	}

}
