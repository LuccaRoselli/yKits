package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.CombatRunnable;
import com.luccadev.br.utils.Uteis;

public class Avatar implements Listener {

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

	public static HashMap<Bat, String> estado = new HashMap<Bat, String>();

	public static void throwBats(final Player player, String modo) {
		final ArrayList<Bat> bats = new ArrayList<Bat>();
		final Vector dir = player.getEyeLocation().getDirection();
		for (int x2 = 0; x2 <= 6; ++x2) {
			final Bat bat2 = (Bat) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.BAT);
			bat2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3));
			bat2.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 3));
			bat2.setVelocity(dir);
			bats.add(bat2);
			estado.put(bat2, modo);
		}
		for (Bat b : bats) {
			if (estado.get(b).equalsIgnoreCase("Agua")) {

				Location location = player.getEyeLocation();
				BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, 40);
				while (blocksToAdd.hasNext()) {
					Location blockToAdd = blocksToAdd.next().getLocation();
					player.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.WATER, 40);
					player.playSound(blockToAdd, Sound.IRONGOLEM_THROW, 3.0F, 3.0F);
				}
			} else if (estado.get(b).equalsIgnoreCase("Fogo")) {

				Location location = player.getEyeLocation();
				BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, 60);
				while (blocksToAdd.hasNext()) {
					Location blockToAdd = blocksToAdd.next().getLocation();
					player.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.LAVA, 40);
					player.playSound(blockToAdd, Sound.IRONGOLEM_THROW, 3.0F, 3.0F);
				}
			} else if (estado.get(b).equalsIgnoreCase("Terra")) {

				Location location = player.getEyeLocation();
				BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, 40);
				while (blocksToAdd.hasNext()) {
					Location blockToAdd = blocksToAdd.next().getLocation();
					player.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.GRASS, 40);
					player.playSound(blockToAdd, Sound.IRONGOLEM_THROW, 3.0F, 3.0F);
				}
			} else if (estado.get(b).equalsIgnoreCase("Ar")) {

				Location location = player.getEyeLocation();
				BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, 60);
				while (blocksToAdd.hasNext()) {
					Location blockToAdd = blocksToAdd.next().getLocation();
					player.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.QUARTZ_BLOCK, 40);
					player.playSound(blockToAdd, Sound.IRONGOLEM_THROW, 3.0F, 3.0F);
				}
			}
		}
		final CombatRunnable run2 = new CombatRunnable() {
			public HashMap<Bat, ArrayList<Double>> d = new HashMap<Bat, ArrayList<Double>>();
			public ArrayList<String> infected = new ArrayList<String>();
			public int x = 0;

			@Override
			public void run() {
				if (this.x != 23) {
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
								if (estado.get(b).equalsIgnoreCase("Agua")) {
									p.damage(12.0D);
									p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
									p.setVelocity(p.getLocation().getDirection().multiply(-1));

								} else if (estado.get(b).equalsIgnoreCase("Fogo")) {
									p.setFireTicks(400);
									p.damage(12.0D);
									p.setVelocity(p.getLocation().getDirection().multiply(-1));

								} else if (estado.get(b).equalsIgnoreCase("Terra")) {
									p.damage(12.0D);
									p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0));
									p.setVelocity(p.getLocation().getDirection().multiply(-1));

								} else if (estado.get(b).equalsIgnoreCase("Ar")) {
									p.damage(14.0D);
									p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
									p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
									p.setVelocity(p.getLocation().getDirection().multiply(-1));

								}
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, Long> cooldown = new HashMap();

	@EventHandler
	public void fogo(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("avatar"))
				&& (p.getItemInHand().getType() == Material.WOOL) && (p.getItemInHand().getDurability() == 14)) {
			if (!Uteis.hasCooldown(p)) {
				throwBats(p, "fogo");
				Uteis.addCooldown(p, 15);
			} else {
				Uteis.sendCooldownMessage(p);
			}
		}
	}

	@EventHandler
	public void agua(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("avatar"))
				&& (p.getItemInHand().getType() == Material.WOOL) && (p.getItemInHand().getDurability() == 11)) {
			if (!Uteis.hasCooldown(p)) {
				throwBats(p, "agua");
				Uteis.addCooldown(p, 15);
			} else {
				Uteis.sendCooldownMessage(p);
			}
		}
	}

	@EventHandler
	public void ar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("avatar"))
				&& (p.getItemInHand().getType() == Material.WOOL) && (p.getItemInHand().getDurability() == 0)) {
			if (!Uteis.hasCooldown(p)) {
				throwBats(p, "ar");
				Uteis.addCooldown(p, 15);
			} else {
				Uteis.sendCooldownMessage(p);
			}
		}
	}

	@EventHandler
	public void terra(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("avatar"))
				&& (p.getItemInHand().getType() == Material.WOOL) && (p.getItemInHand().getDurability() == 13)) {
			if (!Uteis.hasCooldown(p)) {
				throwBats(p, "terra");
				Uteis.addCooldown(p, 15);
			} else {
				Uteis.sendCooldownMessage(p);
			}
		}
	}

	public static ArrayList<String> Red2 = new ArrayList<String>();
	public static ArrayList<String> Ferro2 = new ArrayList<String>();
	public static ArrayList<String> AvatarH = new ArrayList<String>();
	public static ArrayList<String> Terra2 = new ArrayList<String>();

	@EventHandler
	public void Trocar(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		ItemStack Terra = new ItemStack(Material.WOOL, 1, (short) 13);
		ItemMeta terram = Terra.getItemMeta();
		terram.setDisplayName("§aTerra");
		Terra.setItemMeta(terram);

		ItemStack Agua = new ItemStack(Material.WOOL, 1, (short) 11);
		ItemMeta Aguam = Agua.getItemMeta();
		Aguam.setDisplayName("§bAgua");
		Agua.setItemMeta(Aguam);

		ItemStack AR = new ItemStack(Material.WOOL);
		ItemMeta ARm = AR.getItemMeta();
		ARm.setDisplayName("§5§oAvatar Hability");
		AR.setItemMeta(ARm);

		ItemStack Fogo = new ItemStack(Material.WOOL, 1, (short) 14);
		ItemMeta Fogom = Fogo.getItemMeta();
		Fogom.setDisplayName("§cFogo");
		Fogo.setItemMeta(Fogom);
		if (((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("avatar"))
				&& (p.getItemInHand().equals(AR))) {
			p.setItemInHand(Agua);
			Ferro2.remove(p.getName());
			AvatarH.add(p.getName());
		} else if ((AvatarH.contains(p.getName()))
				&& ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("avatar"))
				&& (p.getItemInHand().equals(Agua))) {
			p.setItemInHand(Terra);
			AvatarH.remove(p.getName());
			Terra2.add(p.getName());
		} else if ((Terra2.contains(p.getName())) && (Terra2.contains(p.getName()))
				&& ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("avatar"))
				&& (p.getItemInHand().equals(Terra))) {
			p.setItemInHand(Fogo);
			Terra2.remove(p.getName());
			Red2.add(p.getName());
		} else if ((Red2.contains(p.getName()))
				&& ((e.getAction() == Action.LEFT_CLICK_AIR) || (e.getAction() == Action.LEFT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("avatar"))
				&& (p.getItemInHand().equals(Fogo))) {
			p.setItemInHand(AR);
			Red2.remove(p.getName());
			Ferro2.add(p.getName());
		}
	}

	@EventHandler
	public void dano(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Snowball))) {
			Snowball s = (Snowball) e.getDamager();
			Player p = (Player) e.getEntity();
			if (s.hasMetadata("fogo")) {
				e.setDamage(e.getDamage() + 15.0D);
				p.setFireTicks(400);
			}
		}
	}

	@EventHandler
	public void dano2(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Snowball))) {
			Snowball s = (Snowball) e.getDamager();
			Player p = (Player) e.getEntity();
			if (s.hasMetadata("agua")) {
				e.setDamage(e.getDamage() + 15.0D);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
			}
		}
	}

	@EventHandler
	public void dano3(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Snowball))) {
			Snowball s = (Snowball) e.getDamager();
			Player p = (Player) e.getEntity();
			if (s.hasMetadata("terra")) {
				e.setDamage(e.getDamage() + 15.0D);
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
			}
		}
	}

	@EventHandler
	public void dano4(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Snowball))) {
			Snowball s = (Snowball) e.getDamager();
			Player p = (Player) e.getEntity();
			if (s.hasMetadata("ar")) {
				e.setDamage(e.getDamage() + 15.0D);
				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
				Vector v = p.getLocation().getDirection().multiply(0).setY(1.0D);
				p.setVelocity(v);
			}
		}
	}

}
