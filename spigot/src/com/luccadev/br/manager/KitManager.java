package com.luccadev.br.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.luccadev.br.constructors.Kit;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class KitManager implements Listener {
	private KitManager() {
	}

	private static KitManager classe = new KitManager();

	public static KitManager getInstance() {
		return classe;
	}

	public static HashMap<String, Kit> kits = new HashMap<>();
	public static ArrayList<String> knames = new ArrayList<>();
	public static HashMap<Player, String> lastkit = new HashMap<Player, String>();
	public static HashMap<String, String> pkits = new HashMap<String, String>();
	
	public static ArrayList<Kit> getKits(){
		return Kit.kits;
	}
	
	public static ArrayList<Kit> phabs = new ArrayList<Kit>();
	
	public static ArrayList<Kit> getPlayerHabs(Player p){
		for (String s : knames){
			if (p.hasPermission("kit." + s)){
				phabs.add(getKitByName(s));
				return phabs;
			}
		}
		return phabs;
	}
	
	public static ArrayList<Kit> pnothabs = new ArrayList<Kit>();
	
	public static ArrayList<Kit> getPlayerNoHabs(Player p){
		for (String s : knames){
			if (!p.hasPermission("kit." + s)){
				pnothabs.add(getKitByName(s));
				return phabs;
			}
		}
		return pnothabs;
	}

	public static ItemStack getPote(int i) {
		ItemStack stack = new ItemStack(Material.BOWL, i);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§7Pote");
		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack getRedMush(int i) {
		ItemStack stack = new ItemStack(Material.RED_MUSHROOM, i);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§cCogu Vermelho");
		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack getBrownMush(int i) {
		ItemStack stack = new ItemStack(Material.BROWN_MUSHROOM, i);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§cCogu Marrom");
		stack.setItemMeta(meta);
		return stack;
	}

	public static ItemStack getSoup() {
		ItemStack sopa = Uteis.setItem(Material.MUSHROOM_SOUP, 1, "§6Sopa",
				Arrays.asList("§e3.5 §4❤"));
		return sopa;
	}

	public String getUsingKitName(Player p) {
		if (pkits.containsKey(p.getName())) {
			return pkits.get(p.getName());
		}
		return "Nenhum";
	}

	public String getLastKit(Player p) {
		return lastkit.get(p);
	}

	public static int totalOthersKit(Player p) {
		int i = 0;
		for (String kit : knames) {
			if (!p.hasPermission("kit." + kit)) {
				i++;
			}
		}
		return i;
	}

	public enum PageType {
		OWNED, OTHERS, ERROR;
	}

	public static void darSopas(Player p) {
		for (ItemStack i : p.getInventory().getContents()) {
			if (i == null) {
				p.getInventory().addItem(KitManager.getSoup());
			}
		}
	}

	public static int getTotalKits() {
		return knames.size();
	}

	public static Kit getKitByName(String name) {
		return kits.get(name.toLowerCase());
	}

	public static boolean kitExists(String kit) {
		return kits.containsKey(kit.toLowerCase());
	}

	public static Boolean usingKit(Player p) {
		if (KitManager.getInstance().getUsingKitName(p)
				.equalsIgnoreCase("Nenhum")) {
			return true;
		}
		return false;
	}

	public static int getTotalOwnedKit(Player p) {
		int i = 0;
		for (String kit : knames) {
			if (p.hasPermission("kit." + kit)) {
				i++;
			}
		}
		return i;
	}

	public static void selectKit(Player p, String kit) {
		if (usingKit(p)) {
			if (p.hasPermission(StringUtils.permissaoprefix + "kit." + kit)) {
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2, 2);
				p.sendMessage(StringUtils.getPrefix('a') + "Voce pegou o kit §3" + kit + "§b!");
				pkits.put(p.getName(), kit);
				getKitByName(kit).setKit(p);
				SCManager.send(p);
			} else {
				p.sendMessage(StringUtils.avisovermelho + "§bVoce não pode usar esse kit!");
			}
		} else {
			p.sendMessage(StringUtils.avisovermelho + "§bVoce não pode usar outro kit!");
		}
	}

	public static boolean hasLastKit(Player p) {
		if (!lastkit.get(p).equalsIgnoreCase("nenhum")) {
			return true;
		}
		return false;
	}

	public void zerarKit(Player p) {
		pkits.put(p.getName(), "Nenhum");
	}
}
