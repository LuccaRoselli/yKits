package com.luccadev.br.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.Achievement;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.storage.PlayerMySQL;

public class PlayerConfigFile {

	public static File getPlayerConfig(Player p) {
		File playerFile = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
				+ p.getUniqueId().toString() + ".yml");
		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return playerFile;
	}

	public static ArrayList<String> kills = new ArrayList<String>();

	public static void printAll() {
		File folder = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				File playerFile = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
						+ listOfFiles[i].getName());
				FileConfiguration playerFileConfig = YamlConfiguration.loadConfiguration(playerFile);
				String a = String.valueOf(playerFileConfig.getConfigurationSection("Stats").getKeys(false))
						.replace("[", "").replace("]", "");
				Integer kills3 = playerFileConfig.getInt("Stats." + a + ".Kills");
				if (kills3 > 0) {
					kills.add(String.valueOf(a + "-" + kills3));
				}
			} else if (listOfFiles[i].isDirectory()) {
			}
		}
	}

	public static FileConfiguration getPlayerConfig44(Player p) {
		File playerFile = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
				+ p.getUniqueId().toString() + ".yml");
		return YamlConfiguration.loadConfiguration(playerFile);
	}

	public static void playerConfig(Player p) {
		File playersDir = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB");
		if (!playersDir.exists()) {
			playersDir.mkdir();
		}
		File playerFile = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
				+ p.getUniqueId().toString() + ".yml");
		FileConfiguration playerFileConfig = YamlConfiguration.loadConfiguration(playerFile);
		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();
				playerFileConfig.set("Stats." + p.getName() + ".Balance", PlayerMySQL.getBalance(p.getUniqueId()));
				playerFileConfig.set("Stats." + p.getName() + ".Kills", PlayerMySQL.getKills(p.getUniqueId()));
				playerFileConfig.set("Stats." + p.getName() + ".Deaths", PlayerMySQL.getDeaths(p.getUniqueId()));
				playerFileConfig.set("Stats." + p.getName() + ".Xp", PlayerMySQL.getXp(p.getUniqueId()));
				for (Achievement a : Achievement.getAchievements()) {
					playerFileConfig.set("Achievement." + a.getType().toString() + ".completou", false);
				}
				playerFileConfig.set("mysql", false);
				playerFileConfig.save(playerFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void savePlayerConfig(Player p) {
		File playerFile = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
				+ p.getUniqueId().toString() + ".yml");
		FileConfiguration playerFileConfig = YamlConfiguration.loadConfiguration(playerFile);
		try {
			playerFileConfig.save(playerFile);
		} catch (Exception e) {
			System.out.println("Não foi possivel salvar os stats do " + p.getName());
		}
	}

	public static void savePlayerStats(Player p) {
		File playerFile = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
				+ p.getUniqueId().toString() + ".yml");
		FileConfiguration playerFileConfig = YamlConfiguration.loadConfiguration(playerFile);
		if (Main.jasalvou == false){
			boolean salvar = false;
			try {
				if (StatsManager.getBalance(p) != 0
						|| StatsManager.moneyatual.get(p) != StatsManager.getBalance(p)) {
					playerFileConfig.set("Stats." + p.getName() + ".Balance", StatsManager.getBalance(p));
					salvar = true;
				}
				if (StatsManager.getKills(p) != 0
						|| StatsManager.killsatual.get(p) != StatsManager.getKills(p)) {
					playerFileConfig.set("Stats." + p.getName() + ".Kills", StatsManager.getKills(p));
					salvar = true;
				}
				if (StatsManager.getDeaths(p) != 0
						|| StatsManager.deathsatual.get(p) != StatsManager.getDeaths(p)) {
					playerFileConfig.set("Stats." + p.getName() + ".Deaths", StatsManager.getDeaths(p));
					salvar = true;
				}
				if (StatsManager.getXp(p) != 0
						|| StatsManager.xpatual.get(p) != StatsManager.getXp(p)) {
					playerFileConfig.set("Stats." + p.getName() + ".Xp", StatsManager.getXp(p));
					salvar = true;
				}
				if (salvar) {
					playerFileConfig.save(playerFile);
				} else {
					System.out
							.println("Os status de " + p.getName() + " não forão salvos, pois não houve alteração ou é 0!");
				}
			} catch (Exception e) {
				System.out.println("Não foi possivel salvar os stats do " + p.getName());
				e.printStackTrace();
			}
		} else {
		}
	}
	
	public static void saveAllOnMySQL(){
		File folder = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				File playerFile1 = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
						+ listOfFiles[i].getName());
				FileConfiguration playerFileConfig1 = YamlConfiguration.loadConfiguration(playerFile1);
				String a = String.valueOf(playerFileConfig1.getConfigurationSection("Stats").getKeys(false))
						.replace("[", "").replace("]", "");
				Integer kills = playerFileConfig1.getInt("Stats." + a + ".Kills");
				Integer deaths = playerFileConfig1.getInt("Stats." + a + ".Deaths");
				Integer xp = playerFileConfig1.getInt("Stats." + a + ".Xp");
				Integer balance = playerFileConfig1.getInt("Stats." + a + ".Balance");
				PlayerMySQL.setBalance(UUID.fromString(listOfFiles[i].getName().split(".yml")[0]), balance);
				PlayerMySQL.setKills(UUID.fromString(listOfFiles[i].getName().split(".yml")[0]), kills);
				PlayerMySQL.setDeaths(UUID.fromString(listOfFiles[i].getName().split(".yml")[0]), deaths);
				PlayerMySQL.setXp(UUID.fromString(listOfFiles[i].getName().split(".yml")[0]), xp);
				listOfFiles[i].delete();
				System.out.println("Status do jogador " + UUID.fromString(listOfFiles[i].getName().split(".yml")[0]) + " salvo no mysql!");
				
			} else if (listOfFiles[i].isDirectory()) {
			}
		}
	}
}
