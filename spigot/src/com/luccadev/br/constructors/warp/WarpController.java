package com.luccadev.br.constructors.warp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.luccadev.br.Main;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class WarpController {

	Main main;
	Listener listener;

	public WarpController(Main m) {
		this.main = m;
		this.listener = (Listener) new Listener() {

			@EventHandler
			public void onQuit(PlayerQuitEvent e) {
				Player p = e.getPlayer();
				if (Warp.isInWarp(p)) {
					Warp w = Warp.getPlayerWarp(p);
					w.removePlayer(p);
				}
			}

			
			@EventHandler(priority = EventPriority.HIGHEST)
			public void onRespawn(final PlayerRespawnEvent e) {
				new BukkitRunnable() {

					@Override
					public void run() {
						Player p = e.getPlayer();
						if (Warp.isInWarp(p)) {
							Warp w = Warp.getPlayerWarp(p);
							w.teleportPlayer(p, w.getType(), true);
							p.sendMessage(StringUtils.avisoverde + "Você renasceu na §e" + w.getWarpName().toUpperCase() + "§7!");
							p.sendMessage(StringUtils.avisoverde + "Caso deseje voltar ao spawn, digite §a/spawn§7.");
						} else {
							Uteis.setSpawnItems(p);
							Uteis.tpSpawn(p);
						}
					}
				}.runTaskLater(Main.getMe(), 5L);
			}

		};
		this.main.getServer().getPluginManager().registerEvents(this.listener, (Plugin) this.main);
		WarpManager.addWarps();
		Bukkit.getConsoleSender().sendMessage("§6[WARP CONTROLLER] §7WarpController registrado.");
		Bukkit.getConsoleSender().sendMessage("§6[WARP CONTROLLER] §7Warps registradas: §b" + Warp.getAllWarps().size());
	}

}
