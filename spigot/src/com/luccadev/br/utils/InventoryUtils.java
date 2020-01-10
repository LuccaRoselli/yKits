package com.luccadev.br.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {
	
	public static void removerVidro(Inventory inv){
		for(int i = 0; i < inv.getSize(); i++){
			if(inv.getItem(i) != null){
				if(inv.getItem(i).getType() == Material.THIN_GLASS){
					inv.setItem(i, new ItemStack(Material.AIR));
				}
			}
		}
	}
	
	public static void setHeader(Inventory inv, char tipo){
		if (tipo == 'a'){
			inv.setItem(0, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(1, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(2, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(3, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(4, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(5, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(6, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(7, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(8, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(9, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(10, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(11, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(12, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(13, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(14, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(15, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(16, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1 + 8, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1 + 8 + 1, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1 + 8 + 1 + 8, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(45, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(46, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(47, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(48, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(49, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(50, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(51, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(52, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(53, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
		}
		if (tipo == 'b'){
			inv.setItem(0, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(1, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(2, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(3, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(4, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(5, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(6, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(7, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(8, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(9, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(10, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(11, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(12, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(13, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(14, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(15, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(16, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1 + 8, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(46 - 18, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(46 - 17, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(46 - 16, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(47 - 16, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(48 - 16, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(49 - 16, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(50 - 16, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(51 - 16, Uteis.setItem(Material.THIN_GLASS, 1, StringUtils.getPrefix('b'), null));
		}
		if (tipo == '1'){
			inv.setItem(0, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(2, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(3, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(4, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(5, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(6, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(7, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(9, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1 + 8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1 + 8 + 1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17 + 1 + 8 + 1 + 8 + 1 + 8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(45, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(46, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(47, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(48, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(49, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(50, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(51, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(52, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(53, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
		}
		if (tipo == '2'){
			inv.setItem(0, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(2, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(3, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(4, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(5, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(6, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(7, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(9, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(10, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(12, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(14, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(16, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(18, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(19, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(20, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(21, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(22, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(23, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(24, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(25, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(26, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
		}
		if (tipo == '3'){
			inv.setItem(0, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(2, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(3, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(4, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(5, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(6, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(7, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(9, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(11, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(12, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(13, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(15, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(18, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(19, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(20, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(21, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(22, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(23, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(24, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(25, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(26, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
		}
		if (tipo == '4'){
			inv.setItem(0, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(2, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(6, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(7, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(9, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(18, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(19, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(20, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(24, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(25, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(26, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
		}
		if (tipo == '5'){
			inv.setItem(0, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(2, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(3, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(4, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(5, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(6, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(7, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(9, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(10, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(12, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(14, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(16, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(18, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(19, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(20, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(21, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(23, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(24, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(25, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(26, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
		}
		if (tipo == '6'){
			inv.setItem(0, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(2, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(3, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(4, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(5, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(6, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(7, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(8, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(9, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(11, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(15, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(17, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(18, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(19, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(20, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)0, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(21, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(23, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(24, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(25, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
			inv.setItem(26, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte)15, 1, StringUtils.getPrefix('b'), null));
		}
	}
	
}
