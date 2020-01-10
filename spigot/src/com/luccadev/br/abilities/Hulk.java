package com.luccadev.br.abilities;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Hulk implements Listener {

	KitManager kit = KitManager.getInstance();
	public static ArrayList<Player> cooldown = new ArrayList<Player>();

	@EventHandler
	public void onThrow(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getPassenger() == null)
			return;
		if (p.getItemInHand().getType() != Material.AIR)
			return;
		if ((e.getAction() != Action.LEFT_CLICK_AIR) && (e.getAction() != Action.LEFT_CLICK_BLOCK))
			return;
		if (kit.getUsingKitName(p).equalsIgnoreCase("Hulk")) {
			Entity hulked = p.getPassenger();
			hulked.getVehicle().eject();
			Location loc = p.getLocation();
			loc.setY(loc.getY() + 2.0D);
			hulked.teleport(loc);
			hulked.setVelocity(p.getEyeLocation().getDirection().multiply(2.0D));
		}
	}

	@EventHandler
	public void PickUpPlayer(PlayerInteractEntityEvent e) {
		if ((e.getRightClicked() instanceof Player)) {
			if (!kit.getUsingKitName(e.getPlayer()).equalsIgnoreCase("Hulk"))
				return;
			final Player p = e.getPlayer();
			Player r = (Player) e.getRightClicked();
			if (p.getItemInHand().getType() != Material.AIR)
				return;
			if (p.getPassenger() != null)
				return;
			if (r.getPassenger() != null)
				return;
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
				return;
			}
			Uteis.addCooldown(p, 5);
			p.setPassenger(r);
			r.sendMessage(StringUtils.avisovermelho + "§7Você foi pego por um hulk, aperte §6Shift§7 para sair");
		}
	}
}
