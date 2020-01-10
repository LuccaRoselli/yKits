package com.luccadev.br.abilities;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.luccadev.br.Main;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Scorpion implements Listener {

	@EventHandler
	public void interactEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))
				&& (KitManager.getInstance().getUsingKitName(p).equalsIgnoreCase("Scorpion"))
				&& (p.getItemInHand().getType() == Material.TRIPWIRE_HOOK)) {
			if (Uteis.hasCooldown(p)) {
				Uteis.sendCooldownMessage(p);
				return;
			}
			Uteis.addCooldown(p, 20);
			Location location = p.getEyeLocation();
			BlockIterator blocksToAdd = new BlockIterator(location, 0.0D, 40);
			while (blocksToAdd.hasNext()) {
				Location blockToAdd = blocksToAdd.next().getLocation();
				p.getWorld().playEffect(blockToAdd, Effect.STEP_SOUND, Material.LEAVES, 20);
				p.playSound(blockToAdd, Sound.IRONGOLEM_THROW, 3.0F, 3.0F);
			}
			Snowball h = (Snowball) p.launchProjectile(Snowball.class);
			Vector velo1 = p.getLocation().getDirection().normalize().multiply(10);
			h.setVelocity(velo1);
			h.setMetadata("scorpion", new FixedMetadataValue(Main.getMe(), Boolean.valueOf(true)));
		}
	}

	
	@EventHandler
	public void dano(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player)) && ((e.getDamager() instanceof Snowball))) {
			Snowball s = (Snowball) e.getDamager();
			Player p = (Player) e.getEntity();
			Player h = (Player) s.getShooter();
			if (s.hasMetadata("scorpion")) {
				p.teleport(h.getLocation());
				p.sendMessage(StringUtils.avisovermelho + "Um scorpion te puxou!");
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
			}
		}
	}

}
