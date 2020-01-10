package com.luccadev.br.constructors;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class Skin {
	
	String uuid;
	String name;
	String value;
	String signature;
	
	public Skin(String uuid){
		this.uuid = uuid;
		load();
	}

	public static void setDisplayName(Player p, String name) {
		for (Player online : Bukkit.getOnlinePlayers()){
			PacketPlayOutEntityDestroy despawn = new PacketPlayOutEntityDestroy(new int[] { p.getEntityId() });
			PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn(((CraftPlayer)p).getHandle());
			try {
				Field b = ((CraftPlayer)p).getHandle().getProfile().getClass().getDeclaredField("name");
				b.setAccessible(true);
				b.set(((CraftPlayer)p).getHandle().getProfile(), name);
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (!online.equals(p)){
				((CraftPlayer)online).getHandle().playerConnection.sendPacket(despawn);
				((CraftPlayer)online).getHandle().playerConnection.sendPacket(spawn);
				((CraftPlayer)online).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer)p).getHandle()));
			}
		}
	}
	
	@SuppressWarnings("resource")
	private void load() {
		try {
			URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + this.uuid + "?unsigned=false");
			URLConnection uc = url.openConnection();
			uc.setUseCaches(false);
			uc.setDefaultUseCaches(false);
			uc.addRequestProperty("User-Agent", "Mozilla/5.0");
			uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
			uc.addRequestProperty("Pragma", "no-cache");
			
			String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
			JSONArray properties = (JSONArray) ((JSONObject)obj).get("properties");
			for (int i = 0; i < properties.size(); i++){
				try {
					JSONObject property = (JSONObject)properties.get(i);
					String name = (String)property.get("name");
					String value = (String)property.get("value");
					String signature = property.containsKey("signature") ? (String)property.get("signature") : null;
					
					this.name = name;
					this.value = value;
					this.signature = signature;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public String getSkinValue(){
		return this.value;
	}
	
	public String getSkinName(){
		return this.name;
	}
	
	public String getSkinSignature(){
		return this.signature;
	}

}
