package com.luccadev.br.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.Report;
import com.luccadev.br.constructors.Report.ReportStatus;
import com.luccadev.br.utils.ItemBuilder;
import com.luccadev.br.utils.UpdateEvent;

public class ReportManager
{
    private Main Main;
    private HashMap<Integer, Inventory> pages;
    private HashMap<String, Report> reports;
    private boolean ordering;
    private ItemBuilder builder;
    
    public ReportManager(final Main bc) {
        this.Main = bc;
        this.pages = new HashMap<Integer, Inventory>();
        this.reports = new HashMap<String, Report>();
        this.ordering = false;
        this.builder = new ItemBuilder();
        Bukkit.getPluginManager().registerEvents((Listener)new Listener() {
            @EventHandler
            public void onUpdate(final UpdateEvent e) {
                if (e.getType() == UpdateEvent.UpdateType.MINUTE) {
                    ReportManager.this.checkExpires();
                }
            }
            
            @EventHandler
            public void onInventoryClick(final InventoryClickEvent e) {
                if (e.getInventory() != null && e.getInventory().getType() == InventoryType.CHEST && e.getInventory().getTitle().startsWith("Reports - P\u00e1gina")) {
                    e.setCancelled(true);
                    if (e.getClickedInventory() == e.getInventory()) {
                        new BukkitRunnable() {
                           
							public void run() {
                                final Player p = (Player)e.getWhoClicked();
                                if (e.getSlot() >= 9 && e.getSlot() <= 44) {
                                    if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                                        final String name = e.getCurrentItem().getItemMeta().getDisplayName().split("§7")[1];
                                        final Report report = ReportManager.this.reports.get(name);
                                        if (report != null) {
                                            final Player t = (Bukkit.getPlayerExact(name) == null) ? Bukkit.getPlayer(report.getReported()) : Bukkit.getPlayerExact(name);
                                            if (t == null || report.getStatus() == ReportStatus.OFFLINE || report.getStatus() == ReportStatus.BANNED) {
                                                ReportManager.this.removeReports(name);
                                                e.getInventory().setItem(e.getSlot(), (ItemStack)null);
                                                p.sendMessage("§9§lREPORT §fReport §3§lremovido§f!");
                                                p.setItemOnCursor((ItemStack)null);
                                                for (Player staff : Bukkit.getOnlinePlayers()){
                                                	if (staff.hasPermission("kitpvp.comando.report")){
                                                        SCManager.send(staff);
                                                	}
                                                }
                                            }
                                            else if (report.getStatus() == ReportStatus.OPEN) {
                                                new BukkitRunnable() {
                                                    public void run() {
                                                        p.teleport((Entity)t);
                                                    }
                                                }.runTask((Plugin)ReportManager.this.Main);
                                                report.setStatus(ReportStatus.CHECKING);
                                                if (!report.getAdmins().contains(p.getName()))
                                                	report.getAdmins().add(p.getName());
                                                p.closeInventory();
                                                ReportManager.this.orderPages();
                                                p.sendMessage("§9§LREPORT §FTeleportado para §3§L" + t.getName() + "§f!");
                                            }
                                            else if (report.getStatus() == ReportStatus.CHECKING) {
                                                if (e.getAction() == InventoryAction.PICKUP_HALF) {
                                                    ReportManager.this.removeReports(name);
                                                    e.getInventory().setItem(e.getSlot(), (ItemStack)null);
                                                    p.sendMessage("§9§lREPORT §fReport §3§lremovido§f!");
                                                    p.setItemOnCursor((ItemStack)null);
                                                    for (Player staff : Bukkit.getOnlinePlayers()){
                                                    	if (staff.hasPermission("kitpvp.comando.report")){
                                                            SCManager.send(staff);
                                                    	}
                                                    }
               
                                                }
                                                else {
                                                    new BukkitRunnable() {
                                                        public void run() {
                                                            p.teleport((Entity)t);
                                                        }
                                                    }.runTask((Plugin)ReportManager.this.Main);
                                                    p.closeInventory();
                                                    p.sendMessage("§9§LREPORT §FTeleportado para §3§L" + t.getName() + "§f!");
                                                    if (!report.getAdmins().contains(p.getName()))
                                                    	report.getAdmins().add(p.getName());
                                                    ReportManager.this.orderPages();
                                                }
                                            }
                                        }
                                        else {
                                            p.sendMessage("§9§lREPORT §fJogador n\u00e3o §3§lencontrado§7!");
                                        }
                                    }
                                }
                                else if (e.getSlot() == 0) {
                                    if (e.getCurrentItem().getType() == Material.INK_SACK) {
                                        final int page = Integer.valueOf(e.getInventory().getTitle().split(" ")[3]);
                                        if (ReportManager.this.pages.containsKey(page - 1)) {
                                            p.openInventory((Inventory)ReportManager.this.pages.get(page - 1));
                                        }
                                    }
                                }
                                else if (e.getSlot() == 8 && e.getCurrentItem().getType() == Material.INK_SACK) {
                                    final int page = Integer.valueOf(e.getInventory().getTitle().split(" ")[3]);
                                    if (ReportManager.this.pages.containsKey(page + 1)) {
                                        p.openInventory((Inventory)ReportManager.this.pages.get(page + 1));
                                    }
                                }
                            }
                        }.runTask((Plugin)ReportManager.this.Main);
                    }
                }
            }
        }, (Plugin)this.Main);
    }
    
	public void report(final Player reporter, final Player reported, final String reason) {
        if (!this.reports.containsKey(reported.getName())) {
            this.reports.put(reported.getName(), new Report(reported.getUniqueId()));
        }
        final Report r = this.reports.get(reported.getName());
        if (!r.getReasons().contains(reason)) {
            r.getReasons().add(reason);
        }
        if (!r.getReporters().contains(reporter.getName())) {
            r.getReporters().add(reporter.getName());
        }
        r.updateExpire();
        this.orderPages();
        for (Player j : Bukkit.getOnlinePlayers()) {
            final Player staff = j;
            if (staff.hasPermission("kitpvp.comando.report")) {
                staff.sendMessage("§fNovo §9§LREPORT §frecebido!");
                staff.playSound(staff.getLocation(), Sound.ITEM_BREAK, 0.25f, 1.0f);
                SCManager.send(staff);
            }
        }
    }
    
    public void orderPages() {
        if (!this.ordering) {
            this.ordering = true;
            new BukkitRunnable() {
                public void run() {
                    int page = 1;
                    int current = 9;
                    int total = 0;
                    for (final Map.Entry<String, Report> entry : ReportManager.this.reports.entrySet()) {
                        final ItemStack blank = ReportManager.this.builder.type(Material.STAINED_GLASS_PANE).durability(15).name("§0").build();
                        if (!ReportManager.this.pages.containsKey(page)) {
                            final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, "Reports - P\u00e1gina " + page);
                            inv.setItem(1, blank);
                            inv.setItem(2, blank);
                            inv.setItem(3, blank);
                            inv.setItem(5, blank);
                            inv.setItem(6, blank);
                            inv.setItem(7, blank);
                            inv.setItem(45, blank);
                            inv.setItem(46, blank);
                            inv.setItem(47, blank);
                            inv.setItem(48, blank);
                            inv.setItem(49, blank);
                            inv.setItem(50, blank);
                            inv.setItem(51, blank);
                            inv.setItem(52, blank);
                            inv.setItem(53, blank);
                            ReportManager.this.pages.put(page, inv);
                        }
                        final Inventory inv = ReportManager.this.pages.get(page);
                        if (page > 1) {
                            inv.setItem(0, ReportManager.this.builder.type(Material.INK_SACK).durability(10).name("§7« §aP\u00e1gina Anteiror").build());
                        }
                        else {
                            inv.setItem(0, blank);
                        }
                        inv.setItem(4, ReportManager.this.builder.type(Material.SKULL_ITEM).amount(1).name("§9§lReports").lore("§3§l" + ReportManager.this.reports.size() + "§7 reports no total!").build());
                        if (Math.ceil(ReportManager.this.reports.size() / 36) + 1.0 > page) {
                            inv.setItem(8, ReportManager.this.builder.type(Material.INK_SACK).durability(10).name("§aPr\u00f3xima P\u00e1gina §f»").build());
                        }
                        else {
                            inv.setItem(8, blank);
                        }
                        String prefix = "";
                        String reason = "";
                        for (final String s : entry.getValue().getReasons()) {
                            if (!reason.isEmpty()) {
                                reason = "§b" + String.valueOf(reason) + ", §b";
                            }
                            reason = String.valueOf(reason) + s;
                        }
                        String reporters = "";
                        for (final String s2 : entry.getValue().getReporters()) {
                            if (!reporters.isEmpty()) {
                                reporters = String.valueOf(reporters) + ", ";
                            }
                            reporters = String.valueOf(reporters) + s2;
                        }
                        String admins = "";
                        for (final String s3 : entry.getValue().getAdmins()) {
                            if (!admins.isEmpty()) {
                            	admins = String.valueOf(admins) + ", ";
                            }
                            admins = String.valueOf(admins) + s3;
                        }
                        switch (entry.getValue().getStatus()) {
                            case BANNED: {
                                prefix = "§c§l»";
                                ReportManager.this.builder.type(Material.SKULL_ITEM);
                                ReportManager.this.builder.lore("§7Este jogador se encontra §c§lBANIDO §7clique para apagar este report.");
                                break;
                            }
                            case OFFLINE: {
                                prefix = "§9§l»";
                                ReportManager.this.builder.type(Material.SKULL_ITEM).durability(2);
                                ReportManager.this.builder.lore("§7Este jogador se encontra §9§lOFFLINE §7clique para apagar este report.");
                                break;
                            }
                            case CHECKING: {
                                prefix = "§e§l»";
                                ReportManager.this.builder.type(Material.SKULL_ITEM).durability(4);
                                ReportManager.this.builder.lore("§0\\n§7Raz\u00f5es: §b" + reason + "\\n§7Reportado por: §b" + reporters + "\\nHorário: §b" + entry.getValue().getHour() + "\\n\\nStaffs que já visualizaram o report: §a" + admins + "\\n§0\\n§7Clique esquerdo para §a§lverificar§7 este jogador.\\n§7Clique direito para §e§lapagar§7 este report.");
                                break;
                            }
                            case OPEN: {
                                prefix = "§a§l»";
                                ReportManager.this.builder.type(Material.SKULL_ITEM).durability(3);
                                ReportManager.this.builder.lore("§0\\n§7Raz\u00f5es: §b" + reason + "\\n§7Reportado por: §b" + reporters + "\\nHorário: §b" + entry.getValue().getHour() + "\\n§0\\n§7Clique para §a§lverificar§7 este jogador.");
                                break;
                            }
                        }
                        ReportManager.this.builder.amount(entry.getValue().getReporters().size());
                        ReportManager.this.builder.name(String.valueOf(prefix) + " §7" + entry.getKey());
                        inv.setItem(current, ReportManager.this.builder.build());
                        ++current;
                        ++total;
                        if (current > 44) {
                            current = 9;
                            ++page;
                        }
                        if (total == ReportManager.this.reports.size()) {
                            while (current <= 44) {
                                inv.setItem(current, (ItemStack)null);
                                ++current;
                            }
                        }
                    }
                    final Iterator<Map.Entry<Integer, Inventory>> pagei = ReportManager.this.pages.entrySet().iterator();
                    while (pagei.hasNext()) {
                        final Map.Entry<Integer, Inventory> entry2 = pagei.next();
                        if (page < entry2.getKey()) {
                            entry2.getValue().clear();
                            new BukkitRunnable() {
    
								public void run() {
                                    for (Player j : Bukkit.getOnlinePlayers()) {
                                        final Player p = j;
                                        if (p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getTitle().equalsIgnoreCase("Reports - P\u00e1gina " + entry2.getKey())) {
                                            p.closeInventory();
                                        }
                                    }
                                    pagei.remove();
                                }
                            }.runTaskAsynchronously((Plugin)ReportManager.this.Main);
                        }
                    }
                    ReportManager.changeOrdering(ReportManager.this, false);
                }
            }.runTaskAsynchronously((Plugin)this.Main);
        }
    }
    
    public void checkExpires() {
        new BukkitRunnable() {
           
			public void run() {
                final Iterator<Map.Entry<String, Report>> i = ReportManager.this.reports.entrySet().iterator();
                while (i.hasNext()) {
                    final Map.Entry<String, Report> entry = i.next();
                    try {
                        if (Bukkit.getPlayer(entry.getValue().getReported()) == null) {
                            entry.getValue().setStatus(ReportStatus.OFFLINE);
                        }
                        else if (entry.getValue().getStatus() == ReportStatus.OFFLINE) {
                            entry.getValue().setStatus(ReportStatus.OPEN);
                        }
                        else {
                            if (entry.getValue().getExpire() >= System.currentTimeMillis()) {
                                continue;
                            }
                            i.remove();
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ReportManager.this.orderPages();
                if (ReportManager.this.reports.size() > 0) {
                    for (Player j : Bukkit.getOnlinePlayers()) {
                        final Player staff = j;
                        if (staff.hasPermission("kitpvp.comando.report")) {
                            staff.playSound(staff.getLocation(), Sound.EXPLODE, 0.25f, 1.0f);
                            staff.sendMessage("§0§l");
                            staff.sendMessage("§9§lREPORT §fVoc\u00ea tem §3§l" + ReportManager.this.reports.size() + ((ReportManager.this.reports.size() == 1) ? " report" : " reports") + "§f no momento!");
                            staff.sendMessage("§0§l");
                        }
                    }
                }
            }
        }.runTaskAsynchronously((Plugin)this.Main);
    }
    
    public HashMap<Integer, Inventory> getPages() {
        return this.pages;
    }
    
    public HashMap<String, Report> getReports() {
        return this.reports;
    }
    
    public void open(final Player p) {
        if (this.reports.size() > 0 && this.pages.containsKey(1)) {
            p.openInventory((Inventory)this.pages.get(1));
        }
        else {
            p.sendMessage("§9§lREPORT §fNenhum report no momento!");
        }
    }
    
    public void removeReports(final String name) {
        this.reports.remove(name);
        this.orderPages();
    }
    
    static void changeOrdering(final ReportManager reportManager, final boolean ordering) {
        reportManager.ordering = ordering;
    }
}

