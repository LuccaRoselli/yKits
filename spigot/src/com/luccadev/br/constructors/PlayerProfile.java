package com.luccadev.br.constructors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.Achievement.AchievementType;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.InventoryUtils;
import com.luccadev.br.utils.PlayerConfigFile;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class PlayerProfile {
	
	Player p;
	ArrayList<Kit> playerKits = new ArrayList<Kit>();
	ArrayList<ItemStack> itemKits = new ArrayList<ItemStack>();
	ArrayList<ItemStack> naoKits = new ArrayList<ItemStack>();
	
	public PlayerProfile(Player p){
		this.p = p;
	}
	
	public void loadKits(){
		for (Kit k : KitManager.getKits()){
			if (p.hasPermission(StringUtils.permissaoprefix + "kit." + k.getName().toLowerCase()) || p.isOp()){
				playerKits.add(k);
				itemKits.add(k.getIcon());
			} else {
				naoKits.add(k.getIcon());
				if (k.isBuyable()){
					ArrayList<String> desc = new ArrayList<>();
					for (String a : k.getDesc()){
						desc.add(a.replace("§e§o", "§7"));
					}
					desc.add("");
					desc.add("§6§lPREÇO: §e" + k.getPrice() + "§l£");
					lojaitems.add(Uteis.setItem(k.getMaterial(), 1, k.getColor() + k.getName() + "§7 [" + k.getPrice() + "]", desc));
				}
			}
		}
		Bukkit.getConsoleSender().sendMessage("[" + Main.getMe().getName() + "] §a§l» §7Foram carregados §e" + playerKits.size() + " §7kits para o jogador §e" + p.getName() + "§7.");
	}
	
	static HashMap<Integer, Inventory> pages = new HashMap<>();
	static HashMap<Player, Integer> actualpage = new HashMap<Player, Integer>();
	
	static HashMap<Integer, Inventory> pages2 = new HashMap<>();
	static HashMap<Player, Integer> actualpage2 = new HashMap<Player, Integer>();
	
	int pg = 1;
	int lastpage = 1;
	
	int pg2 = 1;
	int lastpage2 = 1;
	
	
	static HashMap<Integer, Inventory> pagesshop = new HashMap<>();
	static HashMap<Player, Integer> actualpageshop = new HashMap<Player, Integer>();
	static ArrayList<ItemStack> lojaitems = new ArrayList<>();
	
	int pgshop = 1;
	int lastpageshop = 1;
	
	@SuppressWarnings("deprecation")
	public void createGui(){
		lastpage = (int) Math.ceil(itemKits.size() / 21) + 1;
		Inventory inv = Bukkit.createInventory(null, 54, "§9Kits §7- §9Pagina §b1");
		InventoryUtils.setHeader(inv, 'a');
		inv.setItem(3, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§aKits que você possui", null));
		inv.setItem(4, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Kits que você §cnão §7possui", null));
		inv.setItem(5, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Loja de kits", null));
		if (itemKits.isEmpty()){
			for (int item = 0; item < inv.getSize(); item++) {
				while (inv.getItem(item) == null) {
					inv.setItem(item, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)14, 1, "§cVocê não possui nenhum kit", null));
				}
			}
		}
		if (pg + 1 <= lastpage){
	        inv.setItem(35, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§eProxima página §7»", null));
		}
		for (ItemStack item : itemKits){
			if (inv.firstEmpty() == -1){
				pages.put(pg, inv);
				pg++;
				inv = Bukkit.createInventory(null, 54, "§9Kits §7- §9Pagina §b" + pg);
				InventoryUtils.setHeader(inv, 'a');
				inv.setItem(3, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§aKits que você possui", null));
				inv.setItem(4, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Kits que você §cnão §7possui", null));
				inv.setItem(5, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Loja de kits", null));
				if (pg + 1 <= lastpage){
			        inv.setItem(35, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§eProxima página §7»", null));
				}
		        inv.setItem(27, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7« §ePágina anterior", null));
			}
			inv.addItem(item);
		}
		pages.put(pg, inv);
		
		lastpage2 = (int) Math.ceil(naoKits.size() / 21) + 1;
		Inventory inv2 = Bukkit.createInventory(null, 54, "Kits que você não possui §b1");
		InventoryUtils.setHeader(inv2, 'a');
		inv2.setItem(3, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Kits que você possui", null));
		inv2.setItem(4, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§aKits que você não §apossui", null));
		inv2.setItem(5, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Loja de kits", null));
		if (naoKits.isEmpty()){
			for (int item = 0; item < inv2.getSize(); item++) {
				while (inv2.getItem(item) == null) {
					inv2.setItem(item, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)5, 1, "§aVocê tem todos os kits!", null));
				}
			}
		}
		if (pg2 + 1 <= lastpage2){
	        inv2.setItem(35, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§eProxima página §7»", null));
		}
		for (ItemStack item2 : naoKits){
			if (inv2.firstEmpty() == -1){
				pages2.put(pg2, inv2);
				pg2++;
				inv2 = Bukkit.createInventory(null, 54, "Kits que você não possui §b" + pg2);
				InventoryUtils.setHeader(inv2, 'a');
				inv2.setItem(3, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Kits que você possui", null));
				inv2.setItem(4, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§aKits que você não §apossui", null));
				inv2.setItem(5, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Loja de kits", null));
				if (pg2 + 1 <= lastpage2){
			        inv2.setItem(35, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§eProxima página §7»", null));
				}
		        inv2.setItem(27, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7« §ePágina anterior", null));
			}
			inv2.addItem(item2);
		}
		pages2.put(pg2, inv2);
		
		lastpageshop = (int) Math.ceil(lojaitems.size() / 21) + 1;
		Inventory invshop = Bukkit.createInventory(null, 54, "§aLoja de kits §7- §9Pagina §b1");
		InventoryUtils.setHeader(invshop, 'a');
		invshop.setItem(3, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Kits que você possui", null));
		invshop.setItem(4, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Kits que você §cnão §7possui", null));
		invshop.setItem(5, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§aLoja de kits", null));
		if (lojaitems.isEmpty()){
			for (int item = 0; item < invshop.getSize(); item++) {
				while (invshop.getItem(item) == null) {
					invshop.setItem(item, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)5, 1, "§aVocê tem todos os kits!", null));
				}
			}
		}
		if (pgshop + 1 <= lastpageshop){
	        invshop.setItem(35, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§eProxima página §7»", null));
		}
		for (ItemStack item : lojaitems){
			if (invshop.firstEmpty() == -1){
				pagesshop.put(pgshop, invshop);
				pgshop++;
				invshop = Bukkit.createInventory(null, 54, "§aLoja de kits §7- §9Pagina §b" + pgshop);
				InventoryUtils.setHeader(invshop, 'a');
				invshop.setItem(3, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Kits que você possui", null));
				invshop.setItem(4, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7Kits que você §cnão §7possui", null));
				invshop.setItem(5, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§aLoja de kits", null));
				if (pgshop + 1 <= lastpageshop){
			        invshop.setItem(35, Uteis.setItemComData(Material.getMaterial(351),(byte)10, 1, "§eProxima página §7»", null));
				}
		        invshop.setItem(27, Uteis.setItemComData(Material.getMaterial(351),(byte)8, 1, "§7« §ePágina anterior", null));
			}
			invshop.addItem(item);
		}
		pagesshop.put(pgshop, invshop);
	}
	
	public void openKitGui(Integer pagenumber){
		Inventory page = pages.get(pagenumber);
		if (page != null){
			InventoryUtils.removerVidro(page);
			p.openInventory(page);
			
		}else{
			p.sendMessage(StringUtils.avisovermelho + "Esta página não existe!");
		}
	}
	
	public void openKitGui2(Integer pagenumber){
		Inventory page = pages2.get(pagenumber);
		if (page != null){
			InventoryUtils.removerVidro(page);
			p.openInventory(page);
			
		}else{
			p.sendMessage(StringUtils.avisovermelho + "Esta página não existe!");
		}
	}
	
	public void openKitGui3(Integer pagenumber){
		Inventory page = pagesshop.get(pagenumber);
		if (page != null){
			InventoryUtils.removerVidro(page);
			p.openInventory(page);
			
		}else{
			p.sendMessage(StringUtils.avisovermelho + "Esta página não existe!");
		}
	}
	
	public void completeAchievement(AchievementType t){
		try {
			PlayerConfigFile.getPlayerConfig44(p).set("Achievement." + t.toString() + ".completou", Boolean.valueOf(true));
			PlayerConfigFile.savePlayerConfig(p);
		} catch (Exception e) {
			System.out.println("BUG NOS ACHIEVEMENT");
		}
	}
	
	public enum GuiTYPE{
		TEM, NAOTEM
	}
	
	
	static HashMap<Player, Inventory> invskilll = new HashMap<>();
	Inventory invskill;
	
	public void createInventorySkill(){
		invskill = Bukkit.createInventory(null, 27, "§eSkills");
		InventoryUtils.setHeader(invskill, '4');
		System.out.println(Skill.getSkills());
		for (Skill s : Skill.getSkills()){
			ItemStack kit;
			if (Skill.hasSkill(p, KitManager.getKitByName(s.getKitOfSkill().getName())))
				kit = Uteis.setItem(s.getKitOfSkill().getMaterial(), 1, "§6" + s.getKitOfSkill().getName(), Arrays.asList("§2§l► §aVocê já possui essa skill!"));
			else
				kit = Uteis.setItem(s.getKitOfSkill().getMaterial(), 1, "§6" + s.getKitOfSkill().getName(), Arrays.asList("§4§l► §cVocê ainda não possui esta skill!"));
			invskill.addItem(kit);
		}
		invskilll.put(p, invskill);
	}
	
	public void openSkillPrincipal(){
		p.openInventory(invskilll.get(p));
	}

}
