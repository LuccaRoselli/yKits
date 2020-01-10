package com.luccadev.br.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.commands.Admin;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.StringUtils;

import net.minecraft.server.v1_8_R3.EntityPlayer;

public class AdminListener implements Listener {
	KitManager kit = KitManager.getInstance();

	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (Admin.emadmin.contains(pl)) {
				if (p.hasPermission(StringUtils.permissaoprefix + "comando.admin") || p.hasPermission(StringUtils.permissaoprefix + "staff")) {
					p.showPlayer(pl);
				} else {
					p.hidePlayer(pl);
				}
			}
		}
	}

	public static int getAmount(Player p, Material m) {
		int amount = 0;
		for (ItemStack item : p.getInventory().getContents()) {
			if ((item != null) && (item.getType() == m) && (item.getAmount() > 0)) {
				amount += item.getAmount();
			}
		}
		return amount;
	}

	public static final List<Player> autosoupteste = new ArrayList<Player>();
	public static HashMap<String, ItemStack[]> saveinv = new HashMap<String, ItemStack[]>();
	public static HashMap<String, ItemStack[]> armadura = new HashMap<String, ItemStack[]>();
	public static ArrayList<Player> Players = new ArrayList<Player>();

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (Admin.emadmin.contains(e.getPlayer())) {
			e.setCancelled(true);
			e.getPlayer().updateInventory();
		}
	}

	@EventHandler
	public void onPick(PlayerPickupItemEvent e) {
		if (Admin.emadmin.contains(e.getPlayer())) {
			e.setCancelled(true);
			e.getPlayer().updateInventory();
		}
	}

	@EventHandler
	public void ontitan(final PlayerInteractEvent e) {
		if (!Admin.emadmin.contains(e.getPlayer())) {
			return;
		}
		if (!e.getAction().name().contains("RIGHT")) {
			return;
		}
		if (e.getPlayer().getItemInHand().getType() == Material.MAGMA_CREAM) {
			e.setCancelled(true);
			e.getPlayer().performCommand("admin");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {

				@Override
				public void run() {
					e.getPlayer().performCommand("admin");
				}
			}, 5);
		}
	}

	public static void abririnv(Player jogador1, Player jogador2) {
		PlayerInventory inventory = jogador2.getInventory();

		ItemStack separator = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta separatorMeta = separator.getItemMeta();
		separatorMeta.setDisplayName(ChatColor.RESET + "");
		separator.setItemMeta(separatorMeta);

		ItemStack voltar = new ItemStack(Material.FIRE, 1, (short) 0);
		ItemMeta voltarMeta = voltar.getItemMeta();
		voltarMeta.setDisplayName("§c§lKills");
		List<String> re = new ArrayList<String>();
		re.add(ChatColor.DARK_GRAY + "§o" + "Kills " + ChatColor.GOLD + "§o" + StatsManager.getKills(jogador2));
		voltarMeta.setLore(re);
		voltar.setItemMeta(voltarMeta);

		Inventory gui = Bukkit.getServer().createInventory(jogador1, inventory.getSize() + 18,
				jogador2.getDisplayName());
		ItemStack[] GUIContent = gui.getContents();
		for (int i = 9; i < inventory.getSize(); i++) {
			GUIContent[(i - 9)] = inventory.getItem(i);
		}
		for (int i = 0; i < 9; i++) {
			GUIContent[(i + 27)] = inventory.getItem(i);
		}
		for (int i = inventory.getSize(); i < inventory.getSize() + 9; i++) {
			GUIContent[i] = separator;
		}
		GUIContent[(inventory.getSize() + 9)] = inventory.getHelmet();
		GUIContent[(inventory.getSize() + 10)] = inventory.getChestplate();
		GUIContent[(inventory.getSize() + 16)] = inventory.getLeggings();
		GUIContent[(inventory.getSize() + 17)] = inventory.getBoots();

		GUIContent[(inventory.getSize() + 11)] = voltar;
		if (jogador2.getLevel() > 0) {
			GUIContent[(inventory.getSize() + 12)] = new ItemStack(Material.EXP_BOTTLE, jogador2.getLevel());
		} else {
			GUIContent[(inventory.getSize() + 12)] = new ItemStack(Material.EXP_BOTTLE, 1);
		}
		ItemMeta xpMeta = GUIContent[(inventory.getSize() + 12)].getItemMeta();
		xpMeta.setDisplayName(ChatColor.GREEN + "§l" + "Experiencias");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_GRAY + "§o" + "XP " + ChatColor.GOLD + "§o" + StatsManager.getXp(jogador2));
		xpMeta.setLore(lore);
		GUIContent[(inventory.getSize() + 12)].setItemMeta(xpMeta);
		if (jogador2.getActivePotionEffects().size() == 0) {
			GUIContent[(inventory.getSize() + 13)] = new ItemStack(Material.GLASS_BOTTLE, 1);
			ItemMeta meta = GUIContent[(inventory.getSize() + 13)].getItemMeta();
			meta.setLore(Arrays.asList(new String[] { ChatColor.DARK_GRAY + "§o" + "Sem efeitos" }));
			GUIContent[(inventory.getSize() + 13)].setItemMeta(meta);
		} else {
			GUIContent[(inventory.getSize() + 13)] = new Potion(PotionType.WATER_BREATHING).toItemStack(1);
			PotionMeta effectsMeta = (PotionMeta) GUIContent[(inventory.getSize() + 13)].getItemMeta();
			effectsMeta.clearCustomEffects();
			lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + "§o" + jogador2.getActivePotionEffects().size() + ChatColor.DARK_GRAY + "§o"
					+ " active effects");
			xpMeta.setLore(lore);
			for (PotionEffect effect : jogador2.getActivePotionEffects()) {
				effectsMeta.addCustomEffect(effect, true);
			}
			GUIContent[(inventory.getSize() + 13)].setItemMeta(effectsMeta);
		}
		ItemMeta effectsMeta = GUIContent[(inventory.getSize() + 13)].getItemMeta();
		effectsMeta.setDisplayName(ChatColor.DARK_PURPLE + "§l" + "Efeitos de Porções  ");
		GUIContent[(inventory.getSize() + 13)].setItemMeta(effectsMeta);
		if (((Damageable) jogador2).getHealth() > 0.0D) {
			GUIContent[(inventory.getSize() + 14)] = new ItemStack(Material.MUSHROOM_SOUP,
					(int) Math.ceil(((Damageable) jogador2).getHealth()));
			ItemMeta healthMeta = GUIContent[(inventory.getSize() + 14)].getItemMeta();
			healthMeta.setDisplayName(ChatColor.DARK_RED + "§l" + "Vida ");
			lore = new ArrayList<String>();
			lore.add(ChatColor.GOLD + "§o" + (int) Math.ceil(((Damageable) jogador2).getHealth()) + ChatColor.DARK_GRAY
					+ "§o" + "/20");
			healthMeta.setLore(lore);
			GUIContent[(inventory.getSize() + 14)].setItemMeta(healthMeta);
		}
		if (jogador2.getFoodLevel() > 0) {
			GUIContent[(inventory.getSize() + 15)] = new ItemStack(Material.COOKED_BEEF, jogador2.getFoodLevel());
			ItemMeta foodMeta = GUIContent[(inventory.getSize() + 15)].getItemMeta();
			foodMeta.setDisplayName(ChatColor.GOLD + "§l" + "Comida");
			lore = new ArrayList<String>();
			lore.add(ChatColor.DARK_GRAY + "§o" + "Comida: " + ChatColor.GOLD + "§o" + jogador2.getFoodLevel()
					+ ChatColor.DARK_GRAY + "§o" + "/20");
			foodMeta.setLore(lore);
			GUIContent[(inventory.getSize() + 15)].setItemMeta(foodMeta);
		}
		gui.setContents(GUIContent);

		jogador1.openInventory(gui);
	}

	@EventHandler
	public void onAdminAuto(PlayerInteractEntityEvent event) {
		if (!(event.getRightClicked() instanceof Player)) {
			return;
		}
		Player p1 = event.getPlayer();
		Player r = (Player) event.getRightClicked();
		if ((Admin.emadmin.contains(p1)) && (p1.getItemInHand().getType() == Material.DIAMOND_SWORD)) {
			p1.chat("/fftest " + r.getName());
		}
	}

	
	@EventHandler
	public void onInv(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((((e.getAction() == Action.RIGHT_CLICK_AIR ? 1 : 0)
				| (e.getAction() == Action.RIGHT_CLICK_BLOCK ? 1 : 0)) != 0)
				&& (p.getItemInHand().getType() == Material.SKULL_ITEM) && (Admin.emadmin.contains(p))) {
			Inventory invSpec = Bukkit.createInventory(null, 54, "§3§lJogadores");
			for (Player todos : Bukkit.getOnlinePlayers()) {
				ItemStack mitem = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
				SkullMeta imeta = (SkullMeta) mitem.getItemMeta();
				imeta.setDisplayName("§a" + todos.getName());
				List<String> Lore = new ArrayList<String>();
				Lore.add("§c" + ((Damageable) todos).getHealth() + " corações");
				Lore.add("§aKit: §f" + KitManager.getInstance().getUsingKitName(todos));
				Lore.add("§aRank: §f" + StatsManager.getRank(todos));
				Lore.add(" ");
				if (p.hasPermission(StringUtils.permissaoprefix + "comando.admin") || p.hasPermission(StringUtils.permissaoprefix + "staff")) {
					Lore.add("§eAperte lado direito para ir ate o jogador");
					Lore.add("§eAperte lado esquerdo para abrir o inventario");
				}
				imeta.setLore(Lore);
				imeta.setOwner(todos.getName());
				mitem.setItemMeta(imeta);

				invSpec.addItem(new ItemStack[] { mitem });
			}
			p.openInventory(invSpec);
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if ((e.getInventory().getName().equalsIgnoreCase("§3§lJogadores"))
				&& (p.getItemInHand().getType() == Material.SKULL_ITEM) && (e.getSlot() == e.getRawSlot())
				&& (e.getCurrentItem() != null) && (e.getCurrentItem().hasItemMeta())) {
			String nomeJ = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
			Player pp = Bukkit.getPlayerExact(nomeJ);
			e.setCancelled(true);
			if (p.hasPermission(StringUtils.permissaoprefix + "comando.admin") || p.hasPermission(StringUtils.permissaoprefix + "staff")) {
				if (e.getClick() == ClickType.LEFT){
					p.teleport(pp);
					p.sendMessage(StringUtils.avisoverde + "§eTeleportado ate " + pp.getName());
					return;
				} else {
					if (e.getClick() == ClickType.RIGHT){
						abririnv(p, pp);
						return;
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unused" })
	@EventHandler
	public void onADMINSoup(PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();
		if ((e.getRightClicked() instanceof Player)) {
			Player target = (Player) e.getRightClicked();
			if ((Admin.emadmin.contains(p))
					&& (!autosoupteste.contains(p) && ((p.getItemInHand().getType() == Material.MUSHROOM_SOUP)))) {
				e.setCancelled(true);
				final Player r = (Player) e.getRightClicked();
				Damageable hp = r;
				saveinv.put(r.getName(), r.getInventory().getContents());
				r.getInventory().clear();
				r.updateInventory();
				r.setHealth(2);
				autosoupteste.add(p);
				r.getInventory().setItem(33, new ItemStack(Material.MUSHROOM_SOUP));
				r.getInventory().setItem(34, new ItemStack(Material.MUSHROOM_SOUP));
				p.openInventory(r.getInventory());
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
					public void run() {
						if (r.getInventory().contains(Material.BOWL)) {
							p.closeInventory();
							p.sendMessage("▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇");
							p.sendMessage("§6              " + StringUtils.getPrefix('b') + "AutoSoup");
							p.sendMessage("");
							p.sendMessage("      ➟ §cProbabilidade: §bMelhor checar!");
							p.sendMessage("      ➟ §cPlayer: §b" + r.getName());
							p.sendMessage("");
							p.sendMessage("§6              " + StringUtils.getPrefix('b') + "AutoSoup");
							p.sendMessage("▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇");
							r.setHealth(20);
							r.getInventory().setContents((ItemStack[]) saveinv.get(r.getName()));
							autosoupteste.remove(p);
						} else {
							p.closeInventory();
							p.sendMessage("▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇");
							p.sendMessage("§6              " + StringUtils.getPrefix('b') + "AutoSoup");
							p.sendMessage("");
							p.sendMessage("      ➟ §cProbabilidade: §bNão parece!");
							p.sendMessage("      ➟ §cPlayer: §b" + r.getName());
							p.sendMessage("");
							p.sendMessage("§6              " + StringUtils.getPrefix('b') + "AutoSoup");
							p.sendMessage("▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇▇");
							r.setHealth(20);
							r.getInventory().setContents((ItemStack[]) saveinv.get(r.getName()));
							autosoupteste.remove(p);

						}
					}
				}, 30L);
			}
		}
	}

	@EventHandler
	public void Jail(PlayerInteractEntityEvent e) {
		if (!(e.getRightClicked() instanceof Player)) {
			return;
		}
		Player p = e.getPlayer();
		Player r = (Player) e.getRightClicked();
		Material item = p.getInventory().getItemInHand().getType();
		if ((item == Material.IRON_FENCE) && (Admin.emadmin.contains(p))) {
			e.setCancelled(true);
			r.getLocation().add(0.0D, 23.0D, 0.0D).getBlock().setType(Material.BEDROCK);
			r.getLocation().add(0.0D, 21.0D, 1.0D).getBlock().setType(Material.BEDROCK);
			r.getLocation().add(1.0D, 21.0D, 0.0D).getBlock().setType(Material.BEDROCK);
			r.getLocation().add(0.0D, 21.0D, -1.0D).getBlock().setType(Material.BEDROCK);
			r.getLocation().add(-1.0D, 21.0D, 0.0D).getBlock().setType(Material.BEDROCK);
			r.getLocation().add(0.0D, 20.0D, 0.0D).getBlock().setType(Material.BEDROCK);
			r.teleport(r.getLocation().add(0.0D, 21.0D, -0.05D));
		}
	}

	@EventHandler
	public void nofall(PlayerInteractEntityEvent e) {
		if (!(e.getRightClicked() instanceof Player)) {
			return;
		}
		Player p = e.getPlayer();
		Player r = (Player) e.getRightClicked();
		Material item = p.getInventory().getItemInHand().getType();
		if ((item == Material.IRON_BOOTS) && (Admin.emadmin.contains(p))) {
			e.setCancelled(true);
			Vector vector = p.getEyeLocation().getDirection();
			vector.multiply(0.0F);
			vector.setY(1.5F);
			r.setVelocity(vector);
		}
	}

	@EventHandler
	public void onADMIN(PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			Player target = (Player) e.getRightClicked();
			if (Admin.emadmin.contains(p)) {
				if (p.getItemInHand().getType() == Material.AIR) {
					PlayerInventory inv = target.getInventory();
					p.openInventory(inv);
				}
				if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
					Player r = (Player) e.getRightClicked();
					EntityPlayer ep = ((CraftPlayer) r).getHandle();
					p.sendMessage("§7Informações do player §6" + r.getName());
					if (ep.getHealth() % 2.0F == 0.0F) {
						p.sendMessage("§7Vida: §6" + (int) ep.getHealth());
					} else {
						p.sendMessage("§7Vida: §6" + (int) ep.getHealth() + ".5");
					}
					p.sendMessage("§7Sopas: §6" + getAmount(r, Material.MUSHROOM_SOUP));
					p.sendMessage("§7Kit: §6" + kit.getUsingKitName(r));
					p.sendMessage("§7Gamemode: §6" + r.getGameMode());
					/*
					 * p.sendMessage("§7KillStreak: §6" +
					 * KillStreak.getKillStreak(r));
					 */
				}
			}
		}
	}
}