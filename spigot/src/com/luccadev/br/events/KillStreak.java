package com.luccadev.br.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.luccadev.br.constructors.Achievement;
import com.luccadev.br.constructors.Achievement.AchievementType;
import com.luccadev.br.manager.SCManager;

public class KillStreak {

	public static HashMap<Player, Integer> ks = new HashMap<Player, Integer>();

	public static boolean playerExists(Player username) {
		return ks.containsKey(username);
	}

	public static int getKillStreak(Player username) {
		if (!playerExists(username)) {
			return 0;
		}
		return ks.get(username).intValue();
	}

	public static void resetKillStreak(Player username) {
		if (playerExists(username)) {
			return;
		}

		ks.put(username, 0);
	}

	public static void addKill(Player username) {
		if (ks.containsKey(username)) {
			ks.put(username, ks.get(username).intValue() + 1);
			if (getKillStreak(username) == 5) {
				broadcast(username);
			} else if (getKillStreak(username) == 10) {
				broadcast(username);
				if (!Achievement.getAchievement(AchievementType.KILLSTREAK)
						.isCompleted(username)) {
					Achievement.getAchievement(AchievementType.KILLSTREAK)
							.finalize(username);
				}
			} else if (getKillStreak(username) == 15) {
				broadcast(username);
			} else if (getKillStreak(username) == 20) {
				broadcast(username);
			} else if (getKillStreak(username) == 25) {
				broadcast(username);
			} else if (getKillStreak(username) == 35) {
				broadcast(username);
			} else if (getKillStreak(username) == 50) {
				broadcast(username);
			} else if (getKillStreak(username) == 60) {
				broadcast(username);
			} else if (getKillStreak(username) == 70) {
				broadcast(username);
			} else if (getKillStreak(username) == 80) {
				broadcast(username);
			} else if (getKillStreak(username) == 90) {
				broadcast(username);
			} else if (getKillStreak(username) == 100) {
				broadcast(username);
			} else if (getKillStreak(username) == 120) {
				broadcast(username);
			} else if (getKillStreak(username) == 150) {
				broadcast(username);
			} else if (getKillStreak(username) == 200) {
				broadcast(username);
			}
			SCManager.send(username);
		} else {
			ks.put(username, 1);
			SCManager.send(username);
		}
	}

	public static void broadcast(Player username) {
		Bukkit.broadcastMessage("§b§l* §6" + username.getName()
				+ " §7está em um killstreak de " + ChatColor.GOLD
				+ getKillStreak(username) + ChatColor.GRAY + "!");
	}
}
