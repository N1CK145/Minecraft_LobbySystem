package de.n1ck145.lobbySystem.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.n1ck145.lobbySystem.GUI.GUI_Compass;
import de.n1ck145.lobbySystem.commands.CMD_Build;
import de.n1ck145.lobbySystem.commands.CMD_LobbyC;
import de.n1ck145.lobbySystem.commands.CMD_Message;
import de.n1ck145.lobbySystem.commands.CMD_Spawn;
import de.n1ck145.lobbySystem.commands.CMD_Warp;
import de.n1ck145.lobbySystem.items.ITEM_Compass;
import de.n1ck145.lobbySystem.listener.EventManager;
import de.n1ck145.lobbySystem.utils.API_MySQL;
import de.n1ck145.lobbySystem.utils.FileManager;
import net.milkbowl.vault.economy.Economy;

public class Main
extends JavaPlugin {
    private static Main main;
    private ConsoleCommandSender console;
    private String prefix;
    private FileManager locations;
    private FileManager messages;
    private FileManager fm_compass;
    private ArrayList < Player > buildPlayer;
    private ITEM_Compass item_compass;
    private GUI_Compass gui_compass;
    private static Economy econ;

    public void onEnable() {
        main = this;
        this.console = Bukkit.getConsoleSender();
        this.buildPlayer = new ArrayList < > ();

        registerConfig();
        registerEvents();
        registerCommands();
        if(!setupEconomy())
        	console.sendMessage(prefix + "�cNo valid economy plugin found! Please import a Vault compatible economy plugin!");
        else
        	console.sendMessage(prefix + "�aVault was succsessfully set up!");

        this.item_compass = new ITEM_Compass(Material.getMaterial(this.fm_compass.getString("compass.material")));
        this.gui_compass = new GUI_Compass();

        this.console.sendMessage("�8[�b" + getName() + "�8] �aPlugin ready!");
    }


    public void onDisable() {
        API_MySQL.disconnect();
        this.console.sendMessage(prefix + "�4Plugin disabled");
    }

    private void registerCommands() {
    	getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    	getCommand("build").setExecutor(new CMD_Build());
        getCommand("spawn").setExecutor(new CMD_Spawn());
        getCommand("lobbyc").setExecutor(new CMD_LobbyC());
        getCommand("message").setExecutor(new CMD_Message());
        getCommand("warp").setExecutor(new CMD_Warp());
        getCommand("setwarp").setExecutor(new CMD_Warp());
        
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents((Listener) new EventManager(), (Plugin) this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    
    public void registerConfig() {
        ArrayList < String > temp = new ArrayList < > ();

        temp.add("&6Lore 1");
        temp.add("&6Lore 2");

        getConfig().options().copyDefaults(true);
        saveConfig();

        this.locations = new FileManager("plugins/" + getName(), "locations.yml");
        this.fm_compass = new FileManager("plugins/" + getName(), "compass.yml");

        this.messages = new FileManager("plugins/" + getName(), getConfig().getString("message-file"));
        this.messages.addDefault("error.no-permission", "%prefix%&cYou don't have permission to do this.%break%%permission%");
        this.messages.addDefault("error.incorrect-syntax", "%prefix%&cIncorrect syntax! Try &6%command%&c!");
        this.messages.addDefault("error.destroy-lobby", "%prefix%&cYou can't do this here!");
        this.messages.addDefault("error.player-not-found", "%prefix%&6%target% &cnot found!");
        this.messages.addDefault("error.lobby-command-disabled", "%prefix%&cThis command is disabled in this world!");
        this.messages.addDefault("error.spawn-not-set", "%prefix%&cThe spawn wasn't set yet!");
        this.messages.addDefault("error.can-not-cast-number", "%prefix%&6%input% is not a valid number!");


        this.messages.addDefault("join.title", "&aWelcome &6%player%");
        this.messages.addDefault("join.subtitle", "&2Have fun on &6my.server.net&2!");
        this.messages.addDefault("join.public-message", "&6%player% &ajoined the server!");
        this.messages.addDefault("join.private-message", "%prefix%&aWelcome on my brand new server!");

        this.messages.addDefault("quit", "&6%player% &cleft the server!");

        this.messages.addDefault("build-mode.enable", "%prefix%&aYou can build now!");
        this.messages.addDefault("build-mode.disable", "%prefix%&cYou can't build anymore!");
        this.messages.addDefault("build-mode.other.enable", "%prefix%&6%target% &acan now build!");
        this.messages.addDefault("build-mode.other.disable", "%prefix%&6%target% &6can't build anymore!");

        this.messages.addDefault("spawn.telport-to-spawn", "%prefix%&aTeleporting...");
        this.messages.addDefault("spawn.spawn-set", "%prefix%&aSpawn set!");
        
        this.messages.addDefault("cmd.message.receive", "&aMessage from %sender%: &r%message%");
        this.messages.addDefault("cmd.message.send", "&aMessage send to %target%:%break%&r%message%");
        this.messages.addDefault("cmd.warp.invalid", "%prefix%&cInvalid warp!");
        this.messages.addDefault("cmd.warp.success", "%prefix%&aTeleporting to %warp%.");
        this.messages.addDefault("cmd.setwarp.invalid", "%prefix%&cWarp already exists!");
        this.messages.addDefault("cmd.setwarp.success", "%prefix%&aWarp %warp% saved!");

        this.messages.options().copyDefaults(true);
        this.messages.save();

        this.fm_compass.options().copyDefaults(true);
        this.fm_compass.addDefault("get-on-join", Boolean.valueOf(true));
        this.fm_compass.addDefault("slot", Integer.valueOf(4));
        this.fm_compass.addDefault("compass.material", "COMPASS");
        this.fm_compass.addDefault("compass.display-name", "&6Lobby&eCompass");
        this.fm_compass.addDefault("compass.lore", new ArrayList < > (temp));
        this.fm_compass.addDefault("gui.name", "&6&l&nLobby&e&l&nCompass");
        this.fm_compass.addDefault("gui.rows", Integer.valueOf(5));
        temp.clear();
        temp.add("&7You can change this");
        temp.add("&7in the &6compass.yml &7file!");
        if (!this.fm_compass.getConfigurationSection("gui").getKeys(false).contains("items")) {
            this.fm_compass.addDefault("gui.items.testItem.material", "MAGMA_CREAM");
            this.fm_compass.addDefault("gui.items.testItem.display-name", "&4Test&5Item");
            this.fm_compass.addDefault("gui.items.testItem.lore", new ArrayList < > (temp));
            this.fm_compass.addDefault("gui.items.testItem.slot", Integer.valueOf(22));
            this.fm_compass.addDefault("gui.items.testItem.command-execute-by-player", Boolean.valueOf(false));
            this.fm_compass.addDefault("gui.items.testItem.command", "tell %player% change the commands in 'compass.yml'!");
        }
        temp.clear();
        temp.add("DURABILITY:1");
        this.fm_compass.addDefault("compass.enchantments", new ArrayList <> (temp));
        this.fm_compass.save();


        this.prefix = getConfig().getString("prefix");
        this.prefix = color(this.prefix);
    }

    public static Main getMain() {
        return main;
    }

    public String getPrefix() {
        return this.prefix;
    }
    
    public static Economy getEconomy() {
		return econ;
	}

    public ConsoleCommandSender getConsole() {
        return this.console;
    }

    public String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    public String translateVars(String msg) {
        msg = msg.replace("%prefix%", getPrefix());
        msg = msg.replace("%break%", "\n");
        msg = color(msg);
        return msg;
    }
    public String translateVars(String msg, String player) {
        msg = msg.replace("%player%", player);
        msg = msg.replace("%prefix%", getPrefix());
        msg = msg.replace("%break%", "\n");
        msg = color(msg);
        return msg;
    }
    public String translateVars(String msg, String player, String command, String permission) {
        msg = msg.replace("%player%", player);
        msg = msg.replace("%prefix%", getPrefix());
        msg = msg.replace("%command%", command);
        msg = msg.replace("%permission%", permission);
        msg = msg.replace("%break%", "\n");
        msg = color(msg);
        return msg;
    }
    public String translateVars(String msg, String command, String permission) {
        msg = msg.replace("%prefix%", getPrefix());
        msg = msg.replace("%command%", command);
        msg = msg.replace("%permission%", permission);
        msg = msg.replace("%break%", "\n");
        msg = color(msg);
        return msg;
    }
    public String getErrorMessagePlayerNotFount(String executor, String target) {
    	return translateVars(messages.getString("error.player-not-found"), executor).replace("%target%", target);
    }
    public String getErrorMessageNoPermission(String player, String permission) {
    	return translateVars(messages.getString("error.no-permission"), player).replace("%permission%", permission);
    }
    public String getErrorMessageWrongSyntax(String player, String cmd, String permission) {
    	return translateVars(messages.getString("error.incorrect-syntax"), player, cmd, permission);
    }
    public String getErrorMessageCanNotCastNumber(String executor, String input) {
    	return translateVars(messages.getString("error.can-not-cast-number"), executor).replace("%input%", input);
    }
    
    public FileManager getLocations() {
        return this.locations;
    }
    public FileManager getMessages() {
        return this.messages;
    }
    public ArrayList <Player> getBuildPlayer() {
        return this.buildPlayer;
    }
    public ITEM_Compass getItem_Compass() {
        return this.item_compass;
    }
    public FileManager getFileManagerCompass() {
        return this.fm_compass;
    }
    public GUI_Compass getGui_Compass() {
        return this.gui_compass;
    }
}