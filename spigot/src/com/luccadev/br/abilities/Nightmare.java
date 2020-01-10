package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.CombatRunnable;
import com.luccadev.br.utils.Uteis;

public class Nightmare implements Listener {

	public static HashMap<String, ArrayList<CombatRunnable>> task = new HashMap<String, ArrayList<CombatRunnable>>();

	public static void removeTimer(final Player p, final CombatRunnable run) {
		if (task.containsKey(p.getName()) && task.get(p.getName()).contains(run)) {
			run.stop();
			task.get(p.getName()).remove(run);
		}
	}

	public static void addTimer(final Player p, final CombatRunnable run) {
		if (!task.containsKey(p.getName())) {
			task.put(p.getName(), new ArrayList<CombatRunnable>());
		}
		task.get(p.getName()).add(run);
	}

	@EventHandler
	public void click(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (p instanceof Player) {
			if (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Nightmare")) {
				if (p.getItemInHand().getType() == Material.FLINT) {
					if (Uteis.hasCooldown(p)) {
						Uteis.sendCooldownMessage(p);
					} else {
						Uteis.addCooldown(p, 15);
						throwBats(p);
					}
				}
			}
		}
	}

	public static void throwBats(final Player player) {
		final ArrayList<Bat> bats = new ArrayList<Bat>();
		final Vector dir = player.getEyeLocation().getDirection();
		for (int x2 = 0; x2 <= 13; ++x2) {
			final Bat bat2 = (Bat) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.BAT);
			bat2.setVelocity(dir);
			bats.add(bat2);
		}
		final CombatRunnable run2 = new CombatRunnable() {
			public HashMap<Bat, ArrayList<Double>> d = new HashMap<Bat, ArrayList<Double>>();
			public ArrayList<String> infected = new ArrayList<String>();
			public int x = 0;

			@Override
			public void run() {
				if (this.x != 80) {
					final Random r = new Random();
					for (final Bat b : bats) {
						if (!this.d.containsKey(b)) {
							this.d.put(b, new ArrayList<Double>());
							final boolean minus = r.nextBoolean();
							final double y = minus ? (-(Math.random() / 1000.0)) : (Math.random() / 500.0);
							final boolean minus2 = r.nextBoolean();
							final double x = minus2 ? (-(Math.random() / 1000.0)) : (Math.random() / 500.0);
							final boolean minus3 = r.nextBoolean();
							final double z = minus3 ? (-(Math.random() / 1000.0)) : (Math.random() / 500.0);
							this.d.get(b).add(x);
							this.d.get(b).add(y);
							this.d.get(b).add(z);
						}
						final Vector v = new Vector(dir.getX() + this.d.get(b).get(0),
								dir.getY() + this.d.get(b).get(1), dir.getZ() + this.d.get(b).get(2));
						b.setVelocity(v);
						for (final Entity e : b.getNearbyEntities(1.0, 1.0, 1.0)) {
							if (e.getType() == EntityType.PLAYER) {
								final Player p = (Player) e;
								if (this.infected.contains(p.getName()) || p.getName() == player.getName()) {
									continue;
								}
								if (p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
									p.removePotionEffect(PotionEffectType.BLINDNESS);
								}
								p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 2));
								p.damage(5.0);
								this.infected.add(p.getName());
							}
						}
					}
				} else {
					removeTimer(player, this);
					this.stop();
				}
				++this.x;
			}

			@Override
			public void stop() {
				for (final Bat b : bats) {
					b.remove();
					b.setHealth(0.0);
				}
				bats.clear();
				this.infected.clear();
				this.d.clear();
				this.cancel();
			}
		};
		run2.runTaskTimer(Main.getMe(), 0L, 1L);
		addTimer(player, run2);
	}

}
