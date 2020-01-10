package com.luccadev.br.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.luccadev.br.constructors.Fake;
import com.luccadev.br.constructors.Fake.FakeNames;
import com.luccadev.br.experience.ExperienceRank;
import com.luccadev.br.utils.StringUtils;
import com.luccadev.br.utils.Uteis;
import com.nametagedit.plugin.NametagEdit;

public class FakeCommand implements CommandExecutor, Listener {

	@EventHandler
	public void playerquit(PlayerQuitEvent e) {
		if (fakehash.containsKey(e.getPlayer())){
			fakehash.remove(e.getPlayer());
		}
	}

	public static String getText(String url) throws Exception {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			response.append(inputLine);

		in.close();

		return response.toString();
	}

	public static boolean containsInList(String playername) {
		for (String a : FakeNames.fakenames) {
			if (a.equalsIgnoreCase(playername)) {
				return true;
			}
		}
		return false;
	}

	public static HashMap<Player, Fake> fakehash = new HashMap<>();
	public static ArrayList<String> fakenames = new ArrayList<>();
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("fake")) {
			if (p.hasPermission(StringUtils.permissaoprefix + "comando.fake")) {
				if (args.length == 0) {
					if (fakehash.containsKey(p)) {
						try {
							try {
								fakenames.remove(fakehash.get(p).getFakeName());
								fakehash.get(p).toOriginal();
								NametagEdit.getApi().setPrefix(p.getName(), "§7");
								NametagEdit.getApi().setSuffix(p.getName(),
										" §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
												+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
								p.setPlayerListName("§7" + Uteis.getShortStr(p.getName()));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							p.sendMessage("§a§lFAKE: §7Você voltou ao seu nick original.");
							fakehash.remove(p);
						} catch (SecurityException | IllegalArgumentException e) {
							e.printStackTrace();
						}
					} else {
						p.sendMessage("§4§lERRO: §cVocê não está utilizando nenhum disfarce.");
						p.sendMessage("§4§lERRO: §cUtilize: /fake <nick> ou /fake list");
					}
				} else {
					if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
						if (fakehash.size() == 0){
							p.sendMessage("");
							p.sendMessage("      §a§lFAKE LIST");
							p.sendMessage("");
							p.sendMessage("§cNão há ninguém utilizando disfarces neste momento!");
						} else {
							p.sendMessage("");
							p.sendMessage("      §a§lFAKE LIST");
							p.sendMessage("");
							for (final Map.Entry<Player, Fake> entry : fakehash.entrySet()) {
								p.sendMessage("§fUsuário: §e" + entry.getValue().getOriginalPlayerName() + " §7(" + entry.getValue().getFakeName() + "§7)");
							}
						}
						return true;

					}
					if (args.length == 1 && !args[0].equalsIgnoreCase("aleatorio")) {
						if (!fakehash.containsKey(p)) {
							try {
								if (!containsInList(args[0])) {
									Fake f;
									try {
										for (String a : fakenames){
											if (a.toLowerCase().contains(args[0].toLowerCase())){
												p.sendMessage("§4§lERRO: §cOcorreu um erro ao setar seu disfarce.");
												p.sendMessage("§4§lERRO: §cEste disfarce ja está em uso por alguém.");
												return true;
											}
										}
										for (Player ae : Bukkit.getOnlinePlayers()){
											if (ae.getName().toLowerCase().equals(args[0].toLowerCase())){
												p.sendMessage("§4§lERRO: §cOcorreu um erro ao setar seu disfarce.");
												p.sendMessage("§4§lERRO: §cVocê não pode usar o nome de alguém online.");
												return true;
											}
										}
										f = new Fake(p, p.getName(), args[0]);
										fakehash.put(p, f);
										fakenames.add(args[0]);
										p.sendMessage("§a§lFAKE: §7Aproveite seu novo disfarce!");
										p.sendMessage("§a§lFAKE: §7Seu novo nome: §a" + args[0]);
										NametagEdit.getApi().setPrefix(p.getName(), "§7");
										NametagEdit.getApi().setSuffix(p.getName(),
												" §7(§" + ExperienceRank.getPlayerRank(p).getSymbolColor()
														+ ExperienceRank.getPlayerRank(p).getSymbol() + "§7)");
										p.setPlayerListName("§7" + Uteis.getShortStr(p.getName()));
									} catch (Exception e) {
										e.printStackTrace();
										p.sendMessage("§4§lERRO: §cOcorreu um erro ao setar seu disfarce.");
									}
								} else {
									p.sendMessage("§4§lERRO: §cVocê não pode usar um disfarce de uma conta original.");
									p.sendMessage("§4§lERRO: §cA conta §4" + args[0] + " §cé original.");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							p.sendMessage("§4§lERRO: §cVocê já está utilizando um disfarce");
						}

					}
				}

			}
		}

		return false;
	}
}
