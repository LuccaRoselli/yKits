package com.luccadev.br.utils;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.warp.Warp;
import com.luccadev.br.manager.StatsManager;
import com.nametagedit.plugin.NametagEdit;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import us.myles.ViaVersion.api.ViaVersion;

@SuppressWarnings("deprecation")
public class Uteis {

	public static HashMap<Player, Long> cooldown = new HashMap<Player, Long>();

	public static ItemStack setItem(Material m, int qnt, String nome, List<String> lore) {
		ItemStack item = new ItemStack(m, qnt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack setItem(Material m, int qnt, String nome, ArrayList<String> lore) {
		ItemStack item = new ItemStack(m, qnt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack setItemComData(Material m, byte data, int qnt, String nome, List<String> lore) {
		ItemStack item = new ItemStack(m, qnt, (short) data);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(nome);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack create_item(Material mat, int qnt, int sh, String name, List<String> lore) {
		ItemStack i = new ItemStack(mat, qnt, (short) sh);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack createHead(int qnt, String nome, List<String> lore, String owner) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, qnt, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setDisplayName(nome);
		meta.setLore(lore);
		meta.setOwner(owner);
		item.setItemMeta(meta);
		return item;
	}

	public static int getRand(int min, int max) {
		int r = new SecureRandom().nextInt((max - min) + 1) + min;
		return r;
	}

	public static void addCooldown(final Player p, int seconds) {
		long cooldownLength;
		cooldownLength = System.currentTimeMillis() + seconds * 1000;
		cooldown.remove(p);
		cooldown.put(p, Long.valueOf(cooldownLength));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
			public void run() {
				cooldown.remove(p);
			}
		}, seconds * 20);
	}

	public static void sendCooldownMessage(Player p) {
		if (hasCooldown(p)) {
			p.sendMessage(StringUtils.avisovermelho + "Aguarde o cooldown de §e" + getCooldown(p) + "§7 segundos!");
			p.playSound(p.getLocation(), Sound.IRONGOLEM_HIT, 0.5f, 1.0f);
		}
	}

	public static String getShortStr(String s) {
		if (s.length() == 16) {
			String shorts = s.substring(0, s.length() - 6);
			return shorts;
		}
		if (s.length() == 15) {
			String shorts = s.substring(0, s.length() - 5);
			return shorts;
		}
		if (s.length() == 14) {
			String shorts = s.substring(0, s.length() - 4);
			return shorts;
		}
		if (s.length() == 13) {
			String shorts = s.substring(0, s.length() - 4);
			return shorts;
		}
		if (s.length() == 12) {
			String shorts = s.substring(0, s.length() - 2);
			return shorts;
		}
		if (s.length() == 11) {
			String shorts = s.substring(0, s.length() - 1);
			return shorts;
		}
		return s;
	}

	public static void sendTag(Player p) {
		if (p.getName().equalsIgnoreCase("luccadev")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add -kitpvp.tag.diretor");
			NametagEdit.getApi().setPrefix(p.getName(), "§e§lDEV §e");
			//NaametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor() + ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + p.getName());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " group set diretor");

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.diretor")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§4§lDIRETOR §4");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + p.getName());

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.coordenador")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§9§lCOORD §9");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.admin")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§c§lADM §c");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.mod")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§5§lMOD §5");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.trial")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§5§lTRIAL §d");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.youtuber")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§b§lYT §b");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.ultimate")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§d§lULTIMATE §d");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.ultra")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§a§lULTRA §a");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.pro")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§6§lPRO §6");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.maker")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§e§lMAKER §e");
			p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));
		} else {
			if (p.hasPermission(StringUtils.permissaoprefix + "tag.normal")) {
				NametagEdit.getApi().setPrefix(p.getName(), "§9§lBETA §7");
				p.setPlayerListName(NametagEdit.getApi().getNametag(p).getPrefix() + getShortStr(p.getName()));

			}
		}
	}
	
	/*
	public static void sendTag(Player p) {
		if (p.getName().equalsIgnoreCase("luccadev")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add -kitpvp.tag.diretor");
			NametagEdit.getApi().setPrefix(p.getName(), "§e§lDEV §e");
			//NaametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor() + ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§e" + p.getName());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " group set diretor");

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.diretor")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§4§lDIRETOR §4");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§4" + p.getName());

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.coordenador")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§9§lCOORD §9");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§9" + p.getName());

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.admin")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§c§lADM §c");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§c" + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.mod")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§5§lMOD §5");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§5" + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.trial")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§5§lTRIAL §d");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§d" + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.youtuber")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§b§lYT §b");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§b" + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.ultimate")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§d§lULTIMATE §d");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§d" + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.ultra")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§a§lULTRA §a");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§a" + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.pro")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§6§lPRO §6");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§6" + getShortStr(p.getName()));

		} else if (p.hasPermission(StringUtils.permissaoprefix + "tag.maker")) {
			NametagEdit.getApi().setPrefix(p.getName(), "§e§lMAKER §e");
			NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
					+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
			p.setPlayerListName("§e" + getShortStr(p.getName()));
		} else {
			if (p.hasPermission(StringUtils.permissaoprefix + "tag.normal")) {
				NametagEdit.getApi().setPrefix(p.getName(), "§9§lBETA §7");
				NametagEdit.getApi().setSuffix(p.getName(), " §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
						+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
				p.setPlayerListName("§7" + getShortStr(p.getName()));

			}
		}
	}
	*/

	public static void giveAndTakeMoneyAndXP(Player matador, Player vitima, boolean message) {
		int xp = getRand(0, 30);
		int coins = getRand(0, 100);

		int xplose = getRand(0, 20);
		int coinslose = getRand(0, 100);

		if (StatsManager.money.get(matador) != 0) {
			StatsManager.money.put(matador,
					Integer.valueOf(((Integer) StatsManager.money.get(matador)).intValue() + coins));
		} else {
			StatsManager.money.put(matador, Integer.valueOf(coins));
		}

		if (StatsManager.money.get(vitima) >= coinslose) {
			StatsManager.money.put(vitima,
					Integer.valueOf(((Integer) StatsManager.money.get(vitima)).intValue() - coinslose));
		} else {
			StatsManager.money.put(vitima, Integer.valueOf(0));
		}

		if (StatsManager.xp.get(vitima) >= xplose) {
			StatsManager.xp.put(vitima, Integer.valueOf(((Integer) StatsManager.xp.get(vitima)).intValue() - xplose));
		} else {
			StatsManager.xp.put(vitima, Integer.valueOf(0));
		}

		if (StatsManager.xp.get(matador) != 0) {
			StatsManager.xp.put(matador, Integer.valueOf(((Integer) StatsManager.xp.get(matador)).intValue() + xp));
		} else {
			StatsManager.xp.put(matador, Integer.valueOf(xp));
		}
		if (message) {
			vitima.sendMessage(StringUtils.avisovermelho + "§7Voce morreu para o jogador §b" + matador.getName());
			vitima.sendMessage(StringUtils.avisovermelho + "§7-§c" + coinslose + " §7Coin's.");
			vitima.sendMessage(StringUtils.avisovermelho + "§7-§c" + xplose + " §7XP's.");
			vitima.playSound(matador.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);

			matador.playSound(matador.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
			matador.sendMessage(StringUtils.avisoverde + "§7Voce matou o jogador §b" + vitima.getName());
			matador.sendMessage(StringUtils.avisoverde + "§7+§a" + coins + " §7Coin's.");
			matador.sendMessage(StringUtils.avisoverde + "§7+§a" + xp + " §7XP's.");

		}
	}

	public static void playCircleParticules(final Player p, final ParticleEffect efeito) {
		new BukkitRunnable() {
			double t = Math.PI / 4;
			Location loc = p.getLocation();

			public void run() {
				t = t + 0.1 * Math.PI;
				for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
					double x = Math.cos(theta);
					double y = 2 * Math.exp(-0.1 * t) * Math.sin(t);
					double z = Math.sin(theta);
					loc.add(x, y, z);
					efeito.send(Bukkit.getOnlinePlayers(), loc, 0, 0, 0, 0, 1);
					loc.subtract(x, y, z);
				}
				if (t > 3) {
					this.cancel();
				}
			}

		}.runTaskTimer(Main.getMe(), 0, 1);
	}

	public enum ConversorType {
		DAY, HOUR, MINUTE, SECOND
	}

	public static int toTick(ConversorType tipo, int i) {
		switch (tipo) {
		case DAY:
			return i * 20 * 60 * 60 * 24;
		case HOUR:
			return i * 20 * 60 * 60;
		case MINUTE:
			return i * 20 * 60;
		case SECOND:
			return i * 20;
		}
		return 0;
	}

	public static int tickToSecond(int i) {
		return i * 20;
	}

	public static int tickToMinute(int i) {
		return i * 1200;
	}

	/*
	 * public static void resetStreak(Player p){ if
	 * (!KillStreak.playerExists(p)){ return; }
	 * 
	 * KillStreak.ks.put(p, 0); }
	 */

	public static Location getLocation(String s) {
		if (!(s == null)) {
			String[] loc = s.split(":");
			return new Location(Bukkit.getWorld(loc[0]), Double.valueOf(loc[1]).doubleValue(),
					Double.valueOf(loc[2]).doubleValue(), Double.valueOf(loc[3]).doubleValue(),
					Float.valueOf(loc[4]).floatValue(), Float.valueOf(loc[5]).floatValue());
		}
		return Bukkit.getWorld("world").getSpawnLocation();
	}

	public static boolean isInsideCuboID(Player p, Location loc1, Location loc2) {
		if (loc1 == null || loc2 == null)
			return false;
		if (loc1.getWorld() != loc2.getWorld())
			return false;
		int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
		int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		int px = p.getLocation().getBlockX();
		int py = p.getLocation().getBlockY();
		int pz = p.getLocation().getBlockZ();
		if (loc1.getWorld() == p.getWorld()) {
			if (px >= x1 && px <= x2) {
				if (py >= y1 && py <= y2) {
					if (pz >= z1 && pz <= z2) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void setLocation(String s, Location l) {
		String location = String.valueOf(l.getWorld().getName()) + ":" + String.valueOf(l.getX()) + ":"
				+ String.valueOf(l.getY()) + ":" + String.valueOf(l.getZ()) + ":" + String.valueOf(l.getYaw()) + ":"
				+ String.valueOf(l.getPitch());
		Main.getMe().getConfig().set(s, location);
		Main.getMe().saveConfig();
	}

	public static String getCooldown(Player p) {
		long cooldownLength = ((Long) cooldown.get(p)).longValue();
		long left = (cooldownLength - System.currentTimeMillis()) / 1000L;
		return left + "";
	}

	public static boolean hasCooldown(Player p) {
		return cooldown.containsKey(p);
	}

	public static void removeCooldown(Player p) {
		cooldown.remove(p);
	}

	public static void setSpawnItems(Player p) {
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setHeldItemSlot(0);
		p.getInventory().setItem(0, setItem(Material.getMaterial(386), 1, "§eKits", Arrays
				.asList("§7Clique para selecionar seu kit", "§7ou visualizar os kits", "§7mensais disponíveis!")));
		p.getInventory().setItem(2, setItem(Material.GOLD_NUGGET, 1, "§eLoja",
				Arrays.asList("§7Clique para visualizar", "§7a loja de kits!")));
		p.getInventory().setItem(4, setItem(Material.MAGMA_CREAM, 1, StringUtils.getPrefix('b'),
				Arrays.asList("§7Clique para visualizar", "§7informações sobre o servidor!")));
		p.getInventory().setItem(6, setItem(Material.COMPASS, 1, "§eWarps",
				Arrays.asList("§7Clique para visualizar", "§7as warps disponíveis!")));
		p.getInventory().setItem(8, setItem(Material.ENCHANTED_BOOK, 1, "§eSkills",
				Arrays.asList("§7Clique para adquirir", "§7skills para seus kits!")));
	}

	public static void tpSpawn(Player p) {
		Location loc = Bukkit.getWorld("world").getSpawnLocation();
		p.teleport(loc);
		if (Warp.isInWarp(p))
			Warp.getPlayerWarp(p).removePlayer(p);
	}

	@SuppressWarnings("unused")
	public static String formatWithPoints(double grana) {
		DecimalFormat s = new DecimalFormat();
		double i = grana;
		String a = s.format(grana);
		if (a.endsWith(".")) {
			a = a.substring(0, a.length() - 1);
		}
		return a;
	}

	public static void sendTablist(Player player, String Header, String Footer) {
		if (ViaVersion.getInstance().getPlayerVersion(player) <= 5) {
			return;
		}
		try {
			IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{'text': '" + Header + "'}");
			IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{'text': '" + Footer + "'}");
			PacketPlayOutPlayerListHeaderFooter packetPlayOut = new PacketPlayOutPlayerListHeaderFooter(header);
			Field field = packetPlayOut.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(packetPlayOut, footer);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOut);
		} catch (Exception exception) {
		}
	}

	public static void teleportRandom(final Player player, int xRadius, int zRadius) {
		player.teleport(new Location(player.getWorld(), -1132, 30, 3993));
		if (!player.getLocation().getChunk().isLoaded()) {
			player.getLocation().getChunk().load();
		}
		final Location newLocation;
		final Chunk targetChunk;

		newLocation = player.getLocation();

		Random random = new Random();

		newLocation.add(random.nextInt(xRadius * 2) - xRadius - 20, 0, random.nextInt(zRadius * 2) - zRadius - 20);

		targetChunk = player.getWorld().getChunkAt(newLocation);

		player.getWorld().refreshChunk(targetChunk.getX(), targetChunk.getZ());
		targetChunk.load();
		while (newLocation.getBlock().getType() != Material.AIR) {
			newLocation.add(0, 1, 0);
		}
		player.teleport(newLocation);
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 10));
	}

	public static FireworkEffect getRandomEffect() {
		return FireworkEffect.builder().with(Type.BALL).withColor(Color.RED).build();
	}

	public static void addPotePotionSplash(Inventory inv, PotionType pt, int forca, int quantidade) {
		Potion splash = new Potion(pt, forca);
		splash.setSplash(true);
		inv.addItem(new ItemStack(splash.toItemStack(quantidade)));
	}

	public static void addPotePotion(Inventory inv, PotionType pt, int forca, int quantidade) {
		Potion splash = new Potion(pt, forca);
		inv.addItem(new ItemStack(splash.toItemStack(quantidade)));
	}
}
