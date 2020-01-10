package com.luccadev.br.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.PlayerConfigFile;
import com.luccadev.br.utils.Uteis;

public class Quit implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		/*
		 * StatsManager.setarBalance(p, StatsManager.getBalance(p));
		 * StatsManager.setKills(p, StatsManager.getKills(p));
		 * StatsManager.setDeaths(p, StatsManager.getDeaths(p));
		 * StatsManager.setXp(p, StatsManager.getXp(p));
		 */
		PlayerConfigFile.savePlayerStats(p);
	}

	@EventHandler
	public void quteis(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		p.getActivePotionEffects().clear();
		if (!KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nenhum")) {
			KitManager.getInstance().zerarKit(p);
		}
		/*
		 * if (Gui1v1.randomQueue.contains(p)) { Gui1v1.randomQueue.remove(p); }
		 */
		Uteis.tpSpawn(p);
		e.setQuitMessage(null);

	}

}
