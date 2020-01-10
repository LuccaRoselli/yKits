package com.luccadev.br.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

public class ItemBuilder
{
    private Material material;
    private int amount;
    private short durability;
    private boolean useMeta;
    private boolean glow;
    private String displayName;
    private HashMap<Enchantment, Integer> enchantments;
    private ArrayList<String> lore;
    private NBTTagCompound basicNBT;
    private NBTTagList enchNBT;
    
    public ItemBuilder() {
        this.material = Material.STONE;
        this.amount = 1;
        this.durability = 0;
        this.useMeta = false;
        this.glow = false;
        this.basicNBT = new NBTTagCompound();
        this.enchNBT = new NBTTagList();
        this.basicNBT.set("ench", (NBTBase)this.enchNBT);
    }
    
    public ItemBuilder type(final Material material) {
        this.material = material;
        return this;
    }
    
    public ItemBuilder amount(int amount) {
        if (amount > 64) {
            amount = 64;
        }
        this.amount = amount;
        return this;
    }
    
    public ItemBuilder durability(final int durability) {
        this.durability = (short)durability;
        return this;
    }
    
    public ItemBuilder name(final String text) {
        if (!this.useMeta) {
            this.useMeta = true;
        }
        this.displayName = text.replace("&", "§");
        return this;
    }
    
    public ItemBuilder enchantment(final Enchantment enchantment) {
        return this.enchantment(enchantment, 1);
    }
    
    public ItemBuilder enchantment(final Enchantment enchantment, final Integer level) {
        if (this.enchantments == null) {
            this.enchantments = new HashMap<Enchantment, Integer>();
        }
        this.enchantments.put(enchantment, level);
        return this;
    }
    
    public ItemBuilder lore(String text) {
        if (!this.useMeta) {
            this.useMeta = true;
        }
        if (this.lore == null) {
            this.lore = new ArrayList<String>();
        }
        final String[] split = text.split(" ");
        text = "";
        for (int i = 0; i < split.length; ++i) {
            if (ChatColor.stripColor(text).length() > 25 || ChatColor.stripColor(text).endsWith(".") || ChatColor.stripColor(text).endsWith("!")) {
                this.lore.add("§7" + text);
                if (text.endsWith(".") || text.endsWith("!")) {
                    this.lore.add("");
                }
                text = "";
            }
            String toAdd = split[i];
            if (toAdd.contains("\\n")) {
                toAdd = toAdd.substring(0, toAdd.indexOf("\\n"));
                split[i] = split[i].substring(toAdd.length() + 2);
                this.lore.add("§7" + text + ((text.length() == 0) ? "" : " ") + toAdd);
                text = "";
                --i;
            }
            else {
                text = String.valueOf(text) + ((text.length() == 0) ? "" : " ") + toAdd;
            }
        }
        this.lore.add("§7" + text);
        return this;
    }
    
    public ItemBuilder lore(final List<String> text) {
        if (!this.useMeta) {
            this.useMeta = true;
        }
        if (this.lore == null) {
            this.lore = new ArrayList<String>();
        }
        for (final String str : text) {
            this.lore.add(str.replace("&", "§"));
        }
        return this;
    }
    
    public ItemBuilder glow() {
        this.glow = true;
        return this;
    }
    
    public ItemStack build() {
        ItemStack stack = new ItemStack(this.material);
        stack.setAmount(this.amount);
        stack.setDurability(this.durability);
        if (this.enchantments != null && !this.enchantments.isEmpty()) {
            for (final Map.Entry<Enchantment, Integer> entry : this.enchantments.entrySet()) {
                stack.addEnchantment((Enchantment)entry.getKey(), (int)entry.getValue());
            }
        }
        if (this.useMeta) {
            final ItemMeta meta = stack.getItemMeta();
            if (this.displayName != null) {
                meta.setDisplayName(this.displayName.replace("&", "§"));
            }
            if (this.lore != null && !this.lore.isEmpty()) {
                meta.setLore(this.lore);
            }
            stack.setItemMeta(meta);
        }
        if (this.glow) {
            final net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
            if (nmsStack.hasTag()) {
                nmsStack.getTag().set("ench", (NBTBase)this.enchNBT);
            }
            else {
                nmsStack.setTag(this.basicNBT);
            }
            stack = (ItemStack)CraftItemStack.asCraftMirror(nmsStack);
        }
        this.material = Material.STONE;
        this.amount = 1;
        this.durability = 0;
        if (this.useMeta) {
            this.useMeta = false;
        }
        if (this.glow) {
            this.glow = false;
        }
        if (this.displayName != null) {
            this.displayName = null;
        }
        if (this.enchantments != null) {
            this.enchantments.clear();
            this.enchantments = null;
        }
        if (this.lore != null) {
            this.lore.clear();
            this.lore = null;
        }
        return stack;
    }
}
