package com.luccadev.br.abilities;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

public class Ryu implements Listener {

	KitManager kit = KitManager.getInstance();

	@EventHandler
	public void QuandoPlayerClicar(PlayerInteractEvent event) {
		final Player p = event.getPlayer();
		if (p.getItemInHand().getType() == Material.BEACON) {
			if (kit.getUsingKitName(p).equalsIgnoreCase("Ryu")) {
				if (Uteis.hasCooldown(p)) {
					Uteis.sendCooldownMessage(p);
					return;
				}
				Uteis.addCooldown(p, 10);
				Location location = p.getEyeLocation();
				BlockIterator blocksToAdd = new BlockIterator(location, 0.0, 40);
				Location blockToAdd;
				Snowball boladenve = (Snowball) p.launchProjectile(Snowball.class);
				Vector velo1 = p.getLocation().getDirection().normalize().multiply(10);
				boladenve.setVelocity(velo1);
				boladenve.setMetadata("Ryu", new FixedMetadataValue(Main.getMe(), true));
				while (blocksToAdd.hasNext()) {
					blockToAdd = blocksToAdd.next().getLocation();
					Effect a = Effect.STEP_SOUND;
					p.getWorld().playEffect(blockToAdd, a, 174);
					p.getWorld().playSound(blockToAdd, Sound.IRONGOLEM_THROW, 3f, 3f);
				}

			}
		}
	}

	
	@EventHandler
	public void eHitado(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Snowball) {
			Snowball a = (Snowball) e.getDamager();
			if (a.hasMetadata("Ryu")) {
				Player p = (Player) a.getShooter();
				Player target = (Player) e.getEntity();
				p.sendMessage(StringUtils.avisoverde + "§7Voce acertou o seu §6hadouken§7!");
				target.sendMessage(
						StringUtils.avisovermelho + "§7Voce levou um §6hadouken§7 de §6" + p.getDisplayName());
				target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 3 * 20, 1));
				e.setDamage(e.getDamage() + 8);
				target.setVelocity(new Vector(0, 1, 0));
			}

		}
	}
}