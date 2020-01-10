package com.luccadev.br.events;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.Achievement;
import com.luccadev.br.constructors.Achievement.AchievementType;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;

public class Death implements Listener {

	KitManager kit = KitManager.getInstance();

	/*
	 * public static HashMap<Player, Integer> kills = new HashMap<Player,
	 * Integer>();
	 * 
	 * @EventHandler public void onDeath(PlayerDeathEvent e){ final Player matou
	 * = e.getEntity().getKiller(); if(!kills.containsKey(matou)){
	 * kills.put(matou, 1);
	 * Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new
	 * Runnable() {
	 * 
	 * @Override public void run() { if (((Integer)kills.get(matou)).intValue()
	 * < 2){ kills.remove(matou);
	 * 
	 * } } }, 400L); } else { int a = 0; a =
	 * ((Integer)kills.get(matou)).intValue(); kills.put(matou,
	 * Integer.valueOf(a + 1)); if (((Integer)kills.get(matou)).intValue() ==
	 * 2){ Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new
	 * Runnable() {
	 * 
	 * @Override public void run() { if (((Integer)kills.get(matou)).intValue()
	 * == 2){ kills.remove(matou); } } }, 400L); } if
	 * (((Integer)kills.get(matou)).intValue() == 3){
	 * Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new
	 * Runnable() {
	 * 
	 * @Override public void run() { if (((Integer)kills.get(matou)).intValue()
	 * == 3){ kills.remove(matou);
	 * 
	 * } } }, 400L); } if (((Integer)kills.get(matou)).intValue() == 4){
	 * Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new
	 * Runnable() {
	 * 
	 * @Override public void run() { if (((Integer)kills.get(matou)).intValue()
	 * == 4){
	 * 
	 * kills.remove(matou); } } }, 400L); } if
	 * (((Integer)kills.get(matou)).intValue() == 5){
	 * Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new
	 * Runnable() {
	 * 
	 * @Override public void run() { if (((Integer)kills.get(matou)).intValue()
	 * == 5){ kills.remove(matou); } } }, 400L); } if
	 * (((Integer)kills.get(matou)).intValue() > 5){ kills.remove(matou); } } if
	 * (((Integer)kills.get(matou)).intValue() == 2){
	 * Bukkit.broadcastMessage(Main.prefix + "§bO Jogador §3" + matou.getName()
	 * + " §bfez um §c§lDOUBLE-KILL§b!"); } else if
	 * (((Integer)kills.get(matou)).intValue() == 3){
	 * Bukkit.broadcastMessage(Main.prefix + "§bO Jogador §3" + matou.getName()
	 * + " §bfez um §c§lTRIPLE-KILL§b!"); } else if
	 * (((Integer)kills.get(matou)).intValue() == 4){
	 * Bukkit.broadcastMessage(Main.prefix + "§bO Jogador §3" + matou.getName()
	 * + " §bfez um §c§lQUADRA-KILL§b!"); } else if
	 * (((Integer)kills.get(matou)).intValue() == 5){
	 * Bukkit.broadcastMessage(Main.prefix + "§bO Jogador §3" + matou.getName()
	 * + " §bfez um §c§lULTRA-KILL§b!"); kills.remove(matou); }
	 * 
	 * }
	 */

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player victim = e.getEntity();
		Player killer = e.getEntity().getKiller();
		if (KillStreak.getKillStreak(victim) >= 10) {
			Bukkit.broadcastMessage("§b§l* §6" + killer.getName() + " §7acabou com o KillStreak de §6"
					+ KillStreak.getKillStreak(victim) + " §7de§6 " + victim.getName() + "§7!");
		}
		if (victim instanceof Player) {
			SCManager.send(victim);
			KillStreak.resetKillStreak(victim);
		}
		if (killer instanceof Player) {
			KillStreak.addKill(killer);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (e.getEntity() instanceof NPC) {
			return;
		}
		if (KitManager.pkits.containsKey(e.getEntity().getName())) {
			KitManager.pkits.remove(e.getEntity().getName());
		}
		final Player vitima = e.getEntity();
		if ((e.getEntity().getKiller() instanceof Player)) {
			Player matador = e.getEntity().getKiller();
			if (matador != vitima) {
				e.getDrops().clear();

				if (Uteis.hasCooldown(vitima)) {
					Uteis.removeCooldown(vitima);
				}
				
				Uteis.giveAndTakeMoneyAndXP(matador, vitima, true);

				if (StatsManager.kills.get(matador) != 0) {
					StatsManager.kills.put(matador,
							Integer.valueOf(((Integer) StatsManager.kills.get(matador)).intValue() + 1));
				} else {
					StatsManager.kills.put(matador, Integer.valueOf(1));
				}

				if (StatsManager.deaths.get(vitima) != 0) {
					StatsManager.deaths.put(vitima,
							Integer.valueOf(((Integer) StatsManager.deaths.get(vitima)).intValue() + 1));
				} else {
					StatsManager.deaths.put(vitima, Integer.valueOf(1));
				}
				/*
				 * if (Gui1v1.randomQueue.contains(vitima)) {
				 * Gui1v1.randomQueue.remove(vitima); }
				 */
				if (StatsManager.getKills(matador) == 50
						&& !Achievement.getAchievement(AchievementType.KILLS).isCompleted(matador)) {
					Achievement.getAchievement(AchievementType.KILLS).finalize(matador);
				}
				if (StatsManager.getDeaths(vitima) == 50
						&& !Achievement.getAchievement(AchievementType.DEATHS).isCompleted(vitima)) {
					Achievement.getAchievement(AchievementType.DEATHS).finalize(vitima);
				}
				SCManager.send(matador);
				if (!kit.getUsingKitName(vitima).equalsIgnoreCase("Nenhum")) {
					kit.zerarKit(vitima);
				}

				SCManager.send(vitima);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
					public void run() {
						if (vitima.isOnline()) {
							EntityPlayer ep = ((CraftPlayer) vitima).getHandle();
							ep.playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
						}
					}
				}, 1);
			}
		} else {
			int xplose = Uteis.getRand(0, 20);
			int coinslose = Uteis.getRand(0, 7);
			vitima.sendMessage(StringUtils.avisovermelho + "§7Voce morreu!");
			vitima.sendMessage(StringUtils.avisovermelho + "§7-§c" + coinslose + " §7Coin's.");
			vitima.sendMessage(StringUtils.avisovermelho + "§7-§c" + xplose + " §7XP's.");

			if (StatsManager.money.get(vitima) >= coinslose) {
				StatsManager.money.put(vitima,
						Integer.valueOf(((Integer) StatsManager.money.get(vitima)).intValue() - coinslose));
			} else {
				StatsManager.money.put(vitima, Integer.valueOf(0));
			}

			if (StatsManager.deaths.get(vitima) != 0) {
				StatsManager.deaths.put(vitima,
						Integer.valueOf(((Integer) StatsManager.deaths.get(vitima)).intValue() + 1));
			} else {
				StatsManager.deaths.put(vitima, Integer.valueOf(1));
			}

			if (StatsManager.xp.get(vitima) >= xplose) {
				StatsManager.xp.put(vitima,
						Integer.valueOf(((Integer) StatsManager.xp.get(vitima)).intValue() - xplose));
			} else {
				StatsManager.xp.put(vitima, Integer.valueOf(0));
			}

			if (Uteis.hasCooldown(vitima)) {
				Uteis.removeCooldown(vitima);
			}
			/*
			 * if (Gui1v1.randomQueue.contains(vitima)) {
			 * Gui1v1.randomQueue.remove(vitima); }
			 */
			if (StatsManager.getDeaths(vitima) == 50
					&& !Achievement.getAchievement(AchievementType.DEATHS).isCompleted(vitima)) {
				Achievement.getAchievement(AchievementType.DEATHS).finalize(vitima);
			}
			/*
			 * Uteis.tpSpawn(vitima);
			 */
			e.getDrops().clear();
			if (!kit.getUsingKitName(vitima).equalsIgnoreCase("Nenhum")) {
				kit.zerarKit(vitima);
			}
			SCManager.send(vitima);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMe(), new Runnable() {
				public void run() {
					if (vitima.isOnline()) {
						EntityPlayer ep = ((CraftPlayer) vitima).getHandle();
						ep.playerConnection.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
					}
				}
			}, 1);
			return;
		}
	}

}
