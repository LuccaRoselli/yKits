package com.luccadev.br.utils.scroller;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.luccadev.br.Main;

public class ServerScroller {

	/*
	public static List<String> efeito = Arrays.asList(new String[] { "§f§lKITPVP", "§f§lKITPVP", "§f§lKITPV§ka",
			"§f§lKITP§kaa", "§f§lKIT§kaaa", "§f§lKI§kaaaa", "§f§lK§kaaaaa", "§f§l§kaaaaaa", "§f§lK§ka      ", "§f§lKI§ka     ",
			"§f§lKIT§ka    ", "§f§lKITP§ka   ", "§f§lKITPV§ka  ", "§f§lKITPVP", });
	public static List<String> efeito2 = Arrays.asList(new String[] { "§f§lKITPVP", "§f§lKITPVP", "§f§lKITPV§ka",
			"§f§lKITP§kaa", "§f§lKIT§kaaa", "§f§lKI§kaaaa", "§f§lK§kaaaaa", "§f§l§kaaaaaa", "§f§lK§ka      ", "§f§lKI§ka     ",
			"§f§lKIT§ka    ", "§f§lKITP§ka   ", "§f§lKITPV§ka  ", "§f§lKITPVP", });
	*/
	public static List<String> efeito = Arrays.asList(new String[] { "§f§lLITHEMC", "§f§lLITHEMC", "§f§lLITHEM§ka",
			"§f§lLITHE§kaa", "§f§lLITH§kaaa", "§f§lLIT§kaaaa", "§f§lLI§kaaaaa", "§f§lL§kaaaaaa", "§f§l§kaaaaaaa", "§f§lL§ka      ", "§f§lLI§ka     ",
			"§f§lLIT§ka    ", "§f§lLITH§ka   ", "§f§lLITHE§ka  ", "§f§lLITHEM§ka", "§f§lLITHEMC", });
	public static List<String> efeito2 = Arrays.asList(new String[] { "§e§lLITHEMC", "§e§lLITHEMC", "§e§lLITHEM§ka",
			"§e§lLITHE§kaa", "§e§lLITH§kaaa", "§e§lLIT§kaaaa", "§e§lLI§kaaaaa", "§e§lL§kaaaaaa", "§e§l§kaaaaaaa", "§e§lL§ka      ", "§e§lLI§ka     ",
			"§e§lLIT§ka    ", "§e§lLITH§ka   ", "§e§lLITHE§ka  ", "§e§lLITHEM§ka", "§e§lLITHEMC", });

	static Iterator<String> ilista = efeito.iterator();
	static Iterator<String> ilista2 = efeito2.iterator();

	static int taskid;
	public static String lastname = "";

	static int vez = 1;

	public static void setup() {
		for (Player pls : Bukkit.getOnlinePlayers()) {
			if (pls.getScoreboard() != null && pls.isOnline()) {
				Scoreboard score = pls.getScoreboard();
				Objective stats = score.getObjective(DisplaySlot.SIDEBAR);
				if (vez == 2) {
					if (stats != null) {
						stats.setDisplayName("§f§lKITPVP");
					}
				} else {
					if (stats != null) {
						stats.setDisplayName("§f§lKITPVP");
					}
				}
			}
		}
		new BukkitRunnable() {
			public void run() {
				new BukkitRunnable() {
					public void run() {
						if (vez == 2) {
							if (!ilista.hasNext()) {
								ilista = efeito.iterator();
								for (Player pls : Bukkit.getOnlinePlayers()) {
									if (pls.getScoreboard() != null) {
										Scoreboard score = pls.getScoreboard();
										Objective stats = score.getObjective(DisplaySlot.SIDEBAR);
										if (stats != null) {
											stats.setDisplayName("§f§lKITPVP");
										}
									}
								}
								vez = 1;
								setup();
								cancel();
								return;
							}
							String next = ilista.next();
							lastname = next;
							for (Player pls : Bukkit.getOnlinePlayers()) {
								Scoreboard score = pls.getScoreboard();
								if (score != null) {
									if (!score.getObjectives().isEmpty()) {
										Objective stats = score.getObjective(DisplaySlot.SIDEBAR);
										if (stats != null) {
											stats.setDisplayName(next);
										}
									}
								}
							}
						} else {
							if (!ilista2.hasNext()) {
								ilista2 = efeito2.iterator();
								for (Player pls : Bukkit.getOnlinePlayers()) {
									if (pls.getScoreboard() != null) {
										Scoreboard score = pls.getScoreboard();
										Objective stats = score.getObjective(DisplaySlot.SIDEBAR);
										if (stats != null) {
											stats.setDisplayName("§f§lKITPVP");
										}
									}
								}
								vez = 2;
								setup();
								cancel();
								return;
							}
							String next = ilista2.next();
							lastname = next;
							for (Player pls : Bukkit.getOnlinePlayers()) {
								Scoreboard score = pls.getScoreboard();
								if (score != null) {
									if (!score.getObjectives().isEmpty()) {
										Objective stats = score.getObjective(DisplaySlot.SIDEBAR);
										if (stats != null) {
											stats.setDisplayName(next);
										}
									}
								}
							}
						}
					}
				}.runTaskTimer(Main.getPlugin(Main.class), 0, 12);
				cancel();
			}
		}.runTaskLater(Main.getPlugin(Main.class), 13);
	}

}
