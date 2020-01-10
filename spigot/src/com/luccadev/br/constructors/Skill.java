package com.luccadev.br.constructors;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.luccadev.br.experience.ExperienceRank;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.storage.PlayerMySQL;
import com.luccadev.br.utils.InventoryUtils;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Skill {

	Kit k;
	int price;
	ArrayList<String> vantagem = new ArrayList<>();
	public static ArrayList<Skill> skills = new ArrayList<Skill>();
	int skillnumber;
	ExperienceRank rank;
	
	public Skill(Kit kit, int price, ArrayList<String> vantagem, int skillnumber, ExperienceRank r){
		this.k = kit;
		this.price = price;
		this.vantagem = vantagem;
		this.skillnumber = skillnumber;
		this.rank = r;
		skills.add(this);
	}
	
	public ItemStack getItemStack(){
		return Uteis.setItem(this.k.getIcon().getType(), 1, this.k.getColor() + this.k.getName(), null);
	}
	
	public int getPrice(){
		return this.price;
	}
	
	public ArrayList<String> getDesc(){
		return this.vantagem;
	}
	
	public int getSkillSlotNumber(){
		return this.skillnumber;
	}
	
	public ExperienceRank getRankRequired(){
		return this.rank;
	}
	
	public static ArrayList<Skill> getSkills(){
		return skills;
	}
	
	public Kit getKitOfSkill(){
		return this.k;
	}
	
	public static Skill getSkillByKitAndNumber(Kit k, int number){
		for (Skill s : getSkills()){
			if (s.getKitOfSkill() == k && s.getSkillSlotNumber() == number){
				return s;
			}
		}
		return null;
	}
	
	public static boolean hasSkill(Player p, Kit k){
		for (Skill s : PlayerMySQL.getSkills(p.getUniqueId())){
			if (PlayerMySQL.getSkls(p.getUniqueId()).equalsIgnoreCase("Nenhum")){
				return false;
			}
			if (s == null){
				return false;
			}
			if (s.getKitOfSkill() == k){
				return true;
			}
		}
		return false;
	}
	
	public static void buySkill(Player p, Kit skill){
		Skill s = getSkillByKitAndNumber(skill, 1);
		if (StatsManager.getBalance(p) >= s.getPrice()) {
			if (StatsManager.getXp(p) < s.getRankRequired().getMinimalXP()){
				p.sendMessage(StringUtils.avisovermelho + "§cVocê não possui rank suficiente para adquirir esta skill!");
				p.closeInventory();
				return;
			}
			if (hasSkill(p, skill)) {
				p.sendMessage(StringUtils.avisovermelho + "§cVocê já tem essa skill!");
				p.closeInventory();
				return;
			}
			if (StatsManager.money.get(p) >= s.getPrice()) {
				StatsManager.money.put(p, Integer
						.valueOf(((Integer) StatsManager.money
								.get(p)).intValue() - s.getPrice()));
			} else {
				StatsManager.money.put(p, Integer.valueOf(0));
			}
			try {
				ArrayList<Skill> skills = new ArrayList<>();
				if (PlayerMySQL.getSkls(p.getUniqueId()).equalsIgnoreCase("Nenhum")){
					PlayerMySQL.clearSkills(p.getUniqueId());
					skills.add(s);
				} else {
					for (Skill skl : PlayerMySQL.getSkills(p.getUniqueId())){
						skills.add(skl);
					}
					skills.add(s);
				}
				PlayerMySQL.setSkills(p.getUniqueId(), skills);
			} catch (Exception e) {
				System.out.println("BUG NAS SKILL");
				e.printStackTrace();
			}
			p.sendMessage(StringUtils.avisoverde + "Parabéns! Você adquiriu a skill §e1 §7do kit §e" + skill.getName());
			p.playSound(p.getLocation(), Sound.LEVEL_UP, 10.0F, 10.0F);
			SCManager.send(p);
		} else {
			p.sendMessage(StringUtils.coinprefix + "Você não tem coins suficiente!");
		}
	}
	
	public static Inventory getInventory(char type, Player p, String skillname){
		if (type == '1'){
			Inventory inv = Bukkit.createInventory(null, 27, "§eSkills");
			InventoryUtils.setHeader(inv, '4');
			for (Skill s : getSkills()){
			inv.addItem(s.getItemStack());
			}
			return inv;
		} else if (type == '2'){
			Inventory inv = Bukkit.createInventory(null, 27, "§eSkills - 2 - §6" + skillname);
			InventoryUtils.setHeader(inv, '5');
			inv.setItem(11, Uteis.setItem(Material.ARROW, 1, "§aSkill §7" + 1, null));
			inv.setItem(13, Uteis.setItem(Material.ARROW, 1, "§aSkill §7" + 2, null));
			inv.setItem(15, Uteis.setItem(Material.ARROW, 1, "§aSkill §7" + 3, null));
			inv.setItem(22, Uteis.setItem(Material.GOLD_NUGGET, 1, "§aMoedas §7► " + StatsManager.getBalance(p), null));
			return inv;
		} else if (type == '3'){
			Inventory inv = Bukkit.createInventory(null, 27, "§eSkills §7- 3 - §6" + skillname);
			InventoryUtils.setHeader(inv, '6');
			inv.setItem(10, Uteis.setItemComData(Material.WOOL, (byte)14, 1, "§cCancelar compra", null));
			inv.setItem(12, Uteis.setItem(Material.PAPER, 1, "§eInformações", getSkillByKitAndNumber(KitManager.getKitByName(inv.getTitle().split("§6")[1]), 1).getDesc()));
			inv.setItem(13, Uteis.setItem(Material.ARROW, 1, "§aSkill §71", null));
			inv.setItem(14, Uteis.setItem(Material.PAPER, 1, "§eCusto §7► " + getSkillByKitAndNumber(KitManager.getKitByName(inv.getTitle().split("§6")[1]), 1).getPrice() + " moedas", null));
			inv.setItem(16, Uteis.setItemComData(Material.WOOL, (byte)5, 1, "§aConfirmar compra", null));
			inv.setItem(22, Uteis.setItem(Material.GOLD_NUGGET, 1, "§aMoedas §7► " + StatsManager.getBalance(p), null));
			return inv;
		}
		return null;
	}
}
