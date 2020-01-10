package com.luccadev.br.abilities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Lord implements Listener {

	public static ArrayList<Player> lord = new ArrayList<Player>();

	@EventHandler
	public void click(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (p instanceof Player) {
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Lord")) {
				if (p.getItemInHand().getType() == Material.DIAMOND) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
					} else {
						lord.add(p);
						Uteis.addCooldown(p, 100);
						p.sendMessage(StringUtils.avisoverde
								+ "§aVocê ativou seus poderes, lembre-se, eles irão durar 30 segundos!");
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {

							@Override
							public void run() {
								lord.remove(p);
								p.sendMessage(StringUtils.avisovermelho + "§cVocê não está mais em sua forma Lord!");
								p.sendMessage(StringUtils.avisovermelho + "§4Você está fraco e lento..");
								p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 2));
								p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 2));
								p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 300, 2));
							}
						}, 30 * 20);
					}
				}
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (lord.contains(e.getEntity())) {
			lord.remove(e.getEntity());
		}
	}

	@EventHandler
	public void onDeath(PlayerQuitEvent e) {
		if (lord.contains(e.getPlayer())) {
			lord.remove(e.getPlayer());
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDano(EntityDamageByEntityEvent e) {
		if ((!(e.getEntity() instanceof Player)) || (!(e.getDamager() instanceof Player))) {
			return;
		}
		Player damager = (Player) e.getDamager();
		Player p = (Player) e.getEntity();
		double dano = e.getDamage();
		if ((KitManager.getInstance().getUsingKitName(damager).equalsIgnoreCase("Lord"))) {
			if (lord.contains(damager)) {
				e.setDamage(dano * 1.4);
				ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 1.0F, 0.0F, 0.0F, 15);
				ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 2.0F, 0.0F, 0.0F, 15);
				ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 15);

				ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 1.0F, 1.0F, -1.0F, 0.0F, 15);
				ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 2.0F, 2.0F, -2.0F, 0.0F, 15);
				ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 3.0F, 3.0F, -3.0F, 0.0F, 15);

				ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 1.0F, 0.0F, 0.0F, 15);
				ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 2.0F, 0.0F, 0.0F, 15);
				ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 15);

				ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 1.0F, 1.0F, -1.0F, 0.0F, 15);
				ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 2.0F, 2.0F, -2.0F, 0.0F, 15);
				ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 3.0F, 3.0F, -3.0F, 0.0F, 15);
				int points = 12;
				double size = 2;
				for (int i = 0; i < 360; i += 360 / points) {
					double angle = (i * Math.PI / 180);
					double x = size * Math.cos(angle);
					double z = size * Math.sin(angle);
					Location loc = damager.getLocation().add(x, 1, z);
					ParticleEffect.SPELL_MOB.send(Bukkit.getOnlinePlayers(), loc, 0, 0, 0, 1, 20);
					ParticleEffect.SPELL_WITCH.send(Bukkit.getOnlinePlayers(), loc, 0, 0, 0, 1, 20);
					ParticleEffect.TOWN_AURA.send(Bukkit.getOnlinePlayers(), loc, 0, 0, 0, 1, 20);
				}
			}
		}
		if ((KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Lord"))) {
			if (lord.contains(p)) {
				damager.setFireTicks(40);
				damager.sendMessage(StringUtils.avisovermelho + "§aO jogador que você está hitando é um lord. Cuidado!");
				p.playEffect(p.getLocation(), Effect.SMOKE, 1);
			}
		}
	}

}
