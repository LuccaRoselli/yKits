package com.luccadev.br.abilities;

import java.util.HashMap;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;

public class Wombo 
implements Listener
{
	
	public static HashMap<String, Integer> combo = new HashMap<String, Integer>();;
	
	
	@EventHandler
	public static void onDeath(PlayerDeathEvent e){
		combo.remove(e.getEntity().getName());
	}
	
	@EventHandler
    public static void onCombo(final EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
            final Player p = (Player)event.getEntity();
            final Player d = (Player)event.getDamager();
            if (combo.containsKey(p.getName())) {
                if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                    p.removePotionEffect(PotionEffectType.SPEED);
                }
                if (combo.get(p.getName()) >= 5) {
                    combo.remove(p.getName());
                    p.sendMessage(StringUtils.avisovermelho + "§cVocê perdeu seu combo!");
                    d.sendMessage(StringUtils.avisoverde + "§eVocê parou o combo de §6§l" + p.getName());
                }
            }
            if (KitManager.getInstance().getUsingKitName(d).equalsIgnoreCase("Wombo")) {
                int x = 0;
                if (combo.containsKey(d.getName())) {
                    x = combo.get(d.getName());
                }
                combo.put(d.getName(), x + 1);
                switch (x + 1) {
                    case 5: {
                        if (d.hasPotionEffect(PotionEffectType.SPEED)) {
                            d.removePotionEffect(PotionEffectType.SPEED);
                        }
                        d.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 0));
                        d.sendMessage(StringUtils.avisoverde + "§eVocê está em um combo de §6§l5");
                        p.sendMessage(StringUtils.avisovermelho + "§2§l" + d.getName() + " está com um combo de 5");
                        break;
                    }
                    case 8: {
                        if (d.hasPotionEffect(PotionEffectType.SPEED)) {
                            d.removePotionEffect(PotionEffectType.SPEED);
                        }
                        d.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1));
                        d.sendMessage(StringUtils.avisoverde + "§eVocê está em um combo de §6§l8");
                        p.sendMessage(StringUtils.avisovermelho + "§2§l" + d.getName() + " está com um combo de 8");
                        break;
                    }
                    case 10: {
                        if (d.hasPotionEffect(PotionEffectType.SPEED)) {
                            d.removePotionEffect(PotionEffectType.SPEED);
                        }
                        d.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
                        d.sendMessage(StringUtils.avisoverde + "§eVocê está em um combo de §6§l10");
                        p.sendMessage(StringUtils.avisovermelho + "§2§l" + d.getName() + " está com um combo de 10");
                        break;
                    }
                }
            }
        }
    }
}
