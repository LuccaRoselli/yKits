package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Reflector implements Listener {
	ArrayList<Player> reflector = new ArrayList<Player>();
	HashMap<Player, Boolean> msg = new HashMap<Player, Boolean>();
	HashMap<Player, Boolean> escudo = new HashMap<Player, Boolean>();

	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if ((KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Reflector"))
				&& (p.getItemInHand().getType() == Material.LEVER)) {
			e.setCancelled(true);
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
			} else {
				Uteis.addCooldown(p, 50);
				reflector.add(p);
				p.sendMessage(StringUtils.avisoverde + "Você ativou o modo §e§lREFLECTOR§7!");
				for (Entity en : p.getNearbyEntities(10, 10, 10)) {
					if (en instanceof Player) {
						((Player) en).sendMessage(StringUtils.avisovermelho + "§cO Jogador §4§l" + p.getName()
								+ " §cativou o modo §c§lREFLECTOR, portanto, ele irá §4§lrefletir danos por 15 segundos§c.");
					}
				}
				msg.put(p, false);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {

					@Override
					public void run() {
						reflector.remove(p);
						p.sendMessage(StringUtils.avisovermelho + "Você não está mais refletindo danos!");
					}
				}, 300L);
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player atacou = (Player) e.getDamager();
			final Player atacado = (Player) e.getEntity();
			if (KitManager.getInstance().getUsingKitName(atacado).equalsIgnoreCase("Reflector")) {
				if (reflector.contains(atacado)) {
					if (KitManager.getInstance().getUsingKitName(atacou).equalsIgnoreCase("Reflector")) {
						if (msg.get(atacado).booleanValue() == false) {
							atacado.sendMessage(StringUtils.avisovermelho + "O Jogador §3" + atacou.getName()
									+ " §7também é §e§lREFLECTOR§7");
							atacado.sendMessage(StringUtils.avisovermelho
									+ "Portanto, o efeito do seu reflector contra ele será §4§lANULADO§7!");
							msg.put(atacado, true);
						}
					} else {
						if (!msg.containsKey(atacou) || msg.get(atacou).booleanValue() == false) {
							atacou.sendMessage(StringUtils.avisovermelho + "§7O jogador §e" + atacado.getName()
									+ " §7é §bREFLECTOR§7, portanto, irá refletir os seus hits por 15 segundos.");
							msg.put(atacou, true);
						}
						atacou.damage(e.getDamage());
						if (!escudo.containsKey(atacado) || escudo.get(atacado).booleanValue() == false) {
							Uteis.playCircleParticules(atacado, ParticleEffect.CRIT);
							escudo.put(atacado, true);
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {

								@Override
								public void run() {
									escudo.put(atacado, false);
								}
							}, 40L);
						}
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (msg.containsKey(e.getPlayer())) {
			msg.remove(e.getPlayer());
			escudo.remove(e.getPlayer());
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (msg.containsKey(e.getEntity())) {
			msg.remove(e.getEntity());
			escudo.remove(e.getEntity());
		}
	}

}
