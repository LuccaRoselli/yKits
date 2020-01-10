package com.luccadev.br.abilities;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.luccadev.br.manager.KitManager;

import net.minecraft.server.v1_8_R3.EntityHuman;

public class Grappler implements Listener
{
    Map<Player, CopyOfFishingHook> hooks;
    
    public Grappler() {
        this.hooks = new HashMap<Player, CopyOfFishingHook>();
    }
    
    @EventHandler
    public void onSlot(final PlayerItemHeldEvent e) {
        if (this.hooks.containsKey(e.getPlayer())) {
            this.hooks.get(e.getPlayer()).remove();
            this.hooks.remove(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        if (this.hooks.containsKey(e.getPlayer()) && !e.getPlayer().getItemInHand().getType().equals(Material.LEASH)) {
            this.hooks.get(e.getPlayer()).remove();
            this.hooks.remove(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onLeash(final PlayerLeashEntityEvent e) {
        final Player player = e.getPlayer();
        if (!KitManager.getInstance().getUsingKitName(player).equalsIgnoreCase("Grappler")) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType().equals(Material.LEASH) && player.getItemInHand().getType() == Material.LEASH) {
            e.setCancelled(true);
            e.getPlayer().updateInventory();
            e.setCancelled(true);
            if (!this.hooks.containsKey(player)) {
                return;
            }
            if (!this.hooks.get(player).isHooked()) {
                return;
            }
            final double distance = this.hooks.get(player).getBukkitEntity().getLocation().distance(player.getLocation());
            final double x = (1.0 + 0.07 * distance) * (this.hooks.get(player).getBukkitEntity().getLocation().getX() - player.getLocation().getX()) / distance;
            final double y = (1.0 + 0.03 * distance) * (this.hooks.get(player).getBukkitEntity().getLocation().getY() - player.getLocation().getY()) / distance;
            final double z = (1.0 + 0.07 * distance) * (this.hooks.get(player).getBukkitEntity().getLocation().getZ() - player.getLocation().getZ()) / distance;
            final Vector velocity = player.getVelocity();
            velocity.setX(x);
            velocity.setY(y);
            velocity.setZ(z);
            player.setVelocity(velocity);
            player.getWorld().playSound(player.getLocation(), Sound.STEP_GRAVEL, 0.0f, 0.0f);
        }
    }
    
    @EventHandler
    public void onClick(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        if (!KitManager.getInstance().getUsingKitName(player).equalsIgnoreCase("Grappler")) {
            return;
        }
        if (e.getPlayer().getItemInHand().getType() == Material.LEASH && player.getItemInHand().getType() == Material.LEASH) {
            e.setCancelled(true);
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (this.hooks.containsKey(player)) {
                    this.hooks.get(player).remove();
                }
                final CopyOfFishingHook copyOfFishingHook = new CopyOfFishingHook(player.getWorld(), (EntityHuman)((CraftPlayer)player).getHandle());
                copyOfFishingHook.spawn(player.getEyeLocation().add(player.getLocation().getDirection().getX(), player.getLocation().getDirection().getY(), player.getLocation().getDirection().getZ()));
                copyOfFishingHook.move(player.getLocation().getDirection().getX() * 5.0, player.getLocation().getDirection().getY() * 5.0, player.getLocation().getDirection().getZ() * 5.0);
                this.hooks.put(player, copyOfFishingHook);
            }
            else {
                if (!this.hooks.containsKey(player)) {
                    return;
                }
                if (!this.hooks.get(player).isHooked()) {
                    return;
                }
                final double distance = this.hooks.get(player).getBukkitEntity().getLocation().distance(player.getLocation());
                final double x = (2.0 + 0.07 * distance) * (this.hooks.get(player).getBukkitEntity().getLocation().getX() - player.getLocation().getX()) / distance;
                final double y = (2.0 + 0.03 * distance) * (this.hooks.get(player).getBukkitEntity().getLocation().getY() - player.getLocation().getY()) / distance;
                final double z = (2.0 + 0.07 * distance) * (this.hooks.get(player).getBukkitEntity().getLocation().getZ() - player.getLocation().getZ()) / distance;
                final Vector velocity = player.getVelocity();
                velocity.setX(x);
                velocity.setY(y);
                velocity.setZ(z);
                player.setVelocity(velocity);
                player.getWorld().playSound(player.getLocation(), Sound.STEP_GRAVEL, 10.0f, 10.0f);
            }
        }
    }
}
