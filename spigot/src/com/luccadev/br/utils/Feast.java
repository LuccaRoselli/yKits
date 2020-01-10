package com.luccadev.br.utils;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import com.luccadev.br.Main;

public class Feast {

    public static void fillAroundChests(Location loc, int radius){
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();

        for(int x = cx - radius; x <= cx + radius; x++){
            for (int z = cz - radius; z <= cz + radius; z++){
                for(int y = (cy - radius); y < (cy + radius); y++){
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + ((cy - y) * (cy - y));
                    if(dist < radius * radius){
                        Location l = new Location(loc.getWorld(), x, y + 2, z);
                        if (l.getBlock().getType() == Material.CHEST) {
                            Chest chest = (Chest)l.getBlock().getState();
                            chest.getInventory().clear();
                            encherFeast(chest);
                        }
                    }
                }
            }
        }
    }
	
	@SuppressWarnings("deprecation")
	public static void encherFeast(Chest chest) {
		List<String> itens = Main.getMe().getConfig().getStringList("FeastItems");
		for (Object item : itens) {
			String[] split = ((String) item).split(",");
			Random random = new Random();
			String id = split[0];
			Integer intg = Integer.valueOf(Integer.parseInt(id));

			ItemStack i = new ItemStack(intg.intValue(), 1);

			int maxc = 0;

			Integer slot = Integer.valueOf(random.nextInt(27));
			while ((chest.getInventory().getItem(slot.intValue()) != null)
					&& (!chest.getInventory().getItem(slot.intValue()).getType().equals(i.getType()))
					&& (maxc < 1000)) {
				slot = Integer.valueOf(random.nextInt(27));
			}
			if (chest.getInventory().getItem(slot.intValue()) != null) {
				i.setAmount(i.getAmount() + 1);
			}
			if (new Random().nextInt(100) < 10) {
				if (i.getType() == Material.EXP_BOTTLE)
					i.setAmount(3);
				chest.getInventory().setItem(slot.intValue(), i);
			}
			chest.update();
		}
	}

}
