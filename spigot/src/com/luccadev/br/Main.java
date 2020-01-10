package com.luccadev.br;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.devlucca.br.cmd.CommandAPI;
import com.devlucca.br.constructors.Rank.OrderType;
import com.devlucca.br.constructors.Rank.User;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.luccadev.br.commands.Admin;
import com.luccadev.br.commands.AdminChat;
import com.luccadev.br.commands.ClanCommand;
import com.luccadev.br.commands.Conquistas;
import com.luccadev.br.commands.FakeCommand;
import com.luccadev.br.commands.GameMode;
import com.luccadev.br.commands.Reportar;
import com.luccadev.br.commands.ResetKit;
import com.luccadev.br.commands.ScoreEnable;
import com.luccadev.br.commands.Spawn;
import com.luccadev.br.commands.Tag;
import com.luccadev.br.commands.WarpSet;
import com.luccadev.br.constructors.Achievement;
import com.luccadev.br.constructors.Achievement.AchievementType;
import com.luccadev.br.constructors.Kit.Kits;
import com.luccadev.br.constructors.PlayerProfile;
import com.luccadev.br.constructors.Skill;
import com.luccadev.br.constructors.warp.WarpController;
import com.luccadev.br.desafio1v1.cmd1v1;
import com.luccadev.br.events.ServerListeners;
import com.luccadev.br.experience.ExperienceRank;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.ReportManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.storage.MySQL;
import com.luccadev.br.storage.PlayerMySQL;
import com.luccadev.br.storage.exception.MySQLException;
import com.luccadev.br.utils.BoxMessages;
import com.luccadev.br.utils.Feast;
import com.luccadev.br.utils.PlayerConfigFile;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.UpdateScheduler;
import com.luccadev.br.utils.Uteis;
import com.luccadev.br.utils.scroller.ServerScroller;

import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class Main extends JavaPlugin {

	private static Plugin plugin;
	public static MySQL mysql;
	public static String ip;
	public static int porta;
	public static String database;
	public static String user;
	public static String senha;
	public static boolean jasalvou = false;
	private ReportManager reportManager;

	
	public void onEnable() {
		plugin = this;
		Kits.loadKits();
		try {
			registerEvents();
		} catch (InstantiationException e) {
		}
		try {
			registerAbilities();
		} catch (InstantiationException e) {
		}
		saveDefaultConfig();
		Bukkit.getConsoleSender().sendMessage(
				"[" + getMe().getName() + "] §a§l» §7Foram carregados §e" + KitManager.getKits().size() + " §7kits.");
		new Achievement("Kills", AchievementType.KILLS, 50, "Mate 50 players para", "completar este achievement!");
		new Achievement("Deaths", AchievementType.DEATHS, 50, "Morra 50 vezes para", "completar este achievement!");
		new Achievement("KillStreak", AchievementType.KILLSTREAK, 10, "F aça uma KillStreak de 10 para", "completar este achievement!");
		addSkills();

		loadConfig();

		CommandAPI.setupAPI(getServer());
		CommandAPI.registerCommand("report", new Reportar());
		CommandAPI.registerCommand("gm", new GameMode());
		CommandAPI.registerCommand("gamemode", new GameMode());
		CommandAPI.registerCommand("s", new AdminChat());
		CommandAPI.registerCommand("fake", new FakeCommand());
		CommandAPI.registerCommand("admin", new Admin());
		CommandAPI.registerCommand("resetkit", new ResetKit());
		CommandAPI.registerCommand("score", new ScoreEnable());
		CommandAPI.registerCommand("clan", new ClanCommand());
		CommandAPI.registerCommand("tag", new Tag());
		//CommandAPI.registerCommand("1v1", new cmd1v1());
		CommandAPI.registerCommand("pos1", new cmd1v1());
		CommandAPI.registerCommand("pos2", new cmd1v1());
		CommandAPI.registerCommand("set1v1", new cmd1v1());
		CommandAPI.registerCommand("spawn", new Spawn());
		CommandAPI.registerCommand("conquistas", new Conquistas());
		CommandAPI.registerCommand("warpset", new WarpSet());
		this.reportManager = new ReportManager(this);
		System.out.println("report manager hookado");
		try {
			PlayerConfigFile.printAll();
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		try {
			mysql = new MySQL(ip, porta, database, user, senha);
			mysql.executeStatement("CREATE TABLE IF NOT EXISTS " + PlayerMySQL.table + "("
					+ "uuid VARCHAR(40) NOT NULL," + "caixas INT NOT NULL," + "balance INT NOT NULL,"
					+ "kills INT NOT NULL," + "deaths INT NOT NULL," + "xp INT NOT NULL," + "pname VARCHAR(500)," + "skills VARCHAR(500))");
			System.out.println("MySQL: Conectado com sucesso!");
		} catch (MySQLException e) {
			System.out.println("MySQL: Erro ao tentar se conectar com o banco de dados.");
			BoxMessages.box(new String[]{"IP:" + ip, "PORTA: " + porta, "DATABASE: " + database, "USER: " + user, "SENHA: " + senha}, "MYSQL INFOS");
		} catch (NullPointerException e) {
			System.out.println("MySQL: Erro ao tentar se conectar com o banco de dados (2).");
			BoxMessages.box(new String[]{"IP:" + ip, "PORTA: " + porta, "DATABASE: " + database, "USER: " + user, "SENHA: " + senha}, "MYSQL INFOS");
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerProfile pp = new PlayerProfile(p);
			pp.loadKits();
			pp.createGui();
			pp.createInventorySkill();
			StatsManager.updateStats(p);
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.broadcastMessage("§7§m§o§l------------------------------------");
				Bukkit.broadcastMessage("§7               " + StringUtils.getPrefix('b') + "§8: §eSTATUS");
				Bukkit.broadcastMessage("§7§m§o§l------------------------------------");
				Bukkit.broadcastMessage("§fServidor reiniciando para salvar todos os status");
				Bukkit.broadcastMessage("§fno nosso banco de dados em §e30 §fminutos.");
				Bukkit.broadcastMessage("§7§m§o§l------------------------------------");
			}
		}.runTaskLater(this, Uteis.tickToMinute(30));

		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.broadcastMessage("§7§m§o§l------------------------------------");
				Bukkit.broadcastMessage("§7               " + StringUtils.getPrefix('b') + "§8: §eSTATUS");
				Bukkit.broadcastMessage("§7§m§o§l------------------------------------");
				Bukkit.broadcastMessage("§fServidor reiniciando para salvar todos os status");
				Bukkit.broadcastMessage("§fno nosso banco de dados em §e15 §fminutos.");
				Bukkit.broadcastMessage("§7§m§o§l------------------------------------");
			}
		}.runTaskLater(this, Uteis.tickToMinute(45));

		new BukkitRunnable() {

			@Override
			public void run() {
				Bukkit.getServer().setWhitelist(true);
				for (Player p : Bukkit.getOnlinePlayers()) {
					PlayerConfigFile.savePlayerStats(p);
					p.kickPlayer("§7§m§o§l---------------------------------------------\n" + "§7       "
							+ StringUtils.getPrefix('b')
							+ "§e: STATUS\n§7§m§o§l---------------------------------------------\n§fServidor reiniciando para salvar todos os status!\n§fVoltamos em no máximo §e2 §fminutos.\n§7§m§o§l---------------------------------------------");
				}
				jasalvou = true;
				PlayerConfigFile.saveAllOnMySQL();
				Bukkit.getServer().setWhitelist(false);

			}
		}.runTaskLater(this, Uteis.tickToMinute(60));
		System.out.println("Registrando WarpManager...");
		new WarpController(this);
		ServerScroller.setup();
		getServer().getScheduler().runTaskTimer(this, new UpdateScheduler(), 1L, 1L);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()){
					p.sendMessage("");
					p.sendMessage("            §a§lFEAST & MINIFEAST");
					p.sendMessage("");
					p.sendMessage("§fOs baús foram §erecarregados§f.");
					p.sendMessage("§fCorra para garantir seus itens!");
					p.sendMessage("");
					p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
					p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
					p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
					Feast.fillAroundChests(new Location(Bukkit.getWorld("world"), -1131, 23, 3993), 15);
					Feast.fillAroundChests(new Location(Bukkit.getWorld("world"), -1269, 22, 3930), 15);
					Feast.fillAroundChests(new Location(Bukkit.getWorld("world"), -1222, 20, 3940), 15);
					Feast.fillAroundChests(new Location(Bukkit.getWorld("world"), -1124, 23, 3836), 15);
					Feast.fillAroundChests(new Location(Bukkit.getWorld("world"), -1141, 23, 3922), 15);
				}
			}
		}.runTaskTimer(this, Uteis.tickToMinute(1), Uteis.tickToMinute(5));
		ServerListeners.feztop10 = false;
	}
	
	@SuppressWarnings("deprecation")
	public static void spawnNPC() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Location loc = new Location(Bukkit.getWorld("world"), -1379, 97, 2822);
		Villager villager = (Villager)Bukkit.getWorld("world").spawnCreature(loc, EntityType.VILLAGER);
		villager.setProfession(Villager.Profession.LIBRARIAN);
		
		EntityVillager nmsVillager = ((CraftVillager) villager).getHandle();
		//Get the field
		Field goal = nmsVillager.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("goalSelector");
		goal.setAccessible(true);//remove the protected modifier
		 
		 
		//Evilly Remove the final modifier
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(goal, goal.getModifiers() & ~Modifier.FINAL);//Evilness shall ensue
		PathfinderGoalSelector goalSelector = new PathfinderGoalSelector(nmsVillager.world != null && nmsVillager.world.methodProfiler != null ? nmsVillager.world.methodProfiler : null);
		goal.set(nmsVillager, goalSelector);
		
		Hologram h = HologramsAPI.createHologram(Main.getMe(), new Location(Bukkit.getWorld("world"), -1379, 97, 2822).add(0, 2.9, 0));
		h.insertTextLine(0, "§a§lCAIXAS");
		h.insertTextLine(1, "§7Aqui você poderá comprar/abrir caixas");
		h.insertTextLine(2, "§7e ganhar §akits §7aleatoriamente!");
	}

	public static HashMap<String, Hologram> holofound = new HashMap<>();
	
	public enum Top10Type{
		XP,
		WINS;
	}
	
	public static void doTop10(Top10Type type){
		if (type == Top10Type.XP){
			PlayerMySQL.getTop("xp", 10);
		} else {
			if (type == Top10Type.WINS){
				PlayerMySQL.getTop("kills", 10);
			}
		}
		for (String ac : PlayerConfigFile.kills){
			System.out.println(ac);
		}
		com.devlucca.br.constructors.Rank r = new com.devlucca.br.constructors.Rank();
		for (String ac : PlayerConfigFile.kills) {
			r.add(ac.split("-")[0], Double.valueOf(ac.split("-")[1]));
		}
		ArrayList<User> quests = r.getOrderType(OrderType.LARGER);
		Location loc = null;
		if (type == Top10Type.XP){
			loc = new Location(Bukkit.getWorld("world"), -1384.185, 101.5, 2837.334);
		} else {
			if (type == Top10Type.WINS){
				loc = new Location(Bukkit.getWorld("world"), -1373.730, 101.5, 2835);
			}
		}
		Hologram h = HologramsAPI.createHologram(Main.getMe(), loc);
		String str = null;
		if (type == Top10Type.XP){
			h.insertTextLine(0, "§f§l§nTOP §a§l§n10§f§l§n XP");
			str = "XP";
		} else {
			if (type == Top10Type.WINS){
				h.insertTextLine(0, "§f§l§nTOP §a§l§n10§f§l§n WINS");
				str = "WINS";
			}
		}
		h.insertTextLine(1, "§fAqui você verá os 10 jogadores com");
		h.insertTextLine(2, "§fmais §a" + str + " §fdo servidor!");
		h.insertTextLine(3, " ");
		for (int i = 0; i < 10; i++) {
			User user = quests.get(i);
			char color = '0';
			switch (i + 1) {
			case 1: case 2:
				color = 'a';
				break;
			case 3: case 4:
				color = '2';
				break;
			case 5: case 6:
				color = 'e';
				break;
			case 7: case 8:
				color = 'c';
				break;
			case 9: case 10:
				color = '4';
				break;
			}
			h.appendTextLine(String.valueOf(
					"§f" + "§" + color + (i + 1) + ". §fLugar: §e" + user.getUser() + " §f- §e" + user.getValue() + "")
					.replace(".0", ""));
		}
		h.appendTextLine(" ");
		h.appendTextLine("§f§lOBS: §fO Top 10 atualiza");
		h.appendTextLine("§fao reiniciar o servidor.");
		holofound.put(str, h);
		PlayerConfigFile.kills.clear();
	}
	
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			PlayerConfigFile.savePlayerStats(p);
		}
		jasalvou = true;
		PlayerConfigFile.saveAllOnMySQL();
	}

	public ReportManager getReportManager() {
		return this.reportManager;
	}

	public void loadConfig() {
		try {
			ip = getConfig().getString("MySQL.IP");
			porta = getConfig().getInt("MySQL.Porta");
			database = getConfig().getString("MySQL.Database");
			user = getConfig().getString("MySQL.User");
			senha = getConfig().getString("MySQL.Senha");
		} catch (Exception e) {
			throw new IllegalArgumentException("Config invalid");
		}
	}

	public void addSkills() {
		ArrayList<String> stompervantagem = new ArrayList<String>();
		stompervantagem.add("§7Aumente o raio do");
		stompervantagem.add("§7stomper para 3 blocos");
		new Skill(KitManager.getKitByName("Stomper"), 1000, stompervantagem, 1, ExperienceRank.ADVENTURER);
	}

	public void registerEvents() throws InstantiationException {
		for (Class<?> clazz : getClasses(this.getFile(), "com.luccadev.br.events")) {
			if (Listener.class.isAssignableFrom(clazz)) {
				try {
					Bukkit.getConsoleSender()
							.sendMessage("§6[EVENTOS] §7Evento §c" + clazz.getSimpleName() + " §7registrado.");
					getServer().getPluginManager().registerEvents((Listener) clazz.newInstance(), this);
				} catch (Exception e) {
				}
			}
		}
	}

	public void registerAbilities() throws InstantiationException {
		for (Class<?> clazz : getClasses(this.getFile(), "com.luccadev.br.abilities")) {
			if (Listener.class.isAssignableFrom(clazz)) {
				try {
					Bukkit.getConsoleSender()
							.sendMessage("§6[HABILIDADES] §7Habilidade §c" + clazz.getSimpleName() + " §7registrada.");
					getServer().getPluginManager().registerEvents((Listener) clazz.newInstance(), this);
				} catch (Exception e) {
				}
			}
		}
	}

	public static MySQL getMySQL() {
		return mysql;
	}

	public static Set<Class<?>> getClasses(File jarFile, String packageName) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		try {
			JarFile file = new JarFile(jarFile);
			for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
				JarEntry jarEntry = entry.nextElement();
				String name = jarEntry.getName().replace("/", ".");
				if (name.startsWith(packageName) && name.endsWith(".class") && !name.contains("$"))
					classes.add(Class.forName(name.substring(0, name.length() - 6)));
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classes;
	}

	public static Plugin getMe() {
		return plugin;
	}

}
