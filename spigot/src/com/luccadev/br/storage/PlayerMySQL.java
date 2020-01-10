package com.luccadev.br.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.Skill;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.PlayerConfigFile;

public class PlayerMySQL implements Listener {

	public static String table = "lithemc_pvp";

	public static boolean contaisInMySQL(UUID uuid) {
		return Main.getMySQL()
				.resultSetBoolean("SELECT * FROM " + table + " WHERE uuid LIKE '%" + uuid.toString() + "%'");
	}

	public static void setMySQL(UUID uuid) {
		Main.getMySQL().preparedStatement("INSERT INTO " + table + " (uuid,caixas,balance,kills,deaths,xp,pname,skills) " +
	"VALUES('" + uuid.toString() + "', '0', " + "'" + 0 + "', '" + 0 +"', '" + 0 +"', '" + 0 + "', '" + Bukkit.getPlayer(UUID.fromString(uuid.toString())).getDisplayName() + "', '" + "Nenhum" + "')");
	}

	public static int getBalance(UUID uuid) {
		return (Integer) Main.getMySQL()
				.resultSet("SELECT balance FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "balance");
	}
	
	public static int getKills(UUID uuid) {
		return (Integer) Main.getMySQL()
				.resultSet("SELECT kills FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "kills");
	}
	
	public static int getCrates(UUID uuid) {
		return (Integer) Main.getMySQL()
				.resultSet("SELECT caixas FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "caixas");
	}
	
	public static void addCrates(UUID uuid, int caixas) {
		Main.getMySQL().executeStatement("UPDATE " + table + " SET caixas = caixas + " + caixas + " WHERE uuid='" + uuid.toString() + "';");
	}

	public static String[] getTop(String type, int amount) {
		String[] array = new String[amount];
		try {
			Statement s = Main.getMySQL().getStatement();
			ResultSet r = s.executeQuery("SELECT * FROM " + table + " ORDER BY (" + type.toLowerCase()
			+ ") DESC LIMIT " + amount);
			System.out.println("exec query");
			while (r.next()) {
				PlayerConfigFile.kills.add(String.valueOf(r.getString("pname") + "-" + r.getInt(type.toLowerCase())));
			}
			if (PlayerConfigFile.kills.size() < amount){
				int i = amount - PlayerConfigFile.kills.size();
				while(i != 0){
					PlayerConfigFile.kills.add(String.valueOf("▬▬▬▬▬▬▬-0"));
					i--;
				}
			}
		} catch (SQLException e) {
		}
		return array;
	}

	
	public static int getDeaths(UUID uuid) {
		return (Integer) Main.getMySQL()
				.resultSet("SELECT deaths FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "deaths");
	}

	
	public static int getXp(UUID uuid) {
		return (Integer) Main.getMySQL()
				.resultSet("SELECT xp FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "xp");
	}

	
	public static String getSkls(UUID uuid) {
		return (String) Main.getMySQL()
				.resultSet("SELECT skills FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "skills");
	}

	public static List<Skill> getSkills(UUID uuid) {
		return formatKits((String) Main.getMySQL()
				.resultSet("SELECT skills FROM " + table + " WHERE uuid='" + uuid.toString() + "';", "skills"));
	}
	
	public static void setBalance(UUID uuid, int coin) {
		Main.getMySQL()
				.executeStatement("UPDATE " + table + " SET balance='" + coin + "' WHERE uuid='" + uuid.toString() + "';");
	}
	
	public static void clearSkills(UUID uuid) {
		if (getSkls(uuid).equalsIgnoreCase("Nenhum")){
		Main.getMySQL()
				.executeStatement("UPDATE " + table + " SET skills='" + "" + "' WHERE uuid='" + uuid.toString() + "';");
		}
	}
	
	public static void setKills(UUID uuid, int kills) {
		Main.getMySQL()
				.executeStatement("UPDATE " + table + " SET kills='" + kills + "' WHERE uuid='" + uuid.toString() + "';");
	}
	
	public static void setCaixas(UUID uuid, int caixas) {
		Main.getMySQL()
				.executeStatement("UPDATE " + table + " SET caixas='" + caixas + "' WHERE uuid='" + uuid.toString() + "';");
	}
	
	public static void setDeaths(UUID uuid, int deaths) {
		Main.getMySQL()
				.executeStatement("UPDATE " + table + " SET deaths='" + deaths + "' WHERE uuid='" + uuid.toString() + "';");
	}
	
	public static void setXp(UUID uuid, int xp) {
		Main.getMySQL()
				.executeStatement("UPDATE " + table + " SET xp='" + xp + "' WHERE uuid='" + uuid.toString() + "';");
	}
	
	public static void setSkills(UUID uuid, List<Skill> kits) {
		Main.getMySQL().executeStatement(
				"UPDATE " + table + " SET skills='" + setSkills(kits) + "' WHERE uuid='" + uuid.toString() + "';");
	}
	
	public static boolean hasCrate(Player p){
		return StatsManager.caixas.get(p) > 0;
	}
	
	private static List<Skill> formatKits(String kits) {
		if (kits == null || kits == "")
			return new ArrayList<Skill>();

		List<Skill> list = new ArrayList<Skill>();
		String[] kitArgs = kits.split(",");

		try {
			for (String s : kitArgs) {
				list.add(Skill.getSkillByKitAndNumber(KitManager.getKitByName(s), 1));
			}
		} catch (Exception e) {
		}

		return list;
	}

	private static String setSkills(List<Skill> kits) {

		String line = "";

		for (Skill kit : kits) {

			if (line == "")
				line = kit.getKitOfSkill().getName() + "";
			else
				line += "," + kit.getKitOfSkill().getName();
		}
		return line;
	}

}
