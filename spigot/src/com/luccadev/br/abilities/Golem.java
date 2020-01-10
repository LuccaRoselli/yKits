package com.luccadev.br.abilities;

import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Golem implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void click(PlayerInteractEvent event){
		final Player p = event.getPlayer();
		if(p instanceof Player){
			if(KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Golem")){
				if (p.getItemInHand().getType() == Material.OBSIDIAN){
					if(Uteis.hasCooldown(p)){
						Uteis.sendCooldownMessage(p);
						return;
					}
					Uteis.addCooldown(p, 30);
					Location loc = p.getLocation();
			        int cx = loc.getBlockX();
			        int cy = loc.getBlockY();
			        int cz = loc.getBlockZ();
			        World w = loc.getWorld();
            		int rSquared = 5 * 5;
            		for (int x = cx - 5; x <= cx + 5; x++) {
                		for (int z = cz - 5; z <= cz + 5; z++) {
                    		if ((cx - x) * (cx -x) + (cz - z) * (cz - z) <= rSquared && (cx - x) * (cx -x) + (cz - z) * (cz - z) > 4 /*2 squared*/) {
                    			final Location l = new Location(w, x, cy, z);
                        		p.getWorld().playEffect(l, Effect.STEP_SOUND, p.getLocation().subtract(0, 2, 0).getBlock().getTypeId() );
                    		}
                		}
            		}
            		List<Entity> nearby = p.getNearbyEntities(5,5,5);
    				for(Entity ent : nearby){
		    			((Player)ent).playSound(p.getLocation(), Sound.EXPLODE, 5f, 5f);
		    			Vector vec = ent.getLocation().getDirection().multiply(5).setY(0.8);
		    			ent.setVelocity(vec);
    				}
    			}
			}
		}
	}
}
