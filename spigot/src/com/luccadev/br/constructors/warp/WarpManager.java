package com.luccadev.br.constructors.warp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.luccadev.br.constructors.warp.Warp.WarpType;
import com.luccadev.br.utils.InventoryUtils;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class WarpManager {
	
	public static ArrayList<Player> fpsarray = new ArrayList<>();
	public static ArrayList<Player> lavaarray = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public static void openWarpInventory(Player p){
	
		Inventory inv = Bukkit.createInventory(null, 36, "§6Warps - Página §b1");
		InventoryUtils.setHeader(inv, 'b');
		inv.setItem(3, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));
		inv.setItem(4, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));
		inv.setItem(5, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));
		for (Warp w : Warp.getAllWarps()){
			ItemStack i = w.getIcon().clone();
			ItemMeta meta = i.getItemMeta();
			List<String> lore = new ArrayList<String>(); // default List<String> just in case the item has no lore
			if(meta.hasLore()){
			    lore = meta.getLore(); // add lines onto current lore if the item has lore
			}
			lore.add(" §7Jogadores: §a" + w.getPlayerCount());
			lore.add(" ");
			meta.setLore(lore); // update lore
			i.setItemMeta(meta); // update item
			inv.addItem(i);
		}
		InventoryUtils.removerVidro(inv);
		p.updateInventory();
		p.openInventory(inv);
		p.updateInventory();
		
	}
	
	public static void addWarps(){
		Warp fps = new Warp("FPS", WarpType.FPS, fpsarray);
		fps.setIcon(Uteis.setItem(Material.GLASS, 1, "§e" + fps.getWarpName(),
						Arrays.asList(" ", " §   §7Deseja jogar com mais fps?",
								"   §7Então venha já à arena §e" + fps.getWarpName() + "§7!", "")));
		Warp lava = new Warp("Lava Challenge", WarpType.LAVA, lavaarray);
		lava.setIcon(Uteis.setItem(Material.LAVA_BUCKET, 1, "§e" + lava.getWarpName(),
						Arrays.asList(" ", "   §7Deseja treinar seu refil?",
								"   §7Então venha já a §e" + lava.getWarpName() + "§7!", "")));
		
	}

}
