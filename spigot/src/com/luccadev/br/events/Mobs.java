package com.luccadev.br.events;

import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Horse;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class Mobs implements Listener {

	@EventHandler
	public void onCreeperSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Creeper)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSkeletonSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Skeleton)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSpiderSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Spider)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onWitherSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Wither)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onZombieSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Zombie)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSlimeSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Slime)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onGhastSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Ghast)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPigZombieSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof PigZombie)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPigZombieSpawn2(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Pig)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEndermanSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Enderman)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onCaveSpiderSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof CaveSpider)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSilverfishSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Silverfish)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlazeSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Blaze)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMagmaCubeSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof MagmaCube)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onWitchSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Witch)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSheepSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Sheep)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onCowSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Cow)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onChickenSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Chicken)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onSquidSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Squid)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onMooshroomSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof MushroomCow)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onOcelotSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Ocelot)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onVillagerSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Villager)) {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void onHorseSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof Horse)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEnderDragonSpawn(CreatureSpawnEvent e) {
		if ((e.getEntity() instanceof EnderDragon)) {
			e.setCancelled(true);
		}
	}

}
