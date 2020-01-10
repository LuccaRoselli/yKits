package com.luccadev.br.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.Kit.Kits;
import com.luccadev.br.storage.PlayerMySQL;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

import net.md_5.bungee.api.ChatColor;

public class CrateManager{
	
	public static HashMap<Player, BukkitTask> runnable = new HashMap<Player, BukkitTask>();
	
	
    public static void scroll(final Inventory inventory, final int n, final ItemStack itemStack) {
        for (int i = 0; i < n - 1; ++i) {
            inventory.setItem(i, inventory.getItem(i + 1));
        }
        inventory.setItem(n - 1, itemStack);
    }
    
    public static ItemStack getRandomStack(){
		return (ItemStack) Kits.kititems.get(new Random().nextInt(Kits.kititems.size()));
    }
	
	public static void openInv(final Player p){
		if (!PlayerMySQL.hasCrate(p)){
			p.sendMessage(StringUtils.avisovermelho + "Você não possui caixas!");
			p.closeInventory();
			return;
		}
		final Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§eAbrindo caixa");
        p.openInventory(inv);
        inv.setItem(0, getRandomStack());
        inv.setItem(1, getRandomStack());
        inv.setItem(2, getRandomStack());
        inv.setItem(3, getRandomStack());
        inv.setItem(4, getRandomStack());
        runnable.put(p, new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
				i++;
     			p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                scroll(inv, 5, getRandomStack());
                if (i == 25){
                	runnable.get(p).cancel();
                	runnable.remove(p);
                	runnable.put(p, new BukkitRunnable() {
						int ii = 0;
						@Override
						public void run() {
							ii++;
			     			p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
			                scroll(inv, 5, getRandomStack());
			                if (ii == 14){
			                	runnable.get(p).cancel();
			                	runnable.remove(p);
			                	runnable.put(p, new BukkitRunnable() {
									int iii = 0;
									@Override
									public void run() {
										iii++;
						     			p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
						                scroll(inv, 5, getRandomStack());
						                if (iii == 8){
						                	runnable.get(p).cancel();
						                	runnable.remove(p);
						                 	runnable.put(p, new BukkitRunnable() {
						            			int i = 0;
						            			@Override
						            			public void run() {
						            				i++;
						            				final ItemStack a = inv.getItem(2);
						            				if (i % 2 == 0){
						            					inv.setItem(2, a);
						            					inv.setItem(0, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte) 5, 1,
						            							"§a§lWIN!", null));
						            					inv.setItem(2, a);
						            					inv.setItem(1, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte) 5, 1,
						            							"§a§lWIN!", null));
						            					inv.setItem(3, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte) 5, 1,
						            							"§a§lWIN!", null));
						            					inv.setItem(4, Uteis.setItemComData(Material.STAINED_GLASS_PANE, (byte) 5, 1,
						            							"§a§lWIN!", null));
						            				} else {
						            					inv.setItem(0, new ItemStack(Material.AIR, 1));
						            					inv.setItem(1, new ItemStack(Material.AIR, 1));
						            					inv.setItem(3, new ItemStack(Material.AIR, 1));
						            					inv.setItem(4, new ItemStack(Material.AIR, 1));
						            					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
						            				}
						            				if (i == 6){
									                	runnable.get(p).cancel();
									                	runnable.remove(p);      
									                	p.closeInventory();
									                	PlayerMySQL.setCaixas(p.getUniqueId(), StatsManager.caixas.get(p) - 1);
									                	StatsManager.caixas.put(p, StatsManager.caixas.get(p) - 1);
									                	if (p.hasPermission(StringUtils.permissaoprefix + ".kit." + ChatColor.stripColor(inv.getItem(2).getItemMeta().getDisplayName().split("§e")[1]).toLowerCase())){
									                		p.sendMessage(StringUtils.avisovermelho + "Você tirou um kit repetido! :/");
									                	} else {
									                		p.sendMessage(StringUtils.avisoverde + "Você abriu uma caixa.");
										                	p.sendMessage(StringUtils.avisoverde + "Seu premio foi: §eKit " + inv.getItem(2).getItemMeta().getDisplayName().split("§e")[1]);
										                	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + p.getName() + " add " + StringUtils.permissaoprefix + ".kit." + ChatColor.stripColor(inv.getItem(2).getItemMeta().getDisplayName().split("§e")[1]).toLowerCase());
									                	}
						            				}
						            			}
						            		}.runTaskTimer(Main.getMe(), 20L, 10L));
						                }
									}
								}.runTaskTimer(Main.getMe(), 1l, 8L));
			                }
						}
					}.runTaskTimer(Main.getMe(), 1L, 5L));
                }
			}
		}.runTaskTimer(Main.getMe(), 1L, 3L));
	}
	
	@SuppressWarnings("deprecation")
	public static void principalGui(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36, "§6Caixas");
		inv.setItem(3, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));
		inv.setItem(4, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));
		inv.setItem(5, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));

		inv.setItem(21, Uteis.setItem(Material.CHEST, StatsManager.caixas.get(p), "§aCaixas", Arrays.asList("§7Você possui §a" + StatsManager.caixas.get(p) + "§7 caixas!")));
		inv.setItem(23, Uteis.setItem(Material.GOLD_NUGGET, 1, "§aComprar caixas", Arrays.asList("§7Clique aqui para comprar caixas!")));
		p.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	public static void comprarCaixas(Player p) {
		Inventory inv = Bukkit.createInventory(null, 36, "§6Caixas ⇢ Loja");
		inv.setItem(3, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));
		inv.setItem(4, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));
		inv.setItem(5, Uteis.setItemComData(Material.getMaterial(351), (byte) 8, 1, StringUtils.getPrefix('b'), null));

		inv.setItem(20, Uteis.setItem(Material.CHEST, 1, "§a1 Caixa", Arrays.asList("§7Clique aqui para comprar §a1 §7caixa!", "", "§6§lPREÇO: §e1.500 Coins")));
		inv.setItem(22, Uteis.setItem(Material.CHEST, 5, "§a5 Caixas", Arrays.asList("§7Clique aqui para comprar §a5 §7caixas!", "", "§6§lPREÇO: §e7.500 Coins")));
		inv.setItem(24, Uteis.setItem(Material.CHEST, 10, "§a10 Caixas", Arrays.asList("§7Clique aqui para comprar §a10 §7caixas!", "", "§6§lPREÇO: §e10.000 Coins", "§a§l✔ §7Desconto de §a5.000 §7coins!")));
		p.openInventory(inv);
	}

}
