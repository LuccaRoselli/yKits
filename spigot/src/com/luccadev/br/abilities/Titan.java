package com.luccadev.br.abilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Titan implements Listener {

	public enum ImortalEnum {
		ON, OFF;
	}

	private static HashMap<Player, ImortalEnum> imortal = new HashMap<>();


	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!imortal.containsKey(p)) {
			imortal.put(p, ImortalEnum.OFF);
		} else {
			imortal.put(p, getImortal(p));
		}
	}

	@EventHandler
	public void ontitan(PlayerInteractEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		if (!KitManager.getInstance().getUsingKitName(e.getPlayer()).equalsIgnoreCase("titan")) {
			return;
		}
		if (!e.getAction().name().contains("RIGHT")) {
			return;
		}
		if (e.getPlayer().getItemInHand().getType() == Material.BEDROCK) {
			final Player p = e.getPlayer();
			e.setCancelled(true);
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
			} else {
				setImortal(p, true);
				p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.BEACON, 20);
				p.playSound(p.getLocation(), Sound.IRONGOLEM_THROW, 3.0F, 3.0F);
				p.sendMessage(StringUtils.avisoverde + "§7Você está invencivel por 10 segundos! ");
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 5));
				Uteis.addCooldown(p, 20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
					public void run() {
						Titan.setImortal(p, false);
						p.sendMessage(StringUtils.avisovermelho + "Você não está mais invencivel! ");
					}
				}, Uteis.tickToSecond(10));
			}
		}
	}

	@EventHandler
	public void dano(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if (isImortal(p)) {
				e.setCancelled(true);
				p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 10);
			}
		}
	}

	@EventHandler
	public void entityDamage(EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Player && (e.getEntity() instanceof Player))) {
			Player d = (Player) e.getEntity();
			Player p = (Player) e.getDamager();
			if (isImortal(p)) {
				e.setCancelled(true);
				d.getWorld().playEffect(d.getLocation(), Effect.SMOKE, 10);
				p.sendMessage(StringUtils.avisovermelho + "Você não pode causar dano no modo Titan!");
			}
		}
	}

	public static ImortalEnum getImortal(Player p) {
		return (ImortalEnum) imortal.get(p);
	}

	public static boolean isImortal(Player p) {
		if (getImortal(p) == ImortalEnum.ON) {
			return true;
		}
		return false;
	}

	public static void setImortal(Player p, boolean i) {
		if (i) {
			imortal.put(p, ImortalEnum.ON);
		} else {
			imortal.put(p, ImortalEnum.OFF);
		}
	}
}
