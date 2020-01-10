package com.luccadev.br.abilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Ninja implements Listener {
	public static HashMap<String, String> tpTo = new HashMap<String, String>();
	public static HashMap<String, Long> cooldown = new HashMap<String, Long>();

	@EventHandler
	public void ninja(PlayerToggleSneakEvent e) {
		if ((!e.isSneaking()) && (KitManager.getInstance().getUsingKitName(e.getPlayer()).equals("Ninja"))
				&& (tpTo.containsKey(e.getPlayer().getName()))) {
			if (!Uteis.hasCooldown(e.getPlayer())) {
				Player to = Bukkit.getPlayer((String) tpTo.get(e.getPlayer().getName()));
				if ((to.isOnline()) && (e.getPlayer().getLocation().distance(to.getLocation()) <= 100.0D)) {
					e.getPlayer().teleport(to.getLocation());
					Uteis.addCooldown(e.getPlayer(), 5);
				} else {
					e.getPlayer().sendMessage(StringUtils.avisovermelho + "O jogador esta muito longe");
				}
			} else {
				Uteis.sendCooldownMessage(e.getPlayer());
			}
		}
	}

	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (((e.getDamager() instanceof Player)) && ((e.getEntity() instanceof Player))) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			if (KitManager.getInstance().getUsingKitName(d).equals("Ninja")) {
				tpTo.put(d.getName(), p.getName());
			}
		}
	}

	@EventHandler
	public void death(PlayerDeathEvent e) {
		if ((e.getEntity().getKiller() instanceof Player)) {
			Player p = e.getEntity().getKiller();
			if (tpTo.containsKey(p.getName())) {
				tpTo.remove(p.getName());
			}
		}
	}

	@EventHandler
	public void quitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (tpTo.containsKey(p.getName())) {
			tpTo.remove(p.getName());
		}
	}

	@EventHandler
	public void death2(PlayerDeathEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = e.getEntity();
			if (tpTo.containsKey(p.getName())) {
				tpTo.remove(p.getName());
			}
		}
	}
}
