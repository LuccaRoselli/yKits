package com.luccadev.br.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.luccadev.br.Main;
import com.luccadev.br.constructors.PlayerProfile;
import com.luccadev.br.manager.KitManager;
import com.luccadev.br.manager.SCManager;
import com.luccadev.br.manager.StatsManager;
import com.luccadev.br.storage.PlayerMySQL;
import com.luccadev.br.utils.PlayerConfigFile;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;

public class Join implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		/*
		 * if (((CraftPlayer)p).getHandle().playerConnection.networkManager.
		 * getVersion () > 46) { p.kickPlayer(
		 * "§c§lO SERVIDOR NÃO SUPORTA A VERSÃO 1.8!\n§c§lFAVOR ENTRAR NA 1.7."
		 * ); }
		 */
		if (!PlayerMySQL.contaisInMySQL(e.getPlayer().getUniqueId())) {
			PlayerMySQL.setMySQL(e.getPlayer().getUniqueId());
			System.out.println("MySQL criado para o jogador " + e.getPlayer().getName());
			PlayerMySQL.addCrates(p.getUniqueId(), 1);
			StatsManager.caixas.put(p, PlayerMySQL.getCrates(p.getUniqueId()));
			e.getPlayer().sendMessage(StringUtils.avisoverde + "Você ganhou §e1 §7caixa por ser seu primeiro login! :)");
			e.getPlayer().sendMessage(StringUtils.avisoverde + "Seja bem-vindo! Volte sempre.");
		} else {
			System.out.println("MySQL ja existe para o jogador " + e.getPlayer().getName());
		}
		if (!p.getLocation().getChunk().isLoaded()){
			p.getLocation().getChunk().load();
		}
		final PlayerProfile pp = new PlayerProfile(p);
		pp.loadKits();
		pp.createGui();
		pp.createInventorySkill();
		KitManager.lastkit.put(p, "Nenhum");
		Uteis.setSpawnItems(p);
		Uteis.tpSpawn(p);
		p.setHealth(20.0D);
		p.setLevel(0);
		e.setJoinMessage(null);
		p.setMaxHealth(20.0D);
		KillStreak.resetKillStreak(p);
		p.setGameMode(GameMode.ADVENTURE);
		/*
		 * BarAPI.setMessage(p, "§e§lPVP.MC-FRAME.COM", 10);
		 */
		PlayerConfigFile.playerConfig(p);
		StatsManager.updateStats(p);
		/*
		 * try { final Statement statement = Main.c.createStatement(); statement
		 * .executeUpdate("INSERT INTO TBL_PVP (nome) SELECT * FROM (SELECT '" +
		 * p.getName() +
		 * "') AS tmp WHERE NOT EXISTS ( SELECT nome FROM TBL_PVP WHERE nome = '"
		 * + p.getName() + "') LIMIT 1;"); statement.close(); } catch
		 * (SQLException ex) { ex.printStackTrace(); }
		 */
		if (!SCManager.scb.containsKey(p)) {
			SCManager.scb.put(p, true);
		}
		SCManager.send(p);
		p.spigot().setCollidesWithEntities(false);
		new BukkitRunnable() {

			@Override
			public void run() {
				/*
				 * MinecraftServer server =
				 * ((CraftServer)Bukkit.getServer()).getServer(); WorldServer
				 * world = ((CraftWorld)Bukkit.getWorlds().get(0)).getHandle();
				 * EntityPlayer npc = new EntityPlayer(server, world, new
				 * GameProfile(UUID.randomUUID(), "BOBECAO"), new
				 * PlayerInteractManager(world));
				 * 
				 * npc.teleportTo(p.getLocation(), false);
				 * 
				 * PlayerConnection connection =
				 * ((CraftPlayer)p).getHandle().playerConnection;
				 * connection.sendPacket(new
				 * PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER,
				 * npc)); connection.sendPacket(new
				 * PacketPlayOutNamedEntitySpawn(npc));
				 */
				Location locdahora = p.getLocation().add(0, 0.7, 0);
				p.teleport(locdahora);
				Uteis.sendTag(p);
				Uteis.sendTablist(p,
						"§e§lLithe§f§lMC§f -§7 Rede de Servidores\n §eJogadores:§7 " + Bukkit.getOnlinePlayers().size() + " §7| §ePing: §7" + ((CraftPlayer)p).getHandle().ping + " ",
						"\n §eTeamSpeak:§7 ts3.lithemc.com.br | §eSite: §7wwww.lithemc.com.br \n §eLoja:§7 store.lithemc.com.br | §eDiscord: §7discord.lithemc.com.br \n §eTwitter: §7@LitheMC");
			}
		}.runTaskLater(Main.getMe(), 20L);
	}
	

}
