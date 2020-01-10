package com.luccadev.br.constructors;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.luccadev.br.manager.KitManager;
import com.luccadev.br.utils.Uteis;

public class Kit {
	
	public static ArrayList<Kit> kits = new ArrayList<Kit>();
	
	String name;
	ArrayList<ItemStack> items = new ArrayList<>();
	ArrayList<String> desc = new ArrayList<>();
	String hab;
	ItemStack main;
	ItemStack helmet;
	ItemStack plate;
	ItemStack leggs;
	ItemStack boots;
	ItemStack icon;
	Material simple;
	KitType ktype;
	Integer kitprice;
	
	public Kit(String nome){
		this.name = nome;
		this.helmet = new ItemStack(Material.AIR);
		this.plate = new ItemStack(Material.AIR);
		this.leggs = new ItemStack(Material.AIR);
		this.boots = new ItemStack(Material.AIR);
		this.icon = new ItemStack(Material.BLAZE_POWDER);
		this.simple = Material.BLAZE_POWDER;
		this.desc = new ArrayList<>();
		this.hab = "§9Nenhuma";
		this.kitprice = 0;
		ItemMeta meta = icon.getItemMeta();
		meta.setDisplayName("§aKit " + nome);
		icon.setItemMeta(meta);
		KitManager.knames.add(name);
		KitManager.kits.put(name.toLowerCase(), this);
		kits.add(this);
	}
	
	public enum KitType {
		VERMELHO,ROXO,VERDE,CINZA
	}
	
	public KitType habType(){
		return this.ktype;
	}
	
	public ChatColor getColor(){
		if (ktype == KitType.CINZA){
			return ChatColor.GRAY;
		} else if (ktype == KitType.ROXO){
			return ChatColor.DARK_PURPLE;
			
		} else if (ktype == KitType.VERDE){
			return ChatColor.GREEN;
		} else {
			if (ktype == KitType.VERMELHO){
				return ChatColor.RED;
			}
		}
		return ChatColor.DARK_PURPLE;
	}
	
	public void setType(KitType t){
		this.ktype = t;
	}
	
	public void setPrice(Integer preço){
		this.kitprice = preço;
	}
	
	public int getPrice(){
		return this.kitprice;
	}
	
	public boolean isBuyable(){
		return this.kitprice > 0;
	}
	
	public void setMain(Material m){
		ItemStack stack = new ItemStack(m);
		ItemMeta meta = stack.getItemMeta();
		if (!getName().equalsIgnoreCase("Percy Jackson")){
		meta.setDisplayName("§b" + this.name);
		} else {
		meta.setDisplayName("§eAnaklusmos");
		}
		stack.setItemMeta(meta);
		main = stack;
	}
	
	public void addItem(Material m, String name){
		ItemStack stack = new ItemStack(m);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§5§o" + getName() + " Hability");
		stack.setItemMeta(meta);
		items.add(stack);
	}
	
	public String getPermission(){
		return "kit." + getName().toLowerCase();
	}
	
	public void addItem(Material m, String name, byte data){
		ItemStack stack = new ItemStack(m, 1, data);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§5§o" + getName() + " Hability");
		stack.setItemMeta(meta);
		items.add(stack);
	}
	
	public void addItem(Material m, String name, Integer quant){
		ItemStack stack = new ItemStack(m, quant);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§5§o" + getName() + " Hability");
		stack.setItemMeta(meta);
		items.add(stack);
	}
	
	public void addItem(Material m, String name, Integer quant, byte data){
		ItemStack stack = new ItemStack(m, quant, data);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§5§o" + getName() + " Hability");
		stack.setItemMeta(meta);
		items.add(stack);
	}
	
	public void setHelmet(Material m){
		ItemStack stack = new ItemStack(m);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§b" + this.name);
		stack.setItemMeta(meta);
		helmet = stack;
	}
	
	public void setPlate(Material m){
		ItemStack stack = new ItemStack(m);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§b" + this.name);
		stack.setItemMeta(meta);
		plate = stack;
	}
	
	public void setLeggs(Material m){
		ItemStack stack = new ItemStack(m);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§b" + this.name);
		stack.setItemMeta(meta);
		leggs = stack;
	}
	
	public void setBoots(Material m){
		ItemStack stack = new ItemStack(m);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§b" + this.name);
		stack.setItemMeta(meta);
		boots = stack;
	}
	
	public ArrayList<ItemStack> getItems(){
		return this.items;
	}
	
	public void setIcon(Material m){
		ItemStack stack = new ItemStack(m);
		this.simple = m;
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§6§lKIT • §e" + name);
	    meta.setLore(this.desc);
		stack.setItemMeta(meta);
		this.icon = stack;
	}
	
	public void setIcon(Material m, byte d){
		ItemStack stack = new ItemStack(m, 1, d);
		this.simple = m;
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§6§lKIT • §e" + name);
		ArrayList<String> name = new ArrayList<String>();
	    meta.setLore(this.desc);
	    meta.setLore(name);
		stack.setItemMeta(meta);
		this.icon = stack;
	}
	
	public void setDesc(ArrayList<String> descri){
		this.desc = descri;
	}
	
	public ArrayList<String> getDesc(){
		return desc;
	}
	
	public String getHab(){
		return hab;
	}
	
	public void setHab(String h){
		this.hab = "§9" + h;
	}

	public void addMainEnchant(Enchantment e){
		main.addEnchantment(e, 1);
	}
	
	public void addSecondEnchant(Enchantment e, Integer lvl){
		for(ItemStack i : items){
			i.addUnsafeEnchantment(e, lvl);
		}
	}
	
	public void setKit(Player p){
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setHelmet(helmet);
		p.getInventory().setChestplate(plate);
		p.getInventory().setLeggings(leggs);
		p.getInventory().setBoots(boots);
		p.getInventory().setHeldItemSlot(0);
		p.getInventory().setItem(0, main);
		p.getInventory().setItem(14, KitManager.getRedMush(32));
		p.getInventory().setItem(15, KitManager.getBrownMush(32));
		p.getInventory().setItem(13, KitManager.getPote(32));
        KitManager.lastkit.put(p, getName());
		p.getInventory().setItem(8, Uteis.setItem(Material.COMPASS, 1, "§aBússola", null));
		Uteis.teleportRandom(p, 100, 100);
		p.closeInventory();
	    for (ItemStack i : this.items)
	        p.getInventory().addItem(new ItemStack[] { i });
		p.getInventory().setHeldItemSlot(0);
		for(ItemStack i : p.getInventory().getContents()){
			if(i == null){
				p.getInventory().addItem(KitManager.getSoup());
			}
		}
	}
	
	/*
	public void setKit(Player p){
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setHelmet(helmet);
		p.getInventory().setChestplate(plate);
		p.getInventory().setLeggings(leggs);
		p.getInventory().setBoots(boots);
		p.getInventory().setHeldItemSlot(0);
		p.getInventory().setItem(Variaveis.slotsw.get(p), main);
		p.getInventory().setItem(14, KitManager.getRedMush(32));
		p.getInventory().setItem(15, KitManager.getBrownMush(32));
		p.getInventory().setItem(13, KitManager.getPote(32));
        KitManager.lastkit.put(p, getName());
		p.getInventory().setItem(Variaveis.slotbussola.get(p), Uteis.setItem(Material.COMPASS, 1, "§aBússola", null));
		Uteis.teleportRandom(p, 55, 55);
		p.closeInventory();
		for(ItemStack i : items){
			if (items.size() == 1){
			p.getInventory().setItem(Variaveis.slothab.get(p), i);
			} else if (items.size() == 2){
				p.getInventory().addItem(i);
			}
		}
		for(ItemStack i : p.getInventory().getContents()){
			if(i == null){
				p.getInventory().addItem(KitManager.getSoup());
			}
		}
	}
	*/

	public String getName(){
		return this.name;
	}

	public ItemStack getIcon(){
		return this.icon;
	}
	
	public Material getMaterial(){
		return this.simple;
	}
	
	public static class Kits{
		
		public static ArrayList<ItemStack> kititems = new ArrayList<ItemStack>();
		
		@SuppressWarnings("deprecation")
		public static void loadKits(){
		    Kit pvp = new Kit("PvP");
		    pvp.setMain(Material.STONE_SWORD);
		    ArrayList<String> pvpdesc = new ArrayList<String>();
		    pvpdesc.add("§e§oKit simples com nenhuma habilidade");
		    pvp.setDesc(pvpdesc);
		    pvp.setIcon(Material.STONE_SWORD);
		    pvp.addMainEnchant(Enchantment.DAMAGE_ALL);
		    pvp.setType(KitType.CINZA);
		    
		    Kit kangaroo = new Kit("Kangaroo");
		    kangaroo.setMain(Material.STONE_SWORD);
		    ArrayList<String> kangaroodesc = new ArrayList<String>();
		    kangaroodesc.add("§e§oComeça com um firework que quando");
		    kangaroodesc.add("§e§ovoce for usar ira dar um pulo duplo");
		    kangaroo.setDesc(kangaroodesc);
		    kangaroo.addItem(Material.FIREWORK, "§6Kangaroo Rocket");
		    kangaroo.setIcon(Material.FIREWORK);
		    kangaroo.setType(KitType.ROXO);
		    kangaroo.setPrice(10000);
		    
		    Kit firework = new Kit("Firework");
		    firework.setMain(Material.STONE_SWORD);
		    ArrayList<String> fireworklore = new ArrayList<String>();
		    fireworklore.add("§e§oComeça com uma Firework que quando");
		    fireworklore.add("§e§ovoce clicar em alguem ira arremeça-lo");
		    fireworklore.add("§e§opara cima");
		    firework.setDesc(fireworklore);
		    firework.addItem(Material.FIREWORK, "§aPassagem para o Céu");
		    firework.setIcon(Material.FIREWORK);
		    firework.setType(KitType.VERDE);
		    firework.setPrice(9000);
		    
		    Kit ewok = new Kit("eWok");
		    ewok.setMain(Material.STONE_SWORD);
		    ArrayList<String> ewoklore = new ArrayList<String>();
		    ewoklore.add("§e§oComeça com uma enchada de ouro que");
		    ewoklore.add("§e§oira lançar uma bola de neve, quando");
		    ewoklore.add("§e§oesta bola de neve encostar em alguem");
		    ewoklore.add("§e§oira arremeça-lo para cima.");
		    ewok.setDesc(ewoklore);
		    ewok.addItem(Material.GOLD_HOE, "§aeWok");
		    ewok.setIcon(Material.GOLD_HOE);
		    ewok.setType(KitType.VERDE);
		    ewok.setPrice(10000);
		    
		    Kit ryu = new Kit("Ryu");
		    ryu.setMain(Material.STONE_SWORD);
		    ArrayList<String> ryulore = new ArrayList<String>();
		    ryulore.add("§e§oVoce ira poder soltar um Hadouken");
		    ryu.setDesc(ryulore);
		    ryu.addItem(Material.BEACON, "§aHadouken!");
		    ryu.setIcon(Material.BEACON);
		    ryu.setType(KitType.VERDE);
		    ryu.setPrice(12000);
		    
		    Kit nuke = new Kit("Nuke");
		    nuke.setMain(Material.STONE_SWORD);
		    ArrayList<String> nukelore = new ArrayList<String>();
		    nukelore.add("§e§oCom este kit você podera explodir");
		    nukelore.add("§e§otodos que estao em volta");
		    nuke.setDesc(nukelore);
		    nuke.addItem(Material.TNT, "§aNuke");
		    nuke.setIcon(Material.TNT);
		    nuke.setType(KitType.ROXO);
		    nuke.setPrice(10000);
		    
		    Kit wither = new Kit("Wither");
		    wither.setMain(Material.STONE_SWORD);
		    ArrayList<String> witherlore = new ArrayList<String>();
		    witherlore.add("§e§oCom uma nether star você ira poder");
		    witherlore.add("§e§olançar cabeças de Wither");
		    wither.setDesc(witherlore);
		    wither.addItem(Material.getMaterial(399), "§aWither");
		    wither.setIcon(Material.getMaterial(399));
		    wither.setType(KitType.ROXO);
		    wither.setPrice(8000);
		    
		    Kit golem = new Kit("Golem");
		    golem.setMain(Material.STONE_SWORD);
		    ArrayList<String> golemlore = new ArrayList<String>();
		    golemlore.add("§e§oCom este kit você podera criar uma");
		    golemlore.add("§e§oproteção em volta de você");
		    golem.setDesc(golemlore);
		    golem.addItem(Material.OBSIDIAN, "§aGolem");
		    golem.setIcon(Material.OBSIDIAN);
		    golem.setType(KitType.ROXO);
		    golem.setPrice(15000);
		    
		    Kit moon = new Kit("Moon");
		    moon.setMain(Material.STONE_SWORD);
		    ArrayList<String> moonlore = new ArrayList<String>();
		    moonlore.add("§e§oCom este kit você podera soltar");
		    moonlore.add("§e§omorcegos que lhe ajudarão!");
		    moon.setDesc(moonlore);
		    moon.addItem(Material.COAL, "§aMoon");
		    moon.setIcon(Material.COAL);
		    moon.setType(KitType.ROXO);
		    moon.setPrice(15000);
		    
		    Kit forcefield = new Kit("Forcefield");
		    forcefield.setMain(Material.STONE_SWORD);
		    ArrayList<String> forcefieldlore = new ArrayList<String>();
		    forcefieldlore.add("§e§oAtivando sua habilidade, todos que estiverem");
		    forcefieldlore.add("§e§oem 5 blocos de distância de você, levarão dano.");
		    forcefield.setDesc(forcefieldlore);
		    forcefield.addItem(Material.IRON_FENCE, "§aForcefield");
		    forcefield.setIcon(Material.IRON_FENCE);
		    forcefield.setType(KitType.VERMELHO);
		    forcefield.setPrice(20000);
		    
		    Kit titan = new Kit("Titan");
		    titan.setMain(Material.STONE_SWORD);
		    ArrayList<String> titanlore = new ArrayList<String>();
		    titanlore.add("§e§oAo entrar no modo titan");
		    titanlore.add("§e§ovocê não levará danos");
		    titan.setDesc(titanlore);
		    titan.addItem(Material.BEDROCK, "§aTitan Mode");
		    titan.setIcon(Material.BEDROCK);
		    titan.setType(KitType.VERMELHO);
		    titan.setPrice(13000);
		    
		    Kit c4 = new Kit("C4");
		    c4.setMain(Material.STONE_SWORD);
		    ArrayList<String> c4lore = new ArrayList<String>();
		    c4lore.add("§e§oLance sua c4");
		    c4lore.add("§e§ocontra seus inimigos e exploda-os");
		    c4.setDesc(c4lore);
		    c4.addItem(Material.SLIME_BALL, "§aC4");
		    c4.setIcon(Material.SLIME_BALL);
		    c4.setType(KitType.VERMELHO);
		    c4.setPrice(20000);
		    
		    Kit avatar = new Kit("Avatar");
		    avatar.setMain(Material.STONE_SWORD);
		    ArrayList<String> avatarlore = new ArrayList<String>();
		    avatarlore.add("§e§oTenha o domínio");
		    avatarlore.add("§e§odos 4 poderes naturais");
		    avatar.setDesc(avatarlore);
		    avatar.addItem(Material.WOOL, "§aAvatar");
		    avatar.setIcon(Material.WOOL);
		    avatar.setType(KitType.VERMELHO);
		    avatar.setPrice(20000);
		    
		    Kit stomper = new Kit("Stomper");
		    stomper.setMain(Material.STONE_SWORD);
		    ArrayList<String> stomperlore = new ArrayList<String>();
		    stomperlore.add("§e§oMate seus inimigos");
		    stomperlore.add("§e§opisoteados!");
		    stomper.setDesc(stomperlore);
		    stomper.addItem(Material.GOLDEN_APPLE, "§aStomper Apple");
		    stomper.setIcon(Material.DIAMOND_BOOTS);
		    stomper.setType(KitType.VERMELHO);
		    stomper.setPrice(25000);
		    
		    Kit zeus = new Kit("Zeus");
		    zeus.setMain(Material.STONE_SWORD);
		    ArrayList<String> zeuslore = new ArrayList<String>();
		    zeuslore.add("§e§oDomine o poder");
		    zeuslore.add("§e§odos raios!");
		    zeus.setDesc(zeuslore);
		    zeus.addItem(Material.NETHER_STAR, "§aZeus");
		    zeus.setIcon(Material.MUSHROOM_SOUP);
		    zeus.setType(KitType.ROXO);
		    zeus.setPrice(12000);
		    
		    Kit endermage = new Kit("Endermage");
		    endermage.setMain(Material.STONE_SWORD);
		    ArrayList<String> endermagelore = new ArrayList<String>();
		    endermagelore.add("§e§oPuxe jogadores com seu portal!");
		    endermage.setDesc(endermagelore);
		    endermage.addItem(Material.PORTAL, "§cEndermage");
		    endermage.setIcon(Material.PORTAL);
		    endermage.setType(KitType.ROXO);
		    endermage.setPrice(15000);
		    
		    Kit sonic = new Kit("Sonic");
		    sonic.setMain(Material.STONE_SWORD);
		    ArrayList<String> soniclore = new ArrayList<String>();
		    soniclore.add("§e§oSe mova rapidamente, e");
		    soniclore.add("§e§oao passar perto de inimigos, de dano neles!");
		    sonic.setDesc(soniclore);
		    sonic.addItem(Material.LAPIS_BLOCK, "§cSonic");
		    sonic.setIcon(Material.LAPIS_BLOCK);
		    sonic.setType(KitType.VERDE);
		    sonic.setPrice(10000);
		    
		    Kit flare = new Kit("Flare");
		    flare.setMain(Material.STONE_SWORD);
		    ArrayList<String> flarelore = new ArrayList<String>();
		    flarelore.add("§e§oQueime seus inimigos!");
		    flare.setDesc(flarelore);
		    flare.addItem(Material.BLAZE_POWDER, "§6Habilidade Flare");
		    flare.setIcon(Material.BLAZE_POWDER);
		    flare.setType(KitType.ROXO);
		    flare.setPrice(14000);
		    
		    Kit fireshield = new Kit("FireShield");
		    fireshield.setMain(Material.STONE_SWORD);
		    ArrayList<String> fireshieldlore = new ArrayList<String>();
		    fireshieldlore.add("§e§oCrie um escudo");
		    fireshieldlore.add("§e§ode fogo, que o protegerá!");
		    fireshield.setDesc(fireshieldlore);
		    fireshield.addItem(Material.MAGMA_CREAM, "§6Habilidade FireShield");
		    fireshield.setIcon(Material.MAGMA_CREAM);
		    fireshield.setType(KitType.VERMELHO);
		    fireshield.setPrice(18000);
		    
		    Kit grappler = new Kit("Grappler");
		    grappler.setMain(Material.STONE_SWORD);
		    ArrayList<String> grapplerlore = new ArrayList<String>();
		    grapplerlore.add("§e§oUse seu laço para");
		    grapplerlore.add("§e§ose mover rapidamente!");
		    grappler.setDesc(grapplerlore);
		    grappler.addItem(Material.LEASH, "§6Grappler Leash");
		    grappler.setIcon(Material.LEASH);
		    grappler.setType(KitType.ROXO);
		    grappler.setPrice(16000);
		    
		    Kit scorpion = new Kit("Scorpion");
		    scorpion.setMain(Material.STONE_SWORD);
		    ArrayList<String> scorpionlore = new ArrayList<String>();
		    scorpionlore.add("§e§oPuxe jogadores a seu");
		    scorpionlore.add("§e§oalcance, com seu poder!");
		    scorpion.setDesc(scorpionlore);
		    scorpion.addItem(Material.TRIPWIRE_HOOK, "§6Scorpion Hook");
		    scorpion.setIcon(Material.TRIPWIRE_HOOK);
		    scorpion.setType(KitType.VERDE);
		    scorpion.setPrice(14000);
		    
		    Kit ninja = new Kit("Ninja");
		    ninja.setMain(Material.STONE_SWORD);
		    ArrayList<String> ninjalore = new ArrayList<String>();
		    ninjalore.add("§e§oBata em um jogador e agache-se");
		    ninjalore.add("§e§opara teleportar-se ate ele!");
		    ninja.setDesc(ninjalore);
		    ninja.setIcon(Material.COAL_BLOCK);
		    ninja.setType(KitType.ROXO);
		    ninja.setPrice(15000);
		    
		    Kit devastator = new Kit("Devastator");
		    devastator.setMain(Material.STONE_SWORD);
		    ArrayList<String> devastatorlore = new ArrayList<String>();
		    devastatorlore.add("§e§oFaça uma chuva");
		    devastatorlore.add("§e§ode bolas de fogo,");
		    devastatorlore.add("§e§ocausando um grande dano em seus inimigos!");
		    devastator.setDesc(devastatorlore);
		    devastator.addItem(Material.FIREBALL, "§6Devastator");
		    devastator.setIcon(Material.FIREBALL);
		    devastator.setType(KitType.VERMELHO);
		    devastator.setPrice(25000);
		    
		    Kit damager = new Kit("Damager");
		    damager.setMain(Material.STONE_SWORD);
		    ArrayList<String> damagerlore = new ArrayList<String>();
		    damagerlore.add("§e§oAo tirar pvp com algum player");
		    damagerlore.add("§e§otenha uma porcentagem de dar mais dano!");
		    damager.setDesc(damagerlore);
		    damager.setIcon(Material.REDSTONE);
		    damager.setType(KitType.VERMELHO);
		    damager.setPrice(23000);
		    
		    Kit wombo = new Kit("Wombo");
		    wombo.setMain(Material.STONE_SWORD);
		    ArrayList<String> wombolore = new ArrayList<String>();
		    wombolore.add("§e§oAo acertar um combo em");
		    wombolore.add("§e§oseu inimigo, ganhe boosts!");
		    wombo.setDesc(wombolore);
		    wombo.setIcon(Material.DIAMOND_SWORD);
		    wombo.setType(KitType.VERDE);
		    wombo.setPrice(14000);
		    
		    Kit nightmare = new Kit("Nightmare");
		    nightmare.setMain(Material.STONE_SWORD);
		    ArrayList<String> nightmarelore = new ArrayList<String>();
		    nightmarelore.add("§e§oLançe morcegos contra seu inimigo,");
		    nightmarelore.add("§e§oao acerta-lo, cause efeitos negativos!");
		    nightmare.setDesc(nightmarelore);
		    nightmare.addItem(Material.FLINT, "§6Nightmare");
		    nightmare.setIcon(Material.FLINT);
		    nightmare.setType(KitType.VERDE);
		    nightmare.setPrice(13000);
		    
		    Kit darius = new Kit("Darius");
		    darius.setMain(Material.STONE_SWORD);
		    ArrayList<String> dariuslore = new ArrayList<String>();
		    dariuslore.add("§e§oCom seu machado, puxe");
		    dariuslore.add("§e§oseus inimigos até você!");
		    darius.setDesc(dariuslore);
		    darius.setIcon(Material.GOLD_AXE);
		    darius.addItem(Material.GOLD_AXE, "Dariu's Axe");
		    darius.setType(KitType.ROXO);
		    darius.setPrice(15000);
		   
		    Kit reflector = new Kit("Reflector");
		    reflector.setMain(Material.STONE_SWORD);
		    ArrayList<String> reflectorlore = new ArrayList<String>();
		    reflectorlore.add("§e§oAo ativar sua habilidade");
		    reflectorlore.add("§e§ovocê não levará hit, e o dano causado pelo");
		    reflectorlore.add("§e§oinimigo será refletido nele!");
		    reflector.setDesc(reflectorlore);
		    reflector.addItem(Material.LEVER, "§6Reflector Activator");
		    reflector.setIcon(Material.LEVER);
		    reflector.setType(KitType.VERMELHO);
		    reflector.setPrice(25000);
		    
		    Kit lightning = new Kit("Lightning");
		    lightning.setMain(Material.STONE_SWORD);
		    ArrayList<String> lightninglore = new ArrayList<String>();
		    lightninglore.add("§e§oAcerte um raio em seus inimigos");
		    lightninglore.add("§e§oque estiverem 5 blocos perto de você!");
		    lightning.setDesc(lightninglore);
		    lightning.addItem(Material.GHAST_TEAR, "§6Fisherman");
		    lightning.setIcon(Material.GHAST_TEAR);
		    lightning.setType(KitType.ROXO);
		    lightning.setPrice(13000);
		    
		    Kit lord = new Kit("Lord");
		    lord.setMain(Material.STONE_SWORD);
		    ArrayList<String> lordlore = new ArrayList<String>();
		    lordlore.add("§e§oAo ativar seus poderes de Lord");
		    lordlore.add("§e§ocause grandes danos em seus inimigos");
		    lordlore.add("§e§oe ao ser atacado, deixe-os pegando fogo!");
		    lord.setDesc(lordlore);
		    lord.addItem(Material.DIAMOND, "§6Lord");
		    lord.setIcon(Material.DIAMOND);
		    lord.setPrice(18000);
		    
		    Kit magma = new Kit("Magma");
		    magma.setMain(Material.STONE_SWORD);
		    ArrayList<String> magmalore = new ArrayList<String>();
		    magmalore.add("§e§oAo levar hit, tenha 20% de chance de");
		    magmalore.add("§e§odeixar seu oponente pegando fogo.");
		    magmalore.add("§e§oVocê levará dano ao entrar na água!");
		    magma.setDesc(magmalore);
		    magma.setIcon(Material.FIRE);
		    magma.setType(KitType.ROXO);
		    magma.setPrice(14000);
		    
		    Kit molotov = new Kit("Molotov");
		    molotov.setMain(Material.STONE_SWORD);
		    ArrayList<String> molotovlore = new ArrayList<String>();
		    molotovlore.add("§e§oJogue uma molotov no chão");
		    molotovlore.add("§e§oe deixe-o pegando fogo!");
		    molotov.setDesc(molotovlore);
		    molotov.addItem(Material.SNOW_BALL, "§6Molotov");
		    molotov.setIcon(Material.SNOW_BALL);
		    molotov.setType(KitType.ROXO);
		    molotov.setPrice(15000);
		    
		    Kit monk = new Kit("Monk");
		    monk.setMain(Material.STONE_SWORD);
		    ArrayList<String> monklore = new ArrayList<String>();
		    monklore.add("§e§oColoque o item que está na mão");
		    monklore.add("§e§odo inimigo, em um lugar aleatório do inventário dele!");
		    monk.setDesc(monklore);
		    monk.addItem(Material.BLAZE_ROD, "§6Monk Staff");
		    monk.setIcon(Material.BLAZE_ROD);
		    monk.setType(KitType.ROXO);
		    monk.setPrice(14000);
		    
		    Kit velotrol = new Kit("Velotrol");
		    velotrol.setMain(Material.STONE_SWORD);
		    ArrayList<String> velotrollore = new ArrayList<String>();
		    velotrollore.add("§e§oCom seu carrinhfo, pedale");
		    velotrollore.add("§e§oe exploda uma bomba no local onde parar!");
		    velotrol.setDesc(velotrollore);
		    velotrol.addItem(Material.MINECART, "§6Velotrol");
		    velotrol.setIcon(Material.MINECART);
		    velotrol.setType(KitType.VERMELHO);
		    velotrol.setPrice(18000);
		    
		    Kit supernova = new Kit("Supernova");
		    supernova.setMain(Material.STONE_SWORD);
		    ArrayList<String> supernovalore = new ArrayList<String>();
		    supernovalore.add("§e§oDestrua seu inimigo");
		    supernovalore.add("§e§ocom suas flechas!");
		    supernova.setDesc(supernovalore);
		    supernova.addItem(Material.NETHER_STAR, "§6Supernova");
		    supernova.setIcon(Material.ARROW);
		    supernova.setType(KitType.VERMELHO);
		    supernova.setPrice(18000);
		    
		    for (Kit k : KitManager.getKits()){
		    	kititems.add(k.getIcon());
		    }
		}
	}
	
}