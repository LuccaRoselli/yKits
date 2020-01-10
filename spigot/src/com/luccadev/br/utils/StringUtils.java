package com.luccadev.br.utils;

public class StringUtils {
	
	
	public static String coinprefix = "§8❮§e⛁§8❯ §7";
	public static String avisoverde = "§8❮§e§lLithe§f§lMC§8❯ §7";
	public static String site = "www.lithemc.com.br";
	public static String avisovermelho = "§8❮§e§lLithe§c§lMC§8❯ §7";
	public static String noperm = "§8❮§4§l!§8❯ §7Você não tem permissão para fazer isso.";
	private static String prefix = "LitheMC";
	public static String permissaoprefix = "kitpvp.";
	public static String PROTECAO = "§8§lPROTEÇÃO: §7";
	
	public static String getPrefix(char type){
		if (type == 'a')
			return "§a" + prefix + " §7» ";
		else if (type == 'b')
			return "§e§lLithe§f§lMC";
		return null;
	}
	
	public static String getSite(){
		return site;
	}

}
