package com.luccadev.br.abilities;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import com.luccadev.br.manager.KitManager;

public class Fisherman implements Listener {

	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		Entity caught = event.getCaught();
		Block block = event.getHook().getLocation().getBlock();
		if ((caught != null)
				&& (caught != block)
				&& (KitManager.getInstance().getUsingKitName(event.getPlayer())
						.equalsIgnoreCase("Fisherman"))) {
			caught.teleport(event.getPlayer().getLocation());
		}
	}
}
