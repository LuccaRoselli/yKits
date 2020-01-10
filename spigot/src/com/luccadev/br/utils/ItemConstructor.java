package com.luccadev.br.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemConstructor {
public static HashMap<ItemStack, String> items = new HashMap<>();
	
	public ItemStack create(Material m, String id, String displayName, String[] lore, int amount, short data, boolean glow) {
		ItemStack item = new ItemStack(m, amount, data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		ArrayList<String> l = new ArrayList<>();
		if(lore.length > 0) {
		for(int i = 0 ; i < lore.length; i++) {
			l.add(lore[i]);
		 }
		}
		meta.setLore(l);
		item.setItemMeta(meta);
		
		if(glow){}
		
	    if(items.containsKey(item)) {
	    	items.remove(item);
	    }
	    items.put(item, id);
		return item;
	}
	
	public HashMap<ItemStack, String> getItems() {
		return items;
	}
	
	public ItemStack getItemByID(String id) {
		if(getItems().containsValue(id)) {
			for(Entry<ItemStack, String> en : items.entrySet()) {
			 if(en.getValue().equals(id)) {
				 return (ItemStack)en.getKey();
			 }
			}
		}
		return null;
	}
	
}
