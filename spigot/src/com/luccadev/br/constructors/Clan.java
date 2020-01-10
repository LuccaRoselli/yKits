package com.luccadev.br.constructors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.luccadev.br.Main;

public class Clan {

	String name;
	String tag;
	String owner;
	public static ArrayList<Clan> allclans = new ArrayList<>();
	List<String> members;
	List<String> donos;

	public Clan(File clan){
		if (clan.exists()){
			FileConfiguration fc = YamlConfiguration.loadConfiguration(clan);
			this.name = clan.getName().split(".yml")[0];
			this.owner = fc.getString("Owner");
			this.tag = fc.getString("ClanTag");
			members = fc.getStringList("Integrantes.Membros");
			donos = fc.getStringList("Integrantes.Donos");
		}
	}
	
	public Clan(final String owner, String clanname, final String tag) {
		this.name = clanname;
		this.owner = owner;
		this.tag = tag;
		File clansPasta = new File(Main.getMe().getDataFolder() + File.separator + "ClansDB");
		if (!clansPasta.exists()) {
			clansPasta.mkdir();
		}
		final File clanArchive = new File(Main.getMe().getDataFolder() + File.separator + "ClansDB" + File.separator + clanname + ".yml");
		final FileConfiguration clanArchiveEditor = YamlConfiguration.loadConfiguration(clanArchive);
		new Thread() {
			public void run() {
				if (!clanArchive.exists()) {
					try {
						clanArchive.createNewFile();
						
						clanArchiveEditor.set("Owner", owner);
						
						clanArchiveEditor.set("ClanTag", tag);
						
						ArrayList<String> integrantes = new ArrayList<>();
						integrantes.add(owner);
						clanArchiveEditor.set("Integrantes.Membros", integrantes);
						
						ArrayList<String> donos = new ArrayList<>();
						donos.add(owner);
						clanArchiveEditor.set("Integrantes.Donos", donos);
						
						clanArchiveEditor.save(clanArchive);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void addMember(Player membro){
		final File clanArchive = new File(Main.getMe().getDataFolder() + File.separator + "ClansDB" + File.separator + this.name + ".yml");
		List<String> lista = YamlConfiguration.loadConfiguration(clanArchive).getStringList("Integrantes.Membros");
		if (!lista.contains(membro.getName())){
			lista.add(membro.getName());
			FileConfiguration clanFileEditor = YamlConfiguration.loadConfiguration(clanArchive);
			try {
				clanFileEditor.set("Integrantes.Membros", lista);
				clanFileEditor.save(clanArchive);
			} catch (Exception e) {
				
			}
		}
	}
	
	public void addOwner(Player membro){
		final File clanArchive = new File(Main.getMe().getDataFolder() + File.separator + "ClansDB" + File.separator + this.name + ".yml");
		List<String> lista = YamlConfiguration.loadConfiguration(clanArchive).getStringList("Integrantes.Membros");
		if (!lista.contains(membro.getName())){
			lista.add(membro.getName());
			FileConfiguration clanFileEditor = YamlConfiguration.loadConfiguration(clanArchive);
			try {
				clanFileEditor.set("Integrantes.Donos", lista);
				clanFileEditor.save(clanArchive);
			} catch (Exception e) {
				
			}
		}
	}
	
	public static Clan getClanByName(String clanname){
		for (Clan c : allclans){
			if (c.getClanName().equalsIgnoreCase(clanname)){
				return c;
			}
		}
		return null;
	}
	
	public String getClanTag(){
		return this.tag;
	}
	
	public String getClanName(){
		return this.name;
	}
	
	public static ArrayList<Clan> getClans(){
		return allclans;
	}
	
	public List<String> getMembers(){
		return this.members;
	}
	
	public List<String> getOwners(){
		return this.donos;
	}
	
	public static void loadAllClans(){
		File folder = new File(Main.getMe().getDataFolder() + File.separator + "ClansDB" + File.separator);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				File clanArchive = new File(Main.getMe().getDataFolder() + File.separator + "ClansDB" + File.separator
						+ listOfFiles[i].getName());
				
				allclans.add(new Clan(clanArchive));
				System.out.println(clanArchive.getName() + " carregado.");
			} else if (listOfFiles[i].isDirectory()) {
			}
		}
	}
	
	public static boolean exists(String clanname){
		for (Clan c : getClans()){
			System.out.println(c.getClanName());
			if (c.getClanName().equalsIgnoreCase(clanname)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isOwner(Player p){
		return getOwners().contains(p.getName());
	}

}
