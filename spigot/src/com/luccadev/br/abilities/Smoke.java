package com.luccadev.br.abilities;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Smoke implements Listener {
	Integer shed_id = null;
	static HashMap<Player, Integer> as = new HashMap<Player, Integer>();

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (as.containsKey(e.getEntity())) {
			Bukkit.getScheduler().cancelTask(((Integer) Smoke.as.get(e.getEntity())).intValue());
			Smoke.as.remove(e.getEntity());
			Smoke.itemp.get(e.getEntity()).remove();
			Smoke.itemp.remove(e.getEntity());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (as.containsKey(e.getPlayer())) {
			Bukkit.getScheduler().cancelTask(((Integer) Smoke.as.get(e.getPlayer())).intValue());
			Smoke.as.remove(e.getPlayer());
			Smoke.itemp.get(e.getPlayer()).remove();
			Smoke.itemp.remove(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onTp(PlayerTeleportEvent e) {
		if (as.containsKey(e.getPlayer())) {
			Bukkit.getScheduler().cancelTask(((Integer) Smoke.as.get(e.getPlayer())).intValue());
			Smoke.as.remove(e.getPlayer());
			Smoke.itemp.get(e.getPlayer()).remove();
			Smoke.itemp.remove(e.getPlayer());
		}
	}
	
	public static HashMap<Player, Item> itemp = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Smoke")) {
			if (p.getItemInHand().getType() != Material.getMaterial(351)) {
				return;
			}
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
				return;
			}
			final Item it = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.getMaterial(351)));
			it.setVelocity(p.getLocation().getDirection().multiply(1));
			it.setPickupDelay(2147483647);
			it.setMetadata("smoke", new FixedMetadataValue(Main.getMe(), "smoke"));
			itemp.put(p, it);
			Uteis.addCooldown(p, 20);
			int a = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getMe(), new Runnable() {
				public void run() {
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 0.0F, 0.0F, 0.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 1.0F, 0.0F, 1.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 2.0F, 0.0F, 2.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 3.0F, 0.0F, 3.0F, 0.0F, 50);

					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 50);

					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 5.0F, 5.0F, 5.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 5.0F, 5.0F, 5.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 5.0F, 5.0F, 5.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 5.0F, 5.0F, 5.0F, 0.0F, 50);

					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 5.0F, 3.0F, 5.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 5.0F, 3.0F, 5.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 5.0F, 3.0F, 5.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 5.0F, 3.0F, 5.0F, 0.0F, 50);

					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 1.0F, 3.0F, 1.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 2.0F, 3.0F, 2.0F, 0.0F, 50);
					ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), it.getLocation(), 3.0F, 3.0F, 3.0F, 0.0F, 50);
					for (Entity en : it.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
						if (((en instanceof Player)) && (en != p)) {
							((Player) en).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 50));
							((Player) en).damage(2.0D);
						}
					}
				}
			}, 0L, 3L);
			as.put(p, Integer.valueOf(a));
			Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getMe(), new Runnable() {
				public void run() {
					if (as.containsKey(p)) {
						Bukkit.getScheduler().cancelTask(((Integer) Smoke.as.get(p)).intValue());
						Smoke.as.remove(p);
						Smoke.itemp.get(p).remove();
						Smoke.itemp.remove(p);
					}
				}
			}, 160L);
		}
	}
}
