package com.luccadev.br.constructors.warp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.luccadev.br.Main;
import com.luccadev.br.events.KillStreak;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Warp {

	public enum WarpType {
		FPS, ONEvsONE, LAVA;
	}

	WarpType type;
	ItemStack warpIcon;
	String warpname;
	public static HashMap<WarpType, ArrayList<Player>> wgetter = new HashMap<>();
	public static ArrayList<Warp> warps = new ArrayList<>();
	public static HashMap<Player, Warp> inwarp = new HashMap<>();

	public Warp(String nome, WarpType tipo, ArrayList<Player> array) {
		this.type = tipo;
		this.warpname = nome;
		wgetter.put(tipo, array);
		warps.add(this);
	}

	public static ArrayList<Warp> getAllWarps() {
		return warps;
	}

	public void setIcon(ItemStack stack) {
		this.warpIcon = stack;
	}

	public ItemStack getIcon() {
		if (this.warpIcon == null)
			return Uteis.setItem(Material.STONE, 1, "§eWarp sem ícone",
					Arrays.asList("", "§7Nenhum ícone encontrado", "§7para esta warp."));
		else
			return this.warpIcon;
	}
	public static boolean isInWarp(Player p) {
		return inwarp.containsKey(p);
	}

	public static Warp getPlayerWarp(Player p) {
		if (isInWarp(p)) {
			return inwarp.get(p);
		}
		return null;
	}

	public void addPlayer(Player p) {
		if (!getWarpAray().contains(p)) {
			getWarpAray().add(p);
			inwarp.put(p, this);
		}
	}

	public void removePlayer(Player p) {
		if (getWarpAray().contains(p)) {
			getWarpAray().remove(p);
			inwarp.remove(p);
		}
	}

	public void teleportPlayer(Player p, WarpType tipo, boolean renascido) {
		String warpname = getWarpName().toLowerCase();
		if (warpname.contains(" "))
			warpname = warpname.replace(" ", "_");
		if (!Main.getMe().getConfig().isSet("Warps." + warpname)) return;
		p.getInventory().clear();
		KitManager.getInstance().zerarKit(p);
		KitManager.pkits.put(p.getName(), getWarpName().toUpperCase());
		KillStreak.resetKillStreak(p);
		if (tipo == WarpType.FPS) {
			p.getInventory().setHelmet(Uteis.setItem(Material.IRON_HELMET, 1, " ", null));
			p.getInventory().setChestplate(Uteis.setItem(Material.IRON_CHESTPLATE, 1, " ", null));
			p.getInventory().setLeggings(Uteis.setItem(Material.IRON_LEGGINGS, 1, " ", null));
			p.getInventory().setBoots(Uteis.setItem(Material.IRON_BOOTS, 1, " ", null));
			ItemStack espada = Uteis.setItem(Material.DIAMOND_SWORD, 1, " ", null);
			espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			p.getInventory().setItem(0, espada);
			p.getInventory().setHeldItemSlot(0);
			KitManager.darSopas(p);
		} else if (tipo == WarpType.LAVA) {
			p.getInventory().setArmorContents(null);
			p.getInventory().setItem(14, KitManager.getRedMush(32));
			p.getInventory().setItem(15, KitManager.getBrownMush(32));
			p.getInventory().setItem(13, KitManager.getPote(32));
			KitManager.darSopas(p);
		}
		addPlayer(p);
		SCManager.send(p);
		p.teleport(Uteis.getLocation(Main.getMe().getConfig().getString("Warps." + warpname)));
		if (renascido == false)
			p.sendMessage(StringUtils.avisoverde + "Você está na warp §e" + getWarpName().toUpperCase() + "§7!");
	}

	public int getPlayerCount() {
		return getWarpAray().size();
	}

	public WarpType getType() {
		return this.type;
	}

	public String getWarpName() {
		return this.warpname;
	}

	public ArrayList<Player> getWarpAray() {
		return wgetter.get(this.type);
	}

	public static Warp getWarpByName(String name) {
		for (Warp w : getAllWarps()) {
			if (w.getWarpName().toLowerCase().equals(name.toLowerCase())) {
				return w;
			}
		}
		return null;
	}

	public static Warp getWarpByType(WarpType type) {
		for (Warp w : getAllWarps()) {
			if (w.getType() == type) {
				return w;
			}
		}
		return null;
	}

}
