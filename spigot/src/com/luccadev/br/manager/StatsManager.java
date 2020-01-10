package com.luccadev.br.manager;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.Clan;
import com.luccadev.br.experience.ExperienceRank;
import com.luccadev.br.storage.PlayerMySQL;
import com.luccadev.br.utils.PlayerConfigFile;

public class StatsManager {

	public static HashMap<Player, Integer> kills;
	public static HashMap<Player, Integer> deaths;
	public static HashMap<Player, Integer> money;
	public static HashMap<Player, Integer> xp;
	public static HashMap<Player, Clan> playerclan;
	public static HashMap<Player, Integer> caixas;

	public static HashMap<Player, Integer> killsatual;
	public static HashMap<Player, Integer> deathsatual;
	public static HashMap<Player, Integer> moneyatual;
	public static HashMap<Player, Integer> xpatual;
	
	static {
		kills = new HashMap<Player, Integer>();
		deaths = new HashMap<Player, Integer>();
		money = new HashMap<Player, Integer>();
		xp = new HashMap<Player, Integer>();
		caixas = new HashMap<Player, Integer>();
		playerclan = new HashMap<Player, Clan>();
		
		killsatual = new HashMap<Player, Integer>();
		deathsatual = new HashMap<Player, Integer>();
		moneyatual = new HashMap<Player, Integer>();
		xpatual = new HashMap<Player, Integer>();
	}

	public static double getKDR(Player p) {
		if ((getKills(p) > 0) && (getDeaths(p) > 0)) {
			return getKills(p) / getDeaths(p);
		}
		if ((getKills(p) > 0) && (getDeaths(p) <= 0)) {
			return getKills(p);
		}
		if ((getKills(p) == 0) && (getDeaths(p) == 0)) {
			return 0.0D;
		}
		if ((getKills(p) == 0) && (getKills(p) > 0)) {
			return 0.0D;
		}
		return 0.0D;
	}

	/*
	 * public static void updateStats(final Player p) { kills.put(p,
	 * Files.getClasse().getstats().getInt("PreMySQL." + p.getName() +
	 * ".Kills")); deaths.put(p, Files.getClasse().getstats().getInt("PreMySQL."
	 * + p.getName() + ".Deaths")); xp.put(p,
	 * Files.getClasse().getstats().getInt("PreMySQL." + p.getName() + ".Xp"));
	 * money.put(p, Files.getClasse().getstats().getInt("PreMySQL." +
	 * p.getName() + ".Balance")); }
	 */
	public static void updateStats(final Player p) {
		if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getBoolean("mysql") == true){
	    	if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Kills") != 0){
	    		StatsManager.kills.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Kills"));
	    		StatsManager.killsatual.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Kills"));
	    	} else {
	    		StatsManager.kills.put(p, 0);
	    		StatsManager.killsatual.put(p, 0);
	    	}
	    	if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Deaths") != 0){
	    		StatsManager.deaths.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Deaths"));
	    		StatsManager.deathsatual.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Deaths"));
	    	} else {
	    		StatsManager.deaths.put(p, 0);
	    		StatsManager.deathsatual.put(p, 0);
	    	}
	    	if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Xp") != 0){
	    		StatsManager.xp.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Xp"));
	    		StatsManager.xpatual.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Xp"));
	    	} else {
	    		StatsManager.xp.put(p, 0);
	    		StatsManager.xpatual.put(p, 0);
	    	}
	    	if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Balance") != 0){
	    	StatsManager.money.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Balance"));
	    	StatsManager.moneyatual.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Balance"));
	    	} else {
	    		StatsManager.money.put(p, 0);
	    		StatsManager.moneyatual.put(p, 0);
	    	}
	    	StatsManager.caixas.put(p, PlayerMySQL.getCrates(p.getUniqueId()));
		} else {
			if (PlayerMySQL.getKills(p.getUniqueId()) != 0) {
				StatsManager.kills.put(p, PlayerMySQL.getKills(p.getUniqueId()));
				StatsManager.killsatual.put(p, PlayerMySQL.getKills(p.getUniqueId()));
			} else {
				StatsManager.kills.put(p, 0);
				StatsManager.killsatual.put(p, 0);
			}
			if (PlayerMySQL.getDeaths(p.getUniqueId()) != 0) {
				StatsManager.deaths.put(p, PlayerMySQL.getDeaths(p.getUniqueId()));
				StatsManager.deathsatual.put(p, PlayerMySQL.getDeaths(p.getUniqueId()));
			} else {
				StatsManager.deaths.put(p, 0);
				StatsManager.deathsatual.put(p, 0);
			}
			if (PlayerMySQL.getXp(p.getUniqueId()) != 0) {
				StatsManager.xp.put(p, PlayerMySQL.getXp(p.getUniqueId()));
				StatsManager.xpatual.put(p, PlayerMySQL.getXp(p.getUniqueId()));
			} else {
				StatsManager.xp.put(p, 0);
				StatsManager.xpatual.put(p, 0);
			}
			if (PlayerMySQL.getBalance(p.getUniqueId()) != 0) {
				StatsManager.money.put(p, PlayerMySQL.getBalance(p.getUniqueId()));
				StatsManager.moneyatual.put(p, PlayerMySQL.getBalance(p.getUniqueId()));
			} else {
				StatsManager.money.put(p, 0);
				StatsManager.moneyatual.put(p, 0);
			}
	    	StatsManager.caixas.put(p, PlayerMySQL.getCrates(p.getUniqueId()));
			File playerFile = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
					+ p.getUniqueId().toString() + ".yml");
			FileConfiguration playerFileConfig = YamlConfiguration.loadConfiguration(playerFile);
			try {
				playerFileConfig.set("mysql", true);
				playerFileConfig.save(playerFile);
			} catch (Exception e) {
			}
		}
	}
	
	public static void updateStats(final UUID uuid) {
			Player p = Bukkit.getPlayer(uuid);
		if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getBoolean("mysql") == true){
	    	if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Kills") != 0){
	    		StatsManager.kills.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Kills"));
	    		StatsManager.killsatual.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Kills"));
	    	} else {
	    		StatsManager.kills.put(p, 0);
	    		StatsManager.killsatual.put(p, 0);
	    	}
	    	if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Deaths") != 0){
	    		StatsManager.deaths.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Deaths"));
	    		StatsManager.deathsatual.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Deaths"));
	    	} else {
	    		StatsManager.deaths.put(p, 0);
	    		StatsManager.deathsatual.put(p, 0);
	    	}
	    	if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Xp") != 0){
	    		StatsManager.xp.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Xp"));
	    		StatsManager.xpatual.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Xp"));
	    	} else {
	    		StatsManager.xp.put(p, 0);
	    		StatsManager.xpatual.put(p, 0);
	    	}
	    	if (YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Balance") != 0){
	    	StatsManager.money.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Balance"));
	    	StatsManager.moneyatual.put(p, YamlConfiguration.loadConfiguration(PlayerConfigFile.getPlayerConfig(p)).getInt("Stats." + p.getName() + ".Balance"));
	    	} else {
	    		StatsManager.money.put(p, 0);
	    		StatsManager.moneyatual.put(p, 0);
	    	}
	    	StatsManager.caixas.put(p, PlayerMySQL.getCrates(p.getUniqueId()));
		} else {
			if (PlayerMySQL.getKills(p.getUniqueId()) != 0) {
				StatsManager.kills.put(p, PlayerMySQL.getKills(p.getUniqueId()));
				StatsManager.killsatual.put(p, PlayerMySQL.getKills(p.getUniqueId()));
			} else {
				StatsManager.kills.put(p, 0);
				StatsManager.killsatual.put(p, 0);
			}
			if (PlayerMySQL.getDeaths(p.getUniqueId()) != 0) {
				StatsManager.deaths.put(p, PlayerMySQL.getDeaths(p.getUniqueId()));
				StatsManager.deathsatual.put(p, PlayerMySQL.getDeaths(p.getUniqueId()));
			} else {
				StatsManager.deaths.put(p, 0);
				StatsManager.deathsatual.put(p, 0);
			}
			if (PlayerMySQL.getXp(p.getUniqueId()) != 0) {
				StatsManager.xp.put(p, PlayerMySQL.getXp(p.getUniqueId()));
				StatsManager.xpatual.put(p, PlayerMySQL.getXp(p.getUniqueId()));
			} else {
				StatsManager.xp.put(p, 0);
				StatsManager.xpatual.put(p, 0);
			}
			if (PlayerMySQL.getBalance(p.getUniqueId()) != 0) {
				StatsManager.money.put(p, PlayerMySQL.getBalance(p.getUniqueId()));
				StatsManager.moneyatual.put(p, PlayerMySQL.getBalance(p.getUniqueId()));
			} else {
				StatsManager.money.put(p, 0);
				StatsManager.moneyatual.put(p, 0);
			}
	    	StatsManager.caixas.put(p, PlayerMySQL.getCrates(p.getUniqueId()));
			File playerFile = new File(Main.getMe().getDataFolder() + File.separator + "PlayersDB" + File.separator
					+ p.getUniqueId().toString() + ".yml");
			FileConfiguration playerFileConfig = YamlConfiguration.loadConfiguration(playerFile);
			try {
				playerFileConfig.set("mysql", true);
				playerFileConfig.save(playerFile);
			} catch (Exception e) {
			}
		}
	}

	public static void updateCurrentClan(Player p, Clan c) {
		StatsManager.playerclan.put(p, c);
	}

	// KILLS

	/*
	 * public static void adicionarKill(final Player matador) { try { final
	 * Statement statement = Main.c.createStatement(); statement.executeUpdate(
	 * "UPDATE TBL_PVP SET kills = kills+1 WHERE nome = '" + matador.getName() +
	 * "'"); final ResultSet rKill = statement.executeQuery(
	 * "SELECT kills FROM TBL_PVP WHERE nome = '" + matador.getName() + "'");
	 * while (rKill.next()) { kills.put(matador, rKill.getInt("kills")); }
	 * rKill.close(); } catch (SQLException ex) { ex.printStackTrace(); } }
	 * 
	 * 
	 * public static void setKills(final Player p, int kill) { try { final
	 * Statement statement = Main.c.createStatement(); statement.executeUpdate(
	 * "UPDATE TBL_PVP SET kills = "+ kill + " WHERE nome = '" + p.getName() +
	 * "'"); } catch (SQLException ex) { ex.printStackTrace(); } }
	 */

	public static int getKills(Player p) {
		return kills.get(p).intValue();
	}

	public static Clan getClan(Player p) {
		return playerclan.get(p);
	}

	// XP

	/*
	 * public static void setXp(final Player p, int xpp) { try { final Statement
	 * statement = Main.c.createStatement(); statement.executeUpdate(
	 * "UPDATE TBL_PVP SET xp = "+ xpp + " WHERE nome = '" + p.getName() + "'");
	 * } catch (SQLException ex) { ex.printStackTrace(); } }
	 */

	public static int getXp(Player p) {
		return xp.get(p).intValue();
	}

	// MORTES

	/*
	 * public static void adicionarDeath(final Player morto) { try { final
	 * Statement statement = Main.c.createStatement(); statement.executeUpdate(
	 * "UPDATE TBL_PVP SET deaths = deaths+1 WHERE nome = '" + morto.getName() +
	 * "'"); final ResultSet rDeath = statement.executeQuery(
	 * "SELECT deaths FROM TBL_PVP WHERE nome = '" + morto.getName() + "'");
	 * while (rDeath.next()) { deaths.put(morto, rDeath.getInt("deaths")); }
	 * rDeath.close(); } catch (SQLException ex) { ex.printStackTrace(); } }
	 */

	/*
	 * public static void setDeaths(final Player p, int death) { try { final
	 * Statement statement = Main.c.createStatement(); statement.executeUpdate(
	 * "UPDATE TBL_PVP SET deaths = "+ death + " WHERE nome = '" + p.getName() +
	 * "'"); } catch (SQLException ex) { ex.printStackTrace(); } }
	 */

	public static int getDeaths(Player p) {
		return deaths.get(p).intValue();
	}

	// DINHEIRO

	/*
	 * public static void adicionarDinheiro(final Player p, int coins) { try {
	 * final Statement statement = Main.c.createStatement();
	 * statement.executeUpdate("UPDATE TBL_PVP SET money = money+"+ coins +
	 * " WHERE nome = '" + p.getName() + "'"); } catch (SQLException ex) {
	 * ex.printStackTrace(); } }
	 * 
	 * public static void removerDinheiro(final Player p, int coins) { try {
	 * final Statement statement = Main.c.createStatement();
	 * statement.executeUpdate("UPDATE TBL_PVP SET money = money-"+ coins +
	 * " WHERE nome = '" + p.getName() + "'"); } catch (SQLException ex) {
	 * ex.printStackTrace(); } }
	 */

	public static int getBalance(Player p) {
		return money.get(p).intValue();
	}

	/*
	 * public static void setarBalance(final Player p, int coins) { try { final
	 * Statement statement = Main.c.createStatement(); statement.executeUpdate(
	 * "UPDATE TBL_PVP SET money = "+ coins + " WHERE nome = '" + p.getName() +
	 * "'"); } catch (SQLException ex) { ex.printStackTrace(); } }
	 */

	public static String getRank(Player p) {
		return ExperienceRank.getPlayerRank(p).getRankName();
	}

	/*
	 * public static String getRank(Player p){ if (getXp(p) >= 0 && getXp(p) <=
	 * 89){ return "Prata I"; } else if (getXp(p) >= 90 && getXp(p) <= 299){
	 * return "Prata II"; } else if (getXp(p) >= 300 && getXp(p) <= 599){ return
	 * "Prata III"; } else if (getXp(p) >= 600 && getXp(p) <= 999){ return
	 * "Prata IV"; } else if (getXp(p) >= 1000 && getXp(p) <= 1599){ return
	 * "Prata V"; } else if (getXp(p) >= 1600 && getXp(p) <= 2199){ return
	 * "Prata VI"; } else if (getXp(p) >= 2200 && getXp(p) <= 3000){ return
	 * "Ouro"; } else if (getXp(p) > 3000){ return "Platinum"; } return
	 * "Prata I"; }
	 */

}
