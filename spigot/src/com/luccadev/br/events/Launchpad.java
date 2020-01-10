package com.luccadev.br.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class Launchpad implements Listener {

	private List<UUID> noFallDamage;

	public Launchpad() {
		this.noFallDamage = new ArrayList<UUID>();
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		Location standBlock = p.getLocation().clone().add(0.0D, -1.0E-5D, 0.0D);
		if (standBlock.getBlock().getType() == Material.ENDER_PORTAL_FRAME) {
			double xvel = 0.0D;
			double yvel = 3.0D;
			double zvel = 0.0D;
			p.setVelocity(new Vector(xvel, yvel, zvel));
			p.playSound(p.getLocation(), Sound.HORSE_JUMP, 10.0F, 1.0F);
			if (!this.noFallDamage.contains(p.getUniqueId())) {
				this.noFallDamage.add(p.getUniqueId());
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
			return;
		}
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) event.getEntity();
		if (this.noFallDamage.contains(p.getUniqueId())) {
			event.setDamage(0.0D);
			this.noFallDamage.remove(p.getUniqueId());
		}
	}

}
