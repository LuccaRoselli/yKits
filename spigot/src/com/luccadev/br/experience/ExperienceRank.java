package com.luccadev.br.experience;

import org.bukkit.entity.Player;

import com.luccadev.br.manager.StatsManager;

public enum ExperienceRank {

	ADVENTURER("Adventurer", 0, 1599, '-', '7'), 
	AVENGER("Avenger", 1600, 3199, '=', '7'), 
	DARK("Dark", 3200, 6399, '≡', 'e'), 
	DRAGON("Dragon", 6400, 12799, '☱', 'e'), 
	IMORTAL("Imortal", 12800, 18799, '☳', 'e'), 
	HERO("HERO", 18800, 24799, '☷', 'e'), 
	LEGENDARY("Legendary", 24800, 30799, '★', '6'), 
	TITAN("Titan", 30800, 36799, '⍣', '6'), 
	WARRRIOR("Warrior", 36800, 42800, '⧩', 'a');

	private String name;
	private int minimoxp;
	private int maximoxp;
	private char c;
	private char color;

	ExperienceRank(String name, int minimoxp, int maximoxp, char c, char color) {
		this.name = name;
		this.minimoxp = minimoxp;
		this.maximoxp = maximoxp;
		this.c = c;
		this.color = color;
	}

	public String getRankName() {
		return this.name;
	}
	
	public char getSymbol(){
		return this.c;
	}
	
	public char getSymbolColor(){
		return this.color;
	}

	public int getMinimalXP() {
		return this.minimoxp;
	}

	public int getMaximumXP() {
		return this.maximoxp;
	}

	public static Integer getPercentageToLevelUp(Player p) {
		int score = StatsManager.getXp(p) - getPlayerRank(p).getMinimalXP();
		int total = getPlayerRank(p).getMaximumXP() - getPlayerRank(p).getMinimalXP();
		return (score * 100 / total);
	}

	public static boolean isRank(Player p, ExperienceRank r) {
		if (StatsManager.getXp(p) >= r.getMinimalXP() && StatsManager.getXp(p) <= r.getMaximumXP())
			return true;
		return false;
	}

	public static ExperienceRank getPlayerRank(Player p) {
		if (isRank(p, ADVENTURER)) {
			return ExperienceRank.ADVENTURER;
		} else if (isRank(p, ExperienceRank.AVENGER)) {
			return ExperienceRank.AVENGER;
		} else if (isRank(p, ExperienceRank.DARK)) {
			return ExperienceRank.DARK;
		} else if (isRank(p, ExperienceRank.DRAGON)) {
			return ExperienceRank.DRAGON;
		} else if (isRank(p, ExperienceRank.IMORTAL)) {
			return ExperienceRank.IMORTAL;
		} else if (isRank(p, ExperienceRank.HERO)) {
			return ExperienceRank.HERO;
		} else if (isRank(p, ExperienceRank.LEGENDARY)) {
			return ExperienceRank.LEGENDARY;
		} else if (isRank(p, ExperienceRank.TITAN)) {
			return ExperienceRank.TITAN;
		} else if (isRank(p, ExperienceRank.WARRRIOR)) {
			return ExperienceRank.WARRRIOR;
		}
		return null;
	}

	public int getRemainingToNextLevel() {
		return (getMaximumXP() - getMinimalXP()) + 1;
	}

}
