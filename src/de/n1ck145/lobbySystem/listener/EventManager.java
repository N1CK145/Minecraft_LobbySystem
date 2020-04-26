package de.n1ck145.lobbySystem.listener;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.n1ck145.lobbySystem.GUI.GUI_Compass;
import de.n1ck145.lobbySystem.main.Main;
import de.n1ck145.lobbySystem.utils.FileManager;

public class EventManager implements Listener {
    private Main main = Main.getMain();
    private FileManager messages = this.main.getMessages();
    private FileManager compass = this.main.getFileManagerCompass();

    @EventHandler
    public void joinMessage(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendTitle(this.main.translateVars(this.messages.getString("join.title"), p.getName()), this.main.translateVars(this.messages.getString("join.subtitle"), p.getName()));
        e.setJoinMessage(this.main.translateVars(this.messages.getString("join.public-message"), p.getName()));
        p.sendMessage(this.main.translateVars(this.messages.getString("join.private-message")));
    }

    @EventHandler
    public void quitMessage(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(this.main.translateVars(this.messages.getString("quit"), p.getName()));
    }

    @EventHandler
    public void onLobbyGrief(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!this.main.getConfig().getStringList("system-enable-worlds").contains(p.getWorld().getName()))
            return;
        if (this.main.getBuildPlayer().contains(p))
            return;
        e.setCancelled(true);
        p.sendMessage(this.main.translateVars(this.messages.getString("error.destroy-lobby"), p.getName()));
    }

    @EventHandler
    public void onLobbyGrief(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!this.main.getConfig().getStringList("system-enable-worlds").contains(e.getPlayer().getWorld().getName()))
            return;
        if (this.main.getBuildPlayer().contains(p))
            return;
        e.setCancelled(true);
        p.sendMessage(this.main.translateVars(this.messages.getString("error.destroy-lobby"), p.getName()));
    }

    @EventHandler
    public void removeCanBuild(PlayerQuitEvent e) {
        if (!this.main.getBuildPlayer().contains(e.getPlayer()))
            return;
        this.main.getBuildPlayer().remove(e.getPlayer());
    }

    @EventHandler
    public void givePlayerLobbyCompass(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!main.getConfig().getStringList("system-enable-worlds").contains(e.getPlayer().getWorld().getName()))
    		return;
        if (!this.main.getFileManagerCompass().getBoolean("get-on-join"))
            return;
        int slot = this.main.getFileManagerCompass().getInt("slot");
        p.getInventory().setItem(slot, this.main.getItem_Compass().get());
    }

    @EventHandler
    public void givePlayerLobbyCompass(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        
        if(main.getConfig().getBoolean("clear-players-inv-on-world-change"))
        	p.getInventory().clear();
        if(!main.getConfig().getStringList("system-enable-worlds").contains(e.getPlayer().getWorld().getName()))
    		return;
        if(main.getConfig().getStringList("system-enable-worlds").contains(p.getWorld().getName())) {
        	if (!this.main.getFileManagerCompass().getBoolean("get-on-join"))
        		return;
        	int slot = this.main.getFileManagerCompass().getInt("slot");        	
        	p.getInventory().setItem(slot, this.main.getItem_Compass().get());
        }
    }

    @EventHandler
    public void playerMoveItemInInventory(InventoryClickEvent e) {
    	if(!main.getConfig().getStringList("system-enable-worlds").contains(e.getWhoClicked().getWorld().getName()))
    		return;
        if (e.getClickedInventory() == null)
            return;
        if (this.main.getBuildPlayer().contains(e.getClickedInventory().getHolder()))
            return;
        if (this.main.getConfig().getBoolean("can-move-items-in-inventory"))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void playerDropItem(PlayerDropItemEvent e) {
    	if(!main.getConfig().getStringList("system-enable-worlds").contains(e.getPlayer().getWorld().getName()))
    		return;
        if (this.main.getConfig().getBoolean("can-drop-items"))
            return;
        if (this.main.getBuildPlayer().contains(e.getPlayer()))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void openCompassGui(PlayerInteractEvent e) {
    	if(!main.getConfig().getStringList("system-enable-worlds").contains(e.getPlayer().getWorld().getName()))
    		return;
        if (!e.getPlayer().getItemInHand().equals(this.main.getItem_Compass().get()))
            return;
        if (e.getAction() == Action.PHYSICAL)
            return;
        Inventory inv = GUI_Compass.getInventory();
        e.getPlayer().openInventory(inv);
    }

    @EventHandler
    public void food(FoodLevelChangeEvent e) {
    	if(e.getEntityType() != EntityType.PLAYER)
    		return;
    	if(!main.getConfig().getStringList("system-enable-worlds").contains(e.getEntity().getWorld().getName()))
    		return;
        if (this.main.getConfig().getBoolean("change-foodlevel"))
            return;
        if (e.getFoodLevel() < ((Player) e.getEntity()).getFoodLevel())
            e.setCancelled(true);
    }

    @EventHandler
    public void getDamage(EntityDamageEvent e) {
    	if(e.getEntityType() != EntityType.PLAYER)
    		return;
    	if(!main.getConfig().getStringList("system-enable-worlds").contains(e.getEntity().getWorld().getName()))
    		return;
        if (this.main.getConfig().getBoolean("player-get-damage"))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void compassGuiClick(InventoryClickEvent e) {
        if (!e.getInventory().getName().equals(this.main.translateVars(this.main.getFileManagerCompass().getString("gui.name"))))
            return;
        for (String s: this.compass.getConfigurationSection("gui.items").getKeys(false)) {
        	Player p = (Player)e.getWhoClicked();
            String path = "gui.items." + s + ".";
            int slot = this.compass.getInt(String.valueOf(path) + "slot");
            if (e.getSlot() == slot) {
            	String command = compass.getString(path + "command");
                if (this.compass.getBoolean(String.valueOf(path) + "command-execute-by-player")) {
                	if(command.toLowerCase().startsWith("server ")) {
                		ByteArrayDataOutput out = ByteStreams.newDataOutput();
                		out.writeUTF("Connect");
                		out.writeUTF(command.split(" ")[1]);
                		p.sendPluginMessage(main, "BungeeCord", out.toByteArray());
                	}else
                		Bukkit.dispatchCommand((CommandSender) p, command);
                } else
                    Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), command);
                return;
            }
        }
    }
    @EventHandler
    public void changeWeather(WeatherChangeEvent e) {
    	if(!main.getConfig().getStringList("system-enable-worlds").contains(e.getWorld().getName()))
    		return;
        if (this.main.getConfig().getBoolean("change-weather"))
            return;
        e.setCancelled(true);
    }
}