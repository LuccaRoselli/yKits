package com.luccadev.br.abilities;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.inventivetalent.particle.ParticleEffect;

import com.luccadev.br.manager.KitManager;

public class Damager
  implements Listener
{
  @EventHandler
  public void onDano(EntityDamageByEntityEvent e)
  {
    if ((!(e.getEntity() instanceof Player)) || (!(e.getDamager() instanceof Player))) {
      return;
    }
    Player t = (Player)e.getDamager();
    Player p = (Player)e.getEntity();
    double dano = e.getDamage();
    Random rand = new Random();
    if ((KitManager.getInstance().getUsingKitName(t).equalsIgnoreCase("Damager")) && 
      (rand.nextInt(90) + 10 < 20))
    {
      e.setDamage(dano * 2.0D);
      ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 1.0F, 0.0F, 0.0F, 15);
      ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 2.0F, 0.0F, 0.0F, 15);
      ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 15);
      
      ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 1.0F, 1.0F, -1.0F, 0.0F, 15);
      ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 2.0F, 2.0F, -2.0F, 0.0F, 15);
      ParticleEffect.CLOUD.send(Bukkit.getOnlinePlayers(), p.getLocation(), 3.0F, 3.0F, -3.0F, 0.0F, 15);
      
      ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 1.0F, 0.0F, 0.0F, 15);
      ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 2.0F, 0.0F, 0.0F, 15);
      ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 0.0F, 3.0F, 0.0F, 0.0F, 15);
      
      ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 1.0F, 1.0F, -1.0F, 0.0F, 15);
      ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 2.0F, 2.0F, -2.0F, 0.0F, 15);
      ParticleEffect.FLAME.send(Bukkit.getOnlinePlayers(), p.getLocation(), 3.0F, 3.0F, -3.0F, 0.0F, 15);
    }
  }
}
