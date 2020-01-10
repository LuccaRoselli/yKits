package com.luccadev.br.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.commands.Admin;
import com.luccadev.br.desafio1v1.cmd1v1;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.BarAPI;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;

public class UmVsUm implements Listener {
	private static Main main;
	private HashMap<Player, Player> Pedido;
	private HashMap<Player, Player> Normal;
	private HashMap<Player, Player> Buffed;
	private HashMap<Player, Player> BuffedSpeed;
	private HashMap<Player, Player> Custom;
	private HashMap<String, Material> Espada;
	private HashMap<String, Boolean> EspadaE;
	private HashMap<String, Integer> EspadaL;
	private HashMap<String, Material> Armadura;
	private HashMap<String, Boolean> ArmaduraE;
	private HashMap<String, Integer> ArmaduraL;
	private HashMap<String, Boolean> Refil;
	private HashMap<String, Integer> Pocao;
	private HashMap<String, Integer> FT;
	private HashMap<String, Integer> Cura;
	private HashMap<Player, Integer> ft;
	private HashMap<Player, Integer> placar;
	private HashMap<String, Material> lutaEspada;
	private HashMap<String, Boolean> lutaEspadaE;
	private HashMap<String, Integer> lutaEspadaL;
	private HashMap<String, Material> lutaArmadura;
	private HashMap<String, Boolean> lutaArmaduraE;
	private HashMap<String, Integer> lutaArmaduraL;
	private HashMap<String, Boolean> lutaRefil;
	private HashMap<String, Integer> lutaPocao;
	private HashMap<String, Integer> lutaCura;
	private Inventory invN;
	private Inventory invC;
	private Inventory invA;

	{
		this.Pedido = new HashMap<Player, Player>();
		this.Normal = new HashMap<Player, Player>();
		this.Buffed = new HashMap<Player, Player>();
		this.BuffedSpeed = new HashMap<Player, Player>();
		this.Custom = new HashMap<Player, Player>();
		this.Espada = new HashMap<String, Material>();
		this.EspadaE = new HashMap<String, Boolean>();
		this.EspadaL = new HashMap<String, Integer>();
		this.Armadura = new HashMap<String, Material>();
		this.ArmaduraE = new HashMap<String, Boolean>();
		this.ArmaduraL = new HashMap<String, Integer>();
		this.Refil = new HashMap<String, Boolean>();
		this.Pocao = new HashMap<String, Integer>();
		this.FT = new HashMap<String, Integer>();
		this.Cura = new HashMap<String, Integer>();
		this.ft = new HashMap<Player, Integer>();
		this.placar = new HashMap<Player, Integer>();
		this.lutaEspada = new HashMap<String, Material>();
		this.lutaEspadaE = new HashMap<String, Boolean>();
		this.lutaEspadaL = new HashMap<String, Integer>();
		this.lutaArmadura = new HashMap<String, Material>();
		this.lutaArmaduraE = new HashMap<String, Boolean>();
		this.lutaArmaduraL = new HashMap<String, Integer>();
		this.lutaRefil = new HashMap<String, Boolean>();
		this.lutaPocao = new HashMap<String, Integer>();
		this.lutaCura = new HashMap<String, Integer>();
		main = Main.getPlugin(Main.class);
	}

	public static HashMap<Player, Player> naLuta = new HashMap<Player, Player>();
	public static ArrayList<Player> naArena = new ArrayList<Player>();

	public static void clear(Player p) {
		p.getInventory().clear();

		p.getInventory().setArmorContents(null);
		for (PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
	}

	public static org.bukkit.inventory.ItemStack FillInv(Player p, org.bukkit.inventory.ItemStack item) {
		for (int i = 0; i < 36; i++) {
			p.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { item });
		}
		return item;
	}

	public static org.bukkit.inventory.ItemStack FillHotBar(Player p, org.bukkit.inventory.ItemStack item) {
		for (int i = 0; i < 8; i++) {
			p.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { item });
		}
		return item;
	}

	public static Inventory Menu(int slots, String nome, Player p) {
		return Bukkit.createInventory(p, slots, ChatColor.translateAlternateColorCodes('&', nome));
	}

	public static org.bukkit.inventory.ItemStack setItem(Material material, int id, String nome, boolean enc,
			Inventory inv, int slot, String... lore) {
		org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material, 1, (byte) id);

		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));

		List<String> l = new ArrayList<String>();
		for (String lo : lore) {
			l.add(ChatColor.translateAlternateColorCodes('&', lo));
		}
		if ((lore.length > 0) && (l.get(0) != "")) {
			meta.setLore(l);

			item.setItemMeta(meta);
		} else {
			meta.setLore(null);

			item.setItemMeta(meta);
		}
		inv.setItem(slot, item);

		return item;
	}

	public static org.bukkit.inventory.ItemStack item(Material stack, int id, String nome, boolean enc,
			String... lore) {
		org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(stack);

		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));

		List<String> l = new ArrayList<String>();
		for (String lo : lore) {
			l.add(ChatColor.translateAlternateColorCodes('&', lo));
		}
		if ((lore.length > 0) && (!((String) l.get(0)).equalsIgnoreCase(""))) {
			meta.setLore(l);

			item.setItemMeta(meta);
		} else {
			meta.setLore(null);

			item.setItemMeta(meta);
		}

		return item;
	}

	public static org.bukkit.inventory.ItemStack item(ItemStack stack, int id, String nome, boolean enc,
			String... lore) {
		org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(stack);

		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', nome));

		List<String> l = new ArrayList<String>();
		for (String lo : lore) {
			l.add(ChatColor.translateAlternateColorCodes('&', lo));
		}
		if ((lore.length > 0) && (!((String) l.get(0)).equalsIgnoreCase(""))) {
			meta.setLore(l);

			item.setItemMeta(meta);
		} else {
			meta.setLore(null);

			item.setItemMeta(meta);
		}
		return item;
	}

	public void InvNormal(final Player p, final Player d) {
		(this.invN = Menu(45, "§4Desafiando §c" + d.getName(), p)).clear();
		final ItemStack vidro = item(Material.THIN_GLASS, 0, " ", false, "");
		final ItemStack normal = item(Material.ENCHANTED_BOOK, 0, "§aNormal", false, "§91v1 Normal");
		final ItemStack buffed = item(Material.ENCHANTED_BOOK, 0, "§aBuffed", false, "§91v1 Normal + Forca II");
		final ItemStack buffSpeed = item(Material.ENCHANTED_BOOK, 0, "§aBuffed com Speed II", false,
				"§91v1 Normal + Forca II + Speed II");
		final ItemStack custom = item(Material.ANVIL, 0, "§aCustom", false, "§91v1 Customizado");
		this.invN.setItem(19, normal);
		this.invN.setItem(21, buffed);
		this.invN.setItem(23, buffSpeed);
		this.invN.setItem(25, custom);
		for (int i = 0; i < this.invN.getSize(); ++i) {
			if (this.invN.getItem(i) == null) {
				this.invN.setItem(i, vidro);
			}
		}
		p.openInventory(this.invN);
	}

	@SuppressWarnings("deprecation")
	public void invAceitarN(final Player p, final Player d) {
		String nome = d.getName();
		if (nome.length() > 11) {
			nome = nome.substring(0, 9);
			nome = String.valueOf(nome) + "...";
		}
		if (UmVsUm.Random.contains(p)) {
			UmVsUm.Random.remove(p);
		}
		if (UmVsUm.Random.contains(d)) {
			UmVsUm.Random.remove(d);
		}
		(this.invA = Menu(45, "§2Desafiado por §a" + nome, p)).clear();
		final ItemStack iron = item(Material.IRON_FENCE, 0, " ", false, "");
		final ItemStack espada = item(Material.DIAMOND_SWORD, 0, "§eEspada", false,
				"§9Espada de Diamante com Sharpness I");
		espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		final ItemStack refil = item(Material.BOWL, 0, "§eRefil", false, "§9Sem Refil");
		final ItemStack armadura = item(Material.IRON_CHESTPLATE, 0, "§eArmadura", false, "§9Armadura de Ferro");
		final ItemStack pocao = item(Material.GLASS_BOTTLE, 0, "§ePocoes", false, "  §6■ §dSem Pocoes");
		final ItemStack cura = item(Material.MUSHROOM_SOUP, 0, "§eTipo de Cura", false,
				ChatColor.translateAlternateColorCodes('§', "  §6> §aSopas"));
		final ItemStack aceitar = item(Material.WOOL, 0, "§2§lAceitar o desafio de " + d.getName(), false,
				"§9Aceitar o Desafio");
		aceitar.setDurability((short) DyeColor.LIME.getData());
		final ItemStack ft = item(Material.ITEM_FRAME, 0, "§eFirst To", false, "  §6# §aUma luta");
		this.invA.setItem(10, espada);
		this.invA.setItem(12, armadura);
		this.invA.setItem(14, refil);
		this.invA.setItem(16, pocao);
		this.invA.setItem(28, ft);
		this.invA.setItem(30, aceitar);
		this.invA.setItem(31, aceitar);
		this.invA.setItem(32, aceitar);
		this.invA.setItem(34, cura);
		for (int i = 0; i < this.invA.getSize(); ++i) {
			if (this.invA.getItem(i) == null) {
				this.invA.setItem(i, iron);
			}
		}
		p.openInventory(this.invA);
	}

	@SuppressWarnings("deprecation")
	public void invAceitarB(final Player p, final Player d) {
		String nome = d.getName();
		if (nome.length() > 11) {
			nome = nome.substring(0, 9);
			nome = String.valueOf(nome) + "...";
		}
		if (UmVsUm.Random.contains(p)) {
			UmVsUm.Random.remove(p);
		}
		if (UmVsUm.Random.contains(d)) {
			UmVsUm.Random.remove(d);
		}
		(this.invA = Menu(45, "§2Desafiado por §a" + nome, p)).clear();
		final ItemStack iron = item(Material.IRON_FENCE, 0, " ", false, "");
		final ItemStack espada = item(Material.DIAMOND_SWORD, 0, "§eEspada", false,
				"§9Espada de Diamante com Sharpness I");
		espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		final ItemStack refil = item(Material.BOWL, 0, "§eRefil", false, "§9Sem Refil");
		final ItemStack armadura = item(Material.IRON_CHESTPLATE, 0, "§eArmadura", false, "§9Armadura de Ferro");
		ItemStack pocao;
		final Potion po = new Potion(PotionType.STRENGTH, 2);
		final ItemStack forca = po.toItemStack(1);
		pocao = item(forca, 0, "§ePocoes", false, "");
		final ItemMeta pMeta2 = pocao.getItemMeta();
		pMeta2.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6■ §cForca II")));
		pocao.setItemMeta(pMeta2);
		final ItemStack cura = item(Material.MUSHROOM_SOUP, 0, "§eTipo de Cura", false,
				ChatColor.translateAlternateColorCodes('§', "  §6> §aSopas"));
		final ItemStack aceitar = item(Material.WOOL, 0, "§2§lAceitar o desafio de " + d.getName(), false,
				"§9Aceitar o Desafio");
		aceitar.setDurability((short) DyeColor.LIME.getData());
		final ItemStack ft = item(Material.ITEM_FRAME, 0, "§eFirst To", false, "  §6# §aUma luta");
		this.invA.setItem(10, espada);
		this.invA.setItem(12, armadura);
		this.invA.setItem(14, refil);
		this.invA.setItem(16, pocao);
		this.invA.setItem(28, ft);
		this.invA.setItem(30, aceitar);
		this.invA.setItem(31, aceitar);
		this.invA.setItem(32, aceitar);
		this.invA.setItem(34, cura);
		for (int i = 0; i < this.invA.getSize(); ++i) {
			if (this.invA.getItem(i) == null) {
				this.invA.setItem(i, iron);
			}
		}
		p.openInventory(this.invA);
	}

	@SuppressWarnings("deprecation")
	public void invAceitarS(final Player p, final Player d) {
		String nome = d.getName();
		if (nome.length() > 11) {
			nome = nome.substring(0, 9);
			nome = String.valueOf(nome) + "...";
		}
		if (UmVsUm.Random.contains(p)) {
			UmVsUm.Random.remove(p);
		}
		if (UmVsUm.Random.contains(d)) {
			UmVsUm.Random.remove(d);
		}
		(this.invA = Menu(45, "§2Desafiado por §a" + nome, p)).clear();
		final ItemStack iron = item(Material.IRON_FENCE, 0, " ", false, "");
		final ItemStack espada = item(Material.DIAMOND_SWORD, 0, "§eEspada", false,
				"§9Espada de Diamante com Sharpness I");
		espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		final ItemStack refil = item(Material.BOWL, 0, "§eRefil", false, "§9Sem Refil");
		final ItemStack armadura = item(Material.IRON_CHESTPLATE, 0, "§eArmadura", false, "§9Armadura de Ferro");
		final ItemStack pocao = item(Material.POTION, 0, "§ePocoes", false, "  §6■ §cForca II + Speed II");
		final ItemStack cura = item(Material.MUSHROOM_SOUP, 0, "§eTipo de Cura", false,
				ChatColor.translateAlternateColorCodes('§', "  §6> §aSopas"));
		final ItemStack aceitar = item(Material.WOOL, 0, "§2§lAceitar o desafio de " + d.getName(), false,
				"§9Aceitar o Desafio");
		aceitar.setDurability((short) DyeColor.LIME.getData());
		final ItemStack ft = item(Material.ITEM_FRAME, 0, "§eFirst To", false, "  §6# §aUma luta");
		this.invA.setItem(10, espada);
		this.invA.setItem(12, armadura);
		this.invA.setItem(14, refil);
		this.invA.setItem(16, pocao);
		this.invA.setItem(28, ft);
		this.invA.setItem(30, aceitar);
		this.invA.setItem(31, aceitar);
		this.invA.setItem(32, aceitar);
		this.invA.setItem(34, cura);
		for (int i = 0; i < this.invA.getSize(); ++i) {
			if (this.invA.getItem(i) == null) {
				this.invA.setItem(i, iron);
			}
		}
		p.openInventory(this.invA);
	}

	@SuppressWarnings("deprecation")
	public void invAceitarC(final Player p, final Player d) {
		String nome = d.getName();
		if (nome.length() > 11) {
			nome = nome.substring(0, 9);
			nome = String.valueOf(nome) + "...";
		}
		if (UmVsUm.Random.contains(p)) {
			UmVsUm.Random.remove(p);
		}
		if (UmVsUm.Random.contains(d)) {
			UmVsUm.Random.remove(d);
		}
		(this.invA = Menu(45, "§2Desafiado por §a" + nome, p)).clear();
		final ItemStack iron = item(Material.IRON_FENCE, 0, " ", false, "");
		final ItemStack espada = item(this.Espada.get(String.valueOf(p.getName()) + d.getName()), 0, "§eEspada", false,
				"");
		final ItemMeta eMeta = espada.getItemMeta();
		if (espada.getType() == Material.DIAMOND_SWORD) {
			eMeta.setLore(
					Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bEspada de Diamante")));
		} else if (espada.getType() == Material.IRON_SWORD) {
			eMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bEspada de Ferro")));
		} else if (espada.getType() == Material.STONE_SWORD) {
			eMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bEspada de Pedra")));
		} else if (espada.getType() == Material.GOLD_SWORD) {
			eMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bEspada de Ouro")));
		} else if (espada.getType() == Material.WOOD_SWORD) {
			eMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bEspada de Madeira")));
		}
		espada.setItemMeta(eMeta);
		if (this.EspadaE.get(String.valueOf(p.getName()) + d.getName())) {
			espada.addEnchantment(Enchantment.DAMAGE_ALL,
					(int) this.EspadaL.get(String.valueOf(p.getName()) + d.getName()));
			final ItemMeta eMetaE = espada.getItemMeta();
			final List<String> espadaL = new ArrayList<String>();
			espadaL.add(eMeta.getLore().get(0));
			espadaL.add(eMeta.getLore().get(1));
			if (espada.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 1) {
				espadaL.add(" ");
				espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cSharpness I"));
				eMetaE.setLore(espadaL);
				espada.setItemMeta(eMetaE);
			} else if (espada.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 2) {
				espadaL.add(" ");
				espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cSharpness II"));
				eMetaE.setLore(espadaL);
				espada.setItemMeta(eMetaE);
			} else if (espada.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 3) {
				espadaL.add(" ");
				espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cSharpness III"));
				eMetaE.setLore(espadaL);
				espada.setItemMeta(eMetaE);
			} else if (espada.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 4) {
				espadaL.add(" ");
				espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cSharpness IV"));
				eMetaE.setLore(espadaL);
				espada.setItemMeta(eMetaE);
			} else if (espada.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 5) {
				espadaL.add(" ");
				espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cSharpness V"));
				eMetaE.setLore(espadaL);
				espada.setItemMeta(eMetaE);
			}
		}
		final ItemStack armadura = item(this.Armadura.get(String.valueOf(p.getName()) + d.getName()), 0, "§eArmadura",
				false, "");
		final ItemMeta aMeta = armadura.getItemMeta();
		if (armadura.getType() == Material.DIAMOND_CHESTPLATE) {
			aMeta.setLore(
					Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bArmadura de Diamante")));
		} else if (armadura.getType() == Material.IRON_CHESTPLATE) {
			aMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bArmadura de Ferro")));
		} else if (armadura.getType() == Material.CHAINMAIL_CHESTPLATE) {
			aMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bArmadura de Chain")));
		} else if (armadura.getType() == Material.GOLD_CHESTPLATE) {
			aMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bArmadura de Ouro")));
		} else if (armadura.getType() == Material.LEATHER_CHESTPLATE) {
			aMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §bArmadura de Couro")));
		}
		armadura.setItemMeta(aMeta);
		if (this.ArmaduraE.get(String.valueOf(p.getName()) + d.getName())) {
			armadura.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
					(int) this.ArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
			final ItemMeta aMetaE = armadura.getItemMeta();
			final List<String> armaduraL = new ArrayList<String>();
			armaduraL.add(aMeta.getLore().get(0));
			armaduraL.add(aMeta.getLore().get(1));
			if (armadura.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 1) {
				armaduraL.add(" ");
				armaduraL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cProtection I"));
				aMetaE.setLore(armaduraL);
				armadura.setItemMeta(aMetaE);
			} else if (armadura.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 2) {
				armaduraL.add(" ");
				armaduraL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cProtection II"));
				aMetaE.setLore(armaduraL);
				armadura.setItemMeta(aMetaE);
			} else if (armadura.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 3) {
				armaduraL.add(" ");
				armaduraL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cProtection III"));
				aMetaE.setLore(armaduraL);
				armadura.setItemMeta(aMetaE);
			} else if (armadura.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 4) {
				armaduraL.add(" ");
				armaduraL.add(ChatColor.translateAlternateColorCodes('§', "  §6● §cProtection IV"));
				aMetaE.setLore(armaduraL);
				armadura.setItemMeta(aMetaE);
			}
		}
		ItemStack refil;
		if (this.Refil.get(String.valueOf(p.getName()) + d.getName())) {
			if (this.Cura.get(String.valueOf(p.getName()) + d.getName()) == 1) {
				refil = item(Material.MUSHROOM_SOUP, 0, "§eRefil", false, "");
				final ItemMeta rMeta = refil.getItemMeta();
				rMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6+ §aCom Refil")));
				refil.setItemMeta(rMeta);
			} else {
				refil = item(Material.POTION, 0, "§eRefil", false, "");
				final ItemMeta rMeta = refil.getItemMeta();
				rMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6+ §aCom Refil")));
				refil.setItemMeta(rMeta);
			}
		} else if (this.Cura.get(String.valueOf(p.getName()) + d.getName()) == 1) {
			refil = item(Material.BOWL, 0, "§eRefil", false, "");
			final ItemMeta rMeta = refil.getItemMeta();
			rMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6- §cSem Refil")));
			refil.setItemMeta(rMeta);
		} else {
			refil = item(Material.GLASS_BOTTLE, 0, "§eRefil", false, "");
			final ItemMeta rMeta = refil.getItemMeta();
			rMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6+ §aCom Refil")));
			refil.setItemMeta(rMeta);
		}
		ItemStack pocao;
		if (this.Pocao.get(String.valueOf(p.getName()) + d.getName()) == 1) {
			pocao = item(Material.GLASS_BOTTLE, 0, "§ePocoes", false, "");
			final ItemMeta pMeta = pocao.getItemMeta();
			pMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6■ §dSem Pocoes")));
			pocao.setItemMeta(pMeta);
		} else if (this.Pocao.get(String.valueOf(p.getName()) + d.getName()) == 2) {
			final Potion po = new Potion(PotionType.STRENGTH, 2);
			final ItemStack forca = po.toItemStack(1);
			pocao = item(forca, 0, "§ePocoes", false, "");
			final ItemMeta pMeta2 = pocao.getItemMeta();
			pMeta2.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6■ §cForca II")));
			pocao.setItemMeta(pMeta2);
		} else if (this.Pocao.get(String.valueOf(p.getName()) + d.getName()) == 3) {
			final Potion po = new Potion(PotionType.SPEED, 2);
			final ItemStack speed = po.toItemStack(1);
			pocao = item(speed, 0, "§ePocoes", false, "");
			final ItemMeta pMeta2 = pocao.getItemMeta();
			pMeta2.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6■ §bSpeed II")));
			pocao.setItemMeta(pMeta2);
		} else {
			pocao = item(Material.POTION, 0, "§ePocoes", false, "");
			final ItemMeta pMeta = pocao.getItemMeta();
			pMeta.setLore(
					Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6■ §9Speed II e Forca II")));
			pocao.setItemMeta(pMeta);
		}
		final ItemStack ft = item(Material.ITEM_FRAME, 0, "§eFirst to", false, "");
		final ItemMeta fMeta = ft.getItemMeta();
		if (this.FT.get(String.valueOf(p.getName()) + d.getName()) == 1) {
			fMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6# §aUma luta")));
			ft.setItemMeta(fMeta);
		} else if (this.FT.get(String.valueOf(p.getName()) + d.getName()) == 3) {
			ft.setAmount(3);
			fMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6# §aFirst to 3")));
			ft.setItemMeta(fMeta);
		} else if (this.FT.get(String.valueOf(p.getName()) + d.getName()) == 5) {
			ft.setAmount(5);
			fMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6# §aFirst to 5")));
			ft.setItemMeta(fMeta);
		}
		ItemStack cura;
		if (this.Cura.get(String.valueOf(p.getName()) + d.getName()) == 1) {
			cura = item(Material.MUSHROOM_SOUP, 0, "§eTipo de Cura", false, "");
			final ItemMeta cMeta = cura.getItemMeta();
			cMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6> §aSopas")));
			cura.setItemMeta(cMeta);
		} else {
			final Potion po2 = new Potion(PotionType.INSTANT_HEAL, 2);
			po2.setSplash(true);
			cura = item(po2.toItemStack(1), 0, "§eTipo de Cura", false, "");
			final ItemMeta hMeta = cura.getItemMeta();
			hMeta.setLore(Arrays.asList(" ", ChatColor.translateAlternateColorCodes('§', "  §6> §aPocoes")));
			cura.setItemMeta(hMeta);
		}
		final ItemStack aceitar = item(Material.WOOL, 0, "§2§lAceitar o desafio de " + d.getName(), false,
				"§9Aceitar o Desafio");
		aceitar.setDurability((short) DyeColor.LIME.getData());
		this.invA.setItem(10, espada);
		this.invA.setItem(12, armadura);
		this.invA.setItem(14, refil);
		this.invA.setItem(16, pocao);
		this.invA.setItem(28, ft);
		this.invA.setItem(30, aceitar);
		this.invA.setItem(31, aceitar);
		this.invA.setItem(32, aceitar);
		this.invA.setItem(34, cura);
		for (int i = 0; i < this.invA.getSize(); ++i) {
			if (this.invA.getItem(i) == null) {
				this.invA.setItem(i, iron);
			}
		}
		p.openInventory(this.invA);
	}

	@SuppressWarnings("deprecation")
	public void InvCustom(final Player p, final Player d, final Inventory inv) {
		(this.invC = Menu(45, inv.getTitle(), p)).clear();
		final List<String> espadaL = new ArrayList<String>();
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "§9Troque a Espada"));
		espadaL.add(" ");
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-esquerdo"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §bEspada:"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Diamante"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Ferro"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Pedra"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Ouro"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Madeira"));
		espadaL.add(" ");
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-direito"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §bEncantamento:"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Sem Sharpness"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Sharpness I"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Sharpness II"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Sharpness III"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Sharpness IV"));
		espadaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Sharpness V"));
		final List<String> armaduraL = new ArrayList<String>();
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "§9Troque a Armadura"));
		armaduraL.add(" ");
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-esquerdo"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "  §bArmadura:"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "    §7Diamante"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Ferro"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "    §7Chain"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "    §7Ouro"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "    §7Couro"));
		armaduraL.add(" ");
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-direito"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "  §bEncantamento:"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Sem Protection"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "    §7Protection I"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "    §7Protection II"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "    §7Protection III"));
		armaduraL.add(ChatColor.translateAlternateColorCodes('§', "    §7Protection IV"));
		final List<String> refilL = new ArrayList<String>();
		refilL.add(ChatColor.translateAlternateColorCodes('§', "§9Alterne o Refil"));
		refilL.add(" ");
		refilL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-esquerdo"));
		refilL.add(ChatColor.translateAlternateColorCodes('§', "  §bRefil:"));
		refilL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Sem Refil"));
		refilL.add(ChatColor.translateAlternateColorCodes('§', "    §7Com Refil"));
		final List<String> pocaoL = new ArrayList<String>();
		pocaoL.add(ChatColor.translateAlternateColorCodes('§', "§9Alterne as Pocoes"));
		pocaoL.add(" ");
		pocaoL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-esquerdo"));
		pocaoL.add(ChatColor.translateAlternateColorCodes('§', "  §bPocoes:"));
		pocaoL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Nenhuma"));
		pocaoL.add(ChatColor.translateAlternateColorCodes('§', "    §7Forca II"));
		pocaoL.add(ChatColor.translateAlternateColorCodes('§', "    §7Speed II"));
		pocaoL.add(ChatColor.translateAlternateColorCodes('§', "    §7Speed II e Forca II"));
		final List<String> ftL = new ArrayList<String>();
		ftL.add(ChatColor.translateAlternateColorCodes('§', "§9Escolha quantas lutas para ganhar"));
		ftL.add(" ");
		ftL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-esquerdo"));
		ftL.add(ChatColor.translateAlternateColorCodes('§', "  §bFirst to:"));
		ftL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Uma luta"));
		ftL.add(ChatColor.translateAlternateColorCodes('§', "    §7First to 3"));
		ftL.add(ChatColor.translateAlternateColorCodes('§', "    §7First to 5"));
		final List<String> curaL = new ArrayList<String>();
		curaL.add(ChatColor.translateAlternateColorCodes('§', "§9Troque o Tipo de Cura"));
		curaL.add(" ");
		curaL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-esquerdo"));
		curaL.add(ChatColor.translateAlternateColorCodes('§', "  §bTipo de cura:"));
		curaL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Sopas"));
		curaL.add(ChatColor.translateAlternateColorCodes('§', "    §7Pocoes"));
		final ItemStack vidro = item(Material.THIN_GLASS, 0, " ", false, "");
		final ItemStack espada = item(Material.DIAMOND_SWORD, 0, "§bEspada", false, "");
		final ItemMeta emeta = espada.getItemMeta();
		emeta.setLore(espadaL);
		espada.setItemMeta(emeta);
		final ItemStack armadura = item(Material.IRON_CHESTPLATE, 0, "§bArmadura", false, "");
		final ItemMeta ameta = armadura.getItemMeta();
		ameta.setLore(armaduraL);
		armadura.setItemMeta(ameta);
		final ItemStack pocao = item(Material.GLASS_BOTTLE, 0, "§bPocoes", false, "");
		final ItemMeta pmeta = pocao.getItemMeta();
		pmeta.setLore(pocaoL);
		pocao.setItemMeta(pmeta);
		final ItemStack refil = item(Material.BOWL, 0, "§bRefil", false, "");
		final ItemMeta rmeta = refil.getItemMeta();
		rmeta.setLore(refilL);
		refil.setItemMeta(rmeta);
		final ItemStack ft = item(Material.ITEM_FRAME, 0, "§bFirst to", false, "");
		final ItemMeta fmeta = ft.getItemMeta();
		fmeta.setLore(ftL);
		ft.setItemMeta(fmeta);
		final ItemStack desafiar = item(Material.WOOL, 0, "§4§lDesafiar §c" + d.getName(), false, "");
		desafiar.setDurability((short) DyeColor.LIME.getData());
		final ItemStack cura = item(Material.MUSHROOM_SOUP, 0, "§bTipo de cura", false, "");
		final ItemMeta cmeta = cura.getItemMeta();
		cmeta.setLore(curaL);
		cura.setItemMeta(cmeta);
		final ItemStack voltar = item(Material.CARPET, 0, "§c§lVoltar", false, "");
		voltar.setDurability((short) DyeColor.RED.getData());
		this.invC.setItem(10, espada);
		this.invC.setItem(12, armadura);
		this.invC.setItem(14, refil);
		this.invC.setItem(16, pocao);
		this.invC.setItem(28, ft);
		this.invC.setItem(30, desafiar);
		this.invC.setItem(31, desafiar);
		this.invC.setItem(32, desafiar);
		this.invC.setItem(34, cura);
		this.invC.setItem(36, voltar);
		for (int i = 0; i < this.invC.getSize(); ++i) {
			if (this.invC.getItem(i) == null) {
				this.invC.setItem(i, vidro);
			}
		}
		p.closeInventory();
		p.openInventory(this.invC);
	}

	@EventHandler
	public void onInventoryClickCustom(final InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		final ItemStack item = e.getCurrentItem();
		final String nome = e.getInventory().getTitle().replaceAll(ChatColor.DARK_RED + "Desafiando " + ChatColor.RED,
				"");
		final Player d = Bukkit.getServer().getPlayer(nome);
		if (naArena.contains(p) && e.getInventory().getTitle() != ""
				&& e.getInventory().getTitle().contains("Desafiando")) {
			e.setCancelled(true);
			if (item != null && item.hasItemMeta()) {
				if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Espada")) {
					if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT) {
						if (item.getType() == Material.DIAMOND_SWORD) {
							final ItemMeta im = item.getItemMeta();
							final List<String> lore = (List<String>) im.getLore();
							lore.set(4, ChatColor.translateAlternateColorCodes('§', "    §7Diamante"));
							lore.set(5, ChatColor.translateAlternateColorCodes('§', "  §e► Ferro"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.IRON_SWORD);
						} else if (item.getType() == Material.IRON_SWORD) {
							final ItemMeta im = item.getItemMeta();
							final List<String> lore = (List<String>) im.getLore();
							lore.set(5, ChatColor.translateAlternateColorCodes('§', "    §7Ferro"));
							lore.set(6, ChatColor.translateAlternateColorCodes('§', "  §e► Pedra"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.STONE_SWORD);
						} else if (item.getType() == Material.STONE_SWORD) {
							final ItemMeta im = item.getItemMeta();
							final List<String> lore = (List<String>) im.getLore();
							lore.set(6, ChatColor.translateAlternateColorCodes('§', "    §7Pedra"));
							lore.set(7, ChatColor.translateAlternateColorCodes('§', "  §e► Ouro"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.GOLD_SWORD);
						} else if (item.getType() == Material.GOLD_SWORD) {
							final ItemMeta im = item.getItemMeta();
							final List<String> lore = (List<String>) im.getLore();
							lore.set(7, ChatColor.translateAlternateColorCodes('§', "    §7Ouro"));
							lore.set(8, ChatColor.translateAlternateColorCodes('§', "  §e► Madeira"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.WOOD_SWORD);
						} else if (item.getType() == Material.WOOD_SWORD) {
							final ItemMeta im = item.getItemMeta();
							final List<String> lore = (List<String>) im.getLore();
							lore.set(8, ChatColor.translateAlternateColorCodes('§', "    §7Madeira"));
							lore.set(4, ChatColor.translateAlternateColorCodes('§', "  §e► Diamante"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.DIAMOND_SWORD);
						}
					} else if (e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
						final ItemMeta im = item.getItemMeta();
						final List<String> lore = (List<String>) im.getLore();
						if (item.getEnchantments().isEmpty()) {
							lore.set(12, ChatColor.translateAlternateColorCodes('§', "    §7Sem Sharpness"));
							lore.set(13, ChatColor.translateAlternateColorCodes('§', "  §e► Sharpness I"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.addEnchantment(Enchantment.DAMAGE_ALL, 1);
						} else if (item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) < 5) {
							if (item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 1) {
								lore.set(13, ChatColor.translateAlternateColorCodes('§', "    §7Sharpness I"));
								lore.set(14, ChatColor.translateAlternateColorCodes('§', "  §e► Sharpness II"));
								im.setLore(lore);
								item.setItemMeta(im);
								item.addEnchantment(Enchantment.DAMAGE_ALL,
										item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1);
							} else if (item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 2) {
								lore.set(14, ChatColor.translateAlternateColorCodes('§', "    §7Sharpness II"));
								lore.set(15, ChatColor.translateAlternateColorCodes('§', "  §e► Sharpness III"));
								im.setLore(lore);
								item.setItemMeta(im);
								item.addEnchantment(Enchantment.DAMAGE_ALL,
										item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1);
							} else if (item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 3) {
								lore.set(15, ChatColor.translateAlternateColorCodes('§', "    §7Sharpness III"));
								lore.set(16, ChatColor.translateAlternateColorCodes('§', "  §e► Sharpness IV"));
								im.setLore(lore);
								item.setItemMeta(im);
								item.addEnchantment(Enchantment.DAMAGE_ALL,
										item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1);
							} else if (item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) == 4) {
								lore.set(16, ChatColor.translateAlternateColorCodes('§', "    §7Sharpness IV"));
								lore.set(17, ChatColor.translateAlternateColorCodes('§', "  §e► Sharpness V"));
								im.setLore(lore);
								item.setItemMeta(im);
								item.addEnchantment(Enchantment.DAMAGE_ALL,
										item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) + 1);
							}
						} else {
							lore.set(17, ChatColor.translateAlternateColorCodes('§', "    §7Sharpness V"));
							lore.set(12, ChatColor.translateAlternateColorCodes('§', "  §e► Sem Sharpness"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.removeEnchantment(Enchantment.DAMAGE_ALL);
						}
					}
				}
				if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Armadura")) {
					if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT) {
						final ItemMeta im = item.getItemMeta();
						final List<String> lore = (List<String>) im.getLore();
						if (item.getType() == Material.IRON_CHESTPLATE) {
							lore.set(5, ChatColor.translateAlternateColorCodes('§', "    §7Ferro"));
							lore.set(6, ChatColor.translateAlternateColorCodes('§', "  §e► Chain"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.CHAINMAIL_CHESTPLATE);
						} else if (item.getType() == Material.CHAINMAIL_CHESTPLATE) {
							lore.set(6, ChatColor.translateAlternateColorCodes('§', "    §7Chain"));
							lore.set(7, ChatColor.translateAlternateColorCodes('§', "  §e► Ouro"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.GOLD_CHESTPLATE);
						} else if (item.getType() == Material.GOLD_CHESTPLATE) {
							lore.set(7, ChatColor.translateAlternateColorCodes('§', "    §7Ouro"));
							lore.set(8, ChatColor.translateAlternateColorCodes('§', "  §e► Couro"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.LEATHER_CHESTPLATE);
						} else if (item.getType() == Material.LEATHER_CHESTPLATE) {
							lore.set(8, ChatColor.translateAlternateColorCodes('§', "    §7Couro"));
							lore.set(4, ChatColor.translateAlternateColorCodes('§', "  §e► Diamante"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.DIAMOND_CHESTPLATE);
						} else if (item.getType() == Material.DIAMOND_CHESTPLATE) {
							lore.set(4, ChatColor.translateAlternateColorCodes('§', "    §7Diamante"));
							lore.set(5, ChatColor.translateAlternateColorCodes('§', "  §e► Ferro"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.setType(Material.IRON_CHESTPLATE);
						}
					} else if (e.getClick() == ClickType.RIGHT || e.getClick() == ClickType.SHIFT_RIGHT) {
						final ItemMeta im = item.getItemMeta();
						final List<String> lore = (List<String>) im.getLore();
						if (item.getEnchantments().isEmpty()) {
							lore.set(12, ChatColor.translateAlternateColorCodes('§', "    §7Sem Protection"));
							lore.set(13, ChatColor.translateAlternateColorCodes('§', "  §e► Protection I"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
						} else if (item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) < 4) {
							if (item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 1) {
								lore.set(13, ChatColor.translateAlternateColorCodes('§', "    §7Protection I"));
								lore.set(14, ChatColor.translateAlternateColorCodes('§', "  §e► Protection II"));
								im.setLore(lore);
								item.setItemMeta(im);
								item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1);
							} else if (item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 2) {
								lore.set(14, ChatColor.translateAlternateColorCodes('§', "    §7Protection II"));
								lore.set(15, ChatColor.translateAlternateColorCodes('§', "  §e► Protection III"));
								im.setLore(lore);
								item.setItemMeta(im);
								item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1);
							} else if (item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) == 3) {
								lore.set(15, ChatColor.translateAlternateColorCodes('§', "    §7Protection III"));
								lore.set(16, ChatColor.translateAlternateColorCodes('§', "  §e► Protection IV"));
								im.setLore(lore);
								item.setItemMeta(im);
								item.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) + 1);
							}
						} else {
							lore.set(16, ChatColor.translateAlternateColorCodes('§', "    §7Protection IV"));
							lore.set(12, ChatColor.translateAlternateColorCodes('§', "  §e► Sem Protection"));
							im.setLore(lore);
							item.setItemMeta(im);
							item.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
						}
					}
				}
				if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Refil")
						&& (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT)) {
					final ItemMeta im = item.getItemMeta();
					final List<String> lore = (List<String>) im.getLore();
					if (item.getType() == Material.BOWL) {
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "    §7Sem Refil"));
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "  §e► Com Refil"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.MUSHROOM_SOUP);
					} else if (item.getType() == Material.MUSHROOM_SOUP) {
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "    §7Com Refil"));
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "  §e► Sem Refil"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.BOWL);
					} else if (item.getType() == Material.GLASS_BOTTLE) {
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "    §7Sem Refil"));
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "  §e► Com Refil"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.POTION);
					} else if (item.getType() == Material.POTION) {
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "    §7Com Refil"));
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "  §e► Sem Refil"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.GLASS_BOTTLE);
					}
				}
				if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Pocoes")
						&& (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT)) {
					final ItemMeta im = item.getItemMeta();
					final List<String> lore = (List<String>) im.getLore();
					if (item.getType() == Material.GLASS_BOTTLE) {
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "    §7Nenhuma"));
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "  §e► Forca II"));
						im.setLore(lore);
						item.setItemMeta(im);
						final Potion pocao = new Potion(PotionType.STRENGTH, 2);
						final ItemStack forca = pocao.toItemStack(1);
						forca.setItemMeta(im);
						this.invC.setItem(16, forca);
					} else if (item.getType() == Material.POTION && item.getItemMeta().getLore().get(5).contains("►")) {
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "    §7Forca II"));
						lore.set(6, ChatColor.translateAlternateColorCodes('§', "  §e► Speed II"));
						im.setLore(lore);
						item.setItemMeta(im);
						final Potion pocao = new Potion(PotionType.SPEED, 2);
						final ItemStack speed = pocao.toItemStack(1);
						speed.setItemMeta(im);
						this.invC.setItem(16, speed);
					} else if (item.getType() == Material.POTION && item.getItemMeta().getLore().get(6).contains("►")) {
						lore.set(6, ChatColor.translateAlternateColorCodes('§', "    §7Speed II"));
						lore.set(7, ChatColor.translateAlternateColorCodes('§', "  §e► Speed II e Forca II"));
						im.setLore(lore);
						item.setItemMeta(im);
						final ItemStack pocao2 = new ItemStack(Material.POTION);
						pocao2.setItemMeta(im);
						this.invC.setItem(16, pocao2);
					} else if (item.getType() == Material.POTION && item.getItemMeta().getLore().get(7).contains("►")) {
						lore.set(7, ChatColor.translateAlternateColorCodes('§', "    §7Speed II e Forca II"));
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "  §e► Nenhuma"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.GLASS_BOTTLE);
					}
				}
				if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "First to")
						&& (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT)) {
					final ItemMeta im = item.getItemMeta();
					final List<String> lore = (List<String>) im.getLore();
					if (item.getType() == Material.ITEM_FRAME && item.getAmount() == 1) {
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "    §7Uma luta"));
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "  §e► First to 3"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.ITEM_FRAME);
						item.setAmount(3);
					} else if (item.getType() == Material.ITEM_FRAME && item.getAmount() == 3) {
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "    §7First to 3"));
						lore.set(6, ChatColor.translateAlternateColorCodes('§', "  §e► First to 5"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.ITEM_FRAME);
						item.setAmount(5);
					} else if (item.getType() == Material.ITEM_FRAME && item.getAmount() == 5) {
						lore.set(6, ChatColor.translateAlternateColorCodes('§', "    §7First to 5"));
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "  §e► Uma luta"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.ITEM_FRAME);
						item.setAmount(1);
					}
				}
				if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Tipo de cura")
						&& (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.SHIFT_LEFT)) {
					final ItemMeta im = item.getItemMeta();
					final List<String> lore = (List<String>) im.getLore();
					if (item.getType() == Material.MUSHROOM_SOUP) {
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "    §7Sopas"));
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "  §e► Pocoes"));
						im.setLore(lore);
						final Potion pocao = new Potion(PotionType.INSTANT_HEAL, 2);
						pocao.setSplash(true);
						final ItemStack heal = pocao.toItemStack(1);
						heal.setItemMeta(im);
						this.invC.setItem(34, heal);
						if (this.invC.getItem(14).getType() == Material.BOWL) {
							final ItemStack refil = new ItemStack(Material.GLASS_BOTTLE);
							refil.getItemMeta().setLore(this.invC.getItem(14).getItemMeta().getLore());
							refil.setItemMeta(this.invC.getItem(14).getItemMeta());
							this.invC.setItem(14, refil);
						} else if (this.invC.getItem(14).getType() == Material.MUSHROOM_SOUP) {
							final ItemStack refil = new ItemStack(Material.POTION);
							refil.getItemMeta().setLore(this.invC.getItem(14).getItemMeta().getLore());
							refil.setItemMeta(this.invC.getItem(14).getItemMeta());
							this.invC.setItem(14, refil);
						}
					} else if (item.getType() == Material.POTION) {
						lore.set(5, ChatColor.translateAlternateColorCodes('§', "    §7Pocoes"));
						lore.set(4, ChatColor.translateAlternateColorCodes('§', "  §e► Sopas"));
						im.setLore(lore);
						item.setItemMeta(im);
						item.setType(Material.MUSHROOM_SOUP);
						if (this.invC.getItem(14).getType() == Material.GLASS_BOTTLE) {
							final ItemStack refil2 = new ItemStack(Material.BOWL);
							refil2.getItemMeta().setLore(this.invC.getItem(14).getItemMeta().getLore());
							refil2.setItemMeta(this.invC.getItem(14).getItemMeta());
							this.invC.setItem(14, refil2);
						} else if (this.invC.getItem(14).getType() == Material.POTION) {
							final List<String> refilL = new ArrayList<String>();
							refilL.add(ChatColor.translateAlternateColorCodes('§', "§9Alterne o Refil"));
							refilL.add(" ");
							refilL.add(ChatColor.translateAlternateColorCodes('§', "§aClick-esquerdo"));
							refilL.add(ChatColor.translateAlternateColorCodes('§', "  §bRefil:"));
							refilL.add(ChatColor.translateAlternateColorCodes('§', "    §7Sem Refil"));
							refilL.add(ChatColor.translateAlternateColorCodes('§', "  §e► Com Refil"));
							final ItemStack refil3 = item(Material.MUSHROOM_SOUP, 0, "§bRefil", false, "");
							this.invC.setItem(14, refil3);
						}
					}
				}
				if (item.getItemMeta().getDisplayName().contains(new StringBuilder().append(ChatColor.DARK_RED)
						.append(ChatColor.BOLD).append("Desafiar").toString())) {
					this.Espada.put(String.valueOf(d.getName()) + p.getName(), this.invC.getItem(10).getType());
					if (this.invC.getItem(10).getEnchantments().isEmpty()) {
						this.EspadaE.put(String.valueOf(d.getName()) + p.getName(), false);
					} else {
						this.EspadaE.put(String.valueOf(d.getName()) + p.getName(), true);
					}
					this.EspadaL.put(String.valueOf(d.getName()) + p.getName(),
							this.invC.getItem(10).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
					this.Armadura.put(String.valueOf(d.getName()) + p.getName(), this.invC.getItem(12).getType());
					if (this.invC.getItem(12).getEnchantments().isEmpty()) {
						this.ArmaduraE.put(String.valueOf(d.getName()) + p.getName(), false);
					} else {
						this.ArmaduraE.put(String.valueOf(d.getName()) + p.getName(), true);
					}
					this.ArmaduraL.put(String.valueOf(d.getName()) + p.getName(),
							this.invC.getItem(12).getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
					if (this.invC.getItem(14).getType() == Material.BOWL
							|| this.invC.getItem(14).getType() == Material.GLASS_BOTTLE) {
						this.Refil.put(String.valueOf(d.getName()) + p.getName(), false);
					} else {
						this.Refil.put(String.valueOf(d.getName()) + p.getName(), true);
					}
					if (this.invC.getItem(16).getItemMeta().getLore().contains("  " + ChatColor.GREEN + "► Nenhuma")) {
						this.Pocao.put(String.valueOf(d.getName()) + p.getName(), 1);
					} else if (this.invC.getItem(16).getItemMeta().getLore()
							.contains("  " + ChatColor.GREEN + "► Forca II")) {
						this.Pocao.put(String.valueOf(d.getName()) + p.getName(), 2);
					} else if (this.invC.getItem(16).getItemMeta().getLore()
							.contains("  " + ChatColor.GREEN + "► Speed II")) {
						this.Pocao.put(String.valueOf(d.getName()) + p.getName(), 3);
					} else if (this.invC.getItem(16).getItemMeta().getLore()
							.contains("  " + ChatColor.GREEN + "► Speed II e Forca II")) {
						this.Pocao.put(String.valueOf(d.getName()) + p.getName(), 4);
					}
					this.FT.put(String.valueOf(d.getName()) + p.getName(), this.invC.getItem(28).getAmount());
					if (this.invC.getItem(34).getType() == Material.MUSHROOM_SOUP) {
						this.Cura.put(String.valueOf(d.getName()) + p.getName(), 1);
					} else if (this.invC.getItem(34).getType() == Material.POTION) {
						this.Cura.put(String.valueOf(d.getName()) + p.getName(), 2);
					}
					this.Custom.put(p, d);
					this.Pedido.put(p, d);
					p.closeInventory();
					p.sendMessage(
							StringUtils.avisoverde + "§eVoce desafiou §6§l" + d.getName() + " §epara um 1v1 Custom!");
					d.sendMessage(StringUtils.avisoverde + "§6§l" + p.getName() + " §ete desafiou para um 1v1 Custom!");
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) UmVsUm.main,
							(Runnable) new Runnable() {
								@Override
								public void run() {
									if (UmVsUm.this.Custom.containsKey(p)) {
										UmVsUm.this.Custom.remove(p);
									}
									if (UmVsUm.this.Pedido.containsKey(p)) {
										UmVsUm.this.Pedido.remove(p);
									}
									if (UmVsUm.this.Custom.containsKey(d)) {
										UmVsUm.this.Custom.remove(d);
									}
									if (UmVsUm.this.Pedido.containsKey(d)) {
										UmVsUm.this.Pedido.remove(d);
									}
								}
							}, 200L);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClickAceitar(final InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		final ItemStack item = e.getCurrentItem();
		if (naArena.contains(p) && e.getInventory().getTitle() != ""
				&& e.getInventory().getTitle().contains("Desafiado")) {
			final String nome = e.getInventory().getItem(30).getItemMeta().getDisplayName()
					.replaceAll(new StringBuilder().append(ChatColor.DARK_GREEN).append(ChatColor.BOLD)
							.append("Aceitar o desafio de ").toString(), "");
			final Player d = Bukkit.getServer().getPlayer(nome);
			e.setCancelled(true);
			if (item != null && item.hasItemMeta() && item.getType() == Material.WOOL
					&& item.getItemMeta().getDisplayName().contains(new StringBuilder().append(ChatColor.DARK_GREEN)
							.append(ChatColor.BOLD).append("Aceitar").toString())) {
				p.closeInventory();
				this.Pedido.remove(p);
				this.Pedido.remove(d);
				this.Normal.remove(p);
				this.Normal.remove(d);
				this.Buffed.remove(p);
				this.Buffed.remove(d);
				this.BuffedSpeed.remove(p);
				this.BuffedSpeed.remove(d);
				this.Custom.remove(p);
				this.Custom.remove(d);

				p.teleport(Uteis.getLocation(Main.getMe().getConfig().getString("1V1.Pos1")));

				d.teleport(Uteis.getLocation(Main.getMe().getConfig().getString("1V1.Pos2")));
				clear(p);
				clear(d);
				for (Player pl : Bukkit.getOnlinePlayers()) {
					p.hidePlayer(pl);
					d.hidePlayer(pl);
					p.showPlayer(d);
					d.showPlayer(p);
				}
				if (this.invA.getItem(10) != null && this.invA.getItem(10).hasItemMeta()) {
					final Material m = this.invA.getItem(10).getType();
					if (this.invA.getItem(10).getEnchantments().isEmpty()) {
						p.getInventory().setItem(0, new ItemStack(m));
						d.getInventory().setItem(0, new ItemStack(m));
					} else {
						final ItemStack espada = new ItemStack(m);
						espada.addEnchantment(Enchantment.DAMAGE_ALL,
								this.invA.getItem(10).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
						p.getInventory().setItem(0, espada);
						d.getInventory().setItem(0, espada);
					}
				}
				if (this.invA.getItem(12) != null && this.invA.getItem(12).hasItemMeta()) {
					final Material m = this.invA.getItem(12).getType();
					final ItemStack capacete = new ItemStack(Material.IRON_HELMET);
					final ItemStack peitoral = new ItemStack(Material.IRON_CHESTPLATE);
					final ItemStack calca = new ItemStack(Material.IRON_LEGGINGS);
					final ItemStack bota = new ItemStack(Material.IRON_BOOTS);
					if (!this.invA.getItem(12).getEnchantments().isEmpty()) {
						capacete.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
								this.invA.getItem(12).getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
						peitoral.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
								this.invA.getItem(12).getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
						calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
								this.invA.getItem(12).getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
						bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
								this.invA.getItem(12).getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
					}
					if (m == Material.DIAMOND_CHESTPLATE) {
						if (this.invA.getItem(12).getEnchantments().isEmpty()) {
							p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
							p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
							p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
							p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
							d.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
							d.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
							d.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
							d.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
						} else {
							capacete.setType(Material.DIAMOND_HELMET);
							peitoral.setType(Material.DIAMOND_CHESTPLATE);
							calca.setType(Material.DIAMOND_LEGGINGS);
							bota.setType(Material.DIAMOND_BOOTS);
							p.getInventory().setHelmet(capacete);
							p.getInventory().setChestplate(peitoral);
							p.getInventory().setLeggings(calca);
							p.getInventory().setBoots(bota);
							d.getInventory().setHelmet(capacete);
							d.getInventory().setChestplate(peitoral);
							d.getInventory().setLeggings(calca);
							d.getInventory().setBoots(bota);
						}
					} else if (m == Material.IRON_CHESTPLATE) {
						if (this.invA.getItem(12).getEnchantments().isEmpty()) {
							p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
							p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
							p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
							p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
							d.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
							d.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
							d.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
							d.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
						} else {
							capacete.setType(Material.IRON_HELMET);
							peitoral.setType(Material.IRON_CHESTPLATE);
							calca.setType(Material.IRON_LEGGINGS);
							bota.setType(Material.IRON_BOOTS);
							p.getInventory().setHelmet(capacete);
							p.getInventory().setChestplate(peitoral);
							p.getInventory().setLeggings(calca);
							p.getInventory().setBoots(bota);
							d.getInventory().setHelmet(capacete);
							d.getInventory().setChestplate(peitoral);
							d.getInventory().setLeggings(calca);
							d.getInventory().setBoots(bota);
						}
					} else if (m == Material.CHAINMAIL_CHESTPLATE) {
						if (this.invA.getItem(12).getEnchantments().isEmpty()) {
							p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
							p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
							p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
							p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
							d.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
							d.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
							d.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
							d.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
						} else {
							capacete.setType(Material.CHAINMAIL_HELMET);
							peitoral.setType(Material.CHAINMAIL_CHESTPLATE);
							calca.setType(Material.CHAINMAIL_LEGGINGS);
							bota.setType(Material.CHAINMAIL_BOOTS);
							p.getInventory().setHelmet(capacete);
							p.getInventory().setChestplate(peitoral);
							p.getInventory().setLeggings(calca);
							p.getInventory().setBoots(bota);
							d.getInventory().setHelmet(capacete);
							d.getInventory().setChestplate(peitoral);
							d.getInventory().setLeggings(calca);
							d.getInventory().setBoots(bota);
						}
					} else if (m == Material.GOLD_CHESTPLATE) {
						if (this.invA.getItem(12).getEnchantments().isEmpty()) {
							p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
							p.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
							p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
							p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
							d.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
							d.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
							d.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
							d.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
						} else {
							capacete.setType(Material.GOLD_HELMET);
							peitoral.setType(Material.GOLD_CHESTPLATE);
							calca.setType(Material.GOLD_LEGGINGS);
							bota.setType(Material.GOLD_BOOTS);
							p.getInventory().setHelmet(capacete);
							p.getInventory().setChestplate(peitoral);
							p.getInventory().setLeggings(calca);
							p.getInventory().setBoots(bota);
							d.getInventory().setHelmet(capacete);
							d.getInventory().setChestplate(peitoral);
							d.getInventory().setLeggings(calca);
							d.getInventory().setBoots(bota);
						}
					} else if (m == Material.LEATHER_CHESTPLATE) {
						if (this.invA.getItem(12).getEnchantments().isEmpty()) {
							p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
							p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
							p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
							p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
							d.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
							d.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
							d.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
							d.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
						} else {
							capacete.setType(Material.LEATHER_HELMET);
							peitoral.setType(Material.LEATHER_CHESTPLATE);
							calca.setType(Material.LEATHER_LEGGINGS);
							bota.setType(Material.LEATHER_BOOTS);
							p.getInventory().setHelmet(capacete);
							p.getInventory().setChestplate(peitoral);
							p.getInventory().setLeggings(calca);
							p.getInventory().setBoots(bota);
							d.getInventory().setHelmet(capacete);
							d.getInventory().setChestplate(peitoral);
							d.getInventory().setLeggings(calca);
							d.getInventory().setBoots(bota);
						}
					} else if (m == Material.GLASS) {
						p.getInventory().setHelmet(new ItemStack(Material.AIR));
						p.getInventory().setChestplate(new ItemStack(Material.AIR));
						p.getInventory().setLeggings(new ItemStack(Material.AIR));
						p.getInventory().setBoots(new ItemStack(Material.AIR));
						d.getInventory().setHelmet(new ItemStack(Material.AIR));
						d.getInventory().setChestplate(new ItemStack(Material.AIR));
						d.getInventory().setLeggings(new ItemStack(Material.AIR));
						d.getInventory().setBoots(new ItemStack(Material.AIR));
					}
				}
				if (this.invA.getItem(14) != null && this.invA.getItem(14).hasItemMeta()) {
					final Potion po = new Potion(PotionType.INSTANT_HEAL, 2);
					po.setSplash(true);
					final ItemStack pocao = po.toItemStack(1);
					if (this.invA.getItem(14).getType() == Material.MUSHROOM_SOUP) {
						FillInv(p, new ItemStack(Material.MUSHROOM_SOUP));
						FillInv(d, new ItemStack(Material.MUSHROOM_SOUP));
					} else if (this.invA.getItem(14).getType() == Material.BOWL) {
						FillHotBar(p, new ItemStack(Material.MUSHROOM_SOUP));
						FillHotBar(d, new ItemStack(Material.MUSHROOM_SOUP));
					} else if (this.invA.getItem(14).getType() == Material.GLASS_BOTTLE) {
						FillHotBar(p, pocao);
						FillHotBar(d, pocao);
					} else if (this.invA.getItem(14).getType() == Material.POTION) {
						FillInv(p, pocao);
						FillInv(d, pocao);
					}
				}
				if (this.invA.getItem(16) != null && this.invA.getItem(16).hasItemMeta()
						&& this.invA.getItem(16).getType() == Material.POTION) {
					if (this.invA.getItem(16).getDurability() == 41) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
						d.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
					} else if (this.invA.getItem(16).getDurability() == 34) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
						d.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
					} else if (this.invA.getItem(16).getType() == Material.POTION) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
						p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
						d.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
						d.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
					}
				}
				if (this.invA.getItem(28) != null && this.invA.getItem(28).hasItemMeta()
						&& this.invA.getItem(28).getType() == Material.ITEM_FRAME) {
					final String placarP1 = ChatColor.translateAlternateColorCodes('§',
							"§f" + p.getName() + "§6: §e§l0§e/1 §b- §f" + d.getName() + "§6 §e§l0§e/1");
					final String placarD1 = ChatColor.translateAlternateColorCodes('§',
							"§f" + d.getName() + "§6: §e§l0§e/1 §b- §f" + p.getName() + "§6 §e§l0§e/1");
					final String placarP2 = ChatColor.translateAlternateColorCodes('§',
							"§f" + p.getName() + "§6: §e§l0§e/3 §b- §f" + d.getName() + "§6 §e§l0§e/3");
					final String placarD2 = ChatColor.translateAlternateColorCodes('§',
							"§f" + d.getName() + "§6: §e§l0§e/3 §b- §f" + p.getName() + "§6 §e§l0§e/3");
					final String placarP3 = ChatColor.translateAlternateColorCodes('§',
							"§f" + p.getName() + "§6: §e§l0§e/5 §b- §f" + d.getName() + "§6 §e§l0§e/5");
					final String placarD3 = ChatColor.translateAlternateColorCodes('§',
							"§f" + d.getName() + "§6: §e§l0§e/5 §b- §f" + p.getName() + "§6 §e§l0§e/5");
					if (this.invA.getItem(28).getAmount() == 1) {
						this.ft.remove(p);
						this.ft.remove(d);
						this.placar.remove(p);
						this.placar.remove(d);
						this.ft.put(p, 1);
						this.ft.put(d, 1);
						this.placar.put(p, 0);
						this.placar.put(d, 0);
						BarAPI.setMessage(p, placarP1, 5);
						BarAPI.setMessage(d, placarD1, 5);
					} else if (this.invA.getItem(28).getAmount() == 3) {
						this.ft.remove(p);
						this.ft.remove(d);
						this.placar.remove(p);
						this.placar.remove(d);
						this.ft.put(p, 3);
						this.ft.put(d, 3);
						this.placar.put(p, 0);
						this.placar.put(d, 0);
						BarAPI.setMessage(p, placarP2, 5);
						BarAPI.setMessage(d, placarD2, 5);
					} else if (this.invA.getItem(28).getAmount() == 5) {
						this.ft.remove(p);
						this.ft.remove(d);
						this.placar.remove(p);
						this.placar.remove(d);
						this.ft.put(p, 5);
						this.ft.put(d, 5);
						this.placar.put(p, 0);
						this.placar.put(d, 0);
						BarAPI.setMessage(p, placarP3, 5);
						BarAPI.setMessage(d, placarD3, 5);
					}
				}
				this.lutaEspada.put(String.valueOf(p.getName()) + d.getName(), p.getInventory().getItem(0).getType());
				this.lutaEspada.put(String.valueOf(d.getName()) + p.getName(), p.getInventory().getItem(0).getType());
				if (p.getInventory().getItem(0).getEnchantments().isEmpty()) {
					this.lutaEspadaE.put(String.valueOf(p.getName()) + d.getName(), false);
					this.lutaEspadaE.put(String.valueOf(d.getName()) + p.getName(), false);
				} else {
					this.lutaEspadaE.put(String.valueOf(p.getName()) + d.getName(), true);
					this.lutaEspadaE.put(String.valueOf(d.getName()) + p.getName(), true);
					this.lutaEspadaL.put(String.valueOf(p.getName()) + d.getName(),
							p.getInventory().getItem(0).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
					this.lutaEspadaL.put(String.valueOf(d.getName()) + p.getName(),
							p.getInventory().getItem(0).getEnchantmentLevel(Enchantment.DAMAGE_ALL));
				}
				this.lutaArmadura.put(String.valueOf(p.getName()) + d.getName(),
						p.getInventory().getChestplate().getType());
				this.lutaArmadura.put(String.valueOf(d.getName()) + p.getName(),
						p.getInventory().getChestplate().getType());
				if (p.getInventory().getChestplate().getEnchantments().isEmpty()) {
					this.lutaArmaduraE.put(String.valueOf(p.getName()) + d.getName(), false);
					this.lutaArmaduraE.put(String.valueOf(d.getName()) + p.getName(), false);
				} else {
					this.lutaArmaduraE.put(String.valueOf(p.getName()) + d.getName(), true);
					this.lutaArmaduraE.put(String.valueOf(d.getName()) + p.getName(), true);
					this.lutaArmaduraL.put(String.valueOf(p.getName()) + d.getName(),
							p.getInventory().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
					this.lutaArmaduraL.put(String.valueOf(d.getName()) + p.getName(),
							p.getInventory().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
				}
				if (p.getInventory().getItem(1).getType() == Material.MUSHROOM_SOUP) {
					this.lutaCura.put(String.valueOf(p.getName()) + d.getName(), 1);
					this.lutaCura.put(String.valueOf(d.getName()) + p.getName(), 1);
				} else {
					this.lutaCura.put(String.valueOf(p.getName()) + d.getName(), 2);
					this.lutaCura.put(String.valueOf(d.getName()) + p.getName(), 2);
				}
				if (p.getInventory().getItem(9) != null) {
					this.lutaRefil.put(String.valueOf(p.getName()) + d.getName(), true);
					this.lutaRefil.put(String.valueOf(d.getName()) + p.getName(), true);
				} else {
					this.lutaRefil.put(String.valueOf(p.getName()) + d.getName(), false);
					this.lutaRefil.put(String.valueOf(d.getName()) + p.getName(), false);
				}
				if (p.getActivePotionEffects().isEmpty()) {
					this.lutaPocao.put(String.valueOf(p.getName()) + d.getName(), 1);
					this.lutaPocao.put(String.valueOf(d.getName()) + p.getName(), 1);
				} else if (p.hasPotionEffect(PotionEffectType.SPEED)
						&& !p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
					this.lutaPocao.put(String.valueOf(p.getName()) + d.getName(), 2);
					this.lutaPocao.put(String.valueOf(d.getName()) + p.getName(), 2);
				} else if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)
						&& !p.hasPotionEffect(PotionEffectType.SPEED)) {
					this.lutaPocao.put(String.valueOf(p.getName()) + d.getName(), 3);
					this.lutaPocao.put(String.valueOf(d.getName()) + p.getName(), 3);
				} else {
					this.lutaPocao.put(String.valueOf(p.getName()) + d.getName(), 4);
					this.lutaPocao.put(String.valueOf(d.getName()) + p.getName(), 4);
				}
				naLuta.put(p, d);
				naLuta.put(d, p);
			}
		}
	}

	public void quickMatch(Player p, Player d) {
		naLuta.put(p, d);
		naLuta.put(d, p);
		this.Pedido.remove(p);
		this.Pedido.remove(d);
		this.Normal.remove(p);
		this.Normal.remove(d);
		this.Buffed.remove(p);
		this.Buffed.remove(d);
		this.BuffedSpeed.remove(p);
		this.BuffedSpeed.remove(d);
		this.Custom.remove(p);
		this.Custom.remove(d);

		p.teleport(Uteis.getLocation(Main.getMe().getConfig().getString("1V1.Pos1")));

		d.teleport(Uteis.getLocation(Main.getMe().getConfig().getString("1V1.Pos2")));
		this.placar.put(p, 0);
		this.placar.put(d, 0);
		this.ft.put(p, 1);
		this.ft.put(d, 1);
		final String placarP1 = ChatColor.translateAlternateColorCodes('§',
				"§f" + p.getName() + "§6: §e§l0§e/1 §b- §f" + d.getName() + "§6 §e§l0§e/1");
		final String placarD1 = ChatColor.translateAlternateColorCodes('§',
				"§f" + d.getName() + "§6: §e§l0§e/1 §b- §f" + p.getName() + "§6 §e§l0§e/1");
		clear(p);
		clear(d);
		for (Player pl : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(pl);
			d.hidePlayer(pl);
			p.showPlayer(d);
			d.showPlayer(p);
		}
		BarAPI.setMessage(p, placarP1, 5);
		BarAPI.setMessage(d, placarD1, 5);
		ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
		espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		p.getInventory().setItem(0, espada);
		KitManager.darSopas(p);
		d.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		d.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		d.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		d.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		d.getInventory().setItem(0, espada);
		KitManager.darSopas(d);
		Random.remove(p);
		Random.remove(d);
	}

	@EventHandler
	public void onInventoryClickNormal(final InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		final ItemStack item = e.getCurrentItem();
		final String nome = e.getInventory().getTitle().replaceAll(ChatColor.DARK_RED + "Desafiando " + ChatColor.RED,
				"");
		final Player d = Bukkit.getServer().getPlayer(nome);
		if (naArena.contains(p) && e.getInventory().getTitle() != ""
				&& e.getInventory().getTitle().contains("Desafiando")) {
			e.setCancelled(true);
			if (item != null && item.hasItemMeta()) {
				if (item.getType() == Material.ANVIL
						&& item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Custom")) {
					this.InvCustom(p, d, e.getInventory());
				} else if (item.getType() == Material.ENCHANTED_BOOK
						&& item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Normal")) {
					this.Normal.put(p, d);
					this.Pedido.put(p, d);
					p.closeInventory();
					p.sendMessage(
							StringUtils.avisoverde + "§eVoce desafiou §6§l" + d.getName() + " §epara um 1v1 Normal!");
					d.sendMessage(StringUtils.avisoverde + "§6§l" + p.getName() + " §ete desafiou para um 1v1 Normal!");
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) UmVsUm.main,
							(Runnable) new Runnable() {
								@Override
								public void run() {
									if (UmVsUm.this.Normal.containsKey(p)) {
										UmVsUm.this.Normal.remove(p);
									}
									if (UmVsUm.this.Pedido.containsKey(p)) {
										UmVsUm.this.Pedido.remove(p);
									}
									if (UmVsUm.this.Normal.containsKey(d)) {
										UmVsUm.this.Normal.remove(d);
									}
									if (UmVsUm.this.Pedido.containsKey(d)) {
										UmVsUm.this.Pedido.remove(d);
									}
								}
							}, 200L);
				} else if (item.getType() == Material.ENCHANTED_BOOK
						&& item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Buffed")) {
					this.Buffed.put(p, d);
					this.Pedido.put(p, d);
					p.closeInventory();
					p.sendMessage(
							StringUtils.avisoverde + "§eVoce desafiou §6§l" + d.getName() + " §epara um 1v1 Buffed!");
					d.sendMessage(StringUtils.avisoverde + "§6§l" + p.getName() + " §ete desafiou para um 1v1 Buffed!");
				} else if (item.getType() == Material.ENCHANTED_BOOK && item.getItemMeta().getDisplayName()
						.equalsIgnoreCase(ChatColor.GREEN + "Buffed com Speed II")) {
					this.BuffedSpeed.put(p, d);
					this.Pedido.put(p, d);
					p.closeInventory();
					p.sendMessage(
							StringUtils.avisoverde + "§eVoce desafiou §6§l" + d.getName() + " §epara um 1v1 Speed II!");
					d.sendMessage(
							StringUtils.avisoverde + "§6§l" + p.getName() + " §ete desafiou para um 1v1 Speed II!");
				}
			}
		}
	}

	@EventHandler
	public void onCommandProcess(final PlayerCommandPreprocessEvent e) {
		final Player p = e.getPlayer();
		if (naArena.contains(p)) {
			if (naLuta.containsKey(p)) {
				if (!e.getMessage().startsWith("/tell") || !e.getMessage().startsWith("/msg")
						|| !e.getMessage().startsWith("/helpop") || !e.getMessage().startsWith("/r")
						|| !e.getMessage().startsWith("/report") || !e.getMessage().startsWith("/msg")) {
					e.setCancelled(true);
					p.sendMessage(StringUtils.avisovermelho
							+ "§cVoce nao pode usar esse comando em uma luta! Acabe a luta primeiro!");
				}
			} else if (!e.getMessage().startsWith("/tell") && !e.getMessage().startsWith("/msg")
					&& !e.getMessage().startsWith("/helpop") && !e.getMessage().startsWith("/r")
					&& !e.getMessage().startsWith("/report") && !e.getMessage().startsWith("/1v1")
					&& !e.getMessage().startsWith("/msg")) {
				e.setCancelled(true);
				p.sendMessage(StringUtils.avisovermelho
						+ "§cVoce nao pode usar esse comando na arena 1v1! Use /1v1 para sair da arena!");
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public void acabarLuta(final Player p, final Player d) {
		if (naArena.contains(p)) {
			p.setHealth(20);
			p.setVelocity(new Vector(0, 2, 0));
			final double v = ((Damageable) d).getHealth() / 2.0;
			double decimal = v - (int) v;
			if (decimal < 0.25) {
				decimal = 0.0;
			} else if (decimal > 0.75) {
				decimal = 1.0;
			} else {
				decimal = 0.5;
			}
			final double vida = (int) v + decimal;
			int sopas = 0;
			for (final ItemStack itens : d.getInventory().all(Material.MUSHROOM_SOUP).values()) {
				sopas += itens.getAmount();
			}
			d.setNoDamageTicks(100);
			p.setNoDamageTicks(100);
			p.sendMessage(StringUtils.avisovermelho + "§7Voce perdeu a luta contra §3" + d.getName() + " §7que tinha §b"
					+ vida + " ❤ §7e §6" + sopas + " §7sopas");
			d.sendMessage(StringUtils.avisoverde + "§7Voce ganhou a luta contra §3" + p.getName() + " §7com §b" + vida
					+ " ❤ §7e §6" + sopas + " §6sopas");
			d.setHealth(((Damageable) p).getMaxHealth());
			p.getInventory().clear();
			BarAPI.removeBar(p);
			BarAPI.removeBar(d);
			p.getInventory().setArmorContents((ItemStack[]) null);
			this.placar.put(d, this.placar.get(d) + 1);
			final String placarP = ChatColor.translateAlternateColorCodes('§',
					"§f" + p.getName() + "§6: §e§l" + this.placar.get(p) + "§e/" + this.ft.get(p) + " §b- §f"
							+ d.getName() + "§6 §e§l" + this.placar.get(d) + "§e/" + this.ft.get(d));
			final String placarD = ChatColor.translateAlternateColorCodes('§',
					"§f" + d.getName() + "§6: §e§l" + this.placar.get(d) + "§e/" + this.ft.get(d) + " §b- §f"
							+ p.getName() + "§6 §e§l" + this.placar.get(p) + "§e/" + this.ft.get(p));
			BarAPI.setMessage(p, placarP, 5);
			BarAPI.setMessage(d, placarD, 5);
			p.setHealth(20);
			d.setHealth(20);
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (!Admin.emadmin.contains(pl)) {
					p.hidePlayer(pl);
					d.hidePlayer(pl);
					p.showPlayer(d);
					d.showPlayer(p);
				}
			}
			Firework f = (Firework) p.getWorld().spawn(p.getLocation(), Firework.class);

			FireworkMeta fm = f.getFireworkMeta();
			fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.STAR)
					.withColor(Color.YELLOW).withFade(Color.FUCHSIA).build());
			fm.setPower(2);
			f.setFireworkMeta(fm);
			final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { f.getEntityId() });
			for (Player pl2 : Bukkit.getOnlinePlayers()) {
				if (pl2 != p && pl2 != d) {
					((CraftPlayer) pl2).getHandle().playerConnection.sendPacket((Packet) packet);
				}
			}
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) UmVsUm.main, (Runnable) new Runnable() {
				@Override
				public void run() {
					p.setAllowFlight(true);
					p.setFlying(true);
				}
			}, 20L);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) UmVsUm.main, (Runnable) new Runnable() {
				@Override
				public void run() {
					if (UmVsUm.this.placar.get(d) == UmVsUm.this.ft.get(d)
							|| UmVsUm.this.placar.get(d) == UmVsUm.this.ft.get(p)) {
						naLuta.remove(p);
						naArena.remove(p);
						naLuta.remove(d);
						for (Player op : Bukkit.getOnlinePlayers()){
							if (!Admin.emadmin.contains(op)) {
								p.showPlayer(op);
								d.showPlayer(op);
							}
						}
						clear(p);
						clear(d);
						p.updateInventory();
						d.updateInventory();
						BarAPI.removeBar(p);
						BarAPI.removeBar(d);
						Random.remove(d);
						Random.remove(p);
						cmd1v1.v1(p);
						cmd1v1.v1(d);
						p.setFlying(false);
						p.setAllowFlight(false);
						p.setGameMode(GameMode.SURVIVAL);
						UmVsUm.this.placar.remove(p);
						UmVsUm.this.placar.remove(d);
						KitManager.getInstance().zerarKit(p);
					} else {
						clear(p);
						clear(d);
						p.setFlying(false);
						p.setAllowFlight(false);
						p.setGameMode(GameMode.SURVIVAL);
						d.setFlying(false);
						d.setAllowFlight(false);
						d.setGameMode(GameMode.SURVIVAL);
						final ItemStack espada = new ItemStack(
								(Material) UmVsUm.this.lutaEspada.get(String.valueOf(p.getName()) + d.getName()));
						if (UmVsUm.this.lutaEspadaE.get(String.valueOf(p.getName()) + d.getName())) {
							espada.addEnchantment(Enchantment.DAMAGE_ALL,
									(int) UmVsUm.this.lutaEspadaL.get(String.valueOf(p.getName()) + d.getName()));
						}
						p.getInventory().setItem(0, espada);
						d.getInventory().setItem(0, espada);
						final ItemStack capacete = new ItemStack(Material.IRON_HELMET);
						final ItemStack peitoral = new ItemStack(Material.IRON_CHESTPLATE);
						final ItemStack calca = new ItemStack(Material.IRON_LEGGINGS);
						final ItemStack bota = new ItemStack(Material.IRON_BOOTS);
						final ItemStack m = new ItemStack(
								(Material) UmVsUm.this.lutaArmadura.get(String.valueOf(p.getName()) + d.getName()));
						if (m.getType() == Material.IRON_CHESTPLATE) {
							if (!UmVsUm.this.lutaArmaduraE.get(String.valueOf(p.getName()) + d.getName())) {
								p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
								p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
								p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
								p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
								d.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
								d.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
								d.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
								d.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
							} else {
								capacete.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								peitoral.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								p.getInventory().setHelmet(capacete);
								p.getInventory().setChestplate(peitoral);
								p.getInventory().setLeggings(calca);
								p.getInventory().setBoots(bota);
								d.getInventory().setHelmet(capacete);
								d.getInventory().setChestplate(peitoral);
								d.getInventory().setLeggings(calca);
								d.getInventory().setBoots(bota);
							}
						} else if (m.getType() == Material.CHAINMAIL_CHESTPLATE) {
							if (!UmVsUm.this.lutaArmaduraE.get(String.valueOf(p.getName()) + d.getName())) {
								p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
								p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
								p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
								p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
								d.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
								d.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
								d.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
								d.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
							} else {
								capacete.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								peitoral.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								p.getInventory().setHelmet(capacete);
								p.getInventory().setChestplate(peitoral);
								p.getInventory().setLeggings(calca);
								p.getInventory().setBoots(bota);
								d.getInventory().setHelmet(capacete);
								d.getInventory().setChestplate(peitoral);
								d.getInventory().setLeggings(calca);
								d.getInventory().setBoots(bota);
							}
						} else if (m.getType() == Material.GOLD_CHESTPLATE) {
							if (!UmVsUm.this.lutaArmaduraE.get(String.valueOf(p.getName()) + d.getName())) {
								p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
								p.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
								p.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
								p.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
								d.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
								d.getInventory().setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
								d.getInventory().setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
								d.getInventory().setBoots(new ItemStack(Material.GOLD_BOOTS));
							} else {
								capacete.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								peitoral.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								p.getInventory().setHelmet(capacete);
								p.getInventory().setChestplate(peitoral);
								p.getInventory().setLeggings(calca);
								p.getInventory().setBoots(bota);
								d.getInventory().setHelmet(capacete);
								d.getInventory().setChestplate(peitoral);
								d.getInventory().setLeggings(calca);
								d.getInventory().setBoots(bota);
							}
						} else if (m.getType() == Material.LEATHER_CHESTPLATE) {
							if (!UmVsUm.this.lutaArmaduraE.get(String.valueOf(p.getName()) + d.getName())) {
								p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
								p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
								p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
								p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
								d.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
								d.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
								d.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
								d.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
							} else {
								capacete.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								peitoral.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								p.getInventory().setHelmet(capacete);
								p.getInventory().setChestplate(peitoral);
								p.getInventory().setLeggings(calca);
								p.getInventory().setBoots(bota);
								d.getInventory().setHelmet(capacete);
								d.getInventory().setChestplate(peitoral);
								d.getInventory().setLeggings(calca);
								d.getInventory().setBoots(bota);
							}
						} else if (m.getType() == Material.DIAMOND_CHESTPLATE) {
							if (!UmVsUm.this.lutaArmaduraE.get(String.valueOf(p.getName()) + d.getName())) {
								p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
								p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
								p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
								p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
								d.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
								d.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
								d.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
								d.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
							} else {
								capacete.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								peitoral.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,
										(int) UmVsUm.this.lutaArmaduraL.get(String.valueOf(p.getName()) + d.getName()));
								p.getInventory().setHelmet(capacete);
								p.getInventory().setChestplate(peitoral);
								p.getInventory().setLeggings(calca);
								p.getInventory().setBoots(bota);
								d.getInventory().setHelmet(capacete);
								d.getInventory().setChestplate(peitoral);
								d.getInventory().setLeggings(calca);
								d.getInventory().setBoots(bota);
							}
						}
						final Potion po = new Potion(PotionType.INSTANT_HEAL, 2);
						po.setSplash(true);
						final ItemStack pocao = po.toItemStack(1);
						if (!UmVsUm.this.lutaRefil.get(String.valueOf(p.getName()) + d.getName())) {
							if (UmVsUm.this.lutaCura.get(String.valueOf(p.getName()) + d.getName()) == 1) {
								FillHotBar(p, new ItemStack(Material.MUSHROOM_SOUP));
								FillHotBar(d, new ItemStack(Material.MUSHROOM_SOUP));
							} else {
								FillHotBar(p, pocao);
								FillHotBar(d, pocao);
							}
						} else if (UmVsUm.this.lutaCura.get(String.valueOf(p.getName()) + d.getName()) == 1) {
							FillInv(p, new ItemStack(Material.MUSHROOM_SOUP));
							FillInv(d, new ItemStack(Material.MUSHROOM_SOUP));
						} else {
							FillInv(p, pocao);
							FillInv(d, pocao);
						}
						if (UmVsUm.this.lutaPocao.get(String.valueOf(p.getName()) + d.getName()) == 2) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
							d.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
						} else if (UmVsUm.this.lutaPocao.get(String.valueOf(p.getName()) + d.getName()) == 3) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
							d.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
						} else if (UmVsUm.this.lutaPocao.get(String.valueOf(p.getName()) + d.getName()) == 4) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
							d.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
							d.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
						}

						p.teleport(Uteis.getLocation(Main.getMe().getConfig().getString("1V1.Pos1")));

						d.teleport(Uteis.getLocation(Main.getMe().getConfig().getString("1V1.Pos2")));
					}
				}
			}, 100L);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			final Player p = (Player) e.getEntity();
			final Player d = (Player) e.getDamager();
			if (naArena.contains(p) && naArena.contains(d) && (naLuta.get(d) != p || naLuta.get(p) != d)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		for (final Player pl : naLuta.keySet()) {
			pl.hidePlayer(p);
		}
	}

	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			final Player p = (Player) e.getWhoClicked();
			if (naArena.contains(p) && p.getGameMode() == GameMode.CREATIVE) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(final PlayerQuitEvent e) {
		final Player p = e.getPlayer();
		if (naLuta.containsKey(p)) {
			Player d = UmVsUm.main.getServer().getPlayer(naLuta.get(p).getName());
			if (naArena.contains(p) && naLuta.containsKey(p)) {
				d = UmVsUm.main.getServer().getPlayer(naLuta.get(p).getName());
				naLuta.remove(p);
				naArena.remove(p);
				naLuta.remove(d);
				Random.remove(d);
				Random.remove(p);
				for (Player pl : Bukkit.getOnlinePlayers()) {
					if (Bukkit.getServer().getOnlinePlayers().size() > 2) {
						p.showPlayer(pl);
						d.showPlayer(pl);
					}
				}
				cmd1v1.v1(d);
				p.setFlying(false);
				p.setAllowFlight(false);
				p.setGameMode(GameMode.SURVIVAL);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			final Player p = e.getEntity();
			Player d = p.getKiller();
			if (naArena.contains(p) && naLuta.containsKey(p)) {
				d = UmVsUm.main.getServer().getPlayer(naLuta.get(p).getName());
				this.acabarLuta(p, d);
			}
		}
	}

	@EventHandler
	public void onBlockBreak(final BlockBreakEvent e) {
		final Player p = e.getPlayer();
		if (naArena.contains(p)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent e) {
		final Player p = e.getPlayer();
		if (naArena.contains(p)) {
			e.setCancelled(true);
		}
	}

	public static ArrayList<Player> Random = new ArrayList<Player>();

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @EventHandler public void clickbot(final PlayerInteractEvent e){ if
	 * (((e.getAction().equals(Action.RIGHT_CLICK_AIR)) ||
	 * (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) &&
	 * (e.getItem().getType() == Material.getMaterial(351) &&
	 * e.getItem().getDurability() == 8)) { if
	 * (naArena.contains(e.getPlayer())){ e.setCancelled(true); final Player p1
	 * = e.getPlayer(); if (!Random.contains(p1)) { Random.add(p1);
	 * e.getPlayer().getInventory().setItem(8,
	 * Uteis.setItemComData(Material.getMaterial(351), (byte)10, 1,
	 * "§7Procurando partida §6§l ...", null)); if (Random.size() == 2) {
	 * p1.sendMessage("§aPartida Encontrada!");
	 * invAceitarN((Player)Random.get(0), (Player)Random.get(1));
	 * Random.clear(); }
	 * Bukkit.getServer().getScheduler().runTaskLater(Main.getMe(), new
	 * Runnable() { public void run() { if (Random.contains(p1)) {
	 * p1.sendMessage("§cNenhuma partida foi encontrada!");
	 * e.getPlayer().getInventory().setItem(8,
	 * Uteis.setItemComData(Material.getMaterial(351), (byte)8, 1,
	 * "§7Partida rápida", null)); Random.remove(p1); } } }, 200L); } } } }
	 */

	public enum MatchType {
		RANKED, NORMAL;
	}

	public void quickMatchSearch(final Player p, MatchType type) {
		if (type == MatchType.NORMAL) {
			if (!Random.contains(p)) {
				Random.add(p);
				p.sendMessage(StringUtils.avisoverde + "§a Voce entrou na fila do 1v1 Rapido");

				p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.INK_SACK, 1, (byte) 8) });

				ItemStack BuscarPartida = new ItemStack(Material.INK_SACK, 1, (short) 10);
				ItemMeta BuscarPartidaMeta = BuscarPartida.getItemMeta();
				BuscarPartidaMeta.setDisplayName(StringUtils.avisoverde + "§a§lBuscando partida rapida...");
				BuscarPartida.setItemMeta(BuscarPartidaMeta);

				p.getInventory().setItem(8, BuscarPartida);
				if (Random.size() == 2) {
					p.sendMessage(StringUtils.avisoverde + "§aUma partida foi encontrada");
					quickMatch((Player) Random.get(0), (Player) Random.get(1));
					Random.clear();
				}
				Bukkit.getServer().getScheduler().runTaskLater(Main.getMe(), new Runnable() {
					public void run() {
						if (Random.contains(p)) {
							p.getInventory()
									.removeItem(new ItemStack[] { new ItemStack(Material.INK_SACK, 1, (byte) 10) });
							Random.remove(p);
							p.sendMessage(StringUtils.avisovermelho + "§cNenhuma partida foi encontrada! :(");
							ItemStack PartidaRapida = new ItemStack(Material.INK_SACK, 1, (short) 8);
							ItemMeta PartidaRapidaMeta = PartidaRapida.getItemMeta();
							PartidaRapidaMeta.setDisplayName(StringUtils.avisoverde + "§aPartida Rapida");
							PartidaRapida.setItemMeta(PartidaRapidaMeta);

							p.getInventory().setItem(8, PartidaRapida);
						}
					}
				}, 200L);
			}
		} else {
			if (type == MatchType.RANKED) {
				if (!Random.contains(p)) {
					Random.add(p);
					p.sendMessage(StringUtils.avisoverde + "§a Voce entrou na fila do 1v1 rapido RANKEADO");

					p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.INK_SACK, 1, (byte) 8) });

					ItemStack BuscarPartida = new ItemStack(Material.INK_SACK, 1, (short) 10);
					ItemMeta BuscarPartidaMeta = BuscarPartida.getItemMeta();
					BuscarPartidaMeta
							.setDisplayName(StringUtils.avisoverde + "§a§lBuscando partida rapida rankeada ...");
					BuscarPartida.setItemMeta(BuscarPartidaMeta);

					p.getInventory().setItem(8, BuscarPartida);
					if (Random.size() == 2 && StatsManager.getRank((Player) Random.get(0))
							.equalsIgnoreCase(StatsManager.getRank((Player) Random.get(1)))) {
						p.sendMessage(StringUtils.avisoverde + "§aUma partida rankeada foi encontrada");
						quickMatch((Player) Random.get(0), (Player) Random.get(1));
						Random.clear();
					}
					Bukkit.getServer().getScheduler().runTaskLater(Main.getMe(), new Runnable() {
						public void run() {
							if (Random.contains(p)) {
								p.getInventory()
										.removeItem(new ItemStack[] { new ItemStack(Material.INK_SACK, 1, (byte) 10) });
								Random.remove(p);
								p.sendMessage(StringUtils.avisoverde + "§cNenhuma partida rankeada foi encontrada! :(");
								ItemStack PartidaRapida = new ItemStack(Material.INK_SACK, 1, (short) 8);
								ItemMeta PartidaRapidaMeta = PartidaRapida.getItemMeta();
								PartidaRapidaMeta.setDisplayName(StringUtils.avisoverde + "§aPartida Rapida");
								PartidaRapida.setItemMeta(PartidaRapidaMeta);

								p.getInventory().setItem(8, PartidaRapida);
							}
						}
					}, 200L);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClicK(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if ((e.getInventory().getTitle().equalsIgnoreCase("§a§lEscolha o modo de partida!"))
				&& (e.getCurrentItem() != null) && (e.getCurrentItem().getTypeId() != 0)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getType() == Material.GOLDEN_APPLE && e.getCurrentItem().getDurability() == 0) {
				e.setCancelled(true);
				p.closeInventory();
				quickMatchSearch(p, MatchType.NORMAL);
			}
			if (e.getCurrentItem().getType() == Material.GOLDEN_APPLE && e.getCurrentItem().getDurability() == 1) {
				e.setCancelled(true);
				p.closeInventory();
				quickMatchSearch(p, MatchType.RANKED);
			}
		}
	}

	@EventHandler
	public void PartidaRapida(PlayerInteractEvent evento) {
		final Player p = evento.getPlayer();
		if ((evento.getPlayer().getItemInHand().getType() == Material.INK_SACK)
				&& evento.getPlayer().getItemInHand().getDurability() == 8 && (naArena.contains(p))) {
			if ((evento.getAction() == Action.RIGHT_CLICK_BLOCK) || (evento.getAction() == Action.RIGHT_CLICK_AIR)) {
				evento.setCancelled(true);
				Inventory inv = Bukkit.createInventory(null, 27, "§a§lEscolha o modo de partida!");
				inv.setItem(4,
						Uteis.createHead(1, "§a§lAjuda",
								Arrays.asList("§eAqui, você escolhe se será", "§euma partida rankeada, ou",
										"§ese você deseja jogar com players", "§ede rank aleatório!", "",
										"§bLembrando, a 1v1 RANKED", "§bescolhará um adversário",
										"§bcom um rank igual ao seu!"),
								p.getName()));
				inv.setItem(16, Uteis.setItemComData(Material.GOLDEN_APPLE, (byte) 1, 1, "§ePartida §bRANKEADA", null));
				inv.setItem(10, Uteis.setItem(Material.GOLDEN_APPLE, 1, "§ePartida §bNORMAL", null));
				ItemStack vidro = new ItemStack(Material.THIN_GLASS);
				ItemMeta metav = vidro.getItemMeta();
				metav.setDisplayName(" ");
				vidro.setItemMeta(metav);
				for (int x = 0; x < inv.getSize(); x++) {
					while (inv.getItem(x) == null) {
						inv.setItem(x, vidro);
					}
					p.openInventory(inv);
				}
			}
		}
	}

	@EventHandler
	public void onInteractEntity(final PlayerInteractEntityEvent e) {
		final Player p = e.getPlayer();
		if (e.getRightClicked() instanceof Player) {
			System.out.println("PORRA");
			final Player d = (Player) e.getRightClicked();
			System.out.println(naArena.contains(p));
			System.out.println(naArena.contains(d));
			if (naArena.contains(p) && naArena.contains(d)) {
				final ItemStack blaze = item(Material.BLAZE_ROD, 0, "§e§l1v1 §r§7- §a1v1 Stick", false, "");
				if (p.getItemInHand().isSimilar(blaze) && !naLuta.containsKey(d)) {
					if (this.Pedido.get(d) == p) {
						if (this.Normal.get(d) == p) {
							this.invAceitarN(p, d);
						}
						if (this.Custom.get(d) == p) {
							this.invAceitarC(p, d);
						} else if (this.Buffed.get(d) == p) {
							this.invAceitarB(p, d);
						} else if (this.BuffedSpeed.get(d) == p) {
							this.invAceitarS(p, d);
						}
					} else if (this.Pedido.get(p) != d) {
						this.InvNormal(p, d);
					} else {
						p.sendMessage(StringUtils.avisovermelho + "§cVoce ja desafiou esse player!");
					}
				}
			}
		}
	}
}
