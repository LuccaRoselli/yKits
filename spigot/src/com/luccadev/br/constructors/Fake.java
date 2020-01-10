package com.luccadev.br.constructors;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.luccadev.br.Main;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_8_R3.MinecraftServer;

public class Fake {
	private Player p;
	private String fakeName;
	private String originalPlayerName;

	@SuppressWarnings("deprecation")
	public String getUUID(String name){
		return Bukkit.getOfflinePlayer(name).getUniqueId().toString().replace("-", "");
	}
	
	public Fake(final Player p, final String originalPlayerName, final String fakeName) throws Exception {
		this.p = p;
		this.originalPlayerName = originalPlayerName;
		this.fakeName = fakeName;
		String realName;
		GameProfile gp;
		Skin skin;
		
		
		String name = this.fakeName;
		realName = this.originalPlayerName;
		gp = ((CraftPlayer)p).getProfile();
		gp.getProperties().clear();
		skin = new Skin(getUUID(name));
		if (skin.getSkinName() != null)
			gp.getProperties().put(skin.getSkinName(), new Property(skin.getSkinName(), skin.getSkinValue(), skin.getSkinSignature()));
		final Player pll = p;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Player pl : Bukkit.getOnlinePlayers()){
					pl.hidePlayer(pll);
				}
			}
		}.runTaskLater(Main.getPlugin(Main.class), 1L);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Player pl : Bukkit.getOnlinePlayers()){
					pl.showPlayer(pll);
				}
			}
		}.runTaskLater(Main.getPlugin(Main.class), 2L);
		
		Skin.setDisplayName(p, name);
		p.setPlayerListName(name);
		p.setDisplayName(name + "§r");
		MinecraftServer.getServer().getPlayerList().players.remove(realName);
	}

	public Player getPlayer() {
		return this.p;
	}

	public String getFakeName() {
		return this.fakeName;
	}

	public String getOriginalPlayerName() {
		return this.originalPlayerName;
	}

	public void toOriginal()
			throws Exception {
		new Fake(this.p, this.originalPlayerName, this.originalPlayerName);
	}

	public static class FakeNames {
		
		
		public static ArrayList<String> fakenames = new ArrayList<>();
				
		public void addFakeNames(){
			fakenames.add("luccadev");
		}

		public static String getRandom() {
			return Arrays
					.asList("Estgarban", "Stitiahin", "Ackmos", "Worough", "Asnough", "Imendo", "Undild", "Rakineves",
							"Oldshykel", "Ustight")
					.get(new SecureRandom().nextInt(10));
		}
	}
}
