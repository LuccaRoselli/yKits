package com.luccadev.br.manager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.eZScoreboard;
import com.luccadev.br.events.KillStreak;
import com.luccadev.br.experience.ExperienceRank;
import com.luccadev.br.utils.StringUtils;

public class SCManager {

	public static Scoreboard sboard;
	public static HashMap<Player, Boolean> scb = new HashMap<Player, Boolean>();

	static char spade = '❤';
	public static String heart = Character.toString(spade);

	public static void send(Player p) {
		if (SCManager.scb.get(p).booleanValue()) {
			final eZScoreboard sb = new eZScoreboard(StringUtils.getPrefix('b'));
			if (p.hasPermission("kitpvp.staff") || p.hasPermission("kitpvp.comando.report")) {
				sb.add("§4");
				if (Main.getPlugin(Main.class).getReportManager().getReports().size() >= 1) {
					sb.add(" §7Reports: §a" + Main.getPlugin(Main.class).getReportManager().getReports().size());
				} else {
					sb.add(" §7Reports: §c" + Main.getPlugin(Main.class).getReportManager().getReports().size());
				}
			}

			sb.add("§a");
			sb.add(" §7Coins §a" + StatsManager.getBalance(p));
			sb.add(" §7Level(XP) §a" + StatsManager.getXp(p));
			if (ExperienceRank.getPlayerRank(p) != ExperienceRank.WARRRIOR)
				sb.add(" §7Next Level(%) §a" + ExperienceRank.getPercentageToLevelUp(p) + "%");
			else 
				sb.add(" §7Next Level(%) §a§m-");
			sb.add("§b");
			sb.add(" §7Rank §e" + StatsManager.getRank(p));
			sb.add(" §7Kit §e" + KitManager.getInstance().getUsingKitName(p));
			sb.add("§c");
			sb.add(" §7Kills §b" + StatsManager.getKills(p));
			sb.add(" §7Deaths §b" + StatsManager.getDeaths(p));
			sb.add(" §7KillStreak §b" + KillStreak.getKillStreak(p));
			sb.add("§3");
			sb.add("§7/score");

			Objective objetodacabeca = sb.getScoreboard().registerNewObjective("showhealth", "health");
			objetodacabeca.setDisplaySlot(DisplaySlot.BELOW_NAME);
			objetodacabeca.setDisplayName("§4" + heart);
			sb.build();
			sb.send(new Player[] { p });
			sboard = sb.getScoreboard();
		} else {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
	}

}
