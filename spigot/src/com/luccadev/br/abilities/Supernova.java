package com.luccadev.br.abilities;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Supernova implements Listener
{
    private ArrayList<NovaDirection> directions;
    private HashMap<Arrow, Vector> arrows;
    
    public void SupernovaAdd() {
        this.directions = new ArrayList<NovaDirection>();
        ArrayList<Double> pitchs = new ArrayList<Double>();
        pitchs.add(0.0);
        pitchs.add(22.5);
        pitchs.add(45.0);
        pitchs.add(67.5);
        pitchs.add(90.0);
        pitchs.add(112.5);
        pitchs.add(135.0);
        pitchs.add(157.5);
        pitchs.add(180.0);
        pitchs.add(202.5);
        pitchs.add(225.0);
        pitchs.add(247.5);
        pitchs.add(270.0);
        pitchs.add(292.5);
        pitchs.add(315.0);
        pitchs.add(337.5);
        for (final double i : pitchs) {
            this.directions.add(new NovaDirection(i, 67.5));
            this.directions.add(new NovaDirection(i, 45.0));
            this.directions.add(new NovaDirection(i, 22.5));
            this.directions.add(new NovaDirection(i, 0.0));
            this.directions.add(new NovaDirection(i, -22.5));
            this.directions.add(new NovaDirection(i, -45.0));
            this.directions.add(new NovaDirection(i, -67.5));
        }
        this.directions.add(new NovaDirection(90.0, 0.0));
        this.directions.add(new NovaDirection(-90.0, 0.0));
        this.directions.add(new NovaDirection(0.0, 90.0));
        this.directions.add(new NovaDirection(0.0, -90.0));
        pitchs.clear();
        pitchs = null;
        this.arrows = new HashMap<Arrow, Vector>();
        new BukkitRunnable() {
            public void run() {
                final Iterator<Map.Entry<Arrow, Vector>> entrys = Supernova.this.arrows.entrySet().iterator();
                while (entrys.hasNext()) {
                	try {
                		 final Map.Entry<Arrow, Vector> entry = entrys.next();
                         final Arrow arrow = entry.getKey();
                         final Vector vec = entry.getValue();
                         if (!arrow.isDead()) {
                             arrow.setVelocity(vec.normalize().multiply(vec.lengthSquared() / 4.0));
                             if (!arrow.isOnGround() && arrow.getTicksLived() < 100) {
                                 continue;
                             }
                             arrow.remove();
                         }
                         else {
                             entrys.remove();
                         }
					} catch (ConcurrentModificationException e) {
					}
                }
            }
        }.runTaskTimerAsynchronously(Main.getMe(), 1L, 1L);
    }
    
	@EventHandler
    public void onPlayerInteractListener(final PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR && KitManager.getInstance().getUsingKitName(e.getPlayer()).equalsIgnoreCase("Supernova")) {
            if (!Uteis.hasCooldown(e.getPlayer())) {
            	SupernovaAdd();
                final Location loc = e.getPlayer().getLocation();
                for (final NovaDirection d : this.directions) {
                    final Arrow arrow = (Arrow)loc.getWorld().spawn(loc.clone().add(0.0, 1.0, 0.0), Arrow.class);
                    final double pitch = (d.getPitch() + 90.0) * 3.141592653589793 / 180.0;
                    final double yaw = (d.getYaw() + 90.0) * 3.141592653589793 / 180.0;
                    final double x = Math.sin(pitch) * Math.cos(yaw);
                    final double y = Math.sin(pitch) * Math.sin(yaw);
                    final double z = Math.cos(pitch);
                    final Vector vec = new Vector(x, z, y);
                    arrow.setShooter((LivingEntity)e.getPlayer());
                    arrow.setVelocity(vec.multiply(2));
                    arrow.setMetadata("Supernova", (MetadataValue)new FixedMetadataValue(Main.getMe(), (Object)e.getPlayer().getUniqueId()));
                    this.arrows.put(arrow, vec);
                }
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.SHOOT_ARROW, 0.5f, 1.0f);
                Uteis.addCooldown(e.getPlayer(), 20);
            }            
            else {
                Uteis.sendCooldownMessage(e.getPlayer());
            }
        }
    }
    
	@EventHandler
    public void onDamage(final EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata("Supernova") && e.getDamager() instanceof Arrow) {
            if (e.getEntity() instanceof Player) {
                final Player p = (Player)e.getEntity();
                final Arrow arrow = (Arrow)e.getDamager();
                try {
                    if (arrow.getShooter() instanceof Player) {
                        final Player s = (Player)arrow.getShooter();
                        if (s.getUniqueId() == p.getUniqueId()) {
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
                catch (Exception ex) {}
            }
            e.setDamage(9.0);
        }
    }
    
    public class NovaDirection
    {
        private double pitch;
        private double yaw;
        
        public NovaDirection(final double pitch, final double yaw) {
            this.pitch = pitch;
            this.yaw = yaw;
        }
        
        public double getPitch() {
            return this.pitch;
        }
        
        public double getYaw() {
            return this.yaw;
        }
    }

}
