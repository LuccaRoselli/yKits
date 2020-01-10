package com.luccadev.br.events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.warp.Warp;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class SpawnProtection implements Listener {
	private static ArrayList<String> hasSP = new ArrayList<String>();

	public static void addProtection(Player p) {
		if (!hasProtection(p))
			hasSP.add(p.getName());
	}

	public static boolean hasProtection(Player p) {
		return hasSP.contains(p.getName());
	}

	public static void removeProtection(Player p) {
		if (hasProtection(p))
			hasSP.remove(p.getName());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (hasProtection(e.getPlayer())) {
			removeProtection(e.getPlayer());
		}
		hasSP.add(e.getPlayer().getName());
		e.getPlayer().sendMessage(StringUtils.PROTECAO + "Voce ganhou a proteção do Spawn!");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDeath(PlayerRespawnEvent e) {
		if (Warp.isInWarp(e.getPlayer())) return;
		if (hasProtection(e.getPlayer())) {
			removeProtection(e.getPlayer());
		}
		hasSP.add(e.getPlayer().getName());
		e.getPlayer().sendMessage(StringUtils.PROTECAO + "Voce ganhou a proteção do Spawn!");
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (((e.getEntity() instanceof Player)) && (hasProtection((Player) e.getEntity()))) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (((e.getDamager() instanceof Player)) && (!e.isCancelled())
				&& (hasProtection((Player) e.getDamager()) && !hasProtection((Player) e.getEntity()))) {
			((Player) e.getDamager()).sendMessage(StringUtils.PROTECAO + "Voce perdeu a proteção do Spawn!");
			removeProtection((Player) e.getDamager());
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if ((hasProtection(e.getPlayer())) && ((!Uteis.isInsideCuboID(e.getPlayer(),
				Uteis.getLocation(Main.getMe().getConfig().getString("Spawn.pos1")),
				Uteis.getLocation(Main.getMe().getConfig().getString("Spawn.pos2")))))) {
			removeProtection(e.getPlayer());
			e.getPlayer().sendMessage(StringUtils.PROTECAO + "Voce perdeu a proteção do Spawn!");
		}
	}
}
