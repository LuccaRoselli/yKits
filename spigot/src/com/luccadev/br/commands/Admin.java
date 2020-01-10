package com.luccadev.br.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Admin implements CommandExecutor, Listener {

	public static ArrayList<Player> emadmin = new ArrayList<Player>();
	public static HashMap<String, ItemStack[]> saveinv = new HashMap<>();
	public static HashMap<String, ItemStack[]> armadura = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("admin")
				&& (p.hasPermission("kitpvp.comando.admin") || (p.hasPermission("kitpvp.staff")))) {
			if (!emadmin.contains(p)) {
				saveinv.put(p.getName(), p.getInventory().getContents());
				armadura.put(p.getName(), p.getInventory().getArmorContents());
				emadmin.add(p);
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.getInventory().setItem(0,
						Uteis.setItem(Material.BLAZE_ROD, 1, "§6Info", Arrays.asList("§7Click-Direito")));
				p.getInventory().setItem(7, Uteis.setItemComData(Material.SKULL_ITEM, (byte) 3, 1,
						"§6Status dos Players", Arrays.asList("§7Veja as informações básicas do player!")));
				p.getInventory().setItem(1, Uteis.setItem(Material.MUSHROOM_SOUP, 1, "§6Auto-Soup",
						Arrays.asList("§7Veja se um player é autosoup.")));
				p.getInventory().setItem(8, Uteis.setItem(Material.MAGMA_CREAM, 1, "§6FastAdmin",
						Arrays.asList("§7Saia e entre do /admin rapidamente.")));
				p.getInventory().setItem(3, Uteis.setItem(Material.IRON_BOOTS, 1, "§6NoFall",
						Arrays.asList("§7Teste se um player é NoFall.")));
				p.getInventory().setItem(5, Uteis.setItem(Material.IRON_FENCE, 1, "§6Prison",
						Arrays.asList("§7Feche o player em uma cadeira de bedrocks!")));
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage(StringUtils.avisoverde + "Você entrou no modo admin.");
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
				for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
					pl.hidePlayer(p);
				}
			} else {
				if (emadmin.contains(p)) {
					emadmin.remove(p);
					p.getInventory().clear();
					p.getInventory().setContents(saveinv.get(p.getName()));
					p.getInventory().setArmorContents(armadura.get(p.getName()));
					saveinv.remove(p.getName());
					armadura.remove(p.getName());
					p.setGameMode(GameMode.SURVIVAL);
					p.sendMessage(StringUtils.avisoverde + "Você saiu do modo admin");
					p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
					for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
						pl.showPlayer(p);
					}
				}
			}
		}
		return false;
	}
}
