package com.luccadev.br.constructors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.luccadev.br.events.KillStreak;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.PlayerConfigFile;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Achievement {
	
	String nome;
	AchievementType type;
	Integer target;
	String[] descricao;
	static ArrayList<Achievement> achievements = new ArrayList<Achievement>();
	
	public Achievement(String name, AchievementType tipo, Integer numero, String... desc){
		this.nome = name;
		this.type = tipo;
		this.target = numero;
		this.descricao = desc;
		achievements.add(this);
	}
	
	public AchievementType getType(){
		return this.type;
	}
	
	public List<String> getDesc(){
		return Arrays.asList(this.descricao);
	}
	
	public Integer getAchievementTarget(){
		return this.target;
	}
	
	public static Achievement getAchievement(AchievementType t){
		for (Achievement a : achievements){
			if (a.getType() == t){
				return a;
			}
		}
		return null;
	}
	
	public static ArrayList<Achievement> getAchievements(){
		return achievements;
	}
	
	public boolean isCompleted(Player p){
		return YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getBoolean("Achievement." + type.toString() + ".completou");
	}
	
	public void finalize(Player p){
		new PlayerProfile(p).completeAchievement(this.type);
		p.sendMessage(StringUtils.avisoverde + "Parabéns! Você desbloqueou um achievement!");
		p.sendMessage(StringUtils.avisoverde + "Você ganhou §e1 §7caixas §e§npro§7 como recompensa!");
	}
	
	public static void guiAchievements(Player p){
		Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, StringUtils.getPrefix('b') + " Achievements");
		float percentagekills = (float)StatsManager.getKills(p)*100/getAchievement(AchievementType.KILLS).getAchievementTarget();
		String percent = percentagekills + "§7%";
		ItemStack kills;
		if (!getAchievement(AchievementType.KILLS).isCompleted(p)){
			kills = Uteis.setItem(Material.STONE_SWORD, 1, "§bAchievement de Kills", Arrays.asList("§7Este achievements, consiste em", "§7você eliminar §e" + getAchievement(AchievementType.KILLS).getAchievementTarget() + " §7inimigos!", "", "§7Progresso: §e" + percent));
			inv.setItem(0, kills);
		} else {
			kills = Uteis.setItem(Material.STONE_SWORD, 1, "§bAchievement de Kills", Arrays.asList("§7Este achievements, consiste em", "§7você eliminar §e" + getAchievement(AchievementType.KILLS).getAchievementTarget() + " §7inimigos!", "", "§aVocê já completou este achievement!"));
			inv.setItem(0, kills);
		}
		float percentagestreak = (float)KillStreak.getKillStreak(p)*100/getAchievement(AchievementType.KILLSTREAK).getAchievementTarget();
		String percent2 = percentagestreak + "§7%";
		ItemStack ks;
		if (!getAchievement(AchievementType.KILLSTREAK).isCompleted(p)){
			ks = Uteis.setItem(Material.DIAMOND_SWORD, 1, "§bAchievement de KillsStreak", Arrays.asList("§7Este achievements, consiste em", "§7você conseguir um killstreak de §e" + getAchievement(AchievementType.KILLSTREAK).getAchievementTarget() + " §7!", "", "§7Progresso: §e" + percent2));
			inv.setItem(2, ks);
		} else {
			ks = Uteis.setItem(Material.DIAMOND_SWORD, 1, "§bAchievement de KillsStreak", Arrays.asList("§7Este achievements, consiste em", "§7você conseguir um killstreak de §e" + getAchievement(AchievementType.KILLSTREAK).getAchievementTarget() + " §7!", "", "§aVocê já completou este achievement!"));
			inv.setItem(2, ks);
		}
		float percentagedeaths = (float)StatsManager.getDeaths(p)*100/getAchievement(AchievementType.DEATHS).getAchievementTarget();
		ItemStack deaths;
		if (!getAchievement(AchievementType.DEATHS).isCompleted(p)){
			deaths = Uteis.setItem(Material.SKULL_ITEM, 1, "§bAchievement de Deaths", Arrays.asList("§7Este achievements, consiste em", "§7você morrer §e" + getAchievement(AchievementType.DEATHS).getAchievementTarget() + " §7vezes!", "", "§7Progresso: §e" + percentagedeaths + "%"));
			inv.setItem(1, deaths);
		} else {
			deaths = Uteis.setItem(Material.SKULL_ITEM, 1, "§bAchievement de Deaths", Arrays.asList("§7Este achievements, consiste em", "§7você morrer §e" + getAchievement(AchievementType.DEATHS).getAchievementTarget() + " §7vezes!", "", "§aVocê já completou este achievement!"));
			inv.setItem(1, deaths);
		}
		
		p.openInventory(inv);
	}
	
    public enum AchievementType{
    	KILLSTREAK, KILLS, DEATHS;
    }
}


