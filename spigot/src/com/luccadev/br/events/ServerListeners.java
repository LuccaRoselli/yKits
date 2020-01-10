package com.luccadev.br.events;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.help.HelpTopic;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.devlucca.br.constructors.Rank.OrderType;
import com.devlucca.br.constructors.Rank.User;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.luccadev.br.Main;
import com.luccadev.br.Main.Top10Type;
import com.luccadev.br.commands.AdminChat;
import com.luccadev.br.commands.FakeCommand;
import com.luccadev.br.experience.ExperienceRank;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.PlayerConfigFile;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.UpdateEvent;
import com.luccadev.br.utils.Uteis;
import com.nametagedit.plugin.NametagEdit;

import net.minecraft.server.v1_8_R3.EntityVillager;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;

public class ServerListeners implements Listener {

	@EventHandler
	public void onColors(SignChangeEvent e) {
		if (e.getLine(0).contains("&")) {
			e.setLine(0, e.getLine(0).replace("&", "§"));
		}
		if (e.getLine(1).contains("&")) {
			e.setLine(1, e.getLine(1).replace("&", "§"));
		}
		if (e.getLine(2).contains("&")) {
			e.setLine(2, e.getLine(2).replace("&", "§"));
		}
		if (e.getLine(3).contains("&")) {
			e.setLine(3, e.getLine(3).replace("&", "§"));
		}
	}

	@EventHandler
	public void noRain(WeatherChangeEvent event) {
		if (event.toWeatherState()) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onExplode(final BlockExplodeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		e.setCancelled(true);
	}

	public void blockRegen(final HashMap<Location, Material> blocks) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
			@Override
			public void run() {
				if (!blocks.isEmpty()) {
					HashMap<Double, Location> hashList = new HashMap<>();
					for (Location loc : blocks.keySet()) {
						hashList.put(loc.getY(), loc);
					}
					List<Double> doubles = new ArrayList<>();
					for (double d : hashList.keySet()) {
						doubles.add(d);
					}
					Location loc = hashList.get(doubles.get(minIndex(doubles)));
					Material b = blocks.get(loc);
					loc.getWorld().getBlockAt(loc).setType(b);
					for (Entity ent : loc.getWorld().getNearbyEntities(loc, 15D, 15D, 15D)) {
						if (ent instanceof Player) {
							((Player) ent).playSound(loc, Sound.CHICKEN_EGG_POP, 1F, 1F);
						}
					}
					blocks.remove(loc);
					blockRegen(blocks);
				}
			}
		}, 1);
	}

	public static int minIndex(List<Double> list) {
		return list.indexOf(Collections.min(list));
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if ((e.getDamager() instanceof Player)) {
			Player p = (Player) e.getDamager();
			if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
				for (PotionEffect ef : p.getActivePotionEffects()) {
					if (ef.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
						double damageP = (ef.getAmplifier() + 1) * 1.3D + 1.0D;
						int damage;
						if (e.getDamage() / damageP <= 1.0D) {
							damage = (ef.getAmplifier() + 1) * 3 + 1;
						} else {
							damage = (int) (e.getDamage() / damageP) + (ef.getAmplifier() + 1) * 3;
						}
						e.setDamage(damage);

						break;
					}
				}
			}
		}
	}

	public static Map<String, Long> delays = new HashMap<String, Long>();
	public static Map<String, Long> delayenchant = new HashMap<String, Long>();

	@EventHandler(priority = EventPriority.LOW)
	public void chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission(StringUtils.permissaoprefix + "chat.flood")
				|| p.hasPermission(StringUtils.permissaoprefix + "staff")) {
			return;
		}
		Long agora = Long.valueOf(System.currentTimeMillis() / 1000L);
		if (delays.containsKey(p.getName())) {
			Long delay = (Long) delays.get(p.getName());
			if (agora.longValue() - delay.longValue() < 3L) {
				p.sendMessage(ChatColor.RED + "Calma apressado!");
				e.setCancelled(true);
				return;
			}
			delays.remove(p.getName());
		}
		delays.put(p.getName(), agora);
	}

	public static boolean feztop10;
	
	@EventHandler
	public void onUpdate(final UpdateEvent e) {
		if (e.getType() == UpdateEvent.UpdateType.MINUTE) {
			if (feztop10 == false){
				feztop10 = true;
				Main.doTop10(Top10Type.WINS);
				Main.doTop10(Top10Type.XP);
				try {
					for (Entity ed : Bukkit.getWorld("world").getEntities()){
						if (ed instanceof Villager){
							ed.remove();
						}
					}
					Main.spawnNPC();
					
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException
						| IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				Uteis.sendTablist(p,
						"§e§lLithe§f§lMC§f -§7 Rede de Servidores\n §eJogadores:§7 " + Bukkit.getOnlinePlayers().size()
								+ " §7| §ePing: §7" + ((CraftPlayer) p).getHandle().ping + " ",
						"\n §eTeamSpeak:§7 ts3.lithemc.com.br | §eSite: §7wwww.lithemc.com.br \n §eLoja:§7 store.lithemc.com.br | §eDiscord: §7discord.lithemc.com.br \n §eTwitter: §7@LitheMC");
			}
		}
	}

	@EventHandler
	public void onPing(ServerListPingEvent e)
			throws IllegalArgumentException, UnsupportedOperationException, Exception {
		e.setMotd("                    §eLithe§fMC §7| §f§lKitPvP§r\n                   §7§nwww.lithemc.com.br");
		/*
		 * OfflinePlayer off =
		 * (OfflinePlayer)Join.motdname.get(e.getAddress().toString());
		 * if(!Join.motdname.containsKey(e.getAddress().toString())) { try {
		 * e.setServerIcon(Bukkit.loadServerIcon(generateImage("Steve"))); }
		 * catch (Exception ex) {} } else { try {
		 * e.setServerIcon(Bukkit.loadServerIcon(generateImage(off.getName())));
		 * } catch (Exception exe) {} }
		 */
	}

	public BufferedImage generateImage(String p) {
		try {
			URL url = new URL("https://mcapi.ca/avatar/2d/" + p + "/64");
			BufferedImage image = ImageIO.read(url);
			BufferedImage finalImage = new BufferedImage(64, 64, Image.SCALE_SMOOTH);
			finalImage.getGraphics().drawImage(image, 0, 0, 64, 64, null);
			finalImage.getGraphics().drawImage(image, 0, 0, 64, 64, null);
			return finalImage;
		} catch (Exception e) {
		}
		return null;
	}

	@EventHandler
	public void chatFormat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage().contains("▒") || e.getMessage().contains("%")) {
			e.setCancelled(true);
			return;
		}
		if (p.hasPermission(StringUtils.permissaoprefix + "chat.colorido")
				|| p.hasPermission(StringUtils.permissaoprefix + "staff")) {
			if (!FakeCommand.fakehash.containsKey(p))
				e.setMessage("§b" + e.getMessage());
		}
		if (p.hasPermission(StringUtils.permissaoprefix + "chat.coloridoyoutuber")) {
			e.setMessage(e.getMessage().replace("&", "§"));
		}
		System.out.println(NametagEdit.getApi().getNametag(p).getPrefix().toString() + "string da tag");
		e.setFormat(
				"§8❮§" + ExperienceRank.getPlayerRank(p).getSymbolColor() + ExperienceRank.getPlayerRank(p).getSymbol()
						+ " §8┃ §" + NametagEdit.getApi().getNametag(p).getPrefix().split("§")[1] + p.getName()
						+ "§8❯ §7" + e.getMessage());
	}

	@EventHandler
	public void criar(SignChangeEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission(StringUtils.permissaoprefix + "sign")
				|| p.hasPermission(StringUtils.permissaoprefix + "staff")) {
			if (e.getLine(0).equalsIgnoreCase("recraft")) {
				e.setLine(0, "§6§l▇ ▇ ▇ ▇ ▇ ▇");
				e.setLine(1, StringUtils.getPrefix('b'));
				e.setLine(2, "§e§lRe§3§lCraft");
				e.setLine(3, "§6§l▇ ▇ ▇ ▇ ▇ ▇");
			} else {
				if (e.getLine(0).equalsIgnoreCase("reparar")) {
					e.setLine(0, "§a§l▇ ▇ ▇ ▇ ▇ ▇");
					e.setLine(1, StringUtils.getPrefix('b'));
					e.setLine(2, "§2§lReparar");
					e.setLine(3, "§a§l▇ ▇ ▇ ▇ ▇ ▇");
				}
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void clicar2(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && ((e.getClickedBlock().getType() == Material.SIGN_POST)
				|| (e.getClickedBlock().getType() == Material.WALL_SIGN))) {
			Sign s = (Sign) e.getClickedBlock().getState();
			if (s.getLine(1).equalsIgnoreCase("§lSOPA") || s.getLine(1).equalsIgnoreCase("§lRECRAFT")) {
				p.getInventory().setItem(13, KitManager.getPote(64));
				p.getInventory().setItem(14, KitManager.getRedMush(64));
				p.getInventory().setItem(15, KitManager.getBrownMush(64));
				p.updateInventory();
				p.damage(3.0D);
				s.setLine(0, "§6§l▇ ▇ ▇ ▇ ▇ ▇");
				s.setLine(1, StringUtils.getPrefix('b'));
				s.setLine(2, "§e§lRe§3§lCraft");
				s.setLine(3, "§6§l▇ ▇ ▇ ▇ ▇ ▇");
				s.update();
				p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 10.0F, 10.0F);
				p.sendMessage(StringUtils.avisoverde + "§7Você pegou §a§lRECRAFT");
			} else if (s.getLine(2).equalsIgnoreCase("§2§lReparar")) {
				for (ItemStack is : p.getInventory().getContents()) {
					try {
						is.setDurability((short) 0);

					} catch (NullPointerException localNullPointerException) {
					}
				}
				for (ItemStack is : p.getEquipment().getArmorContents()) {
					try {
						if (is.getType().name().contains("sword")) {
							is.setDurability((short) 0);
						}
					} catch (NullPointerException localNullPointerException1) {
					}
				}
				s.setLine(0, "§a§l▇ ▇ ▇ ▇ ▇ ▇");
				s.setLine(1, StringUtils.getPrefix('b'));
				s.setLine(2, "§2§lReparar");
				s.setLine(3, "§a§l▇ ▇ ▇ ▇ ▇ ▇");
				s.update();
				p.sendMessage(StringUtils.avisoverde + "§7Seus items foram §a§lreparados §7com sucesso.");
			}
		}
	}

	@EventHandler
	public void clicar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && ((e.getClickedBlock().getType() == Material.SIGN_POST)
				|| (e.getClickedBlock().getType() == Material.WALL_SIGN))) {
			Sign s = (Sign) e.getClickedBlock().getState();
			if (s.getLine(2).equalsIgnoreCase("§e§lRe§3§lCraft")) {
				p.getInventory().setItem(13, KitManager.getPote(64));
				p.getInventory().setItem(14, KitManager.getRedMush(64));
				p.getInventory().setItem(15, KitManager.getBrownMush(64));
				p.updateInventory();
				p.damage(3.0D);
				s.setLine(0, "§6§l▇ ▇ ▇ ▇ ▇ ▇");
				s.setLine(1, StringUtils.getPrefix('b'));
				s.setLine(2, "§e§lRe§3§lCraft");
				s.setLine(3, "§6§l▇ ▇ ▇ ▇ ▇ ▇");
				s.update();
				p.playSound(p.getLocation(), Sound.FIRE_IGNITE, 10.0F, 10.0F);
				p.sendMessage(StringUtils.avisoverde + "§7Você pegou §a§lRECRAFT");
			} else if (s.getLine(2).equalsIgnoreCase("§2§lReparar")) {
				for (ItemStack is : p.getInventory().getContents()) {
					try {
						is.setDurability((short) 0);
					} catch (NullPointerException localNullPointerException) {
					}
				}
				for (ItemStack is : p.getEquipment().getArmorContents()) {
					try {
						if (is.getType().name().contains("sword")) {
							is.setDurability((short) 0);
						}
					} catch (NullPointerException localNullPointerException1) {
					}
				}
				p.sendMessage(StringUtils.avisoverde + "§7Seus items foram §a§lreparados §7com sucesso.");
				s.setLine(0, "§a§l▇ ▇ ▇ ▇ ▇ ▇");
				s.setLine(1, StringUtils.getPrefix('b'));
				s.setLine(2, "§2§lReparar");
				s.setLine(3, "§a§l▇ ▇ ▇ ▇ ▇ ▇");
				s.update();
			}
		}
	}

	public void tpall(Player p, Player p2) {
		p2.teleport(p.getLocation());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if (e.getMessage().toLowerCase().startsWith("/money")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringUtils.avisovermelho + "§cUtilize /rank ou /rank <PLAYER>");
		}
		if (e.getMessage().toLowerCase().startsWith("/me")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringUtils.avisovermelho + "§cVocê não pode fazer isto!");
		}
		if (e.getMessage().toLowerCase().startsWith("/pl")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringUtils.avisovermelho + "§ePlugin feito por §7LUCCADEV!");
		}
		if (e.getMessage().toLowerCase().startsWith("/plugins")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringUtils.avisovermelho + "§ePlugin feito por §7LUCCADEV!");
		}
		if (e.getMessage().toLowerCase().startsWith("/bukkit:pl")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringUtils.avisovermelho + "§ePlugin feito por §7LUCCADEV!");
		}
		if (e.getMessage().toLowerCase().startsWith("/bukkit:plugins")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringUtils.avisovermelho + "§ePlugin feito por §7LUCCADEV!");
		}
		if (e.getMessage().toLowerCase().startsWith("/?")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringUtils.avisovermelho + "§ePlugin feito por §7LUCCADEV!");
		}
		if (e.getMessage().toLowerCase().startsWith("/bukkit:?")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringUtils.avisovermelho + "§ePlugin feito por §7LUCCADEV!");
		}
		if (e.getMessage().toLowerCase().startsWith("/npc")){
			e.setCancelled(true);
			@SuppressWarnings("deprecation")
			Villager villager = (Villager)e.getPlayer().getWorld().spawnCreature(e.getPlayer().getLocation(), EntityType.VILLAGER);
			villager.setProfession(Villager.Profession.LIBRARIAN);
			
			EntityVillager nmsVillager = ((CraftVillager) villager).getHandle();
			//Get the field
			Field goal = nmsVillager.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("goalSelector");
			goal.setAccessible(true);//remove the protected modifier
			 
			 
			//Evilly Remove the final modifier
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(goal, goal.getModifiers() & ~Modifier.FINAL);//Evilness shall ensue
			PathfinderGoalSelector goalSelector = new PathfinderGoalSelector(nmsVillager.world != null && nmsVillager.world.methodProfiler != null ? nmsVillager.world.methodProfiler : null);
			goal.set(nmsVillager, goalSelector);
			
			Hologram h = HologramsAPI.createHologram(Main.getMe(), villager.getLocation().add(0, 2.9, 0));
			System.out.println(e.getPlayer().getLocation().getY());
			h.insertTextLine(0, "§a§lCAIXAS");
			h.insertTextLine(1, "§7Aqui você poderá comprar/abrir caixas");
			h.insertTextLine(2, "§7e ganhar §akits §7aleatoriamente!");
		}
		if (e.getMessage().toLowerCase().startsWith("/spawnpos1")) {
			e.setCancelled(true);
			if (e.getPlayer().getName().equalsIgnoreCase("luccadev")) {
				Uteis.setLocation("Spawn.pos1", e.getPlayer().getLocation());
				System.out.println("setou");
			}
		}
		if (e.getMessage().toLowerCase().startsWith("/spawnpos2")) {
			e.setCancelled(true);
			if (e.getPlayer().getName().equalsIgnoreCase("luccadev")) {
				Uteis.setLocation("Spawn.pos2", e.getPlayer().getLocation());
				System.out.println("setou");
			}
		}
		if (e.getMessage().toLowerCase().startsWith("/holotopkills")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage("feitao kills");
			if (e.getPlayer().getName().equalsIgnoreCase("luccadev")) {
				com.devlucca.br.constructors.Rank r = new com.devlucca.br.constructors.Rank();
				for (String ac : PlayerConfigFile.kills) {
					r.add(ac.split("-")[0], Double.valueOf(ac.split("-")[1]));
				}
				ArrayList<User> quests = r.getOrderType(OrderType.LARGER);
				Hologram h = HologramsAPI.createHologram(Main.getMe(), e.getPlayer().getLocation());
				h.appendTextLine("§a§lTOP KILLS");
				for (int i = 0; i < 5; i++) {
					User user = quests.get(i);
					Bukkit.getConsoleSender().sendMessage(
							"§b" + (i + 1) + " Lugar: §3" + user.getUser() + " §7- §6" + user.getValue() + " kills");
					h.appendTextLine(String.valueOf(
							"§b" + (i + 1) + " Lugar: §3" + user.getUser() + " §7- §6" + user.getValue() + " kills")
							.replace(".0 kills", " kills"));
				}
			}

		}
		if (e.getMessage().toLowerCase().startsWith("/tpall")) {
			if (e.getPlayer().hasPermission(StringUtils.permissaoprefix + "comando.tpall")) {
				e.getPlayer().sendMessage(
						StringUtils.getPrefix('a') + "Todos os jogadores foram teletransportados ate você!");
				for (Player p1 : Bukkit.getOnlinePlayers()) {
					tpall(e.getPlayer(), p1);
				}
				e.setCancelled(true);
			}
		}
		if (!e.isCancelled()) {
			Player p = e.getPlayer();
			String cmd = e.getMessage().split(" ")[0];
			HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(cmd);
			if (topic == null) {
				p.sendMessage(StringUtils.avisovermelho + "§cComando inexistente ou inválido!");
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void foodchangeevent(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void reduzirDano(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Villager)
			event.setCancelled(true);
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		if (!(event.getDamager() instanceof Player)) {
			return;
		}
		Player d = (Player) event.getDamager();
		if (d.getItemInHand().getType() == Material.WOOD_SWORD || d.getItemInHand().getType() == Material.STONE_SWORD
				|| d.getItemInHand().getType() == Material.IRON_SWORD
				|| d.getItemInHand().getType() == Material.DIAMOND_SWORD) {
			event.setDamage(event.getDamage() - 2.5);
		}
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (((event.getEntity() instanceof Player)) && ((event.getDamager() instanceof Player))) {
			Player p = (Player) event.getDamager();
			p.getItemInHand().setDurability((short) -p.getItemInHand().getType().getMaxDurability());
		}
	}

	@EventHandler
	public void nodrop(PlayerDropItemEvent e) {
		if ((e.getItemDrop().getItemStack().getType() == Material.MUSHROOM_SOUP
				|| e.getItemDrop().getItemStack().getType() == Material.RED_MUSHROOM
				|| e.getItemDrop().getItemStack().getType() == Material.BROWN_MUSHROOM
				|| e.getItemDrop().getItemStack().getType() == Material.BOWL)) {
			e.getItemDrop().remove();
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if (e.getPlayer() instanceof Player) {
			if (KitManager.getInstance().getUsingKitName(e.getPlayer()).equalsIgnoreCase("Nenhum")) {
				e.setCancelled(true);
			}
			if (!(e.getItem().getItemStack().getType() == Material.MUSHROOM_SOUP
					|| e.getItem().getItemStack().getType() == Material.RED_MUSHROOM
					|| e.getItem().getItemStack().getType() == Material.BROWN_MUSHROOM
					|| e.getItem().getItemStack().getType() == Material.BOWL)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onProjectileHit(final ProjectileHitEvent event) {
		final Entity entity = event.getEntity();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
			public void run() {
				if (entity.getType() == EntityType.ARROW) {
					entity.remove();
				}
			}
		}, 5);
	}

	@EventHandler
	public void move(EntityDamageEvent event) {
		if (event.getEntity() instanceof Villager)
			event.setCancelled(true);
		if (event.getCause() == DamageCause.VOID) {
			event.setDamage(30);
		}
	}

	@EventHandler
	public void onIgnite(BlockIgniteEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		if ((event.getEntity() instanceof FallingBlock)) {
			event.setCancelled(true);
		}
	}

}
