/*     */ package de.n1ck145.lobbySystem.main;
/*     */ 
/*     */ import de.n1ck145.lobbySystem.GUI.GUI_Compass;
/*     */ import de.n1ck145.lobbySystem.MySQL.API_MySQL;
/*     */ import de.n1ck145.lobbySystem.commands.CMD_Build;
/*     */ import de.n1ck145.lobbySystem.commands.CMD_LobbyC;
/*     */ import de.n1ck145.lobbySystem.commands.CMD_Spawn;
/*     */ import de.n1ck145.lobbySystem.items.ITEM_Compass;
/*     */ import de.n1ck145.lobbySystem.listener.EventManager;
/*     */ import de.n1ck145.lobbySystem.utils.FileManager;
/*     */ import java.util.ArrayList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin
/*     */ {
/*     */   private static Main main;
/*     */   private ConsoleCommandSender console;
/*     */   private String prefix;
/*     */   private FileManager locations;
/*     */   private FileManager messages;
/*     */   private FileManager fm_compass;
/*     */   private ArrayList<Player> buildPlayer;
/*     */   private ITEM_Compass item_compass;
/*     */   private GUI_Compass gui_compass;
/*     */   
/*     */   public void onEnable() {
/*  39 */     main = this;
/*  40 */     this.console = Bukkit.getConsoleSender();
/*  41 */     this.buildPlayer = new ArrayList<>();
/*     */     
/*  43 */     registerConfig();
/*  44 */     registerEvents();
/*  45 */     registerCommands();
/*     */     
/*  47 */     this.item_compass = new ITEM_Compass(Material.getMaterial(this.fm_compass.getString("compass.material")));
/*  48 */     this.gui_compass = new GUI_Compass();
/*     */     
/*  50 */     if (getConfig().getBoolean("MySQL.enable")) {
/*  51 */       mySQLSetup();
/*     */     }
/*     */     
/*  54 */     this.console.sendMessage("§8[§b" + getName() + "§8] §aPlugin ready!");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  59 */     API_MySQL.disconnect();
/*     */   }
/*     */   
/*     */   private void mySQLSetup() {
/*  63 */     API_MySQL.host = getConfig().getString("MySQL.host");
/*  64 */     API_MySQL.port = getConfig().getString("MySQL.port");
/*  65 */     API_MySQL.username = getConfig().getString("MySQL.username");
/*  66 */     API_MySQL.password = getConfig().getString("MySQL.password");
/*  67 */     API_MySQL.database = getConfig().getString("MySQL.database");
/*     */     
/*  69 */     API_MySQL.connect();
/*  70 */     if (!API_MySQL.isConnected())
/*  71 */       return;  API_MySQL.update("CREATE TABLE IF NOT EXISTS lobby_lastOnline(UUID varchar(40) PRIMARY KEY NOT NULL,UserName varchar(30),value TIMESTAMP)ENGINE=InnoDB;");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     API_MySQL.update("CREATE TABLE IF NOT EXISTS lobby_coins(UUID varchar(40) PRIMARY KEY NOT NULL,UserName varchar(30),coins DOUBLE)ENGINE=InnoDB;");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerCommands() {
/*  84 */     getCommand("build").setExecutor((CommandExecutor)new CMD_Build());
/*  85 */     getCommand("spawn").setExecutor((CommandExecutor)new CMD_Spawn());
/*  86 */     getCommand("lobbyc").setExecutor((CommandExecutor)new CMD_LobbyC());
/*     */   }
/*     */   
/*     */   private void registerEvents() {
/*  90 */     PluginManager pm = Bukkit.getPluginManager();
/*  91 */     pm.registerEvents((Listener)new EventManager(), (Plugin)this);
/*     */   }
/*     */   
/*     */   public void registerConfig() {
/*  95 */     ArrayList<String> temp = new ArrayList<>();
/*     */     
/*  97 */     temp.add("&6Lore 1");
/*  98 */     temp.add("&6Lore 2");
/*     */     
/* 100 */     getConfig().options().copyDefaults(true);
/* 101 */     saveConfig();
/*     */     
/* 103 */     this.locations = new FileManager("plugins/" + getName(), "locations.yml");
/* 104 */     this.fm_compass = new FileManager("plugins/" + getName(), "compass.yml");
/*     */     
/* 106 */     this.messages = new FileManager("plugins/" + getName(), getConfig().getString("message-file"));
/* 107 */     this.messages.addDefault("error.no-permission", "%prefix%&cYou don't have permission to do this.%break%%permission%");
/* 108 */     this.messages.addDefault("error.incorrect-syntax", "%prefix%&cIncorrect syntax! Try &6%command%&c!");
/* 109 */     this.messages.addDefault("error.destroy-lobby", "%prefix%&cYou can't do this here!");
/* 110 */     this.messages.addDefault("error.player-not-found", "%prefix%&6%target% &cnot found!");
/* 111 */     this.messages.addDefault("error.lobby-command-disabled", "%prefix%&cThis command is disabled in this world!");
/* 112 */     this.messages.addDefault("error.spawn-not-set", "%prefix%&cThe spawn wasn't set yet!");
/*     */ 
/*     */     
/* 115 */     this.messages.addDefault("join.title", "&aWelcome &6%player%");
/* 116 */     this.messages.addDefault("join.subtitle", "&2Have fun on &6my.server.net&2!");
/* 117 */     this.messages.addDefault("join.public-message", "&6%player% &ajoined the server!");
/* 118 */     this.messages.addDefault("join.private-message", "%prefix%&aWelcome on my brand new server!");
/*     */     
/* 120 */     this.messages.addDefault("quit", "&6%player% &cleft the server!");
/*     */     
/* 122 */     this.messages.addDefault("build-mode.enable", "%prefix%&aYou can build now!");
/* 123 */     this.messages.addDefault("build-mode.disable", "%prefix%&cYou can't build anymore!");
/* 124 */     this.messages.addDefault("build-mode.other.enable", "%prefix%&6%target% &acan now build!");
/* 125 */     this.messages.addDefault("build-mode.other.disable", "%prefix%&6%target% &6can't build anymore!");
/*     */     
/* 127 */     this.messages.addDefault("spawn.telport-to-spawn", "%prefix%&aTeleporting...");
/* 128 */     this.messages.addDefault("spawn.spawn-set", "%prefix%&aSpawn set!");
/*     */     
/* 130 */     this.messages.addDefault("fakt-signs.find", "%prefix%&6You find a fakt sign!");
/* 131 */     this.messages.addDefault("fakt-signs.create", "%prefix%&aYou created a new fakt sign!");
/*     */     
/* 133 */     this.messages.options().copyDefaults(true);
/* 134 */     this.messages.save();
/*     */     
/* 136 */     this.fm_compass.options().copyDefaults(true);
/* 137 */     this.fm_compass.addDefault("get-on-join", Boolean.valueOf(true));
/* 138 */     this.fm_compass.addDefault("slot", Integer.valueOf(4));
/* 139 */     this.fm_compass.addDefault("compass.material", "COMPASS");
/* 140 */     this.fm_compass.addDefault("compass.display-name", "&6Lobby&eCompass");
/* 141 */     this.fm_compass.addDefault("compass.lore", new ArrayList<>(temp));
/* 142 */     this.fm_compass.addDefault("gui.name", "&6&l&nLobby&e&l&nCompass");
/* 143 */     this.fm_compass.addDefault("gui.rows", Integer.valueOf(5));
/* 144 */     temp.clear();
/* 145 */     temp.add("&7You can change this");
/* 146 */     temp.add("&7in the &6compass.yml &7file!");
/* 147 */     if (!this.fm_compass.getConfigurationSection("gui").getKeys(false).contains("items")) {
/* 148 */       this.fm_compass.addDefault("gui.items.testItem.material", "MAGMA_CREAM");
/* 149 */       this.fm_compass.addDefault("gui.items.testItem.display-name", "&4Test&5Item");
/* 150 */       this.fm_compass.addDefault("gui.items.testItem.lore", new ArrayList<>(temp));
/* 151 */       this.fm_compass.addDefault("gui.items.testItem.slot", Integer.valueOf(22));
/* 152 */       this.fm_compass.addDefault("gui.items.testItem.command-execute-by-player", Boolean.valueOf(false));
/* 153 */       this.fm_compass.addDefault("gui.items.testItem.command", "tell %player% change the commands in 'compass.yml'!");
/*     */     } 
/* 155 */     temp.clear();
/* 156 */     temp.add("DURABILITY:1");
/* 157 */     this.fm_compass.addDefault("compass.enchantments", new ArrayList<>(temp));
/* 158 */     this.fm_compass.save();
/*     */ 
/*     */     
/* 161 */     this.prefix = getConfig().getString("prefix");
/* 162 */     this.prefix = color(this.prefix);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Main getMain() {
/* 167 */     return main;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 171 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public ConsoleCommandSender getConsole() {
/* 175 */     return this.console;
/*     */   }
/*     */   
/*     */   public String color(String str) {
/* 179 */     return ChatColor.translateAlternateColorCodes('&', str);
/*     */   }
/*     */   public String translateVars(String msg) {
/* 182 */     msg = msg.replace("%prefix%", getPrefix());
/* 183 */     msg = msg.replace("%break%", "\n");
/* 184 */     msg = color(msg);
/* 185 */     return msg;
/*     */   }
/*     */   public String translateVars(String msg, Player player) {
/* 188 */     msg = msg.replace("%player%", player.getName());
/* 189 */     msg = msg.replace("%prefix%", getPrefix());
/* 190 */     msg = msg.replace("%break%", "\n");
/* 191 */     msg = color(msg);
/* 192 */     return msg;
/*     */   }
/*     */   public String translateVars(String msg, Player player, String command, String permission) {
/* 195 */     msg = msg.replace("%player%", player.getName());
/* 196 */     msg = msg.replace("%prefix%", getPrefix());
/* 197 */     msg = msg.replace("%command%", command);
/* 198 */     msg = msg.replace("%permission%", permission);
/* 199 */     msg = msg.replace("%break%", "\n");
/* 200 */     msg = color(msg);
/* 201 */     return msg;
/*     */   }
/*     */   public FileManager getLocations() {
/* 204 */     return this.locations;
/*     */   }
/*     */   public FileManager getMessages() {
/* 207 */     return this.messages;
/*     */   }
/*     */   public ArrayList<Player> getBuildPlayer() {
/* 210 */     return this.buildPlayer;
/*     */   }
/*     */   public ITEM_Compass getItem_Compass() {
/* 213 */     return this.item_compass;
/*     */   }
/*     */   public FileManager getFileManagerCompass() {
/* 216 */     return this.fm_compass;
/*     */   }
/*     */   public GUI_Compass getGui_Compass() {
/* 219 */     return this.gui_compass;
/*     */   }
/*     */ }


/* Location:              C:\Users\Nicklas\Desktop\LobbySystem.jar!\de\n1ck145\lobbySystem\main\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */