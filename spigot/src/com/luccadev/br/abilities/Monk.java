package com.luccadev.br.abilities;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Monk implements Listener {
	public String monkado = StringUtils.avisovermelho + "Voce foi monkado!";
	public boolean bol = true;

	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent e) {
		if ((e.getRightClicked() instanceof Player)) {
			Player monk = e.getPlayer();
			Player c = (Player) e.getRightClicked();
			if ((monk.getItemInHand().getType() == Material.BLAZE_ROD)
					&& (KitManager.getInstance().getUsingKitName(monk).equalsIgnoreCase("Monk"))) {
				if (Uteis.hasCooldown(monk)) {
					Uteis.sendCooldownMessage(monk);
					return;
				}
				PlayerInventory inv = c.getInventory();
				int slot = new Random().nextInt(this.bol ? 36 : 9);
				ItemStack rd = inv.getItemInHand();
				if (rd == null) {
					rd = new ItemStack(Material.AIR);
				}
				ItemStack rr = inv.getItem(slot);
				if (rr == null) {
					rr = new ItemStack(Material.AIR);
				}
				inv.setItemInHand(rr);
				inv.setItem(slot, rd);
				c.sendMessage(this.monkado);
				monk.sendMessage(StringUtils.avisoverde + "Você monkou §6" + c.getName() + "§7!");

				Uteis.addCooldown(monk, 15);
			}
		}
	}
}
